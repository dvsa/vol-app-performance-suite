package scenarios;

import io.gatling.javaapi.core.ScenarioBuilder;
import journeySteps.ApplicationJourneySteps;
import utils.FeederConfig;
import utils.Header;

import java.util.Objects;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class CreateApplication extends ApplicationJourneySteps {

    public static ScenarioBuilder selfServiceApplicationRegistration() {
        return scenario("Create and submit application")
                .feed(FeederConfig.getFeeder()) // Assuming the feeder is already defined
                .exec(getLoginPage)
                .pause(1)
                .exec(loginPage)
                .exec(session -> session.set("expired-password", session.getString("Location")))
                .pause(2)
                .doIf(session -> session.contains("expired-password") &&
                        session.getString("expired-password") != null &&
                        !Objects.requireNonNull(session.getString("expired-password")).isEmpty())
                .then(exec(changePassword))
                .pause(1)
                .exec(getWelcomePage)
                .pause(2)
                .exec(submitWelcomePage)
                .pause(1)
                .exec(http("get dashboard after welcome")
                        .get("/dashboard")
                        .headers(Header.getAcceptHeaders()))
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
                .exec(director)
                .pause(4)
                .exec(saveDirectorDetails)
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
