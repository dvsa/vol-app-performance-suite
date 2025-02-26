package journeySteps;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static utils.GenericUtils.orderRef;
import static utils.GenericUtils.password;

import activesupport.config.Configuration;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import utils.SetUp;

import java.util.HashMap;
import java.util.Map;

public class ApplicationJourneyStep {
    private static final String env = System.getProperty("env");
    private static final String newPassword = new Configuration().getConfig().getString("password");

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
                    regex(SetUp.securityTokenPattern()).find().
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
                    regex(SetUp.securityTokenPattern()).find().saveAs("securityToken"),
                    status().in(200, 302)
            );

    public static HttpRequestActionBuilder getDashboardAfterWelcome = http("get dashboard after welcome")
            .get("/dashboard")
            .headers(getAcceptHeaders())
            .check(
                    regex(SetUp.securityTokenPattern()).find().saveAs("securityToken")
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

    public static HttpRequestActionBuilder changePassword = http("change password")
            .post("auth/expired-password/")
            .formParam("oldPassword", password(env))
            .formParam("newPassword", newPassword)
            .formParam("confirmPassword", newPassword)
            .formParam("submit", "Save")
            .formParam("security", (Session session) -> session.get("securityToken"));

    public static HttpRequestActionBuilder getLoginPage = http("get login page")
            .get("auth/login/")
            .headers(getAcceptHeaders())
            .check(
                    regex(SetUp.securityTokenPattern()).find().
                            saveAs("securityToken"));

    public static HttpRequestActionBuilder loginPage = http("login")
            .post("auth/login/")
            .formParam("username", "#{Username}")
            .formParam("password", session -> password(env))
            .formParam("submit", "Sign in")
            .formParam("security", (Session session) -> session.get("securityToken"))
            .check(regex(SetUp.location()).find().optional().saveAs("Location"));

    public static HttpRequestActionBuilder landingPage = http("Landing Page")
            .get("/")
            .check(regex("#{Forename}"))
            .check(bodyString().find().saveAs("login_response"));

    public static HttpRequestActionBuilder createNonLGVApplication = http("choose country")
            .post("application/create/")
            .formParam("type-of-licence[operator-location]", "N")
            .formParam("type-of-licence[operator-type]", "lcat_gv")
            .formParam("type-of-licence[licence-type]", "ltyp_sn")
            .formParam("form-actions[saveAndContinue]", "")
            .formParam("security", (Session session) -> session.get("securityToken"));

    public static HttpRequestActionBuilder getCreateApplicationPage = http("get application")
            .get("application/create/")
            .headers(getAcceptHeaders());

    public static HttpRequestActionBuilder createLGVApplication = http("create application")
            .post("application/create/")
            .headers(getFormHeaders())
            .check(
                    regex(SetUp.securityTokenPattern()).find().
                            saveAs("securityToken"))
            .formParam("type-of-licence[operator-location]", "N")
            .formParam("type-of-licence[operator-type]", "lcat_gv")
            .formParam("type-of-licence[licence-type][licence-type]", "ltyp_si")
            .formParam("type-of-licence[licence-type][ltyp_siContent][vehicle-type]", "app_veh_type_lgv")
            .formParam("type-of-licence[licence-type][ltyp_siContent][lgv-declaration][lgv-declaration-confirmation]", "0")
            .formParam("type-of-licence[licence-type][ltyp_siContent][lgv-declaration][lgv-declaration-confirmation]", "1")
            .formParam("form-actions[saveAndContinue]", "")
            .formParam("version", 1)
            .formParam("security", (Session session) -> session.get("securityToken"));


    public static HttpRequestActionBuilder createPSVApplication = http("create psv application")
            .post("application/create/")
            .headers(getFormHeaders())
            .check(
                    regex(SetUp.securityTokenPattern()).find().
                            saveAs("securityToken"))
            .formParam("type-of-licence[operator-location]", "N")
            .formParam("type-of-licence[operator-type]", "lcat_psv")
            .formParam("type-of-licence[licence-type]", "ltyp_sn")
            .formParam("form-actions[saveAndContinue]", "")
            .formParam("security", (Session session) -> session.get("securityToken"));

    public static HttpRequestActionBuilder showDashboard = http("Show dashboard")
            .get("/")
            .check(regex("href=\"/application/(.+?)/").find().saveAs("applicationNumber"));

    public static HttpRequestActionBuilder getBusinessTypePage = http("Show Business Type Page")
            .get("application/${applicationNumber}/business-type/")
            .check(
                    regex(SetUp.securityTokenPattern()).
                            find().saveAs("securityToken"));

    public static HttpRequestActionBuilder businessType = http("business type")
            .post("application/#{applicationNumber}/business-type/")
            .headers(getFormHeaders())
            .formParam("data[type]", "org_t_rc")
            .formParam("form-actions[saveAndContinue]", "")
            .formParam("version", "1")
            .formParam("security", (Session session) -> session.get("securityToken"))
            .check(status().in(200, 209, 302, 304));

    public static HttpRequestActionBuilder getBusinessDetailsPage = http("Show Business Details Page")
            .get("application/#{applicationNumber}/business-details/")
            .headers(getAcceptHeaders())
            .check(
                    regex(SetUp.securityTokenPattern()).
                            find().saveAs("securityToken"));

    public static HttpRequestActionBuilder businessDetails = http("business details")
            .post("application/#{applicationNumber}/business-details/")
            .headers(getFormHeaders())
            .formParam("data[tradingNames][0][name]", "")
            .formParam("data[tradingNames][0][id]", "")
            .formParam("data[tradingNames][0][version]", "")
            .formParam("data[companyNumber][company_number]", "07104043")
            .formParam("registeredAddress[id]", "")
            .formParam("registeredAddress[version]", "2")
            .formParam("data[natureOfBusiness]", "Performance Testing")
            .formParam("registeredAddress[addressLine1]", "1 Gatling House")
            .formParam("registeredAddress[addressLine2]", "VOL")
            .formParam("registeredAddress[addressLine3]", "")
            .formParam("registeredAddress[addressLine4]", "")
            .formParam("registeredAddress[town]", "Nottingham")
            .formParam("registeredAddress[postcode]", "NG2 3HX")
            .formParam("table[rows]", "0")
            .formParam("form-actions[saveAndContinue]", "")
            .formParam("version", "1")
            .formParam("security", (Session session) -> session.get("securityToken"));
}