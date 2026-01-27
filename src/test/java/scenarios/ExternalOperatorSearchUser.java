package scenarios;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.FeederBuilder;
import test.TestSetup;
import java.util.Map;

import static io.gatling.javaapi.core.CoreDsl.*;
import static journeySteps.SearchJourneySteps.*;

public class ExternalOperatorSearchUser {

    private static final FeederBuilder<Object> tradingNameFeeder =
            listFeeder(TestSetup.getTradingNames().stream()
                    .map(map -> (Map<String, Object>) (Map<String, ?>) map)
                    .toList()).circular();

    public static ScenarioBuilder companySearch() {
        return scenario("Search for an operator")
                .feed(tradingNameFeeder)
                .exec(getSearchForBusOperatorPage)
                .pause(2)
                .exec(searchForBusOperatorPage)
                .pause(2);
    }
}