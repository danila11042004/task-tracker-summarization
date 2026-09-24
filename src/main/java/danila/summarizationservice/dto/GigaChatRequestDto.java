package danila.summarizationservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import danila.summarizationservice.enums.GigaChatModel;
import danila.summarizationservice.gigachat.model.GigaChatMessageRequest;
import danila.summarizationservice.gigachat.model.GigaChatResponseFormat;

import java.util.List;

public record GigaChatRequestDto(
        GigaChatModel model,
        List<GigaChatMessageRequest> messages,
        boolean stream,
        @JsonProperty("response_format")
        GigaChatResponseFormat responseFormat) {
}
