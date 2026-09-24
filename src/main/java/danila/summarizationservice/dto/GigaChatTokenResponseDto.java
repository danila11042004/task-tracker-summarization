package danila.summarizationservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GigaChatTokenResponseDto(
        @JsonProperty("access_token")
        String accessToken,
        @JsonProperty("expires_at")
        long expiresAt) {
}
