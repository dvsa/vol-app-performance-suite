package scenarios;

import io.gatling.javaapi.core.ScenarioBuilder;
import test.TestSetup;

import static io.gatling.javaapi.core.CoreDsl.*;
import static journeySteps.SearchJourneySteps.*;

public class ExternalOperatorSearchUser {

    public static ScenarioBuilder companySearch() {
        return scenario("Search for an operator")
                .feed(TestSetup.getTradingNameFeeder())
                .exec(getSearchForBusOperatorPage)
                .pause(2)
                .exec(searchForBusOperatorPage)
                .pause(2);
    }
}