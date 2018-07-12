package scenarios

import io.gatling.core.Predef._
import io.gatling.core.Predef.scenario
import io.gatling.http.Predef.{http, regex, status}

object SearchBusOperator {

  val feeder = csv("BusinessNames.csv").circular
  val imageHeader = Map("Accept" -> "image/png,image/*;q=0.8,*/*;q=0.5")

  val search = scenario("Users on search page")
    .exec(http("find operators")
      .get("search/find-lorry-bus-operators/")
      .headers(imageHeader)
      .check(
        regex("""input type="hidden" name="security" class="js-csrf-token" id="security" value="([^"]*)""").
          find.saveAs("securityToken"))
      .check(regex("Find lorry and bus operators"))
      .check(status.is(200)))
    .pause(5)
    .feed(feeder)
    .exec(http("View Search Results")
      .post("search/find-lorry-bus-operators/")
      .formParam("searchBy", "business")
      .formParam("search",  "${Business}")
      .formParam("security", "${securityToken}")
      .check(status.is(200))
      .check(regex("Search results").exists)
      .body(StringBody("""{ "${Business}" }""")))
}