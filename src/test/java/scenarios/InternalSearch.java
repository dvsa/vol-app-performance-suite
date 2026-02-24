package scenarios;

import io.gatling.javaapi.core.ScenarioBuilder;
import test.TestSetup;

import static io.gatling.javaapi.core.CoreDsl.*;
import static journeySteps.SearchJourneySteps.*;

public class InternalSearch {

    public static ScenarioBuilder internalWorkerLogin() {
        return scenario("Internal Worker Login and Search")
                .feed(TestSetup.getInternalUserFeeder())
                .feed(TestSetup.getTradingNameFeeder())
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