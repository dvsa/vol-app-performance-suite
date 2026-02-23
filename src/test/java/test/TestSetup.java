package test;

import activesupport.database.utils.VolDatabaseUtils;
import activesupport.mailPit.MailPit;
import apiCalls.actions.RegisterUser;
import org.apache.commons.codec.net.QuotedPrintableCodec;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dvsa.testing.lib.url.utils.EnvironmentType;

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