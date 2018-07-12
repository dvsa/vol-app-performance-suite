package utils.api.builders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "totAuthVehicles",
        "totAuthTrailers",
        "trafficArea",
        "totCommunityLicences",
        "enforcementArea",
        "version"
})
public class OperatingCentreUpdater {

    @JsonProperty("id")
    private String id;
    @JsonProperty("totAuthVehicles")
    private Integer totAuthVehicles;
    @JsonProperty("totAuthTrailers")
    private Integer totAuthTrailers;
    @JsonProperty("trafficArea")
    private String trafficArea;
    @JsonProperty("enforcementArea")
    private String enforcementArea;
    @JsonProperty("totCommunityLicences")
    private Integer totCommunityLicences;
    @JsonProperty("version")
    private Integer version;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public OperatingCentreUpdater withId(String id) {
        this.id = id;
        return this;
    }

    @JsonProperty("totAuthVehicles")
    public Integer getTotAuthVehicles() {
        return totAuthVehicles;
    }

    @JsonProperty("totAuthVehicles")
    public void setTotAuthVehicles(Integer totAuthVehicles) {
        this.totAuthVehicles = totAuthVehicles;
    }

    public OperatingCentreUpdater withTotAuthVehicles(Integer totAuthVehicles) {
        this.totAuthVehicles = totAuthVehicles;
        return this;
    }

    @JsonProperty("totAuthTrailers")
    public Integer getTotAuthTrailers() {
        return totAuthTrailers;
    }

    @JsonProperty("totAuthTrailers")
    public void setTotAuthTrailers(Integer totAuthTrailers) {
        this.totAuthTrailers = totAuthTrailers;
    }

    public OperatingCentreUpdater withTAuthTrailers(Integer totAuthTrailers) {
        this.totAuthTrailers = totAuthTrailers;
        return this;
    }

    @JsonProperty("trafficArea")
    public String getTrafficArea() {
        return trafficArea;
    }

    @JsonProperty("trafficArea")
    public void setTrafficArea(String trafficArea) {
        this.trafficArea = trafficArea;
    }

    public OperatingCentreUpdater withTrafficArea(String trafficArea) {
        this.trafficArea = trafficArea;
        return this;
    }

    @JsonProperty("enforcementArea")
    public String getEnforcementArea() {
        return enforcementArea;
    }

    @JsonProperty("enforcementArea")
    public void setEnforcementArea(String enforcementArea) {
        this.enforcementArea = enforcementArea;
    }

    public OperatingCentreUpdater withEnforcementArea(String enforcementArea) {
        this.enforcementArea = enforcementArea;
        return this;
    }
    @JsonProperty("totCommunityLicences")
    public Integer getTotCommunityLicences() {
        return totCommunityLicences;
    }

    @JsonProperty("totCommunityLicences")
    public void setTotCommunityLicences(Integer totCommunityLicences) {
        this.totCommunityLicences = totCommunityLicences;
    }

    public OperatingCentreUpdater withTotCommunityLicences(Integer totCommunityLicences) {
        this.totCommunityLicences = totCommunityLicences;
        return this;
    }

    @JsonProperty("version")
    public Integer getVersion() {
        return version;
    }

    @JsonProperty("version")
    public void setVersion(Integer version) {
        this.version = version;
    }

    public OperatingCentreUpdater withVersion(Integer version) {
        this.version = version;
        return this;
    }

    @Override
    public String toString() {
        return "id:" + getId() + ",totAuthVehicles:" + getTotAuthVehicles() + ",totAuthTrailers:" + getTotAuthTrailers() +
                ",trafficArea:" + getTrafficArea() + ",enforcementArea:" + getEnforcementArea()
        + ",totCommunityLicences:" +  getTotCommunityLicences() + ",version:" + getVersion();
    }
}