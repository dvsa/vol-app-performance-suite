package scenarios

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import utils.SetUp
import utils.SetUp.header_

import scala.concurrent.duration._
import scala.language.postfixOps

object ExternalOperatorSearchUser {

  val companyName = "Eddie"

  val companySearch: ScenarioBuilder = scenario("Search for operator")
    .exec(http("get search page")
      .get("search/find-lorry-bus-operators/")
      .headers(header_)
      .check(
        regex(SetUp.securityTokenPattern).
          find.saveAs("securityToken")))
    .pause(300 milliseconds)
    .exec(http("Search Operator")
      .post("search/find-lorry-bus-operators/")
      .formParam("searchBy", _ => s"business")
      .formParam("search", session => session(companyName).as[String])
      .formParam("submit", _ => s"Search")
      .formParam("index", _ => s"operator")
      .formParam("security", session => session("securityToken").as[String])
      .check(bodyString.saveAs("search_results"))
      .check(regex(session => session(companyName).as[String]).exists))
    .exec(session => {
      println(session("search_results").as[String])
      session
    })
}