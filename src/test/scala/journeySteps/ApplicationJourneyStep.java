package journeySteps;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static utils.GenericUtils.orderRef;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import utils.SetUp;

import java.util.HashMap;
import java.util.Map;

public class ApplicationJourneyStep {
    private static final Map<String, String> acceptHeaders = new HashMap<>();

    static {
        acceptHeaders.put("Accept", "*/*");
    }

    public static Map<String, String> getAcceptHeaders() {
        return acceptHeaders;
    }

    private static final Map<String, String> formHeaders = new HashMap<>();

    static {
        formHeaders.put("Content-Type", "application/x-www-form-urlencoded");
        formHeaders.put("Accept-Language", "en-GB,en-US;q=0.9,en;q=0.8");
        formHeaders.put("Accept-Encoding", "gzip, deflate");
        formHeaders.put("Accept", "*/*");
    }

    public static Map<String, String> getFormHeaders() {
        return formHeaders;
    }

    public static HttpRequestActionBuilder getUserStatus = http("do you already have a licence")
            .get("register/")
            .headers(getAcceptHeaders())
            .check(
                    regex(SetUp.securityTokenPattern()).
                            saveAs("securityToken"));

    public static HttpRequestActionBuilder setUserStatus = http("inform about your status")
            .post("register/")
            .headers(getFormHeaders())
            .formParam("fields[licenceContent][licenceNumber]", "N")
            .formParam("form-actions[submit]", "")
            .formParam("security", (Session session) -> session.get("securityToken"));


    public static HttpRequestActionBuilder getOperatorRepresentation = http("get operator representation")
            .get("register/operator-representation/")
            .headers(getAcceptHeaders());


    public static HttpRequestActionBuilder operatorRepresentation = http("acting on behalf of")
            .post("register/operator-representation/")
            .headers(getFormHeaders())
            .formParam("fields[actingOnOperatorsBehalf]", "N")
            .formParam("fields[licenceNumber]", "")
            .formParam("form-actions[submit]", "")
            .formParam("security", (Session session) -> session.get("securityToken"));

    public static HttpRequestActionBuilder getRegistrationPage = http("get registration page")
            .get("register/operator/")
            .headers(getAcceptHeaders());

    public static HttpRequestActionBuilder registerNewAccount = http("register a new account")
            .post("register/operator/")
            .headers(getFormHeaders())
            .formParam("fields[loginId]", session -> "GatlingVOLUser" + orderRef())
            .formParam("fields[forename]", session -> "Gatling" + orderRef())
            .formParam("fields[familyName]", "Tester")
            .formParam("fields[emailAddress]", "gatling.tests@volTesting.com")
            .formParam("fields[emailConfirm]", "gatling.tests@volTesting.com")
            .formParam("fields[isLicenceHolder]", "N")
            .formParam("fields[licenceNumber]", "")
            .formParam("fields[organisationName]", "VOL-Performance-Test")
            .formParam("fields[businessType]", "org_t_rc")
            .formParam("fields[translateToWelsh]", "N")
            .formParam("fields[termsAgreed]", "Y")
            .formParam("form-actions[submit]", "")
            .formParam("security", (Session session) -> session.get("securityToken"))
            .check(
                    regex("Check your email").exists(),
                    status().is(200)
            );

    public static HttpRequestActionBuilder getWelcomePage = http("get welcome page")
            .get("/welcome")
            .headers(getAcceptHeaders())
            .check(
                    regex(SetUp.securityTokenPattern()).saveAs("securityToken"),
                    status().in(200, 302)
            );

    public static HttpRequestActionBuilder getDashboardAfterWelcome = http("get dashboard after welcome")
            .get("/dashboard")
            .headers(getAcceptHeaders())
            .check(
                    regex(SetUp.securityTokenPattern()).saveAs("securityToken")
            );

    public static HttpRequestActionBuilder submitWelcomePage = http("accept terms and continue")
            .post("/welcome")
            .headers(getFormHeaders())
            .formParam("main[termsAgreed]", "Y")
            .formParam("form-actions[submit]", "")
            .formParam("security", (Session session) -> session.get("securityToken"));

    public static HttpRequestActionBuilder followWelcomeRedirect = http("follow welcome redirect")
            .get("#{redirectUrl}")
            .headers(getAcceptHeaders())
            .check(
                    status().in(200, 302)
            );

   

}
