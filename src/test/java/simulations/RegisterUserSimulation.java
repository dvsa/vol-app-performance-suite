package simulations;


import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static utils.Header.getAcceptHeaders;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import scenarios.RegisterUser;

public class RegisterUserSimulation extends Simulation {

    private final String baseURL = utils.SetUp.baseURL;
    private final int users = utils.SetUp.users;
    private final int rampUp = utils.SetUp.rampUp;

    HttpProtocolBuilder httpConfiguration = http.baseUrl(baseURL)
            .headers(getAcceptHeaders())
            .disableCaching()
            .disableWarmUp()
            .silentResources()
            .perUserNameResolution();

    PopulationBuilder registerUsers = RegisterUser.registerUser()
            .injectOpen(rampUsers(users).during(rampUp)); // Injecting users with ramp-up time

    {
        setUp(registerUsers)
                .protocols(httpConfiguration)
                .assertions(global().failedRequests().count().is(0L));
    }
}