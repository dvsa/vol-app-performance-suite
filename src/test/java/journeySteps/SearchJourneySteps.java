package journeySteps;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static utils.Header.getAcceptHeaders;

import activesupport.config.Configuration;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import utils.SetUp;
public class SearchJourneySteps {

    private static final String password = new Configuration().getConfig().getString("password");
    public static final String companyName = "Eddie";

    public static HttpRequestActionBuilder getToLogin = http("get to the login page")
      .get("auth/login")
      .disableFollowRedirect()
              .headers(getAcceptHeaders())
            .check(
            regex(SetUp.securityTokenPattern).
            find().saveAs("securityToken"));

    public static HttpRequestActionBuilder searchAndLogin = http("login")
      .post("auth/login/")
      .check(regex(SetUp.location).find().optional().saveAs("Location"))
            .formParam("username", "#{Username}")
            .formParam("password",  session -> session.get(password))
            .formParam("submit", "Sign in")
      .formParam("security", (Session session) -> session.get("securityToken"))
      .check(bodyString().saveAs("login_response"));

    public static HttpRequestActionBuilder navigateToLandingPage = http("Landing Page")
      .get("/")
      .check(regex((Session session) -> session.get("Forename")))
            .check(bodyString().saveAs("landing_page"));

    public static HttpRequestActionBuilder search = http("search")
      .post("search/")
        .queryParam("index","licence")
        .queryParam("search","#{Licence}")
        .queryParam("submit","")
      .check(regex((Session session) -> session.get("Licence")).exists());


    public static HttpRequestActionBuilder getSearchForBusOperatorPage = http("navigate to search bus page")
      .get("search/find-lorry-bus-operators/")
      .headers(getAcceptHeaders())
      .check(
        regex(SetUp.securityTokenPattern).
          find().saveAs("securityToken"));

    public static HttpRequestActionBuilder searchForBusOperatorPage = http("search for bus operator")
      .post("search/find-lorry-bus-operators/")
      .formParam("searchBy", "business")
      .formParam("search", (Session session) -> session.get(companyName))
      .formParam("submit", "Search")
      .formParam("index", "operator")
      .formParam("security", (Session session) -> session.get ("securityToken"))
      .check(bodyString().saveAs("search_results"))
      .check(regex((Session session) -> session.get(companyName)).exists());
}