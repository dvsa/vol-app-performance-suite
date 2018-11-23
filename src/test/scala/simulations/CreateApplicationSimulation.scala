package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scenarios.CreateApplication
import utils.{Configuration, Headers}

import scala.concurrent.duration._


class CreateApplicationSimulation extends Simulation {

  val httpConfiguration = http.baseUrl(Configuration.baseURL).headers(Headers.requestHeaders)
    .disableCaching
    .disableWarmUp
    .silentResources
    .perUserNameResolution

  val loginAndCreateApp =
        CreateApplication.selfServiceApplicationRegistration.inject(atOnceUsers(Configuration.users),
          constantUsersPerSec(Configuration.rampUp) during (Configuration.rampDurationInMin minutes))
  setUp(loginAndCreateApp)
    .protocols(httpConfiguration)
    .assertions(global.failedRequests.count.is(0))
}