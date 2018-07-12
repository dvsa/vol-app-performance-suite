package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scenarios.{LoginPage, SearchBusOperator}
import utils.{Environment, Headers}

import scala.concurrent.duration._

class LoadSimulation extends Simulation {


  val httpConfiguration = http.baseURL(Environment.baseURL).headers(Headers.requestHeaders)
    .disableCaching
    .disableResponseChunksDiscarding
    .disableWarmUp
    .silentResources
    .extraInfoExtractor(extraInfo => List(println(extraInfo.request), extraInfo.response, extraInfo.session))

  val NonValidationJourney = List(
    SearchBusOperator.search.inject(
      rampUsers(30) over (15 minutes))
      .throttle(reachRps(10) in (2 minutes),
        holdFor(15 minutes)),
        LoginPage.navigateToLoginPage.inject(
          rampUsers(30) over (15 minutes))
          .throttle(reachRps(10) in (2 minutes),
            holdFor(15 minutes))
  )
  setUp(NonValidationJourney)
    .protocols(httpConfiguration)
    .assertions(global.failedRequests.count.is(0))
}
