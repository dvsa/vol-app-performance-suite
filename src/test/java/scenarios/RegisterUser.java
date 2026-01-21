package scenarios;

import io.gatling.javaapi.core.ScenarioBuilder;

import static io.gatling.javaapi.core.CoreDsl.scenario;
import static journeySteps.ApplicationJourneySteps.*;

public class RegisterUser {

    public static ScenarioBuilder registerUser() {
        return scenario("VOL New User Registration")
                .exec(getUserStatus)
                .pause(5)
                .exec(setUserStatus)
                .pause(3)
                .exec(getOperatorRepresentation)
                .pause(1)
                .exec(operatorRepresentation)
                .pause(2)
                .exec(getRegistrationPage)
                .pause(3)
                .exec(registerNewAccount)
                .pause(2);
    }
}