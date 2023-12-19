package scenarios

import `trait`.ApplicationJourneySteps
import io.gatling.core.Predef._
import io.gatling.core.feeder.BatchableFeederBuilder
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef.flushSessionCookies
import utils.SetUp._

import scala.language.postfixOps

object CreateAndSubmitApplication extends ApplicationJourneySteps {

  val feeder: BatchableFeederBuilder[String] = {
    (env) match {
      case "int" =>
        csv("loginId_int.csv").eager
      case _ =>
        csv("loginId.csv").circular
    }
  }

  val selfServiceApplicationRegistration: ScenarioBuilder = scenario("Create and submit application")
    .feed(feeder)
    .exec(getLoginPage)
    .pause(1)
    .exec(loginPage)
    .doIfOrElse("${env}" != "int") {
      exec(session => session.set("expired-password", "${Location}"))
        .pause(2)
        .doIf(session => session("expired-password").as[String].isEmpty == true) {
          exec(changePassword)
        }
    } {
      exec(session => session)
    }
    .pause(1).
    exec(getCreateApplicationPage)
    .pause(1)
    .exec(showDashboard)
    .pause(3)
    .exec(getBusinessTypePage)
    .pause(4)
    .exec(businessType).pause(5)
    .exec(getBusinessDetailsPage).pause(1)
    .exec(businessDetails)
    .pause(4)
    .exec(addresses)
    .pause(5)
    .exec(director)
    .pause(4)
    .exec(saveDirectorDetails)
    .pause(2)
    .exec(getLicenceAuthorisationPage)
    .pause(1)
    .exec(licenceAuthorisation)
    .pause(4)
    .exec(financialEvidence)
    .pause(3)
    .exec(getTransportManagersPage)
    .pause(2)
    .exec(transportManagersPage)
    .pause(1)
    .exec(navigateToAddTransportManagersPage)
    .pause(2)
    .exec(transportManagersDetails)
    .pause(5)
    .exec(vehicleDetails)
    .pause(7)
    .exec(safetyInspector)
    .pause(4)
    .exec(safetyCompliance)
    .pause(2)
    .exec(financeHistory)
    .pause(2)
    .exec(licenceHistory)
    .pause(2)
    .exec(convictionsAndPenalties)
    .pause(3)
    .exec(flushSessionCookies)
}