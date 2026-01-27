package utils;

import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.core.FeederBuilder;
import test.TestSetup;
import java.util.*;

public class FeederConfig {

    public static FeederBuilder<Object> getFeeder() {
        List<Map<String, String>> users = TestSetup.getAllUsers();

        if (users.isEmpty()) {
            throw new RuntimeException("No users available. Run TestSetup first.");
        }

        List<Map<String, Object>> feederData = users.stream()
                .map(user -> {
                    Map<String, Object> record = new HashMap<>();
                    record.put("Username", user.get("Username"));
                    record.put("loginId", user.get("loginId"));
                    record.put("Forename", user.get("Forename"));
                    record.put("Password", user.get("Password"));
                    record.put("emailAddress", user.get("emailAddress"));
                    return record;
                })
                .toList();

        System.out.println("Loaded " + feederData.size() + " users");
        return CoreDsl.listFeeder(feederData).circular();
    }
}