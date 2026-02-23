package utils;

import activesupport.database.utils.VolDatabaseUtils;
import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.core.FeederBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class FeederConfig {
    private static final Logger LOGGER = LogManager.getLogger(FeederConfig.class);

    public static FeederBuilder<Object> getFeeder() {
        String env = System.getProperty("env", "qa");
        
        try {
            List<Map<String, Object>> users = VolDatabaseUtils.getUsersWithTempPasswords(env);

            if (users.isEmpty()) {
                throw new RuntimeException("No users available. Run TestSetup first.");
            }

            List<Map<String, Object>> feederData = users.stream()
                    .map(user -> {
                        Map<String, Object> record = new HashMap<>();
                        record.put("Username", user.get("Username"));
                        record.put("loginId", user.get("Username"));  // Duplicate for compatibility
                        record.put("Forename", user.get("Forename"));
                        record.put("Password", user.get("Password"));
                        record.put("emailAddress", user.get("emailAddress"));
                        return record;
                    })
                    .toList();

            LOGGER.info("Loaded {} users for Gatling feeder", feederData.size());
            return CoreDsl.listFeeder(feederData).circular();
        } catch (Exception e) {
            LOGGER.error("Failed to load users for feeder", e);
            throw new RuntimeException("Failed to load users", e);
        }
    }
}