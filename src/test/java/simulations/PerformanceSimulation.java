package simulations;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static utils.SetUp.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import scenarios.ExternalOperatorSearchUser;
import scenarios.InternalSearch;
import scenarios.CreateApplication;
import scenarios.RegisterUser;

import java.time.Duration;

public class PerformanceSimulation extends Simulation {

    private static final HttpProtocolBuilder httpConfiguration = http
            .baseUrl(baseURL)
            .disableAutoReferer()
            .disableCaching()
            .disableWarmUp()
            .silentResources()
            .perUserNameResolution();

    private static final PopulationBuilder externalSearch;
    private static final PopulationBuilder internalSearch;
    private static final PopulationBuilder userRegistration;
    private static final PopulationBuilder createApplication;

    static {
        int userCount = Integer.parseInt(System.getProperty("users", "10"));
        int rampUpUsers = Integer.parseInt(System.getProperty("rampUp", "0"));
        int rampDurationMinutes = Integer.parseInt(System.getProperty("duration", "1"));
        String testType = System.getProperty("typeOfTest", "load");

        int externalUsers = Integer.parseInt(System.getProperty("externalUsers", String.valueOf(userCount)));
        int internalUsers = Integer.parseInt(System.getProperty("internalUsers", String.valueOf(userCount)));
        int registrationUsers = Integer.parseInt(System.getProperty("registrationUsers", String.valueOf(userCount)));
        int applicationUsers = Integer.parseInt(System.getProperty("applicationUsers", String.valueOf(userCount )));

        switch (testType.toLowerCase()) {
            case "load":
                if (rampUpUsers > 0 && rampDurationMinutes > 0) {
                    externalSearch = ExternalOperatorSearchUser.companySearch().injectOpen(
                            atOnceUsers(externalUsers),
                            rampUsers(rampUpUsers / 4).during(Duration.ofMinutes(rampDurationMinutes))
                    );
                    internalSearch = InternalSearch.internalWorkerLogin().injectOpen(
                            atOnceUsers(internalUsers),
                            rampUsers(rampUpUsers / 4).during(Duration.ofMinutes(rampDurationMinutes))
                    );
                    userRegistration = RegisterUser.registerUser().injectOpen(
                            atOnceUsers(registrationUsers),
                            rampUsers(rampUpUsers / 4).during(Duration.ofMinutes(rampDurationMinutes))
                    );
                    createApplication = CreateApplication.selfServiceApplicationRegistration().injectOpen(
                            atOnceUsers(applicationUsers),
                            rampUsers(rampUpUsers / 4).during(Duration.ofMinutes(rampDurationMinutes))
                    );
                } else {
                    externalSearch = ExternalOperatorSearchUser.companySearch().injectOpen(atOnceUsers(externalUsers));
                    internalSearch = InternalSearch.internalWorkerLogin().injectOpen(atOnceUsers(internalUsers));
                    userRegistration = RegisterUser.registerUser().injectOpen(atOnceUsers(registrationUsers));
                    createApplication = CreateApplication.selfServiceApplicationRegistration().injectOpen(atOnceUsers(applicationUsers));
                }
                break;
            case "stress":
                externalSearch = ExternalOperatorSearchUser.companySearch().injectOpen(
                        incrementUsersPerSec(externalUsers).times(5).eachLevelLasting(10).separatedByRampsLasting(10).startingFrom(2)
                );
                internalSearch = InternalSearch.internalWorkerLogin().injectOpen(
                        incrementUsersPerSec(internalUsers).times(5).eachLevelLasting(10).separatedByRampsLasting(10).startingFrom(2)
                );
                userRegistration = RegisterUser.registerUser().injectOpen(
                        incrementUsersPerSec(registrationUsers).times(3).eachLevelLasting(10).separatedByRampsLasting(10).startingFrom(1)
                );
                createApplication = CreateApplication.selfServiceApplicationRegistration().injectOpen(
                        incrementUsersPerSec(applicationUsers).times(3).eachLevelLasting(15).separatedByRampsLasting(15).startingFrom(1)
                );
                break;
            case "soak":
                externalSearch = ExternalOperatorSearchUser.companySearch().injectOpen(
                        rampUsers(externalUsers).during(Duration.ofMinutes(rampDurationMinutes))
                ).throttle(reachRps(2).in(60), holdFor(Duration.ofMinutes(rampDurationMinutes)));
                internalSearch = InternalSearch.internalWorkerLogin().injectOpen(
                        rampUsers(internalUsers).during(Duration.ofMinutes(rampDurationMinutes))
                ).throttle(reachRps(2).in(60), holdFor(Duration.ofMinutes(rampDurationMinutes)));
                userRegistration = RegisterUser.registerUser().injectOpen(
                        rampUsers(registrationUsers).during(Duration.ofMinutes(rampDurationMinutes))
                ).throttle(reachRps(1).in(60), holdFor(Duration.ofMinutes(rampDurationMinutes)));
                createApplication = CreateApplication.selfServiceApplicationRegistration().injectOpen(
                        rampUsers(applicationUsers).during(Duration.ofMinutes(rampDurationMinutes))
                ).throttle(reachRps(1).in(120), holdFor(Duration.ofMinutes(rampDurationMinutes)));
                break;
            default:
                externalSearch = ExternalOperatorSearchUser.companySearch().injectOpen(atOnceUsers(externalUsers));
                internalSearch = InternalSearch.internalWorkerLogin().injectOpen(atOnceUsers(internalUsers));
                userRegistration = RegisterUser.registerUser().injectOpen(atOnceUsers(registrationUsers));
                createApplication = CreateApplication.selfServiceApplicationRegistration().injectOpen(atOnceUsers(applicationUsers));
        }
    }

    {
        setUp(externalSearch, internalSearch, userRegistration, createApplication)
                .protocols(httpConfiguration)
                .assertions(global().failedRequests().percent().lt(10.0));
    }
}