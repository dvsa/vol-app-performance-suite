package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder
import scenarios.InternalSearch
import utils.{Headers, SetUp}

import scala.concurrent.duration._


class InternalLicenceSearchSimulation extends Simulation {

  val httpConfiguration: HttpProtocolBuilder = http.baseUrl(SetUp.baseURL).headers(Headers.requestHeaders)
    .disableCaching
    .disableWarmUp
    .silentResources
    .perUserNameResolution
    .maxConnectionsPerHostLikeChrome

  val search =
    InternalSearch.internalWorkerLogin.inject(atOnceUsers(SetUp.users),
          constantUsersPerSec(SetUp.rampUp) during (SetUp.rampDurationInMin minutes))
  setUp(search)
    .protocols(httpConfiguration)
    .assertions(global.failedRequests.count.is(0))
}