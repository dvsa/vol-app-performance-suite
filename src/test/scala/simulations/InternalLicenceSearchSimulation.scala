package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scenarios.InternalSearch
import utils.{Configuration, Headers}

import scala.concurrent.duration._


class InternalLicenceSearchSimulation extends Simulation {

  val httpConfiguration = http.baseUrl(Configuration.baseURL).headers(Headers.requestHeaders)
    .disableCaching
    .disableWarmUp
    .silentResources
    .perUserNameResolution
    .maxConnectionsPerHostLikeChrome

  val search =
    InternalSearch.internalWorkerLogin.inject(atOnceUsers(Configuration.internalUsers),
          constantUsersPerSec(Configuration.rampUp) during (Configuration.rampDurationInMin minutes))
  setUp(search)
    .protocols(httpConfiguration)
    .assertions(global.failedRequests.count.is(0))
}