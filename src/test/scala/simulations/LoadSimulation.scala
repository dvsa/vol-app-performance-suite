package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scenarios.RegisterUser
import utils.{Environment, Headers}
import scala.concurrent.duration._

class LoadSimulation extends Simulation {


  val httpConfiguration = http.baseURL(Environment.baseURL).headers(Headers.requestHeaders)
    .disableCaching
    .disableResponseChunksDiscarding
    .inferHtmlResources()
    .extraInfoExtractor(extraInfo => List(println(extraInfo.request), extraInfo.response, extraInfo.session))

  val registerUser = List(
    RegisterUser.registerUser.inject(
      constantUsersPerSec(1) during (1 minute))
      .throttle(reachRps(1) in (2 seconds),
        holdFor(1 minute))
  )
  setUp(registerUser)
    .protocols(httpConfiguration)
    .assertions(global.failedRequests.count.is(0))
}
