package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scenarios.ViewDetails
import utils.api.CreateAndGrantApplication
import utils.{Environment, Headers}

import scala.concurrent.duration._

class ValidationSimulation extends Simulation {


  after {
    val deleteFile = new CreateAndGrantApplication
    deleteFile.deleteFile()
  }

  val httpConfiguration = http.baseURL(Environment.baseURL).headers(Headers.requestHeaders)
    .disableCaching
    .silentResources
    .disableResponseChunksDiscarding
    .disableWarmUp
    .strict302Handling

  val ValidationJourney =
    ViewDetails.loginIntoExternalSite.inject(
      rampUsers(Integer.parseInt(Environment.user)) over (Integer.parseInt(Environment.interval) minutes)
    )

  setUp(ValidationJourney)
    .protocols(httpConfiguration)
    .assertions(global.failedRequests.count.is(0))
}
