package utils.api.builders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "prevHasLicence",
        "prevHadLicence",
        "prevBeenRefused",
        "prevBeenRevoked",
        "prevBeenAtPi",
        "prevBeenDisqualifiedTc",
        "prevPurchasedAssets",
        "version"
})
public class LicenceHistoryBuilder {

    @JsonProperty("id")
    private String id;
    @JsonProperty("prevHasLicence")
    private String prevHasLicence;
    @JsonProperty("prevHadLicence")
    private String prevHadLicence;
    @JsonProperty("prevBeenRefused")
    private String prevBeenRefused;
    @JsonProperty("prevBeenRevoked")
    private String prevBeenRevoked;
    @JsonProperty("prevBeenAtPi")
    private String prevBeenAtPi;
    @JsonProperty("prevBeenDisqualifiedTc")
    private String prevBeenDisqualifiedTc;
    @JsonProperty("prevPurchasedAssets")
    private String prevPurchasedAssets;
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

    public LicenceHistoryBuilder withId(String id) {
        this.id = id;
        return this;
    }

    @JsonProperty("prevHasLicence")
    public String getPrevHasLicence() {
        return prevHasLicence;
    }

    @JsonProperty("prevHasLicence")
    public void setPrevHasLicence(String prevHasLicence) {
        this.prevHasLicence = prevHasLicence;
    }

    public LicenceHistoryBuilder withPrevHasLicence(String prevHasLicence) {
        this.prevHasLicence = prevHasLicence;
        return this;
    }

    @JsonProperty("prevHadLicence")
    public String getPrevHadLicence() {
        return prevHadLicence;
    }

    @JsonProperty("prevHadLicence")
    public void setPrevHadLicence(String prevHadLicence) {
        this.prevHadLicence = prevHadLicence;
    }

    public LicenceHistoryBuilder withPrevHadLicence(String prevHadLicence) {
        this.prevHadLicence = prevHadLicence;
        return this;
    }

    @JsonProperty("prevBeenRefused")
    public String getPrevBeenRefused() {
        return prevBeenRefused;
    }

    @JsonProperty("prevBeenRefused")
    public void setPrevBeenRefused(String prevBeenRefused) {
        this.prevBeenRefused = prevBeenRefused;
    }

    public LicenceHistoryBuilder withPrevBeenRefused(String prevBeenRefused) {
        this.prevBeenRefused = prevBeenRefused;
        return this;
    }

    @JsonProperty("prevBeenRevoked")
    public String getPrevBeenRevoked() {
        return prevBeenRevoked;
    }

    @JsonProperty("prevBeenRevoked")
    public void setPrevBeenRevoked(String prevBeenRevoked) {
        this.prevBeenRevoked = prevBeenRevoked;
    }

    public LicenceHistoryBuilder withPrevBeenRevoked(String prevBeenRevoked) {
        this.prevBeenRevoked = prevBeenRevoked;
        return this;
    }

    @JsonProperty("prevBeenAtPi")
    public String getPrevBeenAtPi() {
        return prevBeenAtPi;
    }

    @JsonProperty("prevBeenAtPi")
    public void setPrevBeenAtPi(String prevBeenAtPi) {
        this.prevBeenAtPi = prevBeenAtPi;
    }

    public LicenceHistoryBuilder withPrevBeenAtPi(String prevBeenAtPi) {
        this.prevBeenAtPi = prevBeenAtPi;
        return this;
    }

    @JsonProperty("prevBeenDisqualifiedTc")
    public String getPrevBeenDisqualifiedTc() {
        return prevBeenDisqualifiedTc;
    }

    @JsonProperty("prevBeenDisqualifiedTc")
    public void setPrevBeenDisqualifiedTc(String prevBeenDisqualifiedTc) {
        this.prevBeenDisqualifiedTc = prevBeenDisqualifiedTc;
    }

    public LicenceHistoryBuilder withPrevBeenDisqualifiedTc(String prevBeenDisqualifiedTc) {
        this.prevBeenDisqualifiedTc = prevBeenDisqualifiedTc;
        return this;
    }

    @JsonProperty("prevPurchasedAssets")
    public String getPrevPurchasedAssets() {
        return prevPurchasedAssets;
    }

    @JsonProperty("prevPurchasedAssets")
    public void setPrevPurchasedAssets(String prevPurchasedAssets) {
        this.prevPurchasedAssets = prevPurchasedAssets;
    }

    public LicenceHistoryBuilder withPrevPurchasedAssets(String prevPurchasedAssets) {
        this.prevPurchasedAssets = prevPurchasedAssets;
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

    public LicenceHistoryBuilder withVersion(Integer version) {
        this.version = version;
        return this;
    }

    @Override
    public String toString() {
        return "id:" + getId() + ",prevHasLicence:" + getPrevHasLicence() + ",prevHadLicence:" + getPrevHadLicence() + ",prevBeenRefused:"
               + getPrevBeenRefused() + ",prevBeenRevoked:" + getPrevBeenRevoked() + ",prevBeenAtPi:" + getPrevBeenAtPi()
               + ",prevBeenDisqualifiedTc:" + getPrevBeenDisqualifiedTc() + ",prevPurchasedAssets:" + getPrevPurchasedAssets()
               + ",version:" + getVersion();
    }
}