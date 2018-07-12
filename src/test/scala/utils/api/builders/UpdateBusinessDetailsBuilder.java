package utils.api.builders;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "natureOfBusiness",
        "version",
        "name",
        "licence",
        "companyOrLlpNo",
        "registeredAddress"
})
public class UpdateBusinessDetailsBuilder {

    @JsonProperty("id")
    private String id;
    @JsonProperty("natureOfBusiness")
    private String natureOfBusiness;
    @JsonProperty("version")
    private String version;
    @JsonProperty("name")
    private String name;
    @JsonProperty("licence")
    private String licence;
    @JsonProperty("companyOrLlpNo")
    private String companyOrLlpNo;
    @JsonProperty("registeredAddress")
    private AddressBuilder address;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public UpdateBusinessDetailsBuilder withId(String id) {
        this.id = id;
        return this;
    }

    @JsonProperty("natureOfBusiness")
    public String getNatureOfBusiness() {
        return natureOfBusiness;
    }

    @JsonProperty("natureOfBusiness")
    public void setNatureOfBusiness(String natureOfBusiness) {
        this.natureOfBusiness = natureOfBusiness;
    }

    public UpdateBusinessDetailsBuilder withNatureOfBusiness(String natureOfBusiness) {
        this.natureOfBusiness = natureOfBusiness;
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

    public UpdateBusinessDetailsBuilder withVersion(String version) {
        this.version = version;
        return this;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public UpdateBusinessDetailsBuilder withName(String name) {
        this.name = name;
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

    public UpdateBusinessDetailsBuilder withLicence(String licence) {
        this.licence = licence;
        return this;
    }

    @JsonProperty("companyOrLlpNo")
    public String getCompanyOrLlpNo() {
        return companyOrLlpNo;
    }

    @JsonProperty("companyOrLlpNo")
    public void setCompanyOrLlpNo(String companyOrLlpNo) {
        this.companyOrLlpNo = companyOrLlpNo;
    }

    public UpdateBusinessDetailsBuilder withCompanyNumber(String companyNumber) {
        this.companyOrLlpNo = companyNumber;
        return this;
    }

    @JsonProperty("registeredAddress")
    public AddressBuilder getAddress() {
        return address;
    }

    @JsonProperty("registeredAddress")
    public void setAddress(AddressBuilder address) {
        this.address = address;
    }

    public UpdateBusinessDetailsBuilder withAddress(AddressBuilder address) {
        this.address = address;
        return this;
    }

    @Override
    public String toString() {
        return "id:" + getId() + ",natureOfBusiness:" + getNatureOfBusiness() + ",version:" + getVersion() +
                ",name:" + getName() + ",licence:" + getLicence() + ",companyOrLlpNo:" + getCompanyOrLlpNo()
                + ",registeredAddress:" + getAddress();
    }

}