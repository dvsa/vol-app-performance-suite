package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scenarios.ViewDetails
import utils.api.CreateAndGrantApplication
import utils.{Environment, Headers}

import scala.concurrent.duration._

class ValidationSimulation extends Simulation {

//  val user : Int  =  if (System.getProperty("users").isEmpty) System.getProperty("users").toInt else 60
//  val interval : Int = if (System.getProperty("interval").isEmpty) System.getProperty("interval").toInt else 30

  after{
    val deleteFile = new CreateAndGrantApplication
    deleteFile.deleteFile()
  }

  val httpConfiguration = http.baseURL(Environment.baseURL).headers(Headers.requestHeaders)
    .disableCaching
    .silentResources
    .disableResponseChunksDiscarding
    .disableWarmUp
    .strict302Handling

  val ValidationJourney = List(
    ViewDetails.loginIntoExternalSite.inject(
      rampUsers(60) over (30 minutes))
  )
  setUp(ValidationJourney)
    .protocols(httpConfiguration)
    .assertions(global.failedRequests.count.is(0))
}
