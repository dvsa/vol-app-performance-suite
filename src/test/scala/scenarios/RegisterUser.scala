package scenarios

import io.gatling.core.Predef.*
import io.gatling.core.Predef.scenario
import io.gatling.core.structure.ScenarioBuilder
import scenarios.CreateAndSubmitApplication.*

object RegisterUser {

  val registerUser: ScenarioBuilder = scenario("VOL New User Registration")
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
    .pause(2)
}