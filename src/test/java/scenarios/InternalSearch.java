package scenarios;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.FeederBuilder;
import test.TestSetup;
import java.util.Map;

import static io.gatling.javaapi.core.CoreDsl.*;
import static journeySteps.SearchJourneySteps.*;

public class InternalSearch {

    private static final FeederBuilder<Object> internalUserFeeder =
            listFeeder(TestSetup.getInternalUsers().stream()
                    .map(map -> (Map<String, Object>) (Map<String, ?>) map)
                    .toList()).circular();

    private static final FeederBuilder<Object> tradingNameFeeder =
            listFeeder(TestSetup.getTradingNames().stream()
                    .map(map -> (Map<String, Object>) (Map<String, ?>) map)
                    .toList()).circular();

    public static ScenarioBuilder internalWorkerLogin() {
        return scenario("Internal Worker Login and Search")
                .feed(internalUserFeeder)
                .feed(tradingNameFeeder)
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