package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scenarios.ExternalOperatorSearchUser
import utils.{Configuration, Headers}

import scala.concurrent.duration._


class ExternalOperatorSearchSimulation extends Simulation {

  val httpConfiguration = http.baseUrl(Configuration.baseURL).headers(Headers.requestHeaders)
    .disableCaching
    .disableWarmUp
    .silentResources
    .perUserNameResolution
    .maxConnectionsPerHostLikeChrome

  val search =
    ExternalOperatorSearchUser.companySearch.inject(atOnceUsers(Configuration.users),
          constantUsersPerSec(Configuration.rampUp) during (Configuration.rampDurationInMin minutes))
  setUp(search)
    .protocols(httpConfiguration)
    .assertions(global.failedRequests.count.is(0))
}