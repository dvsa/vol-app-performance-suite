package simulations;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static utils.SetUp.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import scenarios.CreateApplication;


public class CreateApplicationSimulation extends Simulation {

    private static final HttpProtocolBuilder httpConfiguration = http
            .baseUrl(baseURL)
            .disableAutoReferer()
            .acceptEncodingHeader("gzip, deflate")
            .acceptLanguageHeader("en-GB,en;q=0.5")
            .upgradeInsecureRequestsHeader("1")
            .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:97.0) Gecko/20100101 Firefox/97.0")
            .disableWarmUp()
            .disableCaching()
            .silentResources()
            .perUserNameResolution();

    private static final PopulationBuilder loginAndCreateApp;

    static {
        String testType = typeofTest != null ? typeofTest : "load";


        switch (testType) {
            case "load":
                loginAndCreateApp = CreateApplication.selfServiceApplicationRegistration().injectOpen(
                        atOnceUsers(users),
                        rampUsers(rampUp).during(rampDurationInMin)
                );
                break;
            case "soak":
                loginAndCreateApp = CreateApplication.selfServiceApplicationRegistration().injectOpen(
                        rampUsers(users).during(rampUp)
                ).throttle(
                        reachRps(3).in(60),
                        holdFor(rampDurationInMin)
                );
                break;
            case "stress":
                loginAndCreateApp = CreateApplication.selfServiceApplicationRegistration().injectOpen(
                        incrementUsersPerSec(users)
                                .times(5)
                                .eachLevelLasting(10)
                                .separatedByRampsLasting(10)
                                .startingFrom(10)
                );
                break;
            default:
                loginAndCreateApp = CreateApplication.selfServiceApplicationRegistration().injectOpen(
                        atOnceUsers(users)
                );
        }
    }

    {
        setUp(loginAndCreateApp)
                .protocols(httpConfiguration)
                .assertions(global().failedRequests().count().is(0L));
    }
}