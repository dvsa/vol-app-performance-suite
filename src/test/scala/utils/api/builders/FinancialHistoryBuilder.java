package utils.api.builders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "version",
        "bankrupt",
        "liquidation",
        "receivership",
        "administration",
        "disqualified",
        "insolvencyDetails",
        "insolvencyConfirmation"
})
public class FinancialHistoryBuilder {

    @JsonProperty("id")
    private String id;
    @JsonProperty("version")
    private String version;
    @JsonProperty("bankrupt")
    private String bankrupt;
    @JsonProperty("liquidation")
    private String liquidation;
    @JsonProperty("receivership")
    private String receivership;
    @JsonProperty("administration")
    private String administration;
    @JsonProperty("disqualified")
    private String disqualified;
    @JsonProperty("insolvencyDetails")
    private String insolvencyDetails;
    @JsonProperty("insolvencyConfirmation")
    private String insolvencyConfirmation;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public FinancialHistoryBuilder withId(String id) {
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

    public FinancialHistoryBuilder withVersion(String version) {
        this.version = version;
        return this;
    }

    @JsonProperty("bankrupt")
    public String getBankrupt() {
        return bankrupt;
    }

    @JsonProperty("bankrupt")
    public void setBankrupt(String bankrupt) {
        this.bankrupt = bankrupt;
    }

    public FinancialHistoryBuilder withBankrupt(String bankrupt) {
        this.bankrupt = bankrupt;
        return this;
    }

    @JsonProperty("liquidation")
    public String getLiquidation() {
        return liquidation;
    }

    @JsonProperty("liquidation")
    public void setLiquidation(String liquidation) {
        this.liquidation = liquidation;
    }

    public FinancialHistoryBuilder withLiquidation(String liquidation) {
        this.liquidation = liquidation;
        return this;
    }

    @JsonProperty("receivership")
    public String getReceivership() {
        return receivership;
    }

    @JsonProperty("receivership")
    public void setReceivership(String receivership) {
        this.receivership = receivership;
    }

    public FinancialHistoryBuilder withReceivership(String receivership) {
        this.receivership = receivership;
        return this;
    }

    @JsonProperty("administration")
    public String getAdministration() {
        return administration;
    }

    @JsonProperty("administration")
    public void setAdministration(String administration) {
        this.administration = administration;
    }

    public FinancialHistoryBuilder withAdministration(String administration) {
        this.administration = administration;
        return this;
    }

    @JsonProperty("disqualified")
    public String getDisqualified() {
        return disqualified;
    }

    @JsonProperty("disqualified")
    public void setDisqualified(String disqualified) {
        this.disqualified = disqualified;
    }

    public FinancialHistoryBuilder withDisqualified(String disqualified) {
        this.disqualified = disqualified;
        return this;
    }

    @JsonProperty("insolvencyDetails")
    public String getInsolvencyDetails() {
        return insolvencyDetails;
    }

    @JsonProperty("insolvencyDetails")
    public void setInsolvencyDetails(String insolvencyDetails) {
        this.insolvencyDetails = insolvencyDetails;
    }

    public FinancialHistoryBuilder withInsolvencyDetails(String insolvencyDetails) {
        this.insolvencyDetails = insolvencyDetails;
        return this;
    }

    @JsonProperty("insolvencyConfirmation")
    public String getInsolvencyConfirmation() {
        return insolvencyConfirmation;
    }

    @JsonProperty("insolvencyConfirmation")
    public void setInsolvencyConfirmation(String insolvencyConfirmation) {
        this.insolvencyConfirmation = insolvencyConfirmation;
    }

    public FinancialHistoryBuilder withInsolvencyConfirmation(String insolvencyConfirmation) {
        this.insolvencyConfirmation = insolvencyConfirmation;
        return this;
    }

    @Override
    public String toString() {
        return "id:" + getId() + ",version:" + getVersion() + ",bankrupt:" + getBankrupt()
        + ",liquidation:" + getLiquidation() + ",receivership:" + getReceivership()
        + ",administration:" + getAdministration() + ",disqualified:" + getDisqualified() + ",insolvencyDetails:" + getInsolvencyDetails()
        + ",insolvencyConfirmation:" + getInsolvencyConfirmation();
    }
}