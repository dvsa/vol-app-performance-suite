//package scenarios;
//
//import io.gatling.core.feeder.BatchableFeederBuilder;
//import io.gatling.javaapi.core.ScenarioBuilder;
//import journeySteps.ApplicationJourneySteps;
//
//import static io.gatling.javaapi.core.CoreDsl.*;
//import static io.gatling.javaapi.http.HttpDsl.http;
//
//public class CreateApplication extends ApplicationJourneySteps {
//
//    private static final String DEFAULT_ENV = "prep";
//
//    public static BatchableFeederBuilder<String> getFeeder(String env){
//        String environment = (env != null) ? env : DEFAULT_ENV;
//
//        if (environment.toLowerCase().equals("int")) {
//            return (BatchableFeederBuilder<String>) csv("loginId_int.csv").eager();
//        }
//        return (BatchableFeederBuilder<String>) csv("loginId.csv").circular(); // Default feeder
//    }
//
//    public static ScenarioBuilder selfServiceApplicationRegistration() {
//        return scenario("Create and submit application")
//                .feed(Feeders.getFeeder(System.getProperty("env", "dev"))) // Assuming the feeder is already defined
//                .exec(getLoginPage)
//                .pause(1)
//                .exec(loginPage)
//                .exec(session -> session.set("expired-password", "${Location}"))
//                .pause(2)
//                .doIf(session -> session.getString("expired-password").isEmpty() == false)
//                .then(exec(changePassword))
//                .pause(1)
//                .exec(getWelcomePage)
//                .pause(2)
//                .exec(submitWelcomePage)
//                .pause(1)
//                .exec(http("get dashboard after welcome")
//                        .get("/dashboard")
//                        .headers(header_))
//                .pause(1)
//                .exec(getCreateApplicationPage)
//                .pause(7)
//                .exec(createLGVApplication)
//                .pause(1)
//                .exec(showDashboard)
//                .pause(3)
//                .exec(getBusinessTypePage)
//                .pause(4)
//                .exec(businessType)
//                .pause(5)
//                .exec(getBusinessDetailsPage)
//                .pause(1)
//                .exec(businessDetails)
//                .pause(4)
//                .exec(addresses)
//                .pause(5)
//                .exec(director)
//                .pause(4)
//                .exec(saveDirectorDetails)
//                .pause(2)
//                .exec(getLicenceAuthorisationPage)
//                .pause(1)
//                .exec(licenceAuthorisation)
//                .pause(4)
//                .exec(financialEvidence)
//                .pause(3)
//                .exec(getTransportManagersPage)
//                .pause(2)
//                .exec(transportManagersPage)
//                .pause(1)
//                .exec(navigateToAddTransportManagersPage)
//                .pause(2)
//                .exec(transportManagersDetails)
//                .pause(5)
//                .exec(vehicleDetails)
//                .pause(7)
//                .exec(safetyInspector)
//                .pause(4)
//                .exec(safetyCompliance)
//                .pause(2)
//                .exec(financeHistory)
//                .pause(2)
//                .exec(licenceHistory)
//                .pause(2)
//                .exec(convictionsAndPenalties)
//                .pause(3)
//                .exec(flushSessionCookies);
//    }
//
//}
