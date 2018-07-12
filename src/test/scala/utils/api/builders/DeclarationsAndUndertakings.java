package utils.api.builders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "version",
        "id",
        "declarationConfirmation",
        "interimRequested",
        "signatureType",
        "interimReason"
})
public class DeclarationsAndUndertakings {

    @JsonProperty("version")
    private String version;
    @JsonProperty("id")
    private String id;
    @JsonProperty("declarationConfirmation")
    private String declarationConfirmation;
    @JsonProperty("interimRequested")
    private String interimRequested;
    @JsonProperty("interimReason")
    private String interimReason;
    @JsonProperty("signatureType")
    private String signatureType;


    @JsonProperty("version")
    public String getVersion() {
        return version;
    }

    @JsonProperty("version")
    public void setVersion(String version) {
        this.version = version;
    }

    public DeclarationsAndUndertakings withVersion(String version) {
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

    public DeclarationsAndUndertakings withId(String id) {
        this.id = id;
        return this;
    }

    @JsonProperty("signatureType")
    public String getSignatureType() {
        return signatureType;
    }

    @JsonProperty("signatureType")
    public void setSignatureType(String signatureType) {
        this.signatureType = signatureType;
    }

    public DeclarationsAndUndertakings withSignatureType(String signatureType) {
        this.signatureType = signatureType;
        return this;
    }

    @JsonProperty("interimRequested")
    public String getInterimRequested() {
        return interimRequested;
    }

    @JsonProperty("interimRequested")
    public void setInterimRequested(String interimRequested) {
        this.interimRequested = interimRequested;
    }

    public DeclarationsAndUndertakings withInterimRequested(String interimRequested) {
        this.interimRequested = interimRequested;
        return this;
    }

    @JsonProperty("interimReason")
    public String getInterimReason() {
        return interimReason;
    }

    @JsonProperty("goodsApplicationInterimReason")
    public void setGoodsApplicationInterimReason(String interimReason) {
        this.interimReason = interimReason;
    }

    public DeclarationsAndUndertakings withInterimReason(String interimReason) {
        this.interimReason = interimReason;
        return this;
    }

    @JsonProperty("declarationConfirmation")
    public String getDeclarationConfirmation() {
        return declarationConfirmation;
    }

    @JsonProperty("declarationConfirmation")
    public void setDeclarationConfirmation(String declarationConfirmation) {
        this.declarationConfirmation = declarationConfirmation;
    }

    public DeclarationsAndUndertakings withDeclarationConfirmation(String declarationConfirmation) {
        this.declarationConfirmation = declarationConfirmation;
        return this;
    }

    @Override
    public String toString() {
        return "version:" + getVersion() + "id:" + getId() + "signatureType:" + getSignatureType()
                + "interimRequested:" + getInterimRequested() + "interimReason:" + getInterimReason() + "declarationConfirmation:" + getDeclarationConfirmation();
    }
}