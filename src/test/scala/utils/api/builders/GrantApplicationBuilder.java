package utils.api.builders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "duePeriod",
        "caseworkerNotes"
})
public class GrantApplicationBuilder {

    @JsonProperty("id")
    private String id;
    @JsonProperty("duePeriod")
    private String duePeriod;
    @JsonProperty("caseworkerNotes")
    private String caseworkerNotes;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public GrantApplicationBuilder withId(String id) {
        this.id = id;
        return this;
    }

    @JsonProperty("duePeriod")
    public String getDuePeriod() {
        return duePeriod;
    }

    @JsonProperty("duePeriod")
    public void setDuePeriod(String duePeriod) {
        this.duePeriod = duePeriod;
    }

    public GrantApplicationBuilder withDuePeriod(String duePeriod) {
        this.duePeriod = duePeriod;
        return this;
    }

    @JsonProperty("caseworkerNotes")
    public String getCaseworkerNotes() {
        return caseworkerNotes;
    }

    @JsonProperty("caseworkerNotes")
    public void setCaseworkerNotes(String caseworkerNotes) {
        this.caseworkerNotes = caseworkerNotes;
    }

    public GrantApplicationBuilder withCaseworkerNotes(String caseworkerNotes) {
        this.caseworkerNotes = caseworkerNotes;
        return this;
    }

    @Override
    public String toString() {
        return "id:" + getId() + ",duePeriod:" + getDuePeriod() + ",caseworkerNotes:" + getCaseworkerNotes();
    }
}