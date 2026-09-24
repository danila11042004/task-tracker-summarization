package danila.summarizationservice.gigachat.model;

import danila.summarizationservice.enums.GigaChatMessageRole;

public record GigaChatMessageRequest(GigaChatMessageRole role, String content) {
}
