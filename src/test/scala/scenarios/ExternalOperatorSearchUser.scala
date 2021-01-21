package scenarios

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import utils.SetUp
import utils.SetUp.header_

import scala.concurrent.duration._

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
      .formParam("searchBy", "business")
      .formParam("search", session => s"""${companyName}""")
      .formParam("submit", "Search")
      .formParam("index", "operator")
      .formParam("security", "${securityToken}")
      .check(bodyString.saveAs("search_results"))
      .check(regex(s"""${companyName}""").exists))
    .exec(session => {
      println(session("search_results").as[String])
      session
    })
}
