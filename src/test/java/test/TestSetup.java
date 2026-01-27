package test;

import activesupport.aws.s3.SecretsManager;
import activesupport.mailPit.MailPit;
import apiCalls.actions.RegisterUser;
import org.apache.commons.codec.net.QuotedPrintableCodec;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dvsa.testing.lib.url.utils.EnvironmentType;
import utils.SQLquery;

import java.sql.*;
import java.util.*;

public class TestSetup {
    private static final Logger LOGGER = LogManager.getLogger(TestSetup.class);
    private static String dbUrl;
    private static String dbUsername;
    private static String dbPassword;

    static {
        try {
            dbUsername = SecretsManager.getSecretValue("dbUsername");
            dbPassword = SecretsManager.getSecretValue("dbPassword");
            LOGGER.info("Database credentials loaded from Secrets Manager");
        } catch (Exception e) {
            LOGGER.error("Failed to get DB credentials", e);
            dbUsername = "fallback_user";
            dbPassword = "fallback_password";
        }
    }

    public static void main(String[] args) {
        try {
            String env = System.getProperty("env", "qa");
            dbUrl = getDatabaseUrl(env);
            LOGGER.info("Using database URL for environment: {}", env);

            String operation = args.length > 0 ? args[0] : "create";

            switch (operation.toLowerCase()) {
                case "cleanup" -> cleanup();
                case "test" -> testDatabaseConnection();
                default -> {
                    int userCount = Integer.parseInt(System.getProperty("users", "10"));
                    createUsersWithTempPasswords(env, userCount);
                    runGatlingTest();
                }
            }
            System.exit(0);
        } catch (Exception e) {
            LOGGER.error("Execution failed", e);
            System.exit(1);
        }
    }

    private static String getDatabaseUrl(String env) {
        String baseParams = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

        return switch (env.toLowerCase()) {
            case "qa", "qualityassurance" ->
                    "jdbc:mysql://olcsdb-rds.qa.olcs.dev-dvsacloud.uk:3306/OLCS_RDS_OLCSDB" + baseParams;
            case "int", "integration" ->
                    "jdbc:mysql://olcsdb-rds.int.olcs.dev-dvsacloud.uk:3306/OLCS_RDS_OLCSDB" + baseParams;
            case "prep", "preproduction" ->
                    "jdbc:mysql://olcsdb-rds.prep.olcs.dev-dvsacloud.uk:3306/OLCS_RDS_OLCSDB" + baseParams;
            default -> {
                LOGGER.warn("Unknown environment: {}. Defaulting to QA", env);
                yield "jdbc:mysql://olcsdb-rds.qa.olcs.dev-dvsacloud.uk:3306/OLCS_RDS_OLCSDB" + baseParams;
            }
        };
    }

    private static void createUsersWithTempPasswords(String env, int userCount) {
        LOGGER.info("Creating {} users for {}", userCount, env);

        EnvironmentType environmentType = switch (env.toLowerCase()) {
            case "qa", "qualityassurance" -> EnvironmentType.QUALITY_ASSURANCE;
            case "int", "integration" -> EnvironmentType.INTEGRATION;
            case "prep", "preproduction" -> EnvironmentType.PREPRODUCTION;
            default -> EnvironmentType.QUALITY_ASSURANCE;
        };

        try {
            createTempPasswordTable();
            clearTempPasswords();

            MailPit mailPit = new MailPit(environmentType);
            QuotedPrintableCodec codec = new QuotedPrintableCodec();

            for (int i = 0; i < userCount; i++) {
                try {
                    LOGGER.info("Creating user {}/{}", i + 1, userCount);

                    RegisterUser registerUser = new RegisterUser();
                    registerUser.registerUser();

                    String username = registerUser.getUserName();
                    String tempPassword = mailPit.retrieveTempPassword(registerUser.getEmailAddress());
                    String decodedPassword = codec.decode(tempPassword);

                    storeTempPassword(username, decodedPassword);

                    LOGGER.info("✅ Created: {}", username);
                    Thread.sleep(200);
                } catch (Exception e) {
                    LOGGER.warn("Failed user {}: {}", i + 1, e.getMessage());
                }
            }

            LOGGER.info("✅ Created {} users with temp passwords stored in database", userCount);

        } catch (Exception e) {
            LOGGER.error("Failed to create users", e);
            throw new RuntimeException(e);
        }
    }

    private static void createTempPasswordTable() {
        try (Connection con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement stmt = con.prepareStatement(SQLquery.createTempPasswordTable())) {
            stmt.executeUpdate();
            LOGGER.info("Temp password table ready");
        } catch (SQLException e) {
            LOGGER.error("Failed to create temp password table", e);
            throw new RuntimeException(e);
        }
    }

    private static void clearTempPasswords() {
        try (Connection con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement stmt = con.prepareStatement(SQLquery.clearTempPasswords())) {
            int deleted = stmt.executeUpdate();
            LOGGER.info("Cleared {} existing temp passwords", deleted);
        } catch (SQLException e) {
            LOGGER.error("Failed to clear temp passwords", e);
        }
    }

    private static void storeTempPassword(String username, String tempPassword) {
        try (Connection con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement stmt = con.prepareStatement(SQLquery.insertTempPassword())) {
            stmt.setString(1, username);
            stmt.setString(2, tempPassword);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Failed to store temp password for user: {}", username, e);
        }
    }

    private static void runGatlingTest() {
        try {
            LOGGER.info("Running Gatling test...");

            String simulationClass = System.getProperty("gatling.simulationClass", "simulations.CreateApplicationSimulation");
            String env = System.getProperty("env", "qa");
            String users = System.getProperty("users", "10");

            ProcessBuilder processBuilder = new ProcessBuilder(
                    "mvn", "gatling:test",
                    "-Dgatling.simulationClass=" + simulationClass,
                    "-Denv=" + env,
                    "-Dsite=ss",
                    "-Dusers=" + users,
                    "-DrampUp=0",
                    "-Dduration=0",
                    "-DtypeOfTest=load"
            );

            processBuilder.inheritIO();
            Process process = processBuilder.start();
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                LOGGER.info("✅ Gatling test completed");
                cleanup();
            } else {
                throw new RuntimeException("Gatling failed with exit code: " + exitCode);
            }
        } catch (Exception e) {
            LOGGER.error("Gatling execution failed", e);
            throw new RuntimeException("Gatling failed", e);
        }
    }

    public static List<Map<String, String>> getAllUsers() {
        List<Map<String, String>> users = new ArrayList<>();

        // Get current environment and set database URL
        String env = System.getProperty("env", "qa");
        String currentDbUrl = getDatabaseUrl(env);

        try (Connection con = DriverManager.getConnection(currentDbUrl, dbUsername, dbPassword);
             PreparedStatement stmt = con.prepareStatement(SQLquery.getUsersWithTempPasswords());
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, String> user = new HashMap<>();
                user.put("Username", rs.getString("Username"));
                user.put("loginId", rs.getString("Username"));
                user.put("Forename", rs.getString("Forename"));
                user.put("familyName", rs.getString("familyName"));
                user.put("emailAddress", rs.getString("emailAddress"));
                user.put("Password", rs.getString("Password"));
                user.put("userId", rs.getString("userId"));
                users.add(user);
            }

            LOGGER.info("Retrieved {} users from database", users.size());
        } catch (Exception e) {
            LOGGER.error("Failed to retrieve users", e);
        }

        return users;
    }

    public static List<Map<String, String>> getTradingNames() {
        List<Map<String, String>> tradingNames = new ArrayList<>();

        String env = System.getProperty("env", "qa");
        String currentDbUrl = getDatabaseUrl(env);

        try (Connection con = DriverManager.getConnection(currentDbUrl, dbUsername, dbPassword);
             PreparedStatement stmt = con.prepareStatement(SQLquery.getTradingNames());
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, String> company = new HashMap<>();
                company.put("companyName", rs.getString("name"));  // Changed from "trading_name" to "name"
                tradingNames.add(company);
            }

            LOGGER.info("Retrieved {} trading names from database", tradingNames.size());
        } catch (Exception e) {
            LOGGER.error("Failed to retrieve trading names", e);
            tradingNames.add(Map.of("companyName", "Eddie"));
        }

        return tradingNames;
    }

    public static List<Map<String, String>> getInternalUsers() {
        List<Map<String, String>> users = new ArrayList<>();

        String env = System.getProperty("env", "qa");
        String currentDbUrl = getDatabaseUrl(env);
        String defaultPassword = SecretsManager.getSecretValue("defaultPassword");

        try (Connection con = DriverManager.getConnection(currentDbUrl, dbUsername, dbPassword);
             PreparedStatement stmt = con.prepareStatement(SQLquery.getInternalUsers());
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, String> user = new HashMap<>();
                user.put("Username", rs.getString("login_id"));
                user.put("loginId", rs.getString("login_id"));
                user.put("Password", defaultPassword);
                users.add(user);
            }

            LOGGER.info("Retrieved {} internal users from database", users.size());
        } catch (Exception e) {
            LOGGER.error("Failed to retrieve internal users", e);
        }

        return users;
    }

    private static void cleanup() {
        try {
            try (Connection con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
                 PreparedStatement stmt = con.prepareStatement(SQLquery.clearTempPasswords())) {
                int deleted = stmt.executeUpdate();
                LOGGER.info("✅ Cleanup completed - removed {} temp passwords", deleted);
            }
        } catch (Exception e) {
            LOGGER.warn("Cleanup failed", e);
        }
    }

    private static void testDatabaseConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)) {
                LOGGER.info("✅ Database connection successful to: {}", dbUrl);
            }
        } catch (Exception e) {
            LOGGER.error("❌ Database connection failed", e);
        }
    }
}