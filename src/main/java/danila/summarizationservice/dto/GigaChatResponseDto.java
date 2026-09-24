package danila.summarizationservice.dto;

import danila.summarizationservice.gigachat.model.GigaChatChoice;

import java.util.List;

public record GigaChatResponseDto(
        List<GigaChatChoice> choices) {
}
