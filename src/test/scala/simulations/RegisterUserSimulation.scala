package simulations

import io.gatling.core.Predef._
import io.gatling.core.structure.PopulationBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder
import scenarios.RegisterUser
import utils.SetUp.{rampDurationInMin, rampUp, users}
import utils.{Headers, SetUp}

import scala.concurrent.duration._
import scala.language.postfixOps


class RegisterUserSimulation extends Simulation {

  val httpConfiguration: HttpProtocolBuilder = http.baseUrl(SetUp.baseURL).headers(Headers.requestHeaders)
    .disableCaching
    .disableWarmUp
    .silentResources
    .perUserNameResolution
    .maxConnectionsPerHostLikeChrome

  val RegisterUsers: PopulationBuilder =
        RegisterUser.registerUser.inject(rampUsers(users) during (rampUp minutes))
          .throttle(reachRps(1) in (60 seconds), holdFor(rampDurationInMin minutes))
  setUp(RegisterUsers)
    .protocols(httpConfiguration)
    .assertions(global.failedRequests.count.is(0))
}