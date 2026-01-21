package simulations;

import static io.gatling.javaapi.core.CoreDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;


import scenarios.InternalSearch;

import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.global;
import static io.gatling.javaapi.core.OpenInjectionStep.atOnceUsers;
import static io.gatling.javaapi.http.HttpDsl.http;
import static utils.SetUp.*;

public class InternalLicenceSearchSimulation extends Simulation {

    private static final HttpProtocolBuilder httpConfiguration = http
            .baseUrl(baseURL)
            .disableAutoReferer()
            .disableCaching()
            .disableWarmUp()
            .silentResources()
            .perUserNameResolution();

    private final PopulationBuilder search = InternalSearch.internalWorkerLogin()
            .injectOpen(
                    atOnceUsers(users),
                    constantUsersPerSec(rampUp).during(Duration.ofMinutes(rampDurationInMin))
            );

    {
        setUp(search)
                .protocols(httpConfiguration)
                .assertions(global().failedRequests().count().is(0L));
    }
}