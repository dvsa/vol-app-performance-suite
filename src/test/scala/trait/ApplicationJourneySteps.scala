package `trait`

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder
import utils.SetUp
import utils.Utilities.{CONFIG, orderRef, password, randomInt}

import scala.language.postfixOps

class ApplicationJourneySteps {

  val newPassword: String = CONFIG.getString("password")
  val header_ : Map[String, String] = Map("Accept" -> "*/*")

  private val headers_1 = Map(
    "Origin" -> SetUp.baseURL,
    "Sec-Fetch-Dest" -> "document",
    "Sec-Fetch-Mode" -> "navigate",
    "Sec-Fetch-Site" -> "same-origin",
    "Sec-Fetch-User" -> "?1",
    "content-type" -> "application/x-www-form-urlencoded"
  )

  val changePassword: HttpRequestBuilder = http("change password")
    .post("auth/expired-password/")
    .formParam("oldPassword", password())
    .formParam("newPassword", newPassword)
    .formParam("confirmPassword", newPassword)
    .formParam("submit", "Save")
    .formParam("security", "${securityToken}")

  val getLoginPage: HttpRequestBuilder = http("get login page")
    .get("auth/login/")
    .headers(header_)
    .check(
      regex(SetUp.securityTokenPattern).
        find.saveAs("securityToken"))

  val loginPage: HttpRequestBuilder = http("login")
    .post("auth/login/")
    .check(regex(SetUp.location).find.optional.saveAs("Location"))
    .formParam("username", "${Username}")
    .formParam("password", password())
    .formParam("submit", "Sign in")
    .formParam("security", "${securityToken}")

  val landingPage: HttpRequestBuilder = http("Landing Page")
    .get("/")
    .check(regex("${Forename}"))
    .check(bodyString.saveAs("login_response"))

  val createNonLGVApplication: HttpRequestBuilder = http("choose country")
    .post("application/create/")
    .formParam("type-of-licence[operator-location]", "N")
    .formParam("type-of-licence[operator-type]", "lcat_gv")
    .formParam("type-of-licence[licence-type]", "ltyp_sn")
    .formParam("form-actions[saveAndContinue]", "")
    .formParam("security", "${securityToken}")

  val getCreateApplicationPage: HttpRequestBuilder = http("get application")
    .get("application/create/")

  val createLGVApplication: HttpRequestBuilder = http("create application")
    .post("application/create/")
    .headers(headers_1)
    .check(
      regex(SetUp.securityTokenPattern).
        find.saveAs("securityToken"))
    .formParam("type-of-licence[operator-location]", "N")
    .formParam("type-of-licence[operator-type]", "lcat_gv")
    .formParam("type-of-licence[licence-type][licence-type]", "ltyp_si")
    .formParam("type-of-licence[licence-type][ltyp_siContent][vehicle-type]", "app_veh_type_lgv")
    .formParam("type-of-licence[licence-type][ltyp_siContent][lgv-declaration][lgv-declaration-confirmation]", "0")
    .formParam("type-of-licence[licence-type][ltyp_siContent][lgv-declaration][lgv-declaration-confirmation]", "1")
    .formParam("form-actions[saveAndContinue]", "")
    .formParam("version", "")
    .formParam("security", "${securityToken}")


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
    .formParam("data[type]", "org_t_rc")
    .formParam("form-actions[saveAndContinue]", "")
    .formParam("version", "1")
    .formParam("security", "${securityToken}")
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
    .formParam("security", "${securityToken}")

  val addresses: HttpRequestBuilder = http("addresses")
    .post("application/${applicationNumber}/addresses/")
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
    .formParam("security", "${securityToken}")

  val getLicenceAuthorisationPage: HttpRequestBuilder = http("Show Licence Authorisation Page")
    .get("application/${applicationNumber}/operating-centres/")
    .check(
      regex(SetUp.securityTokenPattern).
        find.saveAs("securityToken"))

  val licenceAuthorisation: HttpRequestBuilder = http("licence authorisation")
    .post("application/${applicationNumber}/operating-centres/")
    .headers(headers_1)
    .formParam("data[version]", "2")
    .formParam("data[totAuthLgvVehiclesFieldset][totAuthLgvVehicles]", "5")
    .formParam("data[totCommunityLicencesFieldset][totCommunityLicences]", "5")
    .formParam("form-actions[saveAndContinue]", "")
    .formParam("security", "${securityToken}")

  val director: HttpRequestBuilder = http("people")
    .post("application/${applicationNumber}/people/add/")
    .formParam("data[title]", "title_mr")
    .formParam("data[forename]", "Director")
    .formParam("data[familyName]", "Gatling")
    .formParam("data[birthDate][day]", "10")
    .formParam("data[birthDate][month]", "10")
    .formParam("data[birthDate][year]", "1980")
    .formParam("table[rows]", "0")
    .formParam("correspondence_address[town]", "NOTTINGHAM")
    .formParam("form-actions[saveAndContinue]", "")
    .formParam("security", "${securityToken}")

  val saveDirectorDetails: HttpRequestBuilder = http("people")
    .post("application/${applicationNumber}/people/")
    .formParam("table[rows]", "1")
    .formParam("form-actions[saveAndContinue]", "")
    .formParam("security", "${securityToken}")

  val operatingCentreDetails: HttpRequestBuilder = http("add operating centres")
    .post("application/${applicationNumber}/operating-centres/add/")
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
    .formParam("version", "1")
    .formParam("security", "${securityToken}")

  val operatorCentreVehicleDetails: HttpRequestBuilder = http("submit operating centres")
    .post("application/${applicationNumber}/operating-centres/")
    .formParam("table[rows]", "1")
    .formParam("data[version]", "2")
    .formParam("data[totAuthHgvVehiclesFieldset][totAuthHgvVehicles]", "5")
    .formParam("data[totAuthTrailersFieldset][totAuthTrailers]", "5")
    .formParam("data[totCommunityLicencesFieldset][totCommunityLicences]", "3")
    .formParam("form-actions[saveAndContinue]", "")
    .formParam("security", "${securityToken}")

  val financialEvidence: HttpRequestBuilder = http("submit financial evidence")
    .post("application/${applicationNumber}/financial-evidence/")
    .formParam("evidence[uploadedFileCount]", "0")
    .formParam("evidence[files][fileCount]", "")
    .formParam("evidence[files][file]", "(binary)")
    .formParam("evidence[files][__messages__]", "")
    .formParam("evidence[uploadNow]", "0")
    .formParam("form-actions[saveAndContinue]", "")
    .formParam("version", "3")
    .formParam("id", "${applicationNumber}")
    .formParam("security", "${securityToken}")

  val getTransportManagersPage: HttpRequestBuilder = http("Show Transport Manager Page")
    .get("application/${applicationNumber}/transport-managers/")
    .check(
      regex(SetUp.securityTokenPattern).
        find.saveAs("securityToken"))

  val transportManagersPage: HttpRequestBuilder = http("add transport manager")
    .post("application/${applicationNumber}/transport-managers/")
    .headers(headers_1)
    .formParam("table[rows]", "0")
    .formParam("table[action]", "Add")
    .formParam("security", "${securityToken}")

  val navigateToAddTransportManagersPage: HttpRequestBuilder = http("Navigate To Add TM Page")
    .post("application/${applicationNumber}/transport-managers/add/")
    .headers(headers_1)
    .disableFollowRedirect
    .formParam("data[registeredUser]", "")
    .formParam("data[addUser]", "")
    .formParam("security", "${securityToken}")
    .check(status.in(200, 209, 302, 304))

  val transportManagersDetails: HttpRequestBuilder = http("Add Transport Manager Details")
    .post("application/${applicationNumber}/transport-managers/addNewUser/")
    .headers(headers_1)
    .formParam("data[forename]", "LGV-Stefan-Andy")
    .formParam("data[familyName]", "Gatling")
    .formParam("data[birthDate][day]", "10")
    .formParam("data[birthDate][month]", "10")
    .formParam("data[birthDate][year]", "1990")
    .formParam("data[hasEmail]", "Y")
    .formParam("data[username]", _ => s"""GatlingUser${orderRef()}""")
    .formParam("data[emailAddress]", "Gatling@vol.gov")
    .formParam("data[emailConfirm]", "Gatling@vol.gov")
    .formParam("data[translateToWelsh]", "N")
    .formParam("form-actions[continue]", "")
    .formParam("security", "${securityToken}")

  val selectTransportManager: HttpRequestBuilder = http("select transport manager")
    .post("application/${applicationNumber}/transport-managers/add/")
    .formParam("data[registeredUser]", "${transportManagerId}")
    .formParam("form-actions[continue]", "")
    .formParam("security", "${securityToken}")
    .check(currentLocationRegex("""details/(.*)""").saveAs("tmaId"))

  val transportManagerDetails: HttpRequestBuilder = http("submit transport manager")
    .post("application/${applicationNumber}/transport-managers/details/${tmaId}")
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
    .formParam("security", "${securityToken}")

  val transportManagerAnswers: HttpRequestBuilder = http("submit check your answers")
    .post("application/${applicationNumber}/transport-managers/check-answer/${tmaId}confirm/")
    .formParam("form-actions[submit]", "")
    .formParam("security", "${securityToken}")

  val submitTransportManagerAnswers: HttpRequestBuilder = http("submit check your answers")
    .post("application/${applicationNumber}/transport-managers/tm-declaration/${tmaId}")
    .formParam("content[isDigitallySigned]", "N")
    .formParam("form-actions[submit]", "")
    .formParam("version", _ => randomInt())
    .formParam("security", "${securityToken}")

  val vehicleDetails: HttpRequestBuilder = http("submit vehicle")
    .post("application/${applicationNumber}/vehicles")
    .formParam("query[vrm]", "")
    .formParam("query[disc]", "")
    .formParam("query[includeRemoved]", "")
    .formParam("data[version]", "4")
    .formParam("data[hasEnteredReg]", "N")
    .formParam("vehicles[rows]", "0")
    .formParam("form-actions[saveAndContinue]", "")
    .formParam("security", "${securityToken}")

  val safetyInspector: HttpRequestBuilder = http("add safety")
    .post("application/${applicationNumber}/safety/add")
    .formParam("data[isExternal]", "Y")
    .formParam("contactDetails[fao]", "Inspector Gadget")
    .formParam("address[searchPostcode][postcode]", "NG1 5FW")
    .formParam("address[addressLine1]", "APARTMENT 18")
    .formParam("address[addressLine2]", "THE AXIS")
    .formParam("address[town]", "NOTTINGHAM")
    .formParam("address[postcode]", "NG1 5FW")
    .formParam("address[countryCode]", "GB")
    .formParam("form-actions[submit]", "")
    .formParam("security", "${securityToken}")

  val safetyCompliance: HttpRequestBuilder = http("safety compliance")
    .post("application/${applicationNumber}/safety/")
    .formParam("licence[version]", "6")
    .formParam("licence[safetyInsVehicles]", "10")
    .formParam("licence[safetyInsTrailers]", "5")
    .formParam("licence[safetyInsVaries]", "N")
    .formParam("licence[tachographIns]", "tach_internal")
    .formParam("licence[tachographInsName]", "")
    .formParam("table[rows]", "1")
    .formParam("application[version]", "5")
    .formParam("application[safetyConfirmation]", "Y")
    .formParam("form-actions[saveAndContinue]", "")
    .formParam("security", "${securityToken}")

  val financeHistory: HttpRequestBuilder = http("finance history")
    .post("application/${applicationNumber}/financial-history")
    .formParam("data[id]", "${applicationNumber}")
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
    .formParam("security", "${securityToken}")

  val licenceHistory: HttpRequestBuilder = http("licence history")
    .post("application/${applicationNumber}/licence-history/")
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
    .formParam("version", "7")
    .formParam("security", "${securityToken}")

  val convictionsAndPenalties: HttpRequestBuilder = http("convictions penalties")
    .post("application/${applicationNumber}/convictions-penalties")
    .formParam("data[version]", "8")
    .formParam("data[question]", "N")
    .formParam("data[table][rows]", "0")
    .formParam("convictionsConfirmation[convictionsConfirmation]", "Y")
    .formParam("form-actions[saveAndContinue]", "")
    .formParam("security", "${securityToken}")

  val undertakings: HttpRequestBuilder = http("undertakings")
    .post("application/${applicationNumber}/undertakings/")
    .formParam("declarationsAndUndertakings[signatureOptions]", "N")
    .formParam("interim[goodsApplicationInterim]", "N")
    .formParam("interim[goodsApplicationInterimReason]", "")
    .formParam("declarationsAndUndertakings[version]", "9")
    .formParam("declarationsAndUndertakings[id]", "${applicationNumber}")
    .formParam("form-actions[submitAndPay]", "")
    .formParam("security", "${securityToken}")
    .check(regex("GV/SI Application Fee for application ${applicationNumber}"))
    .check(bodyString.saveAs("undertakings"))

  val cpmsRedirect: HttpRequestBuilder = http("navigate to cpms")
    .post("application/${applicationNumber}/pay-and-submit/")
    .formParam("form-actions[pay]", "")
    .formParam("security", "${securityToken}")
    .check(bodyString.saveAs("pay"))
    .check(regex(SetUp.cpmsRedirectURL).find.exists)
}
