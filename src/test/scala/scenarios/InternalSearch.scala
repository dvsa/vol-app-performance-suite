package scenarios

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import utils.Configuration

import scala.concurrent.duration._

object InternalSearch {

  val feeder = csv("src/test/resources/InternalLoginId.csv")
  val header_ = Map("Accept" -> "*/*")

  val internalWorkerLogin = scenario("Login as an internal case worker")
    .feed(feeder)
    .exec(http("Login as a case worker")
      .get("/auth/login")
      .disableFollowRedirect
      .headers(header_)
      .check(
        regex(Configuration.securityTokenPattern).
          find.saveAs("securityToken")))
    .pause(300 milliseconds)
    .exec(http("login")
      .post("auth/login/")
      .check(regex("""name="change-password-form" action="&#x2F;auth&#x2F;expired-password&#x2F;([^"]*)&#x2F;""").find.optional.saveAs("Location"))
      .formParam("username", "${Username}")
      .formParam("password", "${Password}")
      .formParam("submit", "Sign in")
      .formParam("security", "${securityToken}")
      .check(bodyString.saveAs("login_response")))
    .pause(700 milliseconds)
    .exec(session => {
      println(session("login_response").as[String])
      session
    })
    .exec(http("Landing Page")
      .get("/")
      .check(regex("${Forename}"))
      .check(bodyString.saveAs("landing_page")))
    .pause(650 milliseconds)
    .exec(session => {
      println(session("landing_page").as[String])
      session
    })
    .exec(http("search")
      .post("search/")
        .queryParam("index","licence")
        .queryParam("search","${Licence}")
        .queryParam("submit","")
      .check(regex("${Licence}"))
      .check(bodyString.saveAs("Licence_search")))
    .exec(session => {
      println(session("Licence_search").as[String])
      session
    })
}