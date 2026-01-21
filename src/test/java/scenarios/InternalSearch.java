package scenarios;

import io.gatling.javaapi.core.FeederBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;

import static io.gatling.javaapi.core.CoreDsl.csv;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static journeySteps.SearchJourneySteps.*;

public class InternalSearch {

    static FeederBuilder<String> feeder = csv("InternalLoginId.csv").random();

    public static ScenarioBuilder internalWorkerLogin() {
        return scenario("Login as an internal case worker")
                .feed(feeder)
                .exec(getToLogin)
                .pause(30)
                .exec(searchAndLogin)
                .pause(20)
                .exec(navigateToLandingPage)
                .pause(30)
                .exec(search)
                .pause(20);
    }
}
