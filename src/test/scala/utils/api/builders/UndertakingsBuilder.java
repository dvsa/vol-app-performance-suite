package utils.api.builders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "declarationsAndUndertakings"
})
public class UndertakingsBuilder {

    @JsonProperty("declarationsAndUndertakings")
    private DeclarationsAndUndertakings declarationsAndUndertakings;

    @JsonProperty("declarationsAndUndertakings")
    public DeclarationsAndUndertakings getDeclarationsAndUndertakings() {
        return declarationsAndUndertakings;
    }

    @JsonProperty("declarationsAndUndertakings")
    public void setDeclarationsAndUndertakings(DeclarationsAndUndertakings declarationsAndUndertakings) {
        this.declarationsAndUndertakings = declarationsAndUndertakings;
    }

    public UndertakingsBuilder withDeclarationsAndUndertakings(DeclarationsAndUndertakings declarationsAndUndertakings) {
        this.declarationsAndUndertakings = declarationsAndUndertakings;
        return this;
    }

    @Override
    public String toString() {
        return "declarationsAndUndertakings:" + getDeclarationsAndUndertakings();
    }
}