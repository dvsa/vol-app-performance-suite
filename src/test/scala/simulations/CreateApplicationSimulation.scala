package simulations

import io.gatling.core.Predef._
import io.gatling.core.structure.PopulationBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder
import scenarios.CreateAndSubmitApplication
import utils.SetUp
import utils.SetUp.{rampDurationInMin, rampUp, typeofTest, users}

import scala.concurrent.duration._
import scala.language.postfixOps


class CreateApplicationSimulation extends Simulation {

  val httpConfiguration: HttpProtocolBuilder = http.baseUrl(SetUp.baseURL)
    .disableAutoReferer
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-GB,en;q=0.5")
    .upgradeInsecureRequestsHeader("1")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:97.0) Gecko/20100101 Firefox/97.0")
    .upgradeInsecureRequestsHeader("1")
    .disableWarmUp
    .disableCaching
    .silentResources
    .perUserNameResolution

  val loginAndCreateApp: PopulationBuilder =
    (typeofTest)match {
      case "load" =>
      CreateAndSubmitApplication.selfServiceApplicationRegistration.inject(atOnceUsers(SetUp.users),
      constantUsersPerSec(SetUp.rampUp) during (SetUp.rampDurationInMin minutes))
      case "soak" =>
        CreateAndSubmitApplication.selfServiceApplicationRegistration.inject(rampUsers(users) during (rampUp minutes))
          .throttle(reachRps(3) in (60 seconds), holdFor(rampDurationInMin minutes))
      case "stress" =>
        // This is an open workload profile
        // With levels of x arriving users per second depending on users passed in
        // Each level lasting 10 seconds
        // Separated by linear ramps lasting 10 seconds
        CreateAndSubmitApplication.selfServiceApplicationRegistration.inject(incrementUsersPerSec(SetUp.users)
          .times(5)
          .eachLevelLasting(10)
          .separatedByRampsLasting(10 seconds)
          .startingFrom(10))
  }
  setUp(loginAndCreateApp)
    .protocols(httpConfiguration)
    .assertions(global.failedRequests.count.is(0))
}