package utils.api.builders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "version",
        "financialEvidenceUploaded"
})
public class FinancialEvidenceBuilder {

    @JsonProperty("id")
    private String id;
    @JsonProperty("version")
    private Integer version;
    @JsonProperty("financialEvidenceUploaded")
    private Integer financialEvidenceUploaded;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public FinancialEvidenceBuilder withId(String id) {
        this.id = id;
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

    public FinancialEvidenceBuilder withVersion(Integer version) {
        this.version = version;
        return this;
    }

    @JsonProperty("financialEvidenceUploaded")
    public Integer getFinancialEvidenceUploaded() {
        return financialEvidenceUploaded;
    }

    @JsonProperty("financialEvidenceUploaded")
    public void setFinancialEvidenceUploaded(Integer financialEvidenceUploaded) {
        this.financialEvidenceUploaded = financialEvidenceUploaded;
    }

    public FinancialEvidenceBuilder withFinancialEvidenceUploaded(Integer financialEvidenceUploaded) {
        this.financialEvidenceUploaded = financialEvidenceUploaded;
        return this;
    }

    @Override
    public String toString() {
        return "id:" + getId() + ",version:" + getVersion() + ",financialEvidenceUploaded:" + getFinancialEvidenceUploaded();
    }
}