package simulations;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static utils.SetUp.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import scenarios.CreateApplication;

import java.time.Duration;

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
        int userCount = Integer.parseInt(System.getProperty("users", "1"));
        int rampUpUsers = Integer.parseInt(System.getProperty("rampUp", "0"));
        int rampDurationMinutes = Integer.parseInt(System.getProperty("duration", "1"));
        String testType = System.getProperty("typeOfTest", "load");

        switch (testType.toLowerCase()) {
            case "load":
                if (rampUpUsers > 0 && rampDurationMinutes > 0) {
                    loginAndCreateApp = CreateApplication.selfServiceApplicationRegistration().injectOpen(
                            atOnceUsers(userCount),
                            rampUsers(rampUpUsers).during(Duration.ofMinutes(rampDurationMinutes))
                    );
                } else {
                    loginAndCreateApp = CreateApplication.selfServiceApplicationRegistration().injectOpen(
                            atOnceUsers(userCount)
                    );
                }
                break;
            case "soak":
                loginAndCreateApp = CreateApplication.selfServiceApplicationRegistration().injectOpen(
                        rampUsers(userCount).during(Duration.ofMinutes(rampDurationMinutes))
                ).throttle(
                        reachRps(3).in(60),
                        holdFor(Duration.ofMinutes(rampDurationMinutes))
                );
                break;
            case "stress":
                loginAndCreateApp = CreateApplication.selfServiceApplicationRegistration().injectOpen(
                        incrementUsersPerSec(userCount)
                                .times(5)
                                .eachLevelLasting(10)
                                .separatedByRampsLasting(10)
                                .startingFrom(10)
                );
                break;
            default:
                loginAndCreateApp = CreateApplication.selfServiceApplicationRegistration().injectOpen(
                        atOnceUsers(userCount)
                );
        }
    }

    {
        setUp(loginAndCreateApp)
                .protocols(httpConfiguration)
                .assertions(global().failedRequests().count().is(0L));
    }
}