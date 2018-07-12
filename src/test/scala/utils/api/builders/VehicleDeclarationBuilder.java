package utils.api.builders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "psvVehicleSize",
        "psvNoSmallVhlConfirmation",
        "psvOperateSmallVhl",
        "psvSmallVhlNotes",
        "psvLimousines",
        "psvNoLimousineConfirmation",
        "psvOnlyLimousinesConfirmation",
        "version"
})
public class VehicleDeclarationBuilder {

    @JsonProperty("id")
    private String id;
    @JsonProperty("psvVehicleSize")
    private String psvVehicleSize;
    @JsonProperty("psvNoSmallVhlConfirmation")
    private String psvNoSmallVhlConfirmation;
    @JsonProperty("psvOperateSmallVhl")
    private String psvOperateSmallVhl;
    @JsonProperty("psvSmallVhlNotes")
    private String psvSmallVhlNotes;
    @JsonProperty("psvLimousines")
    private String psvLimousines;
    @JsonProperty("psvNoLimousineConfirmation")
    private String psvNoLimousineConfirmation;
    @JsonProperty("psvOnlyLimousinesConfirmation")
    private String psvOnlyLimousinesConfirmation;
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

    public VehicleDeclarationBuilder withId(String id) {
        this.id = id;
        return this;
    }

    @JsonProperty("psvVehicleSize")
    public String getPsvVehicleSize() {
        return psvVehicleSize;
    }

    @JsonProperty("psvVehicleSize")
    public void setPsvVehicleSize(String psvVehicleSize) {
        this.psvVehicleSize = psvVehicleSize;
    }

    public VehicleDeclarationBuilder withPsvVehicleSize(String psvVehicleSize) {
        this.psvVehicleSize = psvVehicleSize;
        return this;
    }

    @JsonProperty("psvNoSmallVhlConfirmation")
    public String getPsvNoSmallVhlConfirmation() {
        return psvNoSmallVhlConfirmation;
    }

    @JsonProperty("psvNoSmallVhlConfirmation")
    public void setPsvNoSmallVhlConfirmation(String psvNoSmallVhlConfirmation) {
        this.psvNoSmallVhlConfirmation = psvNoSmallVhlConfirmation;
    }

    public VehicleDeclarationBuilder withPsvNoSmallVhlConfirmation(String psvNoSmallVhlConfirmation) {
        this.psvNoSmallVhlConfirmation = psvNoSmallVhlConfirmation;
        return this;
    }

    @JsonProperty("psvOperateSmallVhl")
    public String getPsvOperateSmallVhl() {
        return psvOperateSmallVhl;
    }

    @JsonProperty("psvOperateSmallVhl")
    public void setPsvOperateSmallVhl(String psvOperateSmallVhl) {
        this.psvOperateSmallVhl = psvOperateSmallVhl;
    }

    public VehicleDeclarationBuilder withPsvOperateSmallVhl(String psvOperateSmallVhl) {
        this.psvOperateSmallVhl = psvOperateSmallVhl;
        return this;
    }

    @JsonProperty("psvSmallVhlNotes")
    public String getPsvSmallVhlNotes() {
        return psvSmallVhlNotes;
    }

    @JsonProperty("psvSmallVhlNotes")
    public void setPsvSmallVhlNotes(String psvSmallVhlNotes) {
        this.psvSmallVhlNotes = psvSmallVhlNotes;
    }

    public VehicleDeclarationBuilder withPsvSmallVhlNotes(String psvSmallVhlNotes) {
        this.psvSmallVhlNotes = psvSmallVhlNotes;
        return this;
    }

    @JsonProperty("psvLimousines")
    public String getPsvLimousines() {
        return psvLimousines;
    }

    @JsonProperty("psvLimousines")
    public void setPsvLimousines(String psvLimousines) {
        this.psvLimousines = psvLimousines;
    }

    public VehicleDeclarationBuilder withPsvLimousines(String psvLimousines) {
        this.psvLimousines = psvLimousines;
        return this;
    }

    @JsonProperty("psvNoLimousineConfirmation")
    public String getPsvNoLimousineConfirmation() {
        return psvNoLimousineConfirmation;
    }

    @JsonProperty("psvNoLimousineConfirmation")
    public void setPsvNoLimousineConfirmation(String psvNoLimousineConfirmation) {
        this.psvNoLimousineConfirmation = psvNoLimousineConfirmation;
    }

    public VehicleDeclarationBuilder withPsvNoLimousineConfirmation(String psvNoLimousineConfirmation) {
        this.psvNoLimousineConfirmation = psvNoLimousineConfirmation;
        return this;
    }

    @JsonProperty("psvOnlyLimousinesConfirmation")
    public String getPsvOnlyLimousinesConfirmation() {
        return psvOnlyLimousinesConfirmation;
    }

    @JsonProperty("psvOnlyLimousinesConfirmation")
    public void setPsvOnlyLimousinesConfirmation(String psvOnlyLimousinesConfirmation) {
        this.psvOnlyLimousinesConfirmation = psvOnlyLimousinesConfirmation;
    }

    public VehicleDeclarationBuilder withPsvOnlyLimousinesConfirmation(String psvOnlyLimousinesConfirmation) {
        this.psvOnlyLimousinesConfirmation = psvOnlyLimousinesConfirmation;
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

    public VehicleDeclarationBuilder withVersion(Integer version) {
        this.version = version;
        return this;
    }

    @Override
    public String toString() {
        return "id:" + getId() + ",psvVehicleSize:" + getPsvVehicleSize() + ",psvNoSmallVhlConfirmation:" + getPsvNoSmallVhlConfirmation()
        + ",psvOperateSmallVhl:" + getPsvOperateSmallVhl() + ",psvSmallVhlNotes:" + getPsvOperateSmallVhl()
        + ",psvLimousines:" + getPsvLimousines() + ",psvNoLimousineConfirmation:" + getPsvNoLimousineConfirmation()
        + ",psvOnlyLimousinesConfirmation:" + getPsvOnlyLimousinesConfirmation() + ",version:" + getVersion();
    }
}