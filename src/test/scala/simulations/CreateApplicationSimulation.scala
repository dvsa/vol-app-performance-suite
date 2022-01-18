package simulations

import io.gatling.core.Predef._
import io.gatling.core.structure.PopulationBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder
import scenarios.CreateAndSubmitApplication
import utils.{Headers, SetUp}

import scala.concurrent.duration._
import scala.language.postfixOps


class CreateApplicationSimulation extends Simulation {

  val httpConfiguration: HttpProtocolBuilder = http.baseUrl(SetUp.baseURL)
    .headers(Headers.requestHeaders)
    .disableCaching
    .silentResources
    .perUserNameResolution

  val loginAndCreateApp: PopulationBuilder =
    CreateAndSubmitApplication.selfServiceApplicationRegistration.inject(atOnceUsers(SetUp.users),
      constantUsersPerSec(SetUp.rampUp) during (SetUp.rampDurationInMin minutes))
  setUp(loginAndCreateApp)
    .protocols(httpConfiguration)
    .assertions(global.failedRequests.count.is(0))
}