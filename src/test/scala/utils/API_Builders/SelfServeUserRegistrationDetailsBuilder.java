package utils.API_Builders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "loginId",
        "contactDetails",
        "organisationName",
        "businessType"
})
public class SelfServeUserRegistrationDetailsBuilder {

    @JsonProperty("loginId")
    private String loginId;
    @JsonProperty("contactDetails")
    private ContactDetailsBuilder contactDetailsBuilder;
    @JsonProperty("organisationName")
    private String organisationName;
    @JsonProperty("businessType")
    private String businessType;

    @JsonProperty("loginId")
    public String getLoginId() {
        return loginId;
    }

    @JsonProperty("loginId")
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public SelfServeUserRegistrationDetailsBuilder withLoginId(String loginId) {
        this.loginId = loginId;
        return this;
    }

    @JsonProperty("contactDetails")
    public ContactDetailsBuilder getContactDetailsBuilder() {
        return contactDetailsBuilder;
    }

    @JsonProperty("contactDetails")
    public void setContactDetailsBuilder(ContactDetailsBuilder contactDetailsBuilder) {
        this.contactDetailsBuilder = contactDetailsBuilder;
    }

    public SelfServeUserRegistrationDetailsBuilder withContactDetails(ContactDetailsBuilder contactDetailsBuilder) {
        this.contactDetailsBuilder = contactDetailsBuilder;
        return this;
    }

    @JsonProperty("organisationName")
    public String getOrganisationName() {
        return organisationName;
    }

    @JsonProperty("organisationName")
    public void setOrganisationName(String organisationName) {
        this.organisationName = organisationName;
    }

    public SelfServeUserRegistrationDetailsBuilder withOrganisationName(String organisationName) {
        this.organisationName = organisationName;
        return this;
    }

    @JsonProperty("businessType")
    public String getBusinessType() {
        return businessType;
    }

    @JsonProperty("businessType")
    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public SelfServeUserRegistrationDetailsBuilder withBusinessType(String businessType) {
        this.businessType = businessType;
        return this;
    }

    @Override
    public String toString() {
        return "loginId:" + getLoginId()
                + ",contactDetails:" + getContactDetailsBuilder()
                + ",organisationName:" + getOrganisationName()
                + ",businessType:" + getBusinessType();
    }
}