package utils.api.builders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "businessType",
        "version",
        "id",
        "application"
})
public class BusinessTypeBuilder {

    @JsonProperty("businessType")
    private String businessType;
    @JsonProperty("version")
    private String version;
    @JsonProperty("id")
    private String id;
    @JsonProperty("application")
    private String application;

    @JsonProperty("businessType")
    public String getBusinessType() {
        return businessType;
    }

    @JsonProperty("businessType")
    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public BusinessTypeBuilder withBusinessType(String businessType) {
        this.businessType = businessType;
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

    public BusinessTypeBuilder withVersion(String version) {
        this.version = version;
        return this;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public BusinessTypeBuilder withId(String id) {
        this.id = id;
        return this;
    }

    @JsonProperty("application")
    public String getApplication() {
        return application;
    }

    @JsonProperty("application")
    public void setApplication(String application) {
        this.application = application;
    }

    public BusinessTypeBuilder withApplication(String application) {
        this.application = application;
        return this;
    }

    @Override
    public String toString() {
        return "businessType:" + getBusinessType() + ",version:" + getVersion() + ",id:" + getId() + ",application:" + getApplication();
    }
}