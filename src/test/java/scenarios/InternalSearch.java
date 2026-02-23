package scenarios;

import activesupport.database.utils.VolDatabaseUtils;
import activesupport.aws.s3.SecretsManager;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.FeederBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.gatling.javaapi.core.CoreDsl.*;
import static journeySteps.SearchJourneySteps.*;

public class InternalSearch {
    private static final Logger LOGGER = LogManager.getLogger(InternalSearch.class);

    private static FeederBuilder<Object> createInternalUserFeeder() {
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
            
            return listFeeder(feederData).circular();
        } catch (Exception e) {
            LOGGER.error("Failed to load internal users", e);
            throw new RuntimeException("Failed to load internal users", e);
        }
    }

    private static FeederBuilder<Object> createTradingNameFeeder() {
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
            
            return listFeeder(feederData).circular();
        } catch (Exception e) {
            LOGGER.error("Failed to load trading names", e);
            throw new RuntimeException("Failed to load trading names", e);
        }
    }

    public static ScenarioBuilder internalWorkerLogin() {
        return scenario("Internal Worker Login and Search")
                .feed(createInternalUserFeeder())
                .feed(createTradingNameFeeder())
                .exec(getToLogin)
                .pause(2)
                .exec(searchAndLogin)
                .pause(2)
                .exec(navigateToLandingPage)
                .pause(2)
                .exec(getSearchForBusOperatorPage)
                .pause(2)
                .exec(searchForBusOperatorPage)
                .pause(2);
    }
}