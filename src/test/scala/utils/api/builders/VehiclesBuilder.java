package utils.api.builders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "hasEnteredReg",
        "version"
})
public class VehiclesBuilder {

    @JsonProperty("id")
    private String id;
    @JsonProperty("hasEnteredReg")
    private String hasEnteredReg;
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

    public VehiclesBuilder withId(String id) {
        this.id = id;
        return this;
    }

    @JsonProperty("hasEnteredReg")
    public String getHasEnteredReg() {
        return hasEnteredReg;
    }

    @JsonProperty("hasEnteredReg")
    public void setHasEnteredReg(String hasEnteredReg) {
        this.hasEnteredReg = hasEnteredReg;
    }

    public VehiclesBuilder withHasEnteredReg(String hasEnteredReg) {
        this.hasEnteredReg = hasEnteredReg;
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

    public VehiclesBuilder withVersion(Integer version) {
        this.version = version;
        return this;
    }

    @Override
    public String toString() {
        return "id:" + getId() + ",hasEnteredReg:" + getHasEnteredReg() + ",version:" + getVersion();
    }
}