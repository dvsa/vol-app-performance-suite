package simulations;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import scenarios.RegisterUser;
import utils.SetUp.*;
import utils.*;
import utils.Headers;

public class RegisterUserSimulation extends Simulation {

    private final String baseURL = SetUp.baseURL; // Assuming SetUp_ has been converted accordingly
    private int users = SetUp.users; // Assuming SetUp_ has been converted accordingly
    private int rampUp = SetUp.rampUp; // Assuming SetUp_ has been converted accordingly
    private int rampDurationInMin = SetUp.rampDurationInMin; // Assuming SetUp_ has been converted accordingly

    // HTTP Configuration
    HttpProtocolBuilder httpConfiguration = http.baseUrl(baseURL)
            .headers(Headers.requestHeaders) // Assuming Headers.requestHeaders has been converted accordingly
            .disableCaching()
            .disableWarmUp()
            .silentResources()
            .perUserNameResolution();

    // Register Users Population
    PopulationBuilder registerUsers = RegisterUser.registerUser()
            .injectOpen(rampUsers(users).during(rampUp)); // Injecting users with ramp-up time
    registerUsers.throttle(
    reachRps(1).in(60), // Throttle users to reach 1 request per second within 60 seconds
    holdFor(rampDurationInMin).minutes() // Hold the rate for the duration
    );

    // Setting up the simulation
    setUp(registerUsers)
            .protocols(httpConfiguration)
            .assertions(global().failedRequests().count().is(0)); // Assert that there are no failed requests
}
