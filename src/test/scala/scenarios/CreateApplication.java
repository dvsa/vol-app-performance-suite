package scenarios;

import io.gatling.javaapi.core.ScenarioBuilder;
import journeySteps.ApplicationJourneySteps;
import utils.FeederConfig;


import static io.gatling.javaapi.core.CoreDsl.*;

public class CreateApplication extends ApplicationJourneySteps {

    public static ScenarioBuilder selfServiceApplicationRegistration() {
        return scenario("Create and submit application")
                .feed(FeederConfig.getFeeder()) // Assuming the feeder is already defined
                .exec(getLoginPage)
                .pause(20)
                .exec(loginPage)
                .exec(session -> session.set("expired-password", session.getString("Location")))
                .pause(20)
                .doIf(session -> {
                    String expiredPassword = session.getString("expired-password");
                    return expiredPassword != null && !expiredPassword.isEmpty();
                }).then(exec(changePassword))
                .pause(18)
                .exec(getWelcomePage)
                .pause(2)
                .exec(submitWelcomePage)
                .pause(1)
                .exec(showDashboard)
                .pause(1)
                .exec(getCreateApplicationPage)
                .pause(7)
                .exec(createLGVApplication)
                .pause(1)
                .exec(showDashboard)
                .pause(3)
                .exec(getBusinessTypePage)
                .pause(4)
                .exec(businessType)
                .pause(5)
                .exec(getBusinessDetailsPage)
                .pause(1)
                .exec(businessDetails)
                .pause(4)
                .exec(addresses)
                .pause(5)
                .exec(getDirector)
                .pause(5)
                .exec(saveDirectorDetails)
                .pause(4)
                .exec(director)
                .pause(4)
                .pause(2)
                .exec(getLicenceAuthorisationPage)
                .pause(1)
                .exec(licenceAuthorisation)
                .pause(4)
                .exec(financialEvidence)
                .pause(3)
                .exec(getTransportManagersPage)
                .pause(2)
                .exec(transportManagersPage)
                .pause(1)
                .exec(navigateToAddTransportManagersPage)
                .pause(2)
                .exec(transportManagersDetails)
                .pause(5)
                .exec(vehicleDetails)
                .pause(7)
                .exec(safetyInspector)
                .pause(4)
                .exec(safetyCompliance)
                .pause(2)
                .exec(financeHistory)
                .pause(2)
                .exec(licenceHistory)
                .pause(2)
                .exec(convictionsAndPenalties)
                .pause(2)
                .exec(undertakings);
    }
}
