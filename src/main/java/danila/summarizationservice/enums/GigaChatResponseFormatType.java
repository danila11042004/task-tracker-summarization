package danila.summarizationservice.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum GigaChatResponseFormatType {
    @JsonProperty("text")
    TEXT,
    @JsonProperty("json_schema")
    JSON_SCHEMA
}
