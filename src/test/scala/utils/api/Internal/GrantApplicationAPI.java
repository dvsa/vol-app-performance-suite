package utils.api.Internal;

import activesupport.http.RestUtils;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import utils.APIHeaders;
import utils.api.builders.FeesBuilder;
import utils.api.builders.GrantApplicationBuilder;
import utils.api.builders.OverviewBuilder;
import utils.api.builders.Tracking;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static utils.APIHeaders.getHeaders;

public class GrantApplicationAPI {

    private ValidatableResponse apiResponse;
    private static String internalHeader = "e91f1a255e01e20021507465a845e7c24b3a1dc951a277b874c3bcd73dec97a1";
    private static String env = System.getProperty("env");
    private static String baseURL = String.format("http://api.olcs.%s.nonprod.dvsa.aws/api/", env);// TODO need to update uri library to include api url
    int version = 1;
    private List outstandingFeesIds;
    private int feeId;

    public void createOverview(String applicationNumber) {
        int overviewVersion = 1;
        String status = "1";
        String overrideOption = "Y";
        String transportArea = "D";
        String trackingId = "12345";
        String overviewResource = String.format("application/%s/overview/", applicationNumber);
        APIHeaders.headers.put("x-pid", internalHeader);

        do {
            Tracking tracking = new Tracking().withId(trackingId).withVersion(overviewVersion).withAddressesStatus(status).withBusinessDetailsStatus(status).withBusinessTypeStatus(status)
                    .withCommunityLicencesStatus(status).withConditionsUndertakingsStatus(status).withConvictionsPenaltiesStatus(status).withFinancialEvidenceStatus(status)
                    .withFinancialHistoryStatus(status).withLicenceHistoryStatus(status).withOperatingCentresStatus(status).withPeopleStatus(status).withSafetyStatus(status)
                    .withTransportManagersStatus(status).withTypeOfLicenceStatus(status).withDeclarationsInternalStatus(status).withVehiclesDeclarationsStatus(status).withVehiclesStatus(status);
            OverviewBuilder overview = new OverviewBuilder().withId(applicationNumber).withVersion(version).withLeadTcArea(transportArea).withOverrideOppositionDate(overrideOption)
                    .withTracking(tracking);
            apiResponse = RestUtils.put(overview, baseURL.concat(overviewResource), getHeaders());
            version++;
            if (version > 20) {
                version = 1;
            }
        } while (apiResponse.extract().statusCode() == HttpStatus.SC_CONFLICT);
        assertThat(apiResponse.statusCode(HttpStatus.SC_OK));
    }

    public void getOutstandingFees(String applicationNumber) {
        String getOutstandingFeesResource = String.format("application/%s/outstanding-fees/", applicationNumber);
        apiResponse = RestUtils.get(baseURL.concat(getOutstandingFeesResource), getHeaders());
        assertThat(apiResponse.statusCode(HttpStatus.SC_OK));
        outstandingFeesIds = apiResponse.extract().response().body().jsonPath().getList("outstandingFees.id");
    }

    private String dateTimeCreate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public void payOutstandingFees(String organisationId, String applicationNumber) {
        int feesAmount = 405;
        String payer = "apiUser";
        String paymentMethod = "fpm_cash";
        String slipNo = "123456";

        String payOutstandingFeesResource = "transaction/pay-outstanding-fees/";
        FeesBuilder feesBuilder = new FeesBuilder().withFeeIds(outstandingFeesIds).withOrganisationId(organisationId).withApplicationId(applicationNumber)
                .withPaymentMethod(paymentMethod).withReceived(feesAmount).withReceiptDate(dateTimeCreate()).withPayer(payer).withSlipNo(slipNo);
        apiResponse = RestUtils.post(feesBuilder, baseURL.concat(payOutstandingFeesResource), getHeaders());
        assertThat(apiResponse.statusCode(HttpStatus.SC_CREATED));
    }

    public void grant(String applicationNumber) {
        String grantApplicationResource = String.format("application/%s/grant/", applicationNumber);
        GrantApplicationBuilder grantApplication = new GrantApplicationBuilder().withId(applicationNumber).withDuePeriod("9").withCaseworkerNotes("This notes are from the API");
        apiResponse = RestUtils.put(grantApplication, baseURL.concat(grantApplicationResource), getHeaders());
        feeId = apiResponse.extract().response().jsonPath().getInt("id.fee");
    }

    public void payGrantFees(String organisationId, String applicationNumber) {
        int feesAmount = 405;
        String payer = "apiUser";
        String paymentMethod = "fpm_cash";
        String slipNo = "123456";

        String payOutstandingFeesResource = "transaction/pay-outstanding-fees/";
        FeesBuilder feesBuilder = new FeesBuilder().withFeeIds(Collections.singletonList(feeId)).withOrganisationId(organisationId).withApplicationId(applicationNumber)
                .withPaymentMethod(paymentMethod).withReceived(feesAmount).withReceiptDate(dateTimeCreate()).withPayer(payer).withSlipNo(slipNo);
        apiResponse = RestUtils.post(feesBuilder, baseURL.concat(payOutstandingFeesResource), getHeaders());
        assertThat(apiResponse.statusCode(HttpStatus.SC_CREATED));
    }
}