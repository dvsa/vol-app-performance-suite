package scenarios

import `trait`.ApplicationJourneySteps
import io.gatling.core.Predef._
import io.gatling.core.feeder.BatchableFeederBuilder
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import utils.SetUp._

import scala.concurrent.duration._
import scala.language.postfixOps

object CreateAndSubmitApplication extends ApplicationJourneySteps {

  val feeder: BatchableFeederBuilder[String] = {
    (env) match {
      case "int" =>
        csv("loginId_int.csv").eager
      case _ =>
        csv("loginId.csv").eager
    }
  }

  val selfServiceApplicationRegistration: ScenarioBuilder = scenario("Create and submit application")
    .feed(feeder)
    .exec(getLoginPage)
    .pause(1 seconds)
    .exec(loginPage)
    .exec(session => session.set("expired-password", "${Location}"))
    .pause(2 seconds)
    .doIf(session => session("expired-password").as[String].isEmpty == false) {
      exec(changePassword)
    }
    .pause(700 milliseconds)
    .exec(session => {
      println(session("login_response").as[String])
      session
    })
    .exec(landingPage)
    .pause(650 milliseconds)
    .exec(session => {
      println(session("login_response").as[String])
      session
    })
    .exec(chooseCountry)
    .pause(950 milliseconds)
    .exec(showDashboard)
    .pause(850 milliseconds)
    .exec(businessType)
    .pause(1000 milliseconds)
    .exec(businessDetails)
    .pause(950 millisecond)
    .exec(addresses)
    .pause(890 milliseconds)
    .exec(director)
    .pause(850 milliseconds)
    .exec(saveDirectorDetails)
    .pause(2000 milliseconds)
    .exec(operatingCentreDetails)
    .pause(1500 milliseconds)
    .exec(operatorCentreVehicleDetails)
    .pause(3 seconds)
    .exec(financialEvidence)
    .pause(850 milliseconds)
    .exec(transportManagersPage)
    .pause(8)
    .exec(transportManager)
    .pause(8)
    .exec(transportManagerDetails)
    .pause(9)
    .exec(transportManagerAnswers)
    .pause(3)
    .exec(submitTransportManagerAnswers)
    .pause(5)
    .exec(vehicleDetails)
    .pause(7)
    .exec(safetyInspector)
    .pause(850 milliseconds)
    .exec(safetyCompliance)
    .pause(2 seconds)
    .exec(financeHistory)
    .pause(2 seconds)
    .exec(licenceHistory)
    .pause(2 milliseconds)
    .exec(convictionsAndPenalties)
    .pause(850 milliseconds)
    .exec(undertakings)
    .exec(session => {
      println(session("undertakings").as[String])
      session
    })
    .pause(1500 milliseconds)
    .exec(cpmsRedirect)
    .exec(session => {
      println(session("pay").as[String])
      session
    })
    .pause(3 seconds)
    .exec(flushSessionCookies)
}