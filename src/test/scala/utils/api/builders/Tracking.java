package utils.api.builders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "version",
        "addressesStatus",
        "businessDetailsStatus",
        "businessTypeStatus",
        "communityLicencesStatus",
        "conditionsUndertakingsStatus",
        "convictionsPenaltiesStatus",
        "discsStatus",
        "financialEvidenceStatus",
        "financialHistoryStatus",
        "licenceHistoryStatus",
        "operatingCentresStatus",
        "peopleStatus",
        "safetyStatus",
        "taxiPhvStatus",
        "transportManagersStatus",
        "typeOfLicenceStatus",
        "declarationsInternalStatus",
        "vehiclesDeclarationsStatus",
        "vehiclesPsvStatus",
        "vehiclesStatus"
})
public class Tracking {
    @JsonProperty("id")
    private String id;
    @JsonProperty("version")
    private int version;
    @JsonProperty("addressesStatus")
    private String addressesStatus;
    @JsonProperty("businessDetailsStatus")
    private String businessDetailsStatus;
    @JsonProperty("businessTypeStatus")
    private String businessTypeStatus;
    @JsonProperty("communityLicencesStatus")
    private String communityLicencesStatus;
    @JsonProperty("conditionsUndertakingsStatus")
    private String conditionsUndertakingsStatus;
    @JsonProperty("convictionsPenaltiesStatus")
    private String convictionsPenaltiesStatus;
    @JsonProperty("discsStatus")
    private Object discsStatus;
    @JsonProperty("financialEvidenceStatus")
    private String financialEvidenceStatus;
    @JsonProperty("financialHistoryStatus")
    private String financialHistoryStatus;
    @JsonProperty("licenceHistoryStatus")
    private String licenceHistoryStatus;
    @JsonProperty("operatingCentresStatus")
    private String operatingCentresStatus;
    @JsonProperty("peopleStatus")
    private String peopleStatus;
    @JsonProperty("safetyStatus")
    private String safetyStatus;
    @JsonProperty("taxiPhvStatus")
    private Object taxiPhvStatus;
    @JsonProperty("transportManagersStatus")
    private String transportManagersStatus;
    @JsonProperty("typeOfLicenceStatus")
    private String typeOfLicenceStatus;
    @JsonProperty("declarationsInternalStatus")
    private String declarationsInternalStatus;
    @JsonProperty("vehiclesDeclarationsStatus")
    private String vehiclesDeclarationsStatus;
    @JsonProperty("vehiclesPsvStatus")
    private Object vehiclesPsvStatus;
    @JsonProperty("vehiclesStatus")
    private String vehiclesStatus;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public Tracking withId(String id) {
        this.id = id;
        return this;
    }

    @JsonProperty("version")
    public int getVersion() {
        return version;
    }

    @JsonProperty("version")
    public void setVersion(int version) {
        this.version = version;
    }

    public Tracking withVersion(int version) {
        this.version = version;
        return this;
    }

    @JsonProperty("addressesStatus")
    public String getAddressesStatus() {
        return addressesStatus;
    }

    @JsonProperty("addressesStatus")
    public void setAddressesStatus(String addressesStatus) {
        this.addressesStatus = addressesStatus;
    }

    public Tracking withAddressesStatus(String addressesStatus) {
        this.addressesStatus = addressesStatus;
        return this;
    }

    @JsonProperty("businessDetailsStatus")
    public String getBusinessDetailsStatus() {
        return businessDetailsStatus;
    }

    @JsonProperty("businessDetailsStatus")
    public void setBusinessDetailsStatus(String businessDetailsStatus) {
        this.businessDetailsStatus = businessDetailsStatus;
    }

    public Tracking withBusinessDetailsStatus(String businessDetailsStatus) {
        this.businessDetailsStatus = businessDetailsStatus;
        return this;
    }

    @JsonProperty("businessTypeStatus")
    public String getBusinessTypeStatus() {
        return businessTypeStatus;
    }

    @JsonProperty("businessTypeStatus")
    public void setBusinessTypeStatus(String businessTypeStatus) {
        this.businessTypeStatus = businessTypeStatus;
    }

    public Tracking withBusinessTypeStatus(String businessTypeStatus) {
        this.businessTypeStatus = businessTypeStatus;
        return this;
    }

    @JsonProperty("communityLicencesStatus")
    public String getCommunityLicencesStatus() {
        return communityLicencesStatus;
    }

    @JsonProperty("communityLicencesStatus")
    public void setCommunityLicencesStatus(String communityLicencesStatus) {
        this.communityLicencesStatus = communityLicencesStatus;
    }

    public Tracking withCommunityLicencesStatus(String communityLicencesStatus) {
        this.communityLicencesStatus = communityLicencesStatus;
        return this;
    }

    @JsonProperty("conditionsUndertakingsStatus")
    public String getConditionsUndertakingsStatus() {
        return conditionsUndertakingsStatus;
    }

    @JsonProperty("conditionsUndertakingsStatus")
    public void setConditionsUndertakingsStatus(String conditionsUndertakingsStatus) {
        this.conditionsUndertakingsStatus = conditionsUndertakingsStatus;
    }

    public Tracking withConditionsUndertakingsStatus(String conditionsUndertakingsStatus) {
        this.conditionsUndertakingsStatus = conditionsUndertakingsStatus;
        return this;
    }

    @JsonProperty("convictionsPenaltiesStatus")
    public String getConvictionsPenaltiesStatus() {
        return convictionsPenaltiesStatus;
    }

    @JsonProperty("convictionsPenaltiesStatus")
    public void setConvictionsPenaltiesStatus(String convictionsPenaltiesStatus) {
        this.convictionsPenaltiesStatus = convictionsPenaltiesStatus;
    }

    public Tracking withConvictionsPenaltiesStatus(String convictionsPenaltiesStatus) {
        this.convictionsPenaltiesStatus = convictionsPenaltiesStatus;
        return this;
    }

    @JsonProperty("discsStatus")
    public Object getDiscsStatus() {
        return discsStatus;
    }

    @JsonProperty("discsStatus")
    public void setDiscsStatus(Object discsStatus) {
        this.discsStatus = discsStatus;
    }

    public Tracking withDiscsStatus(Object discsStatus) {
        this.discsStatus = discsStatus;
        return this;
    }

    @JsonProperty("financialEvidenceStatus")
    public String getFinancialEvidenceStatus() {
        return financialEvidenceStatus;
    }

    @JsonProperty("financialEvidenceStatus")
    public void setFinancialEvidenceStatus(String financialEvidenceStatus) {
        this.financialEvidenceStatus = financialEvidenceStatus;
    }

    public Tracking withFinancialEvidenceStatus(String financialEvidenceStatus) {
        this.financialEvidenceStatus = financialEvidenceStatus;
        return this;
    }

    @JsonProperty("financialHistoryStatus")
    public String getFinancialHistoryStatus() {
        return financialHistoryStatus;
    }

    @JsonProperty("financialHistoryStatus")
    public void setFinancialHistoryStatus(String financialHistoryStatus) {
        this.financialHistoryStatus = financialHistoryStatus;
    }

    public Tracking withFinancialHistoryStatus(String financialHistoryStatus) {
        this.financialHistoryStatus = financialHistoryStatus;
        return this;
    }

    @JsonProperty("licenceHistoryStatus")
    public String getLicenceHistoryStatus() {
        return licenceHistoryStatus;
    }

    @JsonProperty("licenceHistoryStatus")
    public void setLicenceHistoryStatus(String licenceHistoryStatus) {
        this.licenceHistoryStatus = licenceHistoryStatus;
    }

    public Tracking withLicenceHistoryStatus(String licenceHistoryStatus) {
        this.licenceHistoryStatus = licenceHistoryStatus;
        return this;
    }

    @JsonProperty("operatingCentresStatus")
    public String getOperatingCentresStatus() {
        return operatingCentresStatus;
    }

    @JsonProperty("operatingCentresStatus")
    public void setOperatingCentresStatus(String operatingCentresStatus) {
        this.operatingCentresStatus = operatingCentresStatus;
    }

    public Tracking withOperatingCentresStatus(String operatingCentresStatus) {
        this.operatingCentresStatus = operatingCentresStatus;
        return this;
    }

    @JsonProperty("peopleStatus")
    public String getPeopleStatus() {
        return peopleStatus;
    }

    @JsonProperty("peopleStatus")
    public void setPeopleStatus(String peopleStatus) {
        this.peopleStatus = peopleStatus;
    }

    public Tracking withPeopleStatus(String peopleStatus) {
        this.peopleStatus = peopleStatus;
        return this;
    }

    @JsonProperty("safetyStatus")
    public String getSafetyStatus() {
        return safetyStatus;
    }

    @JsonProperty("safetyStatus")
    public void setSafetyStatus(String safetyStatus) {
        this.safetyStatus = safetyStatus;
    }

    public Tracking withSafetyStatus(String safetyStatus) {
        this.safetyStatus = safetyStatus;
        return this;
    }

    @JsonProperty("taxiPhvStatus")
    public Object getTaxiPhvStatus() {
        return taxiPhvStatus;
    }

    @JsonProperty("taxiPhvStatus")
    public void setTaxiPhvStatus(Object taxiPhvStatus) {
        this.taxiPhvStatus = taxiPhvStatus;
    }

    public Tracking withTaxiPhvStatus(Object taxiPhvStatus) {
        this.taxiPhvStatus = taxiPhvStatus;
        return this;
    }

    @JsonProperty("transportManagersStatus")
    public String getTransportManagersStatus() {
        return transportManagersStatus;
    }

    @JsonProperty("transportManagersStatus")
    public void setTransportManagersStatus(String transportManagersStatus) {
        this.transportManagersStatus = transportManagersStatus;
    }

    public Tracking withTransportManagersStatus(String transportManagersStatus) {
        this.transportManagersStatus = transportManagersStatus;
        return this;
    }

    @JsonProperty("typeOfLicenceStatus")
    public String getTypeOfLicenceStatus() {
        return typeOfLicenceStatus;
    }

    @JsonProperty("typeOfLicenceStatus")
    public void setTypeOfLicenceStatus(String typeOfLicenceStatus) {
        this.typeOfLicenceStatus = typeOfLicenceStatus;
    }

    public Tracking withTypeOfLicenceStatus(String typeOfLicenceStatus) {
        this.typeOfLicenceStatus = typeOfLicenceStatus;
        return this;
    }

    @JsonProperty("declarationsInternalStatus")
    public String getDeclarationsInternalStatus() {
        return declarationsInternalStatus;
    }

    @JsonProperty("declarationsInternalStatus")
    public void setDeclarationsInternalStatus(String declarationsInternalStatus) {
        this.declarationsInternalStatus = declarationsInternalStatus;
    }

    public Tracking withDeclarationsInternalStatus(String declarationsInternalStatus) {
        this.declarationsInternalStatus = declarationsInternalStatus;
        return this;
    }

    @JsonProperty("vehiclesDeclarationsStatus")
    public String getVehiclesDeclarationsStatus() {
        return vehiclesDeclarationsStatus;
    }

    @JsonProperty("vehiclesDeclarationsStatus")
    public void setVehiclesDeclarationsStatus(String vehiclesDeclarationsStatus) {
        this.vehiclesDeclarationsStatus = vehiclesDeclarationsStatus;
    }

    public Tracking withVehiclesDeclarationsStatus(String vehiclesDeclarationsStatus) {
        this.vehiclesDeclarationsStatus = vehiclesDeclarationsStatus;
        return this;
    }

    @JsonProperty("vehiclesPsvStatus")
    public Object getVehiclesPsvStatus() {
        return vehiclesPsvStatus;
    }

    @JsonProperty("vehiclesPsvStatus")
    public void setVehiclesPsvStatus(Object vehiclesPsvStatus) {
        this.vehiclesPsvStatus = vehiclesPsvStatus;
    }

    public Tracking withVehiclesPsvStatus(Object vehiclesPsvStatus) {
        this.vehiclesPsvStatus = vehiclesPsvStatus;
        return this;
    }

    @JsonProperty("vehiclesStatus")
    public String getVehiclesStatus() {
        return vehiclesStatus;
    }

    @JsonProperty("vehiclesStatus")
    public void setVehiclesStatus(String vehiclesStatus) {
        this.vehiclesStatus = vehiclesStatus;
    }

    public Tracking withVehiclesStatus(String vehiclesStatus) {
        this.vehiclesStatus = vehiclesStatus;
        return this;
    }

    @Override
    public String toString() {
        return "id:" + getId() + ",version:" + getVersion() + ",addressesStatus:" + getAddressesStatus() + ",businessDetailsStatus:"
                + getBusinessDetailsStatus() + ",businessTypeStatus:" + getBusinessTypeStatus() + ",communityLicencesStatus:" + getCommunityLicencesStatus() + ",conditionsUndertakingsStatus:" + getConditionsUndertakingsStatus()
                + "convictionsPenaltiesStatus:" + getConvictionsPenaltiesStatus() + ",discsStatus:" + getDiscsStatus() + ",financialEvidenceStatus:" + getFinancialEvidenceStatus() + ",financialHistoryStatus:" + getFinancialEvidenceStatus()
                + ",licenceHistoryStatus:" + getLicenceHistoryStatus() + ",operatingCentresStatus:" + getOperatingCentresStatus() + ",peopleStatus:" + getPeopleStatus()
                + ",safetyStatus:" + getSafetyStatus() + ",taxiPhvStatus:" + getTaxiPhvStatus() + ",transportManagersStatus:" + getTransportManagersStatus()
                + "typeOfLicenceStatus:" + getTypeOfLicenceStatus() + ",declarationsInternalStatus:" + getDeclarationsInternalStatus() + ",vehiclesDeclarationsStatus:" + getVehiclesDeclarationsStatus() + ",vehiclesPsvStatus:" + getVehiclesPsvStatus() + ",vehiclesStatus:" + getVehiclesStatus();
    }
}