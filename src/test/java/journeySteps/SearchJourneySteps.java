package journeySteps;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static utils.Header.getAcceptHeaders;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import utils.SetUp;

public class SearchJourneySteps {

    public static HttpRequestActionBuilder getToLogin = http("get to the login page")
            .get("auth/login")
            .disableFollowRedirect()
            .headers(getAcceptHeaders())
            .check(regex(SetUp.securityTokenPattern).find().saveAs("securityToken"));

    public static HttpRequestActionBuilder searchAndLogin = http("login")
            .post("auth/login/")
            .check(regex(SetUp.location).find().optional().saveAs("Location"))
            .formParam("username", "#{Username}")
            .formParam("password", "#{Password}")
            .formParam("submit", "Sign in")
            .formParam("security", "#{securityToken}")
            .check(bodyString().saveAs("login_response"));

    public static HttpRequestActionBuilder navigateToLandingPage = http("Landing Page")
            .get("/")
            .check(bodyString().saveAs("landing_page"));

    public static HttpRequestActionBuilder search = http("search")
            .post("search/")
            .queryParam("index", "licence")
            .queryParam("search", "#{Licence}")
            .queryParam("submit", "")
            .check(substring("#{Licence}"));

    public static HttpRequestActionBuilder getSearchForBusOperatorPage = http("navigate to search bus page")
            .get("search/find-lorry-bus-operators/")
            .headers(getAcceptHeaders())
            .check(regex(SetUp.securityTokenPattern).find().saveAs("securityToken"));

    public static HttpRequestActionBuilder searchForBusOperatorPage = http("search for bus operator")
            .post("search/find-lorry-bus-operators/")
            .formParam("searchBy", "business")
            .formParam("search", "#{companyName}")
            .formParam("submit", "Search")
            .formParam("index", "operator")
            .formParam("security", "#{securityToken}")
            .check(bodyString().saveAs("search_results"))
            .check(substring("#{companyName}"));
}