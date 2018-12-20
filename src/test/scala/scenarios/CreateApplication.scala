package scenarios

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import utils.Configuration

import scala.concurrent.duration._

object CreateApplication {

  val newPassword = "Password1"

  val feeder = csv("src/test/resources/loginId.csv")
  val header_ = Map("Accept" -> "*/*")

  val headers_0 = Map("Upgrade-Insecure-Requests" -> "1")

  val selfServiceApplicationRegistration = scenario("Create and submit application")
    .feed(feeder)
    .exec(http("get login page")
      .get("auth/login/")
      .disableFollowRedirect
      .headers(header_)
      .check(
        regex(Configuration.securityTokenPattern).
          find.saveAs("securityToken")))
    .pause(300 milliseconds)
    .exec(http("login")
      .post("auth/login/")
      .check(regex("""name="change-password-form" action="&#x2F;auth&#x2F;expired-password&#x2F;([^"]*)&#x2F;""").find.optional.saveAs("Location"))
      .formParam("username", "${Username}")
      .formParam("password", "${Password}")
      .formParam("submit", "Sign in")
      .formParam("security", "${securityToken}"))
    .exec(session => session.set("expired-password", "${Location}"))
    .pause(450 milliseconds)
    .doIf(session => session("expired-password").as[String].isEmpty.equals(false)) {
      exec(http("change password")
        .post("auth/expired-password/${Location}")
        .formParam("oldPassword", "${Password}")
        .formParam("newPassword", "Password1")
        .formParam("confirmPassword", "Password1")
        .formParam("submit", "Save")
        .formParam("security", "${securityToken}")
        .check(bodyString.saveAs("login_response")))
    }
    .pause(700 milliseconds)
    .exec(session => {
      println(session("login_response").as[String])
      session
    })
    .exec(http("Landing Page")
      .get("/")
      .check(regex("${Forename}"))
      .check(bodyString.saveAs("login_response")))
    .pause(650 milliseconds)
    .exec(session => {
      println(session("login_response").as[String])
      session
    })
    .exec(http("choose country")
      .post("application/create/")
      .formParam("type-of-licence[operator-location]", "N")
      .formParam("type-of-licence[operator-type]", "lcat_psv")
      .formParam("type-of-licence[licence-type]", "ltyp_sn")
      .formParam("form-actions[saveAndContinue]", "")
      .formParam("security", "${securityToken}"))
    .pause(950 milliseconds)
    .exec(http("Show dashboard")
      .get("/")
      .check(regex("""href="/application/([^"]*)/"""").find.saveAs("applicationId")))
    .pause(850 milliseconds)
    .exec(http("business type")
      .post("application/${applicationId}/business-type/")
      .formParam("data[type]", "org_t_rc")
      .formParam("form-actions[saveAndContinue]", "")
      .formParam("version", "1")
      .formParam("security", "${securityToken}"))
    .pause(800 milliseconds)
    .exec(http("business details")
      .post("application/${applicationId}/business-details/")
      .formParam("data[companyNumber][company_number]", "41078510")
      .formParam("data[name]", "GatlingBusiness")
      .formParam("data[natureOfBusiness]", "apiTesting")
      .formParam("registeredAddress[addressLine1]", "API House")
      .formParam("registeredAddress[town]", "Nottingham")
      .formParam("registeredAddress[postcode]", "NG23HX")
      .formParam("table[rows]", "0")
      .formParam("form-actions[saveAndContinue]", "")
      .formParam("version", "1")
      .formParam("security", "${securityToken}"))
    .pause(950 millisecond)
    .exec(http("addresses")
      .post("application/${applicationId}/addresses/")
      .formParam("correspondence_address[searchPostcode][postcode]", "NG1 5FW")
      .formParam("correspondence_address[addressLine1]", "3 WOLLATON STREET")
      .formParam("correspondence_address[town]", "NOTTINGHAM")
      .formParam("correspondence_address[postcode]", "NG1 5FW")
      .formParam("registeredAddress[town]", "Nottingham")
      .formParam("registeredAddress[postcode]", "NG23HX")
      .formParam("correspondence_address[countryCode]", "GB")
      .formParam("contact[phone_primary]", "07123456789")
      .formParam("contact[email]", "test@test.com")
      .formParam("establishment_address[countryCode]", "GB")
      .formParam("form-actions[saveAndContinue]", "")
      .formParam("security", "${securityToken}"))
    .pause(890 milliseconds)
    .exec(http("people")
      .post("application/${applicationId}/people/add/")
      .formParam("data[title]", "title_mr")
      .formParam("data[forename]", "Director")
      .formParam("data[familyName]", "Gatling")
      .formParam("data[birthDate][day]", "10")
      .formParam("data[birthDate][month]", "10")
      .formParam("data[birthDate][year]", "1980")
      .formParam("table[rows]", "0")
      .formParam("correspondence_address[town]", "NOTTINGHAM")
      .formParam("form-actions[saveAndContinue]", "")
      .formParam("security", "${securityToken}"))
    .pause(850 milliseconds)
    .exec(http("people")
      .post("application/${applicationId}/people/")
      .formParam("table[rows]", "1")
      .formParam("form-actions[saveAndContinue]", "")
      .formParam("security", "${securityToken}"))
    .pause(750 milliseconds)
    .exec(http("add operating centres")
      .post("application/${applicationId}/operating-centres/add/")
      .formParam("address[searchPostcode][postcode]", "NG1 5FW")
      .formParam("address[addressLine1]", "3 WOLLATON STREET")
      .formParam("address[town]", "NOTTINGHAM")
      .formParam("address[postcode]", "NG1 5FW")
      .formParam("address[countryCode]", "GB")
      .formParam("data[noOfVehiclesRequired]", "5")
      .formParam("data[permission][permission]", "Y")
      .formParam("form-actions[submit]", "")
      .formParam("version", "")
      .formParam("security", "${securityToken}"))
    .pause(900 milliseconds)
    .exec(http("submit operating centres")
      .post("application/${applicationId}/operating-centres/")
      .formParam("table[rows]", "1")
      .formParam("data[version]", "2")
      .formParam("data[totAuthVehicles]", "5")
      .formParam("form-actions[saveAndContinue]", "")
      .formParam("security", "${securityToken}"))
    .pause(750 milliseconds)
    .exec(http("submit financial evidence")
      .post("application/${applicationId}/financial-evidence/")
      .formParam("evidence[uploadedFileCount]", "0")
      .formParam("evidence[files][fileCount]", "")
      .formParam("evidence[files][file]", "(binary)")
      .formParam("evidence[files][__messages__]", "")
      .formParam("evidence[uploadNow]", "0")
      .formParam("form-actions[saveAndContinue]", "")
      .formParam("version", "3")
      .formParam("id", "${applicationId}")
      .formParam("security", "${securityToken}"))
    .pause(850 milliseconds)
    .exec(http("add transport manager")
      .post("application/${applicationId}/transport-managers/")
      .formParam("table[rows]", "0")
      .formParam("table[action]", "Add")
      .formParam("security", "${securityToken}")
      .check(regex("""value="(\d+)""").find.saveAs("transportManagerId")))
    .pause(850 milliseconds)
    .exec(http("select transport manager")
      .post("application/${applicationId}/transport-managers/add/")
      .formParam("data[registeredUser]", "${transportManagerId}")
      .formParam("form-actions[continue]", "")
      .formParam("security", "${securityToken}")
      .check(currentLocationRegex("""details/(\d+)""").saveAs("tmaId")))
    .pause(1 second)
    .exec(http("submit transport manager")
      .post("application/${applicationId}/transport-managers/details/${tmaId}/")
      .formParam("details[birthDate][day]", "23")
      .formParam("details[birthDate][month]", "02")
      .formParam("details[birthDate][year]", "1948")
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
      .formParam("security", "${securityToken}"))
    .pause(850 milliseconds)
    .exec(http("submit check your answers")
      .post("application/${applicationId}/transport-managers/check-answer/${tmaId}/confirm/")
      .formParam("form-actions[submit]", "")
      .formParam("security", "${securityToken}"))
    .pause(850 milliseconds)
    .exec(http("submit check your answers")
      .post("application/${applicationId}/transport-managers/tm-declaration/${tmaId}/")
      .formParam("content[isDigitallySigned]", "N")
      .formParam("form-actions[submit]", "")
      .formParam("version", "")
      .formParam("security", "${securityToken}"))
    .pause(950 milliseconds)
    .exec(http("navigate to vehicle details")
      .post("application/${applicationId}/transport-managers")
      .formParam("table[rows]", "1")
      .formParam("form-actions[saveAndContinue]", "")
      .formParam("security", "${securityToken}"))
    .pause(950 milliseconds)
    .exec(http("submit vehicle")
      .post("application/${applicationId}/vehicles-psv")
      .formParam("data[version]", "4")
      .formParam("data[hasEnteredReg]", "N")
      .formParam("vehicles[rows]", "0")
      .formParam("shareInfo[shareInfo]", "Y")
      .formParam("form-actions[saveAndContinue]", "")
      .formParam("security", "${securityToken}"))
    .pause(850 milliseconds)
    .exec(http("vehicle declarations")
      .post("application/${applicationId}/vehicles-declarations")
      .formParam("psvVehicleSize[size]", "psvvs_both")
      .formParam("smallVehiclesIntention[psvOperateSmallVhl]", "N")
      .formParam("smallVehiclesIntention[psvSmallVhlNotes]", "")
      .formParam("smallVehiclesIntention[psvSmallVhlConfirmation]", "Y")
      .formParam("nineOrMore[psvNoSmallVhlConfirmation]", "N")
      .formParam("limousinesNoveltyVehicles[psvLimousines]", "N")
      .formParam("limousinesNoveltyVehicles[psvNoLimousineConfirmation]", "N")
      .formParam("limousinesNoveltyVehicles[psvNoLimousineConfirmation]", "Y")
      .formParam("limousinesNoveltyVehicles[psvOnlyLimousinesConfirmation]", "N")
      .formParam("form-actions[saveAndContinue]", "")
      .formParam("version", "5")
      .formParam("security", "${securityToken}"))
    .pause(850 milliseconds)
    .exec(http("add safety")
      .post("application/${applicationId}/safety/add")
      .formParam("data[isExternal]", "Y")
      .formParam("contactDetails[fao]", "Inspector Gadget")
      .formParam("address[searchPostcode][postcode]", "NG1 5FW")
      .formParam("address[addressLine1]", "APARTMENT 18")
      .formParam("address[addressLine2]", "THE AXIS")
      .formParam("address[town]", "NOTTINGHAM")
      .formParam("address[postcode]", "NG1 5FW")
      .formParam("address[countryCode]", "GB")
      .formParam("form-actions[submit]", "")
      .formParam("security", "${securityToken}"))
    .pause(850 milliseconds)
    .exec(http("safety compliance")
      .post("application/${applicationId}/safety/")
      .formParam("licence[version]", "6")
      .formParam("licence[safetyInsVehicles]", "10")
      .formParam("licence[safetyInsVaries]", "N")
      .formParam("licence[tachographIns]", "tach_internal")
      .formParam("licence[tachographInsName]", "")
      .formParam("table[rows]", "1")
      .formParam("application[version]", "6")
      .formParam("application[safetyConfirmation]", "Y")
      .formParam("form-actions[saveAndContinue]", "")
      .formParam("security", "${securityToken}"))
    .pause(950 milliseconds)
    .exec(http("finance history")
      .post("application/${applicationId}/financial-history")
      .formParam("data[id]", "${applicationId}")
      .formParam("data[version]", "7")
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
      .formParam("security", "${securityToken}"))
    .pause(650 milliseconds)
    .exec(http("licence history")
      .post("application/${applicationId}/licence-history/")
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
      .formParam("version", "8")
      .formParam("security", "${securityToken}"))
    .pause(850 milliseconds)
    .exec(http("convictions penalties")
      .post("application/${applicationId}/convictions-penalties")
      .formParam("data[version]", "9")
      .formParam("data[question]", "N")
      .formParam("data[table][rows]", "0")
      .formParam("convictionsConfirmation[convictionsConfirmation]", "Y")
      .formParam("form-actions[saveAndContinue]", "")
      .formParam("security", "${securityToken}"))
    .pause(850 milliseconds)
    .exec(http("undertakings")
      .post("application/${applicationId}/undertakings/")
      .formParam("declarationsAndUndertakings[signatureOptions]", "N")
      .formParam("declarationsAndUndertakings[version]", "10")
      .formParam("declarationsAndUndertakings[id]", "${applicationId}")
      .formParam("form-actions[submitAndPay]", "")
      .formParam("security", "${securityToken}")
      .check(regex("PSV/SN Application Fee for application ${applicationId}"))
      .check(bodyString.saveAs("undertakings")))
    .exec(session => {
      println(session("undertakings").as[String])
      session
    })
    .pause(850 milliseconds)
    .exec(http("logout")
        .get("auth/logout/")
        .check(regex("Thank you")))
    .exec(flushSessionCookies)
}