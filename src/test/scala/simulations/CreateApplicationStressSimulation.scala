package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import scenarios.CreateSubmitAndPayForApplication
import utils.{Configuration, Headers}


class CreateApplicationStressSimulation extends Simulation {

  val httpConfiguration = http.baseUrl(Configuration.baseURL).headers(Headers.requestHeaders)
    .disableCaching
    .disableWarmUp
    .silentResources
    .perUserNameResolution
    .maxConnectionsPerHostLikeChrome

  // This is an open workload profile
  // With levels of x arriving users per second depending on users passed in
  // Each level lasting 10 seconds
  // Separated by linear ramps lasting 10 seconds
  val loginAndCreateApp =
        CreateSubmitAndPayForApplication.selfServiceApplicationRegistration.inject(incrementUsersPerSec(Configuration.users)
            .times(5)
          .eachLevelLasting(10)
            .separatedByRampsLasting(10 seconds)
            .startingFrom(5))
  setUp(loginAndCreateApp)
    .protocols(httpConfiguration)
    .assertions(global.failedRequests.count.is(0))
}