package journeySteps;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static utils.GenericUtils.*;
import static utils.Header.getAcceptHeaders;
import static utils.Header.getFormHeaders;
import static utils.SetUp.env;

import activesupport.aws.s3.SecretsManager;
import activesupport.config.Configuration;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import utils.Header;
import utils.SetUp;


public class ApplicationJourneySteps {
    private static final String newPassword = new Configuration().getConfig().getString("password");

    public static HttpRequestActionBuilder getUserStatus = http("do you already have a licence")
            .get("register/")
            .headers(getAcceptHeaders())
            .check(
                    regex(SetUp.securityTokenPattern).find().saveAs("securityToken"));

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
            .get("welcome/")
            .headers(getAcceptHeaders())
            .check(
                    regex(SetUp.securityTokenPattern).find().saveAs("securityToken"),
                    status().in(200, 302)
            );

    public static HttpRequestActionBuilder getDashboardAfterWelcome = http("get dashboard after welcome")
            .get("dashboard/")
            .headers(getAcceptHeaders())
            .check(
                    regex(SetUp.securityTokenPattern).find().saveAs("securityToken")
            );

    public static HttpRequestActionBuilder submitWelcomePage = http("accept terms and continue")
            .post("welcome/")
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
            .headers(Header.getFormHeaders())
            .formParam("newPassword", newPassword)
            .formParam("confirmPassword", newPassword)
            .formParam("submit", "Save")
            .formParam("security", (Session session) -> session.get("securityToken"));

    public static HttpRequestActionBuilder getLoginPage = http("get login page")
            .get("auth/login/")
            .headers(getAcceptHeaders())
            .check(
                    regex(SetUp.securityTokenPattern).find().
                            saveAs("securityToken"));

    public static HttpRequestActionBuilder loginPage = http("login")
            .post("auth/login/")
            .headers(Header.getFormHeaders())
            .formParam("username", "#{Username}")
            .formParam("password", session -> {
                String password = session.get("Password");
                String environment = session.getString(env);
                if ("int".equals(environment)) {
                    return SecretsManager.getSecretValue("intEnvPassword");
                } else {
                    return password;
                }
            })
            .formParam("submit", "")
            .formParam("security", (Session session) -> session.get("securityToken"))
            .check(regex(SetUp.location).find().optional().saveAs("Location"));

    public static HttpRequestActionBuilder landingPage = http("Landing Page")
            .get("/")
            .check(regex("#{Forename}"))
            .check(bodyString().find().saveAs("login_response"));

    public static HttpRequestActionBuilder createNonLGVApplication = http("choose country")
            .post("application/create/")
            .headers(Header.getFormHeaders())
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
                    regex(SetUp.securityTokenPattern).find().
                            saveAs("securityToken"))
            .formParam("type-of-licence[operator-location]", "N")
            .formParam("type-of-licence[operator-type]", "lcat_gv")
            .formParam("type-of-licence[licence-type][licence-type]", "ltyp_si")
            .formParam("type-of-licence[licence-type][ltyp_siContent][vehicle-type]", "app_veh_type_lgv")
            .formParam("type-of-licence[licence-type][ltyp_siContent][lgv-declaration][lgv-declaration-confirmation]", "0")
            .formParam("type-of-licence[licence-type][ltyp_siContent][lgv-declaration][lgv-declaration-confirmation]", "1")
            .formParam("form-actions[saveAndContinue]", "")
            .formParam("version", String.valueOf(String.valueOf(randomInt())))
            .formParam("security", (Session session) -> session.get("securityToken"));


    public static HttpRequestActionBuilder createPSVApplication = http("create psv application")
            .post("application/create/")
            .headers(getFormHeaders())
            .check(
                    regex(SetUp.securityTokenPattern).find().
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
                    regex(SetUp.securityTokenPattern).
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
                    regex(SetUp.securityTokenPattern).
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
            .formParam("version", String.valueOf(randomInt()))
            .formParam("security", (Session session) -> session.get("securityToken"));

    public static HttpRequestActionBuilder addresses = http("addresses")
            .post("application/#{applicationNumber}/addresses/")
            .headers(Header.getFormHeaders())
            .formParam("correspondence_address[version]", "")
            .formParam("correspondence[version]", "")
            .formParam("correspondence_address[searchPostcode][postcode]", "NG1 5FW")
            .formParam("correspondence_address[addressLine1]", "3 WOLLATON STREET")
            .formParam("correspondence_address[town]", "NOTTINGHAM")
            .formParam("correspondence_address[postcode]", "NG1 5FW")
            .formParam("registeredAddress[town]", "Nottingham")
            .formParam("registeredAddress[postcode]", "NG23HX")
            .formParam("correspondence_address[countryCode]", "GB")
            .formParam("contact[phone_primary]", "07123456789")
            .formParam("contact[phone_primary_version]", "")
            .formParam("contact[email]", "test@test.com")
            .formParam("establishment_address[countryCode]", "GB")
            .formParam("security", (Session session) -> session.get("securityToken"));

    public static HttpRequestActionBuilder getLicenceAuthorisationPage = http("Show Licence Authorisation Page")
            .get("application/${applicationNumber}/operating-centres/")
            .check(
                    regex(SetUp.securityTokenPattern).
                            find().saveAs("securityToken"));


    public static HttpRequestActionBuilder licenceAuthorisation = http("licence authorisation")
            .post("application/${applicationNumber}/operating-centres/")
            .headers(Header.getFormHeaders())
            .formParam("data[version]", String.valueOf(randomInt()))
            .formParam("data[totAuthLgvVehiclesFieldset][totAuthLgvVehicles]", "5")
            .formParam("data[totCommunityLicencesFieldset][totCommunityLicences]", "5")
            .formParam("form-actions[saveAndContinue]", "")
            .formParam("security", (Session session) -> session.get("securityToken"));

    public static HttpRequestActionBuilder director = http("add director")
            .post("application/#{applicationNumber}/people/add/")
            .headers(Header.getFormHeaders())
            .formParam("data[title]", "title_mr")
            .formParam("data[forename]", "Director")
            .formParam("data[familyName]", "Gatling")
            .formParam("data[birthDate][day]", "10")
            .formParam("data[birthDate][month]", "10")
            .formParam("data[birthDate][year]", "1980")
            .formParam("table[rows]", "0")
            .formParam("correspondence_address[town]", "NOTTINGHAM")
            .formParam("form-actions[saveAndContinue]", "")
            .formParam("security", (Session session) -> session.get("securityToken"));

    public static HttpRequestActionBuilder getDirector = http("get to directors page")
            .get("application/#{applicationNumber}/people/")
            .headers(Header.getAcceptHeaders());

    public static HttpRequestActionBuilder saveDirectorDetails = http("add a director")
            .post("application/#{applicationNumber}/people/")
            .headers(Header.getFormHeaders())
            .formParam("table[rows]", "0")
            .formParam("form-actions[saveAndContinue]", "Add")
            .formParam("security", (Session session) -> session.get("securityToken"));

    public static HttpRequestActionBuilder operatingCentreDetails = http("add operating centres")
            .post("application/${applicationNumber}/operating-centres/add/")
            .headers(Header.getFormHeaders())
            .formParam("address[searchPostcode][postcode]", "NG1 5FW")
            .formParam("address[addressLine1]", "3 WOLLATON STREET")
            .formParam("address[town]", "NOTTINGHAM")
            .formParam("address[postcode]", "NG1 5FW")
            .formParam("address[countryCode]", "GB")
            .formParam("data[noOfVehiclesRequired]", "5")
            .formParam("data[noOfTrailersRequired]", "5")
            .formParam("advertisements[radio]", "adSendByPost")
            .formParam("data[permission][permission]", "Y")
            .formParam("form-actions[submit]", "")
            .formParam("version", String.valueOf(randomInt()))
            .formParam("security", (Session session) -> session.get("securityToken"));

    public static HttpRequestActionBuilder operatorCentreVehicleDetails = http("submit operating centres")
            .post("application/${applicationNumber}/operating-centres/")
            .headers(Header.getFormHeaders())
            .formParam("table[rows]", "1")
            .formParam("data[version]", "2")
            .formParam("data[totAuthHgvVehiclesFieldset][totAuthHgvVehicles]", "5")
            .formParam("data[totAuthTrailersFieldset][totAuthTrailers]", "5")
            .formParam("data[totCommunityLicencesFieldset][totCommunityLicences]", "3")
            .formParam("form-actions[saveAndContinue]", "")
            .formParam("security", (Session session) -> session.get("securityToken"));

    public static HttpRequestActionBuilder financialEvidence = http("submit financial evidence")
            .post("application/#{applicationNumber}/financial-evidence/")
            .headers(Header.getFormHeaders())
            .formParam("evidence[uploadedFileCount]", "0")
            .formParam("evidence[files][fileCount]", "")
            .formParam("evidence[files][file]", "(binary)")
            .formParam("evidence[files][__messages__]", "")
            .formParam("evidence[uploadNow]", "0")
            .formParam("form-actions[saveAndContinue]", "")
            .formParam("version", "3")
            .formParam("id", "#{applicationNumber}")
            .formParam("security", (Session session) -> session.get("securityToken"));

    public static HttpRequestActionBuilder getTransportManagersPage = http("Show Transport Manager Page")
            .get("application/#{applicationNumber}/transport-managers/")
            .check(
                    regex(SetUp.securityTokenPattern).
                            find().saveAs("securityToken"));

    public static HttpRequestActionBuilder transportManagersPage = http("add transport manager")
            .post("application/#{applicationNumber}/transport-managers/")
            .headers(Header.getFormHeaders())
            .formParam("table[rows]", "0")
            .formParam("table[action]", "Add")
            .formParam("security", (Session session) -> session.get("securityToken"));

    public static HttpRequestActionBuilder navigateToAddTransportManagersPage = http("Navigate To Add TM Page")
            .post("application/#{applicationNumber}/transport-managers/add/")
            .headers(Header.getFormHeaders())
            .disableFollowRedirect()
            .formParam("data[registeredUser]", "")
            .formParam("data[addUser]", "")
            .formParam("security", "${securityToken}")
            .check(status().in(200, 209, 302, 304));

    public static HttpRequestActionBuilder transportManagersDetails = http("Add Transport Manager Details")
            .post("application/#{applicationNumber}/transport-managers/addNewUser/")
            .headers(Header.getFormHeaders())
            .formParam("data[forename]", "LGV-Stefan-Andy")
            .formParam("data[familyName]", "Gatling")
            .formParam("data[birthDate][day]", "10")
            .formParam("data[birthDate][month]", "10")
            .formParam("data[birthDate][year]", "1990")
            .formParam("data[hasEmail]", "Y")
            .formParam("data[username]", session -> "GatlingUser" + orderRef())
            .formParam("data[emailAddress]", "Gatling@vol.gov")
            .formParam("data[emailConfirm]", "Gatling@vol.gov")
            .formParam("data[translateToWelsh]", "N")
            .formParam("form-actions[continue]", "")
            .formParam("security", (Session session) -> session.get("securityToken"));

    public static HttpRequestActionBuilder selectTransportManager = http("select transport manager")
            .post("application/#{applicationNumber}/transport-managers/add/")
            .headers(Header.getFormHeaders())
            .formParam("data[registeredUser]", "#{transportManagerId}")
            .formParam("form-actions[continue]", "")
            .formParam("security", (Session session) -> session.get("securityToken"))
            .check(currentLocationRegex("details/(.*)").saveAs("tmaId"));

    public static HttpRequestActionBuilder transportManagerDetails = http("submit transport manager")
            .post("application/#{applicationNumber}/transport-managers/details/#{tmaId}")
            .headers(Header.getFormHeaders())
            .formParam("details[birthDate][day]", "12")
            .formParam("details[birthDate][month]", "08")
            .formParam("details[birthDate][year]", "1970")
            .formParam("details[emailAddress]", "gatling.tester@dvsa.com")
            .formParam("details[birthPlace]", "Nottingham")
            .formParam("homeAddress[searchPostcode][postcode]", "NG1 5FW")
            .formParam("homeAddress[addressLine1]", "THE AXIS")
            .formParam("homeAddress[town]", "NOTTINGHAM")
            .formParam("homeAddress[postcode]", "NG1 5FW")
            .formParam("homeAddress[countryCode]", "GB")
            .formParam("workAddress[addressLine1]", "3 WOLLATON STREET")
            .formParam("workAddress[town]", "NOTTINGHAM")
            .formParam("workAddress[postcode]", "NG1 5FW")
            .formParam("workAddress[countryCode]", "GB")
            .formParam("responsibilities[tmType]", "tm_t_i")
            .formParam("responsibilities[isOwner]", "Y")
            .formParam("details[birthDate][day]", "16")
            .formParam("details[birthDate][month]", "09")
            .formParam("details[birthDate][year]", "1926")
            .formParam("responsibilities[hoursOfWeek][hoursPerWeekContent][hoursMon]", "7")
            .formParam("responsibilities[hoursOfWeek][hoursPerWeekContent][hoursTue]", "8")
            .formParam("responsibilities[hoursOfWeek][hoursPerWeekContent][hoursWed]", "10")
            .formParam("responsibilities[hoursOfWeek][hoursPerWeekContent][hoursThu]", "10")
            .formParam("responsibilities[hoursOfWeek][hoursPerWeekContent][hoursFri]", "10")
            .formParam("responsibilities[hoursOfWeek][hoursPerWeekContent][hoursSat]", "10")
            .formParam("responsibilities[hoursOfWeek][hoursPerWeekContent][hoursSun]", "10")
            .formParam("responsibilities[otherLicencesFieldset][hasOtherLicences]", "N")
            .formParam("responsibilities[otherLicencesFieldset][otherLicences][rows]", "0")
            .formParam("otherEmployments[hasOtherEmployment]", "N")
            .formParam("otherEmployments[otherEmployment][rows]", "0")
            .formParam("previousHistory[hasConvictions]", "N")
            .formParam("previousHistory[hasPreviousLicences]", "N")
            .formParam("previousHistory[previousLicences][rows]", "0")
            .formParam("security", (Session session) -> session.get("securityToken"));

    public static HttpRequestActionBuilder transportManagerAnswers = http("submit check your answers")
            .post("application/#{applicationNumber}/transport-managers/check-answer/#{tmaId}confirm/")
            .headers(Header.getFormHeaders())
            .formParam("form-actions[submit]", "")
            .formParam("security", (Session session) -> session.get("securityToken"));

    public static HttpRequestActionBuilder submitTransportManagerAnswers = http("submit check your answers")
            .post("application/#{applicationNumber}/transport-managers/tm-declaration/#{tmaId}")
            .formParam("content[isDigitallySigned]", "N")
            .formParam("form-actions[submit]", "")
            .formParam("version", String.valueOf(randomInt()))
            .formParam("security", (Session session) -> session.get("securityToken"));

    public static HttpRequestActionBuilder vehicleDetails = http("submit vehicle")
            .post("application/#{applicationNumber}/vehicles")
            .headers(Header.getFormHeaders())
            .formParam("query[vrm]", "")
            .formParam("query[disc]", "")
            .formParam("query[includeRemoved]", "")
            .formParam("data[version]", String.valueOf(randomInt()))
            .formParam("data[hasEnteredReg]", "N")
            .formParam("vehicles[rows]", "0")
            .formParam("form-actions[saveAndContinue]", "")
            .formParam("security", (Session session) -> session.get("securityToken"));

    public static HttpRequestActionBuilder safetyInspector = http("add safety")
            .post("application/#{applicationNumber}/safety/add")
            .headers(Header.getFormHeaders())
            .formParam("data[isExternal]", "Y")
            .formParam("contactDetails[fao]", "Inspector Gadget")
            .formParam("address[searchPostcode][postcode]", "NG1 5FW")
            .formParam("address[addressLine1]", "APARTMENT 18")
            .formParam("address[addressLine2]", "THE AXIS")
            .formParam("address[town]", "NOTTINGHAM")
            .formParam("address[postcode]", "NG1 5FW")
            .formParam("address[countryCode]", "GB")
            .formParam("form-actions[submit]", "")
            .formParam("security", (Session session) -> session.get("securityToken"));

    public static HttpRequestActionBuilder safetyCompliance = http("safety compliance")
            .post("application/#{applicationNumber}/safety/")
            .headers(Header.getFormHeaders())
            .formParam("licence[version]", "6")
            .formParam("licence[safetyInsVehicles]", "10")
            .formParam("licence[safetyInsTrailers]", "5")
            .formParam("licence[safetyInsVaries]", "N")
            .formParam("licence[tachographIns]", "tach_internal")
            .formParam("licence[tachographInsName]", "")
            .formParam("table[rows]", "1")
            .formParam("application[version]", String.valueOf(randomInt()))
            .formParam("application[safetyConfirmation]", "Y")
            .formParam("form-actions[saveAndContinue]", "")
            .formParam("security", (Session session) -> session.get("securityToken"));

    public static HttpRequestActionBuilder financeHistory = http("finance history")
            .post("application/#{applicationNumber}/financial-history")
            .headers(Header.getFormHeaders())
            .formParam("data[id]", "#{applicationNumber}")
            .formParam("data[version]", "6")
            .formParam("data[bankrupt]", "N")
            .formParam("data[liquidation]", "N")
            .formParam("data[receivership]", "N")
            .formParam("data[administration]", "N")
            .formParam("data[disqualified]", "N")
            .formParam("data[insolvencyDetails]", "")
            .formParam("data[file][fileCount]", "")
            .formParam("data[file][file]", "(binary)")
            .formParam("data[file][__messages__]", "")
            .formParam("data[financialHistoryConfirmation][insolvencyConfirmation]", "Y")
            .formParam("data[niFlag]", "N")
            .formParam("form-actions[saveAndContinue]", "")
            .formParam("security", (Session session) -> session.get("securityToken"));

    public static HttpRequestActionBuilder licenceHistory = http("licence history")
            .post("application/#{applicationNumber}/licence-history/")
            .headers(Header.getFormHeaders())
            .formParam("data[prevHasLicence]", "N")
            .formParam("data[prevHasLicence-table][rows]", "0")
            .formParam("data[prevHadLicence]", "N")
            .formParam("data[prevHadLicence-table][rows]", "0")
            .formParam("data[prevBeenDisqualifiedTc]", "N")
            .formParam("data[prevBeenDisqualifiedTc-table][rows]", "0")
            .formParam("eu[prevBeenRefused]", "N")
            .formParam("eu[prevBeenRefused-table][rows]", "0")
            .formParam("eu[prevBeenRevoked]", "N")
            .formParam("eu[prevBeenRevoked-table][rows]", "0")
            .formParam("pi[prevBeenAtPi]", "N")
            .formParam("pi[prevBeenAtPi-table][rows]", "0")
            .formParam("assets[prevPurchasedAssets]", "N")
            .formParam("assets[prevPurchasedAssets-table][rows]", "0")
            .formParam("form-actions[saveAndContinue]", "")
            .formParam("version", String.valueOf(randomInt()))
            .formParam("security", (Session session) -> session.get("securityToken"));

    public static HttpRequestActionBuilder convictionsAndPenalties = http("convictions penalties")
            .post("application/${applicationNumber}/convictions-penalties")
            .headers(Header.getFormHeaders())
            .formParam("data[version]", "8")
            .formParam("data[question]", "N")
            .formParam("data[table][rows]", "0")
            .formParam("convictionsConfirmation[convictionsConfirmation]", "Y")
            .formParam("form-actions[saveAndContinue]", "")
            .formParam("security", (Session session) -> session.get("securityToken"));

    public static HttpRequestActionBuilder undertakings = http("undertakings")
            .post("application/#{applicationNumber}/undertakings/")
            .headers(Header.getFormHeaders())
            .disableFollowRedirect()
            .formParam("declarationsAndUndertakings[signatureOptions]", "N")
            .formParam("interim[goodsApplicationInterim]", "N")
            .formParam("interim[YContent][goodsApplicationInterimReason]", "")
            .formParam("declarationsAndUndertakings[version]", String.valueOf(randomInt()))
            .formParam("declarationsAndUndertakings[id]", "${applicationNumber}")
            .formParam("form-actions[submitAndPay]", "")
            .formParam("security", (Session session) -> session.get("securityToken"))
            .check(regex("(.*) Application Fee for application #{applicationNumber}"))
            .check(bodyString().saveAs("undertakings"));

    public static HttpRequestActionBuilder cpmsRedirect = http("navigate to cpms")
            .post("application/${applicationNumber}/pay-and-submit/")
            .headers(Header.getFormHeaders())
            .formParam("form-actions[pay]", "")
            .formParam("security", "${securityToken}")
            .check(bodyString().saveAs("pay"))
            .check(regex(SetUp.cpmsRedirectURL).find().exists());
}