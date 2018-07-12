package scenarios

import io.gatling.core.Predef._
import io.gatling.core.Predef.scenario
import io.gatling.http.Predef.{http, regex, status}

object LoginPage {

  val imageHeader = Map("Accept" -> "image/png,image/*;q=0.8,*/*;q=0.5")

  val navigateToLoginPage = scenario("Users on login page")
      .exec(http("get login page")
      .get("login/")
      .headers(imageHeader)
      .check(
        regex("""input type="hidden" name="security" class="js-csrf-token" id="security" value="([^"]*)"""))
      .check(status.is(200))
      .check(regex("Sign in").exists))
}