package danila.summarizationservice.gigachat;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "gigachat")
public record GigaChatProperties(
        String authUrl,
        String chatUrl,
        String authorizationKey) {
}
