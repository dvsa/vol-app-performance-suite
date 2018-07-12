package utils.api.builders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "version",
        "leadTcArea",
        "receivedDate",
        "targetCompletionDate",
        "overrideOppositionDate",
        "tracking"
})
public class OverviewBuilder {

    @JsonProperty("id")
    private String id;
    @JsonProperty("version")
    private int version;
    @JsonProperty("leadTcArea")
    private String leadTcArea;
    @JsonProperty("receivedDate")
    private Object receivedDate;
    @JsonProperty("targetCompletionDate")
    private Object targetCompletionDate;
    @JsonProperty("overrideOppositionDate")
    private String overrideOppositionDate;
    @JsonProperty("tracking")
    private Tracking tracking;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public OverviewBuilder withId(String id) {
        this.id = id;
        return this;
    }

    @JsonProperty("version")
    public int getVersion() {
        return version;
    }

    @JsonProperty("version")
    public void setVersion(int version) {
        this.version = version;
    }

    public OverviewBuilder withVersion(int version) {
        this.version = version;
        return this;
    }

    @JsonProperty("leadTcArea")
    public String getLeadTcArea() {
        return leadTcArea;
    }

    @JsonProperty("leadTcArea")
    public void setLeadTcArea(String leadTcArea) {
        this.leadTcArea = leadTcArea;
    }

    public OverviewBuilder withLeadTcArea(String leadTcArea) {
        this.leadTcArea = leadTcArea;
        return this;
    }

    @JsonProperty("receivedDate")
    public Object getReceivedDate() {
        return receivedDate;
    }

    @JsonProperty("receivedDate")
    public void setReceivedDate(Object receivedDate) {
        this.receivedDate = receivedDate;
    }

    public OverviewBuilder withReceivedDate(Object receivedDate) {
        this.receivedDate = receivedDate;
        return this;
    }

    @JsonProperty("targetCompletionDate")
    public Object getTargetCompletionDate() {
        return targetCompletionDate;
    }

    @JsonProperty("targetCompletionDate")
    public void setTargetCompletionDate(Object targetCompletionDate) {
        this.targetCompletionDate = targetCompletionDate;
    }

    public OverviewBuilder withTargetCompletionDate(Object targetCompletionDate) {
        this.targetCompletionDate = targetCompletionDate;
        return this;
    }

    @JsonProperty("overrideOppositionDate")
    public String getOverrideOppositionDate() {
        return overrideOppositionDate;
    }

    @JsonProperty("overrideOppositionDate")
    public void setOverrideOppositionDate(String overrideOppositionDate) {
        this.overrideOppositionDate = overrideOppositionDate;
    }

    public OverviewBuilder withOverrideOppositionDate(String overrideOppositionDate) {
        this.overrideOppositionDate = overrideOppositionDate;
        return this;
    }

    @JsonProperty("tracking")
    public Tracking getTracking() {
        return tracking;
    }

    @JsonProperty("tracking")
    public void setTracking(Tracking tracking) {
        this.tracking = tracking;
    }

    public OverviewBuilder withTracking(Tracking tracking) {
        this.tracking = tracking;
        return this;
    }

    @Override
    public String toString() {
        return "id:" + getId() + ",version:" + getVersion() + ",leadTcArea:" + getLeadTcArea()
                + ",receivedDate:" + getReceivedDate() + ",targetCompletionDate:" + getTargetCompletionDate() + ",overrideOppositionDate:" + getOverrideOppositionDate()
                + "tracking:" + getTracking();
    }
}