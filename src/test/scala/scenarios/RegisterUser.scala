package scenarios

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.core.Predef.scenario
import io.gatling.core.structure.ScenarioBuilder
import utils.SetUp

object RegisterUser {

  val random = new scala.util.Random
  var emailAddress = "gatling.test@gmail.com"
  val imageHeader = Map("Accept" -> "image/png,image/*;q=0.8,*/*;q=0.5")
  def orderRef(): Int = random.nextInt(Integer.MAX_VALUE)

  val registerUser: ScenarioBuilder = scenario("VOL New User Registration")
    .exec(http("get register page")
      .get("register/")
      .headers(imageHeader)
      .check(
        regex(SetUp.securityTokenPattern).
          saveAs("securityToken")))
      .pause(5)
    .exec(http("register a new account")
      .post("register/")
      .formParam("fields[loginId]", session => s"""GatlingUser${orderRef()}""")
      .formParam("fields[forename]", session => s"""Gatling${orderRef()}""")
      .formParam("fields[familyName]", "Tester")
      .formParam("fields[emailAddress]", "terry.valtech@gmail.com")
      .formParam("fields[emailConfirm]", "terry.valtech@gmail.com")
      .formParam("fields[isLicenceHolder]", "N")
      .formParam("fields[licenceNumber]", "")
      .formParam("fields[organisationName]", "VOL-Performance-Test")
      .formParam("fields[businessType]", "org_t_rc")
      .formParam("fields[translateToWelsh]", "N")
      .formParam("fields[termsAgreed]", "N")
      .formParam("fields[termsAgreed]", "Y")
      .formParam("form-actions[submit]", "")
      .formParam("security", "${securityToken}")
      .check(status.is(200))
      .check(regex("Check your email").exists))
}