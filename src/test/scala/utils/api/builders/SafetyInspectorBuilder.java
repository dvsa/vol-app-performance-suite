package utils.api.builders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "application",
        "licence",
        "isExternal",
        "contactDetails"
})
public class SafetyInspectorBuilder {

    @JsonProperty("application")
    private String application;
    @JsonProperty("licence")
    private String licence;
    @JsonProperty("isExternal")
    private String isExternal;
    @JsonProperty("contactDetails")
    private ContactDetailsBuilder contactDetails;

    @JsonProperty("application")
    public String getApplication() {
        return application;
    }

    @JsonProperty("application")
    public void setApplication(String application) {
        this.application = application;
    }

    public SafetyInspectorBuilder withApplication(String application) {
        this.application = application;
        return this;
    }

    @JsonProperty("licence")
    public String getLicence() {
        return licence;
    }

    @JsonProperty("licence")
    public void setLicence(String licence) {
        this.licence = licence;
    }

    public SafetyInspectorBuilder withLicence(String licence) {
        this.licence = licence;
        return this;
    }

    @JsonProperty("isExternal")
    public String getIsExternal() {
        return isExternal;
    }

    @JsonProperty("isExternal")
    public void setIsExternal(String isExternal) {
        this.isExternal = isExternal;
    }

    public SafetyInspectorBuilder withIsExternal(String isExternal) {
        this.isExternal = isExternal;
        return this;
    }

    @JsonProperty("contactDetails")
    public ContactDetailsBuilder getContactDetails() {
        return contactDetails;
    }

    @JsonProperty("contactDetails")
    public void setContactDetails(ContactDetailsBuilder contactDetails) {
        this.contactDetails = contactDetails;
    }

    public SafetyInspectorBuilder withContactDetails(ContactDetailsBuilder contactDetails) {
        this.contactDetails = contactDetails;
        return this;
    }

    @Override
    public String toString() {
        return "application:" + getApplication() + ",licence:" + getLicence() + ",isExternal:" + getIsExternal()
                + ",contactDetails:" + getContactDetails();
    }
}