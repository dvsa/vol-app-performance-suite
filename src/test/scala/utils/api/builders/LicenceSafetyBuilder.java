package utils.api.builders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "version",
        "safetyInsVehicles",
        "safetyInsVaries",
        "tachographIns"
})
public class LicenceSafetyBuilder {

    @JsonProperty("id")
    private String id;
    @JsonProperty("version")
    private String version;
    @JsonProperty("safetyInsVehicles")
    private String safetyInsVehicles;
    @JsonProperty("safetyInsVaries")
    private String safetyInsVaries;
    @JsonProperty("tachographIns")
    private String tachographIns;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public LicenceSafetyBuilder withId(String id) {
        this.id = id;
        return this;
    }

    @JsonProperty("version")
    public String getVersion() {
        return version;
    }

    @JsonProperty("version")
    public void setVersion(String version) {
        this.version = version;
    }

    public LicenceSafetyBuilder withVersion(String version) {
        this.version = version;
        return this;
    }

    @JsonProperty("safetyInsVehicles")
    public String getSafetyInsVehicles() {
        return safetyInsVehicles;
    }

    @JsonProperty("safetyInsVehicles")
    public void setSafetyInsVehicles(String safetyInsVehicles) {
        this.safetyInsVehicles = safetyInsVehicles;
    }

    public LicenceSafetyBuilder withSafetyInsVehicles(String safetyInsVehicles) {
        this.safetyInsVehicles = safetyInsVehicles;
        return this;
    }

    @JsonProperty("safetyInsVaries")
    public String getSafetyInsVaries() {
        return safetyInsVaries;
    }

    @JsonProperty("safetyInsVaries")
    public void setSafetyInsVaries(String safetyInsVaries) {
        this.safetyInsVaries = safetyInsVaries;
    }

    public LicenceSafetyBuilder withSafetyInsVaries(String safetyInsVaries) {
        this.safetyInsVaries = safetyInsVaries;
        return this;
    }

    @JsonProperty("tachographIns")
    public String getTachographIns() {
        return tachographIns;
    }

    @JsonProperty("tachographIns")
    public void setTachographIns(String tachographIns) {
        this.tachographIns = tachographIns;
    }

    public LicenceSafetyBuilder withTachographIns(String tachographIns) {
        this.tachographIns = tachographIns;
        return this;
    }

    @Override
    public String toString() {
        return "id:" + getId() + ",version:" + getVersion() + ",safetyInsVehicles:" + getSafetyInsVehicles()
        + ",safetyInsVaries:" + getSafetyInsVaries() + ",tachographIns:" + getTachographIns();
    }
}