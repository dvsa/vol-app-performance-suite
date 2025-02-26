package journeySteps


import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.http.request.builder.HttpRequestBuilder

import org.slf4j.LoggerFactory
import utils.SetUp
import utils.Utilities.{CONFIG, orderRef, password, randomInt}

import scala.language.postfixOps

class ApplicationJourneySteps {

  val newPassword: String = CONFIG.getString("password")
  val header_ : Map[String, String] = Map("Accept" -> "*/*")
  private val logger = LoggerFactory.getLogger(classOf[ApplicationJourneySteps])


  
  private val headers_1 = Map(
    "Origin" -> SetUp.baseURL,
    "Sec-Fetch-Dest" -> "document",
    "Sec-Fetch-Mode" -> "navigate",
    "Sec-Fetch-Site" -> "same-origin",
    "Sec-Fetch-User" -> "?1",
    "content-type" -> "application/x-www-form-urlencoded"
  )

  private val headers_welcome = Map(
    "accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8",
    "sec-fetch-dest" -> "document",
    "sec-fetch-mode" -> "navigate",
    "sec-fetch-site" -> "same-origin",
    "sec-fetch-user" -> "?1",
    "cache-control" -> "no-store, no-cache, must-revalidate",
    "pragma" -> "no-cache"
  )

  val getUserStatus: HttpRequestBuilder = http("do you already have a licence")
    .get("register/")
    .headers(header_)
    .check(
      regex(SetUp.securityTokenPattern).
        find.saveAs("securityToken"))

  val setUserStatus: HttpRequestBuilder = http("inform about your status")
    .post("register/")
    .headers(header_)
    .formParam("fields[licenceContent][licenceNumber]", _ => s"N")
    .formParam("form-actions[submit]", _ => "")
    .formParam("security", session => session("securityToken").validate[String])

  val getOperatorRepresentation: HttpRequestBuilder = http("get registration page")
    .get("register/operator-representation/")
    .headers(header_)

  val operatorRepresentation: HttpRequestBuilder = http("acting on behalf of")
    .post("register/operator-representation/")
    .headers(header_)
    .formParam("fields[actingOnOperatorsBehalf]", _ => s"N")
    .formParam("fields[licenceNumber]", _ => s"")
    .formParam("form-actions[submit]", _ => s"")
    .formParam("security", session => session("securityToken").as[String])

  val getRegistrationPage: HttpRequestBuilder = http("get registration page")
    .get("register/operator/")
    .headers(header_)

  val registerNewAccount: HttpRequestBuilder = http("register a new account")
    .post("register/operator/")
    .headers(header_)
    .formParam("fields[loginId]", session => s"GatlingVOLUser${orderRef()}")
    .formParam("fields[forename]", session => s"Gatling${orderRef()}")
    .formParam("fields[familyName]", _ => s"Tester")
    .formParam("fields[emailAddress]", _ => s"gatling.tests@volTesting.com")
    .formParam("fields[emailConfirm]", _ => s"gatling.tests@volTesting.com")
    .formParam("fields[isLicenceHolder]", _ => s"N")
    .formParam("fields[licenceNumber]", _ => s"")
    .formParam("fields[organisationName]", _ => s"VOL-Performance-Test")
    .formParam("fields[businessType]", _ => s"org_t_rc")
    .formParam("fields[translateToWelsh]", _ => s"N")
    .formParam("fields[termsAgreed]", _ => s"Y")
    .formParam("form-actions[submit]", _ => s"")
    .formParam("security", session => session("securityToken").as[Any])
    .check(
      regex("Check your email").exists,
      status.is(200),
    )

  val getWelcomePage: HttpRequestBuilder = http("get welcome page")
    .get("/welcome")
    .headers(headers_welcome)
    .check(
      regex(SetUp.securityTokenPattern).find.saveAs("securityToken"),
      status.in(200, 302)
    )

  val getDashboardAfterWelcome: HttpRequestBuilder = http("get dashboard after welcome")
    .get("/dashboard")
    .headers(header_)
    .check(
      regex(SetUp.securityTokenPattern).find.saveAs("securityToken")
    )

  val submitWelcomePage: HttpRequestBuilder = http("accept terms and continue")
    .post("/welcome")
    .headers(headers_1)
    .formParam("main[termsAgreed]", session => session("Y").as[Any])
    .formParam("form-actions[submit]", session => session("").as[Any])
    .formParam("security", session => session("securityToken").as[String])

  val followWelcomeRedirect: HttpRequestBuilder = http("follow welcome redirect")
    .get("$(redirectUrl)")
    .headers(header_)
    .check(status.in(200, 302))

  val changePassword: HttpRequestBuilder = http("change password")
    .post("auth/expired-password/")
    .formParam("oldPassword", session => session(password()).as[String])
    .formParam("newPassword", session => session(newPassword).as[String])
    .formParam("confirmPassword", session => session(newPassword).as[String])
    .formParam("submit", session => session("Save").as[Any])
    .formParam("security", session => session("securityToken").as[String])

  val getLoginPage: HttpRequestBuilder = http("get login page")
    .get("auth/login/")
    .headers(header_)
    .check(
      regex(SetUp.securityTokenPattern).
        find.saveAs("securityToken"))

  val loginPage: HttpRequestBuilder = http("login")
    .post("auth/login/")
    .check(regex(SetUp.location).find.optional.saveAs("Location"))
    .formParam("username", session => session("Username").as[String])
    .formParam("password", session => session(password()).as[String])
    .formParam("submit", session => session("Sign in").as[Any])
    .formParam("security", session => session("securityToken").as[String])

  val landingPage: HttpRequestBuilder = http("Landing Page")
    .get("/")
    .check(regex("${Forename}"))
    .check(bodyString.saveAs("login_response"))

  val createNonLGVApplication: HttpRequestBuilder = http("choose country")
    .post("application/create/")
    .formParam("type-of-licence[operator-location]", session => session("N").as[Any])
    .formParam("type-of-licence[operator-type]", session => session("lcat_gv").as[Any])
    .formParam("type-of-licence[licence-type]", session => session("ltyp_sn").as[Any])
    .formParam("form-actions[saveAndContinue]", session => session("").as[Any])
    .formParam("security", session => session("securityToken").as[String])

  val getCreateApplicationPage: HttpRequestBuilder = http("get application")
    .get("application/create/")

  val createLGVApplication: HttpRequestBuilder = http("create application")
    .post("application/create/")
    .headers(headers_1)
    .check(
      regex(SetUp.securityTokenPattern).
        find.saveAs("securityToken"))
    .formParam("type-of-licence[operator-location]", session => session("N").as[Any])
    .formParam("type-of-licence[operator-type]", session => session("lcat_gv").as[Any])
    .formParam("type-of-licence[licence-type][licence-type]", session => session("ltyp_si").as[Any])
    .formParam("type-of-licence[licence-type][ltyp_siContent][vehicle-type]", session => session("app_veh_type_lgv").as[Any])
    .formParam("type-of-licence[licence-type][ltyp_siContent][lgv-declaration][lgv-declaration-confirmation]", session => session("0").as[Any])
    .formParam("type-of-licence[licence-type][ltyp_siContent][lgv-declaration][lgv-declaration-confirmation]", session => session("1").as[Any])
    .formParam("form-actions[saveAndContinue]", session => session("").as[Any])
    .formParam("version", 1)
    .formParam("security", session => session("securityToken").as[String])


  val createPSVApplication: HttpRequestBuilder = http("create psv application")
    .post("application/create/")
    .headers(headers_1)
    .check(
      regex(SetUp.securityTokenPattern).
        find.saveAs("securityToken"))
    .formParam("type-of-licence[operator-location]", session => session("N").as[Any])
    .formParam("type-of-licence[operator-type]", session => session("lcat_psv").as[Any])
    .formParam("type-of-licence[licence-type]", session => session("ltyp_sn").as[Any])
    .formParam("form-actions[saveAndContinue]", session => session("").as[Any])
    .formParam("security", session => session("securityToken").as[String])

  val showDashboard: HttpRequestBuilder = http("Show dashboard")
    .get("/")
    .check(regex("""href="/application/(.+?)/"""").find.saveAs("applicationNumber"))

  val getBusinessTypePage: HttpRequestBuilder = http("Show Business Type Page")
    .get("application/${applicationNumber}/business-type/")
    .check(
      regex(SetUp.securityTokenPattern).
        find.saveAs("securityToken"))

  val businessType: HttpRequestBuilder = http("business type")
    .post("application/${applicationNumber}/business-type/")
    .headers(headers_1)
    .formParam("data[type]", session => session("org_t_rc").as[Any])
    .formParam("form-actions[saveAndContinue]", session => session("").as[Any])
    .formParam("version", session => randomInt())
    .formParam("security", session => session("securityToken").as[Any])
    .check(status.in(200, 209, 302, 304))

  val getBusinessDetailsPage: HttpRequestBuilder = http("Show Business Details Page")
    .get("application/${applicationNumber}/business-details/")
    .headers(headers_1)
    .check(
      regex(SetUp.securityTokenPattern).
        find.saveAs("securityToken"))

  val businessDetails: HttpRequestBuilder = http("business details")
    .post("application/${applicationNumber}/business-details/")
    .headers(headers_1)
    .formParam("data[tradingNames][0][name]", session => session("name").asOption[String].getOrElse(""))
    .formParam("data[tradingNames][0][id]", session => session("id").asOption[String].getOrElse(""))
    .formParam("data[tradingNames][0][version]", session => session("version").asOption[String].getOrElse(""))
    .formParam("data[companyNumber][company_number]", session => session("number").asOption[String].getOrElse("07104043"))
    .formParam("registeredAddress[id]", session => session("id").asOption[String].getOrElse(""))
    .formParam("registeredAddress[version]", session => session("version").asOption[String].getOrElse("2"))
    .formParam("data[natureOfBusiness]", session => session("natureOfBusiness").asOption[String].getOrElse("Performance Testing"))
    .formParam("registeredAddress[addressLine1]", session => session("addressLine1").asOption[String].getOrElse("1 Gatling House"))
    .formParam("registeredAddress[addressLine2]", session => session("addressLine2").asOption[String].getOrElse("VOL"))
    .formParam("registeredAddress[addressLine3]", session => session("addressLine3").asOption[String].getOrElse(""))
    .formParam("registeredAddress[addressLine3]", session => session("addressLine4").asOption[String].getOrElse(""))
    .formParam("registeredAddress[town]", session => session("town").asOption[String].getOrElse("Nottingham"))
    .formParam("registeredAddress[postcode]", session => session("postcode").asOption[String].getOrElse("NG2 3HX"))
    .formParam("table[rows]", session => session("rows").asOption[String].getOrElse("0"))
    .formParam("form-actions[saveAndContinue]", session => session("saveAndContinue").asOption[String].getOrElse(""))
    .formParam("version", session => session("version").asOption[String].getOrElse("1"))
    .formParam("security", session => session("token").asOption[String].getOrElse("securityToken"))

  val addresses: HttpRequestBuilder = http("addresses")
    .post("application/${applicationNumber}/addresses/")
    .formParam("correspondence_address[version]", session => randomInt())
    .formParam("correspondence[version]", session => randomInt())
    .formParam("correspondence_address[searchPostcode][postcode]", session => session("NG1 5FW").as[Any])
    .formParam("correspondence_address[addressLine1]", session => session("3 WOLLATON STREET").as[Any])
    .formParam("correspondence_address[town]", session => session("NOTTINGHAM").as[Any])
    .formParam("correspondence_address[postcode]", session => session("NG1 5FW").as[Any])
    .formParam("registeredAddress[town]", session => session("Nottingham").as[Any])
    .formParam("registeredAddress[postcode]", session => session("NG23HX").as[Any])
    .formParam("correspondence_address[countryCode]", session => session("GB").as[Any])
    .formParam("contact[phone_primary]", session => session("07123456789").as[Any])
    .formParam("contact[phone_primary_version]", session => randomInt())
    .formParam("contact[email]", session => session("test@test.com").as[Any])
    .formParam("establishment_address[countryCode]", session => session("GB").as[Any])
    .formParam("security", session => session("securityToken").as[String])

  val getLicenceAuthorisationPage: HttpRequestBuilder = http("Show Licence Authorisation Page")
    .get("application/${applicationNumber}/operating-centres/")
    .check(
      regex(SetUp.securityTokenPattern).
        find.saveAs("securityToken"))

  val licenceAuthorisation: HttpRequestBuilder = http("licence authorisation")
    .post("application/${applicationNumber}/operating-centres/")
    .headers(headers_1)
    .formParam("data[version]", session => randomInt())
    .formParam("data[totAuthLgvVehiclesFieldset][totAuthLgvVehicles]", session => session("5").as[Any])
    .formParam("data[totCommunityLicencesFieldset][totCommunityLicences]", session => session("5").as[Any])
    .formParam("form-actions[saveAndContinue]", session => session("").as[Any])
    .formParam("security", session => session("securityToken").as[String])

  val director: HttpRequestBuilder = http("people")
    .post("application/${applicationNumber}/people/add/")
    .formParam("data[title]", session => session("title_mr").as[Any])
    .formParam("data[forename]", session => session("Director").as[Any])
    .formParam("data[familyName]", session => session("Gatling").as[Any])
    .formParam("data[birthDate][day]", session => session("10").as[Any])
    .formParam("data[birthDate][month]", session => session("10").as[Any])
    .formParam("data[birthDate][year]", session => session("1980").as[Any])
    .formParam("table[rows]", session => session("0").as[Any])
    .formParam("correspondence_address[town]", session => session("NOTTINGHAM").as[Any])
    .formParam("form-actions[saveAndContinue]", session => session("").as[Any])
    .formParam("security", session => session("securityToken").as[Any])

  val saveDirectorDetails: HttpRequestBuilder = http("people")
    .post("application/${applicationNumber}/people/")
    .formParam("table[rows]", session => session("1").as[Any])
    .formParam("form-actions[saveAndContinue]", session => session("").as[Any])
    .formParam("security", session => session("securityToken").as[Any])

  val operatingCentreDetails: HttpRequestBuilder = http("add operating centres")
    .post("application/${applicationNumber}/operating-centres/add/")
    .formParam("address[searchPostcode][postcode]", session => session("NG1 5FW").as[Any])
    .formParam("address[addressLine1]", session => session("3 WOLLATON STREET").as[Any])
    .formParam("address[town]", session => session("NOTTINGHAM").as[Any])
    .formParam("address[postcode]", session => session("NG1 5FW").as[Any])
    .formParam("address[countryCode]", session => session("GB").as[Any])
    .formParam("data[noOfVehiclesRequired]", session => session("5").as[Any])
    .formParam("data[noOfTrailersRequired]", session => session("5").as[Any])
    .formParam("advertisements[radio]", session => session("adSendByPost").as[Any])
    .formParam("data[permission][permission]", session => session("Y").as[Any])
    .formParam("form-actions[submit]", session => session("").as[Any])
    .formParam("version", session => randomInt())
    .formParam("security", session => session("securityToken").as[Any])

  val operatorCentreVehicleDetails: HttpRequestBuilder = http("submit operating centres")
    .post("application/${applicationNumber}/operating-centres/")
    .formParam("table[rows]", session => session("1").as[Any])
    .formParam("data[version]", session => randomInt())
    .formParam("data[totAuthHgvVehiclesFieldset][totAuthHgvVehicles]", session => session("5").as[Any])
    .formParam("data[totAuthTrailersFieldset][totAuthTrailers]", session => session("5").as[Any])
    .formParam("data[totCommunityLicencesFieldset][totCommunityLicences]", session => session("3").as[Any])
    .formParam("form-actions[saveAndContinue]", session => session("").as[Any])
    .formParam("security", session => session("securityToken").as[String])

  val financialEvidence: HttpRequestBuilder = http("submit financial evidence")
    .post("application/${applicationNumber}/financial-evidence/")
    .formParam("evidence[uploadedFileCount]", session => session("0").as[Any])
    .formParam("evidence[files][fileCount]", session => session("").as[Any])
    .formParam("evidence[files][file]", session => session("(binary)").as[Any])
    .formParam("evidence[files][__messages__]", session => session("").as[Any])
    .formParam("evidence[uploadNow]", session => session("0").as[Any])
    .formParam("form-actions[saveAndContinue]", session => session("").as[Any])
    .formParam("version", session => randomInt())
    .formParam("id", session => session("applicationNumber").as[String])
    .formParam("security", session => session("securityToken").as[String])

  val getTransportManagersPage: HttpRequestBuilder = http("Show Transport Manager Page")
    .get("application/${applicationNumber}/transport-managers/")
    .check(
      regex(SetUp.securityTokenPattern).
        find.saveAs("securityToken"))

  val transportManagersPage: HttpRequestBuilder = http("add transport manager")
    .post("application/${applicationNumber}/transport-managers/")
    .headers(headers_1)
    .formParam("table[rows]", session => session("0").as[Any])
    .formParam("table[action]", session => session("Add").as[Any])
    .formParam("security", session => session("securityToken").as[String])

  val navigateToAddTransportManagersPage: HttpRequestBuilder = http("Navigate To Add TM Page")
    .post("application/${applicationNumber}/transport-managers/add/")
    .headers(headers_1)
    .disableFollowRedirect
    .formParam("data[registeredUser]", session => session("").as[Any])
    .formParam("data[addUser]", session => session("").as[Any])
    .formParam("security", session => session("securityToken").as[String])
    .check(status.in(200, 209, 302, 304))

  val transportManagersDetails: HttpRequestBuilder = http("Add Transport Manager Details")
    .post("application/${applicationNumber}/transport-managers/addNewUser/")
    .headers(headers_1)
    .formParam("data[forename]", session => session("LGV-Stefan-Andy").as[Any])
    .formParam("data[familyName]", session => session("Gatling").as[Any])
    .formParam("data[birthDate][day]", session => session("10").as[Any])
    .formParam("data[birthDate][month]", session => session("10").as[Any])
    .formParam("data[birthDate][year]", session => session("1990").as[Any])
    .formParam("data[hasEmail]", session => session("Y").as[Any])
    .formParam("data[username]", session => s"GatlingVOLUser${orderRef()}")
    .formParam("data[emailAddress]", session => session("Gatling@vol.gov").as[Any])
    .formParam("data[emailConfirm]", session => session("Gatling@vol.gov").as[Any])
    .formParam("data[translateToWelsh]", session => session("N").as[Any])
    .formParam("form-actions[continue]", session => session("").as[Any])
    .formParam("security", session => session("securityToken").as[String])

  val selectTransportManager: HttpRequestBuilder = http("select transport manager")
    .post("application/${applicationNumber}/transport-managers/add/")
    .formParam("data[registeredUser]", session => session("transportManagerId").as[Any])
    .formParam("form-actions[continue]", session => session("").as[Any])
    .formParam("security", session => session("securityToken").as[String])
    .check(currentLocationRegex("""details/(.*)""").saveAs("tmaId"))

  val transportManagerDetails: HttpRequestBuilder = http("submit transport manager")
    .post("application/${applicationNumber}/transport-managers/details/${tmaId}")
    .formParam("details[birthDate][day]", session => session("12").as[Any])
    .formParam("details[birthDate][month]", session => session("08").as[Any])
    .formParam("details[birthDate][year]", session => session("1970").as[Any])
    .formParam("details[emailAddress]", session => session("gatling.tester@dvsa.com").as[Any])
    .formParam("details[birthPlace]", session => session("Nottingham").as[Any])
    .formParam("homeAddress[searchPostcode][postcode]", session => session("NG1 5FW").as[Any])
    .formParam("homeAddress[addressLine1]", session => session("THE AXIS").as[Any])
    .formParam("homeAddress[town]", session => session("NOTTINGHAM").as[Any])
    .formParam("homeAddress[postcode]", session => session("NG1 5FW").as[Any])
    .formParam("homeAddress[countryCode]", session => session("GB").as[Any])
    .formParam("workAddress[addressLine1]", session => session("3 WOLLATON STREET").as[Any])
    .formParam("workAddress[town]", session => session("NOTTINGHAM").as[Any])
    .formParam("workAddress[postcode]", session => session("NG1 5FW").as[Any])
    .formParam("workAddress[countryCode]", session => session("GB").as[Any])
    .formParam("responsibilities[tmType]", session => session("tm_t_i").as[Any])
    .formParam("responsibilities[isOwner]", session => session("Y").as[Any])
    .formParam("details[birthDate][day]", session => session("16").as[Any])
    .formParam("details[birthDate][month]", session => session("09").as[Any])
    .formParam("details[birthDate][year]", session => session("1926").as[Any])
    .formParam("responsibilities[hoursOfWeek][hoursPerWeekContent][hoursMon]", session => session("7").as[Any])
    .formParam("responsibilities[hoursOfWeek][hoursPerWeekContent][hoursTue]", session => session("8").as[Any])
    .formParam("responsibilities[hoursOfWeek][hoursPerWeekContent][hoursWed]", session => session("10").as[Any])
    .formParam("responsibilities[hoursOfWeek][hoursPerWeekContent][hoursThu]", session => session("10").as[Any])
    .formParam("responsibilities[hoursOfWeek][hoursPerWeekContent][hoursFri]", session => session("10").as[Any])
    .formParam("responsibilities[hoursOfWeek][hoursPerWeekContent][hoursSat]", session => session("10").as[Any])
    .formParam("responsibilities[hoursOfWeek][hoursPerWeekContent][hoursSun]", session => session("10").as[Any])
    .formParam("responsibilities[otherLicencesFieldset][hasOtherLicences]", session => session("N").as[Any])
    .formParam("responsibilities[otherLicencesFieldset][otherLicences][rows]", session => session("0").as[Any])
    .formParam("otherEmployments[hasOtherEmployment]", session => session("N").as[Any])
    .formParam("otherEmployments[otherEmployment][rows]", session => session("0").as[Any])
    .formParam("previousHistory[hasConvictions]", session => session("N").as[Any])
    .formParam("previousHistory[hasPreviousLicences]", session => session("N").as[Any])
    .formParam("previousHistory[previousLicences][rows]", session => session("0").as[Any])
    .formParam("security", session => session("securityToken").as[String])

  val transportManagerAnswers: HttpRequestBuilder = http("submit check your answers")
    .post("application/${applicationNumber}/transport-managers/check-answer/${tmaId}confirm/")
    .formParam("form-actions[submit]", session => session("").as[Any])
    .formParam("security", session => session("securityToken").as[String])

  val submitTransportManagerAnswers: HttpRequestBuilder = http("submit check your answers")
    .post("application/${applicationNumber}/transport-managers/tm-declaration/${tmaId}")
    .formParam("content[isDigitallySigned]", session => session("N").as[Any])
    .formParam("form-actions[submit]", session => session("").as[Any])
    .formParam("version", session => randomInt())
    .formParam("security", session => session("securityToken").as[String])

  val vehicleDetails: HttpRequestBuilder = http("submit vehicle")
    .post("application/${applicationNumber}/vehicles")
    .formParam("query[vrm]", session => session("").as[Any])
    .formParam("query[disc]", session => session("").as[Any])
    .formParam("query[includeRemoved]", session => session("").as[Any])
    .formParam("data[version]", session => randomInt())
    .formParam("data[hasEnteredReg]", session => session("N").as[Any])
    .formParam("vehicles[rows]", session => session("0").as[Any])
    .formParam("form-actions[saveAndContinue]", session => session("").as[Any])
    .formParam("security", session => session("securityToken").as[String])

  val safetyInspector: HttpRequestBuilder = http("add safety")
    .post("application/${applicationNumber}/safety/add")
    .formParam("data[isExternal]", session => session("Y").as[Any])
    .formParam("contactDetails[fao]", session => session("Inspector Gadget").as[Any])
    .formParam("address[searchPostcode][postcode]", session => session("NG1 5FW").as[Any])
    .formParam("address[addressLine1]", session => session("APARTMENT 18").as[Any])
    .formParam("address[addressLine2]", session => session("THE AXIS").as[Any])
    .formParam("address[town]", session => session("NOTTINGHAM").as[Any])
    .formParam("address[postcode]", session => session("NG1 5FW").as[Any])
    .formParam("address[countryCode]", session => session("GB").as[Any])
    .formParam("form-actions[submit]", session => session("").as[Any])
    .formParam("security", session => session("securityToken").as[String])

  val safetyCompliance: HttpRequestBuilder = http("safety compliance")
    .post("application/${applicationNumber}/safety/")
    .formParam("licence[version]", session => randomInt())
    .formParam("licence[safetyInsVehicles]", session => session("10").as[Any])
    .formParam("licence[safetyInsTrailers]", session => session("5").as[Any])
    .formParam("licence[safetyInsVaries]", session => session("N").as[Any])
    .formParam("licence[tachographIns]", session => session("tach_internal").as[Any])
    .formParam("licence[tachographInsName]", session => session("").as[Any])
    .formParam("table[rows]", session => session("1").as[Any])
    .formParam("application[version]", session => randomInt())
    .formParam("application[safetyConfirmation]", session => session("Y").as[Any])
    .formParam("form-actions[saveAndContinue]", session => session("").as[Any])
    .formParam("security", session => session("securityToken").as[String])

  val financeHistory: HttpRequestBuilder = http("finance history")
    .post("application/${applicationNumber}/financial-history")
    .formParam("data[id]", session => session("applicationNumber").as[Any])
    .formParam("data[version]", session => randomInt())
    .formParam("data[bankrupt]", session => session("N").as[Any])
    .formParam("data[liquidation]", session => session("N").as[Any])
    .formParam("data[receivership]", session => session("N").as[Any])
    .formParam("data[administration]", session => session("N").as[Any])
    .formParam("data[disqualified]", session => session("N").as[Any])
    .formParam("data[insolvencyDetails]", session => session("").as[Any])
    .formParam("data[file][fileCount]", session => session("").as[Any])
    .formParam("data[file][file]", session => session("(binary)").as[Any])
    .formParam("data[file][__messages__]", session => session("").as[Any])
    .formParam("data[financialHistoryConfirmation][insolvencyConfirmation]", session => session("Y").as[Any])
    .formParam("data[niFlag]", session => session("N").as[Any])
    .formParam("form-actions[saveAndContinue]", session => session("").as[Any])
    .formParam("security", session => session("securityToken").as[String])

  val licenceHistory: HttpRequestBuilder = http("licence history")
    .post("application/${applicationNumber}/licence-history/")
    .formParam("data[prevHasLicence]", session => session("N").as[Any])
    .formParam("data[prevHasLicence-table][rows]", session => session("0").as[Any])
    .formParam("data[prevHadLicence]", session => session("N").as[Any])
    .formParam("data[prevHadLicence-table][rows]", session => session("0").as[Any])
    .formParam("data[prevBeenDisqualifiedTc]", session => session("N").as[Any])
    .formParam("data[prevBeenDisqualifiedTc-table][rows]", session => session("0").as[Any])
    .formParam("eu[prevBeenRefused]", session => session("N").as[Any])
    .formParam("eu[prevBeenRefused-table][rows]", session => session("0").as[Any])
    .formParam("eu[prevBeenRevoked]", session => session("N").as[Any])
    .formParam("eu[prevBeenRevoked-table][rows]", session => session("0").as[Any])
    .formParam("pi[prevBeenAtPi]", session => session("N").as[Any])
    .formParam("pi[prevBeenAtPi-table][rows]", session => session("0").as[Any])
    .formParam("assets[prevPurchasedAssets]", session => session("N").as[Any])
    .formParam("assets[prevPurchasedAssets-table][rows]", session => session("0").as[Any])
    .formParam("form-actions[saveAndContinue]", session => session("").as[Any])
    .formParam("version", session => randomInt())
    .formParam("security", session => session("securityToken").as[String])

  val convictionsAndPenalties: HttpRequestBuilder = http("convictions penalties")
    .post("application/${applicationNumber}/convictions-penalties")
    .formParam("data[version]", session => randomInt())
    .formParam("data[question]", session => session("N").as[Any])
    .formParam("data[table][rows]", session => session("0").as[Any])
    .formParam("convictionsConfirmation[convictionsConfirmation]", session => session("Y").as[Any])
    .formParam("form-actions[saveAndContinue]", session => session("").as[Any])
    .formParam("security", session => session("securityToken").as[String])

  val undertakings: HttpRequestBuilder = http("undertakings")
    .post("application/${applicationNumber}/undertakings/").disableFollowRedirect
    .formParam("declarationsAndUndertakings[signatureOptions]", session => session("N").as[Any])
    .formParam("interim[goodsApplicationInterim]", session => session("N").as[Any])
    .formParam("interim[YContent][goodsApplicationInterimReason]", session => session("").as[Any])
    .formParam("declarationsAndUndertakings[version]", session => randomInt())
    .formParam("declarationsAndUndertakings[id]", session => session("applicationNumber").as[String])
    .formParam("form-actions[submitAndPay]", session => session("").as[Any])
    .formParam("security", session => session("securityToken").as[String])
    .check(regex(session => s"Application Fee for application ${session("applicationNumber").as[String]}"))
    .check(bodyString.saveAs("undertakings"))

  val cpmsRedirect: HttpRequestBuilder = http("navigate to cpms")
    .post("application/${applicationNumber}/pay-and-submit/")
    .formParam("form-actions[pay]", session => session("").as[Any])
    .formParam("security", session => session("securityToken").as[String])
    .check(bodyString.saveAs("pay"))
    .check(regex(SetUp.cpmsRedirectURL).find.exists)
}