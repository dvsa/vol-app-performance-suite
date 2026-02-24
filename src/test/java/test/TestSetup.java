package test;

import activesupport.aws.s3.SecretsManager;
import activesupport.database.utils.VolDatabaseUtils;
import activesupport.mailPit.MailPit;
import apiCalls.actions.RegisterUser;
import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.core.FeederBuilder;
import org.apache.commons.codec.net.QuotedPrintableCodec;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dvsa.testing.lib.url.utils.EnvironmentType;

import java.util.*;

public class TestSetup {
    private static final Logger LOGGER = LogManager.getLogger(TestSetup.class);

    public static void main(String[] args) {
        try {
            String env = System.getProperty("env", "qa");
            LOGGER.info("Using environment: {}", env);

            String operation = args.length > 0 ? args[0] : "create";

            switch (operation.toLowerCase()) {
                case "cleanup" -> cleanup(env);
                case "test" -> testDatabaseConnection(env);
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

    // ========================================
    // GATLING FEEDERS - CENTRALIZED
    // ========================================

    /**
     * Creates feeder for users with temporary passwords (for application creation flows)
     */
    public static FeederBuilder<Object> getTempPasswordUserFeeder() {
        String env = System.getProperty("env", "qa");
        try {
            List<Map<String, Object>> users = VolDatabaseUtils.getUsersWithTempPasswords(env);
            
            if (users.isEmpty()) {
                throw new RuntimeException("No temp password users available. Run TestSetup first.");
            }

            List<Map<String, Object>> feederData = users.stream()
                    .map(user -> {
                        Map<String, Object> record = new HashMap<>();
                        record.put("Username", user.get("Username"));
                        record.put("loginId", user.get("Username"));
                        record.put("Forename", user.get("Forename"));
                        record.put("Password", user.get("Password"));
                        record.put("emailAddress", user.get("emailAddress"));
                        return record;
                    })
                    .toList();

            LOGGER.info("Loaded {} temp password users for Gatling", feederData.size());
            return CoreDsl.listFeeder(feederData).circular();
        } catch (Exception e) {
            LOGGER.error("Failed to load temp password users", e);
            throw new RuntimeException("Failed to load users", e);
        }
    }

    /**
     * Creates feeder for internal (DVSA) users (for internal search flows)
     */
    public static FeederBuilder<Object> getInternalUserFeeder() {
        String env = System.getProperty("env", "qa");
        try {
            List<Map<String, Object>> users = VolDatabaseUtils.getInternalUsers(env);
            String defaultPassword = SecretsManager.getSecretValue("defaultPassword");
            
            List<Map<String, Object>> feederData = users.stream()
                    .map(user -> {
                        Map<String, Object> record = new HashMap<>();
                        String loginId = (String) user.get("login_id");
                        record.put("Username", loginId);
                        record.put("loginId", loginId);
                        record.put("Password", defaultPassword);
                        return record;
                    })
                    .toList();
            
            LOGGER.info("Loaded {} internal users for Gatling", feederData.size());
            return CoreDsl.listFeeder(feederData).circular();
        } catch (Exception e) {
            LOGGER.error("Failed to load internal users", e);
            throw new RuntimeException("Failed to load internal users", e);
        }
    }

    /**
     * Creates feeder for trading names (for search operations)
     */
    public static FeederBuilder<Object> getTradingNameFeeder() {
        String env = System.getProperty("env", "qa");
        try {
            List<Map<String, Object>> tradingNames = VolDatabaseUtils.getTradingNames(env);
            
            List<Map<String, Object>> feederData = tradingNames.stream()
                    .map(tradingName -> {
                        Map<String, Object> record = new HashMap<>();
                        record.put("companyName", tradingName.get("name"));
                        return record;
                    })
                    .toList();
            
            LOGGER.info("Loaded {} trading names for Gatling", feederData.size());
            return CoreDsl.listFeeder(feederData).circular();
        } catch (Exception e) {
            LOGGER.error("Failed to load trading names", e);
            // Fallback data for external search
            return CoreDsl.listFeeder(List.of(Map.of("companyName", "Eddie"))).circular();
        }
    }

    // ========================================
    // EXISTING METHODS (UNCHANGED)
    // ========================================

    private static void createUsersWithTempPasswords(String env, int userCount) {
        LOGGER.info("Creating {} users for {}", userCount, env);

        EnvironmentType environmentType = switch (env.toLowerCase()) {
            case "qa", "qualityassurance" -> EnvironmentType.QUALITY_ASSURANCE;
            case "int", "integration" -> EnvironmentType.INTEGRATION;
            case "prep", "preproduction" -> EnvironmentType.PREPRODUCTION;
            default -> EnvironmentType.QUALITY_ASSURANCE;
        };

        try {
            // Use VolDatabaseUtils for table management
            VolDatabaseUtils.createTempPasswordTable(env);
            VolDatabaseUtils.clearTempPasswords(env);

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

                    // Use VolDatabaseUtils to store temp password
                    VolDatabaseUtils.storeTempPassword(env, username, decodedPassword);

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

    private static void runGatlingTest() {
        try {
            LOGGER.info("Running Gatling test...");

            String env = System.getProperty("env", "qa");
            String site = System.getProperty("site", "ss");
            String users = System.getProperty("users", "10");
            String rampUp = System.getProperty("rampUp", "0");
            String duration = System.getProperty("duration", "0");
            String typeOfTest = System.getProperty("typeOfTest", "load");

            ProcessBuilder processBuilder = new ProcessBuilder(
                    "mvn", "gatling:test",
                    "-Denv=" + env,
                    "-Dsite=" + site,
                    "-Dusers=" + users,
                    "-DrampUp=" + rampUp,
                    "-Dduration=" + duration,
                    "-DtypeOfTest=" + typeOfTest
            );

            processBuilder.inheritIO();
            Process process = processBuilder.start();
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                LOGGER.info("✅ Gatling test completed");
                cleanup(env);
            } else {
                throw new RuntimeException("Gatling failed with exit code: " + exitCode);
            }
        } catch (Exception e) {
            LOGGER.error("Gatling execution failed", e);
            throw new RuntimeException("Gatling failed", e);
        }
    }

    private static void cleanup(String env) {
        try {
            int deleted = VolDatabaseUtils.clearTempPasswords(env);
            LOGGER.info("✅ Cleanup completed - removed {} temp passwords", deleted);
        } catch (Exception e) {
            LOGGER.warn("Cleanup failed", e);
        }
    }

    private static void testDatabaseConnection(String env) {
        if (VolDatabaseUtils.testConnection(env)) {
            LOGGER.info("✅ Database connection successful for environment: {}", env);
        } else {
            LOGGER.error("❌ Database connection failed for environment: {}", env);
        }
    }
}