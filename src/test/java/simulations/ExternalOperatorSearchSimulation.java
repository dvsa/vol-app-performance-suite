package simulations;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.core.OpenInjectionStep.atOnceUsers;
import static io.gatling.javaapi.http.HttpDsl.*;
import static utils.SetUp.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import scenarios.ExternalOperatorSearchUser;

import java.time.Duration;


public class ExternalOperatorSearchSimulation extends Simulation {

    private static final HttpProtocolBuilder httpConfiguration = http
            .baseUrl(baseURL)
            .disableAutoReferer()
            .disableCaching()
            .disableWarmUp()
            .silentResources()
            .perUserNameResolution();


    private final PopulationBuilder search = ExternalOperatorSearchUser.companySearch()
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