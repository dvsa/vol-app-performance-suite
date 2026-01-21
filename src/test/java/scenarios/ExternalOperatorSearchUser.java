package scenarios;

import io.gatling.javaapi.core.ScenarioBuilder;

import static io.gatling.javaapi.core.CoreDsl.scenario;
import static journeySteps.SearchJourneySteps.*;

public class ExternalOperatorSearchUser {

    public static ScenarioBuilder companySearch() {
        return scenario("Search for an operator")
                .exec(getSearchForBusOperatorPage)
                .pause(30)
                .exec(searchForBusOperatorPage)
                .pause(20);
    }
}