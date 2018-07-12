package scenarios

import io.gatling.core.Predef._
import io.gatling.core.Predef.scenario
import io.gatling.http.Predef._
import utils.{Environment}

object ViewDetails {

  val env : String = Environment.environment.toString
  val header_ = Map("Accept" -> "*/*")
  val feeder = csv("loginId.csv").circular

  val loginIntoExternalSite = scenario("Users on login page")
    .feed(feeder)
    .exec(http("get login page")
      .get("auth/login/")
      .headers(header_)
      .check(
        regex("""input type="hidden" name="security" class="js-csrf-token" id="security" value="([^"]*)""").
          find.saveAs("securityToken")))
    .pause(16)
  .doIfOrElse(env.equals("da"))
  {
    exec(http("Login")
      .post("auth/login/")
      .formParam("username", "${UserId}")
      .formParam("password", "${Password}")
      .formParam("submit", "Sign in")
      .formParam("security", "${securityToken}")
      .check(regex("Home")))
  } {
    exec(http("Change Password")
      .post("auth/login/")
      .formParam("username", "${UserId}")
      .formParam("password", "${Password}")
      .formParam("submit", "Sign in")
      .formParam("security", "${securityToken}")
      .extraInfoExtractor { extraInfo => List(extraInfo.response.header("Location")) }
      .formParam("oldPassword", "${Password}")
      .formParam("newPassword", "Password1")
      .formParam("confirmPassword", "Password1")
      .formParam("submit", "Save")
      .formParam("security", "${securityToken}")
      .check(regex("Home")))
  }
}