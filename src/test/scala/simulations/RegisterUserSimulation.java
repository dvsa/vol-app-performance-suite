package simulations;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static utils.Header.getAcceptHeaders;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import scenarios.RegisterUser;

public class RegisterUserSimulation extends Simulation {

    private final String baseURL = utils.SetUp.baseURL;
    private final int users = utils.SetUp.users; // Assuming SetUp_ has been converted accordingly
    private final int rampUp = utils.SetUp.rampUp; // Assuming SetUp_ has been converted accordingly
    private final int rampDurationInMin = utils.SetUp.rampDurationInMin; // Assuming SetUp_ has been converted accordingly

    // HTTP Configuration
    HttpProtocolBuilder httpConfiguration = http.baseUrl(baseURL)
            .headers(getAcceptHeaders()) // Assuming Headers.requestHeaders has been converted accordingly
            .disableCaching()
            .disableWarmUp()
            .silentResources()
            .perUserNameResolution();

    // Register Users Population

    PopulationBuilder registerUsers = RegisterUser.registerUser()
            .injectOpen(rampUsers(users).during(rampUp));// Injecting users with ramp-up time

    // Setting up the simulation
    {
        setUp(registerUsers)
                .protocols(httpConfiguration)
                .assertions(global().failedRequests().count().is(0L));
    }
}