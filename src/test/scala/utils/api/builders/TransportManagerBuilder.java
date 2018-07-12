package utils.api.builders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "application",
        "firstName",
        "familyName",
        "hasEmail",
        "username",
        "emailAddress",
        "birthDate"
})
public class TransportManagerBuilder {

    @JsonProperty("application")
    private String application;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("familyName")
    private String familyName;
    @JsonProperty("hasEmail")
    private String hasEmail;
    @JsonProperty("username")
    private String username;
    @JsonProperty("emailAddress")
    private String emailAddress;
    @JsonProperty("birthDate")
    private String birthDate;

    @JsonProperty("application")
    public String getApplication() {
        return application;
    }

    @JsonProperty("application")
    public void setApplication(String application) {
        this.application = application;
    }

    public TransportManagerBuilder withApplication(String application) {
        this.application = application;
        return this;
    }

    @JsonProperty("firstName")
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("firstName")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public TransportManagerBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    @JsonProperty("familyName")
    public String getFamilyName() {
        return familyName;
    }

    @JsonProperty("familyName")
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public TransportManagerBuilder withFamilyName(String familyName) {
        this.familyName = familyName;
        return this;
    }

    @JsonProperty("hasEmail")
    public String getHasEmail() {
        return hasEmail;
    }

    @JsonProperty("hasEmail")
    public void setHasEmail(String hasEmail) {
        this.hasEmail = hasEmail;
    }

    public TransportManagerBuilder withHasEmail(String hasEmail) {
        this.hasEmail = hasEmail;
        return this;
    }

    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    @JsonProperty("username")
    public void setUsername(String username) {
        this.username = username;
    }

    public TransportManagerBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    @JsonProperty("emailAddress")
    public String getEmailAddress() {
        return emailAddress;
    }

    @JsonProperty("emailAddress")
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public TransportManagerBuilder withEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    @JsonProperty("birthDate")
    public String getBirthDate() {
        return birthDate;
    }

    @JsonProperty("birthDate")
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public TransportManagerBuilder withBirthDate(String birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    @Override
    public String toString() {
        return "application:" +  getApplication() + ",firstName:" + getFirstName() + ",familyName:" + getFamilyName()
        + ",hasEmail:" + getEmailAddress() + ",username:" + getUsername() + "emailAddress:" + getEmailAddress()
                + ",birthDate:" + getBirthDate();
    }
}