package scenarios;

import activesupport.database.utils.VolDatabaseUtils;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.FeederBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.gatling.javaapi.core.CoreDsl.*;
import static journeySteps.SearchJourneySteps.*;

public class ExternalOperatorSearchUser {
    private static final Logger LOGGER = LogManager.getLogger(ExternalOperatorSearchUser.class);

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

    public static ScenarioBuilder companySearch() {
        return scenario("Search for an operator")
                .feed(createTradingNameFeeder())
                .exec(getSearchForBusOperatorPage)
                .pause(2)
                .exec(searchForBusOperatorPage)
                .pause(2);
    }
}