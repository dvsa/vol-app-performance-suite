package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scenarios.{CreateApplication}
import utils.{Configuration, Headers}


class CreateApplicationSimulation extends Simulation {

  val httpConfiguration = http.baseURL(Configuration.baseURL).headers(Headers.requestHeaders)
    .disableCaching
    .disableAutoReferer
    .disableWarmUp
    .silentResources

  val loginAndCreateApp =
        CreateApplication.selfServiceApplicationRegistration.inject(atOnceUsers(Configuration.users))
  setUp(loginAndCreateApp)
    .protocols(httpConfiguration)
    .assertions(global.failedRequests.count.is(0))
}