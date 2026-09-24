package danila.summarizationservice.gigachat;

import danila.summarizationservice.dto.GigaChatRequestDto;
import danila.summarizationservice.dto.GigaChatResponseDto;
import danila.summarizationservice.dto.GigaChatTokenResponseDto;
import danila.summarizationservice.exception.GigaChatResponseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.restclient.autoconfigure.RestClientSsl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;
import java.util.UUID;


@Slf4j
@Component
public class GigaChatClient {
    private final GigaChatProperties gigaChatProperties;
    private final RestClient restClient;

    public GigaChatClient(GigaChatProperties gigaChatProperties, RestClientSsl ssl) {
        this.gigaChatProperties = gigaChatProperties;
        restClient = RestClient.builder()
                .apply(ssl.fromBundle("gigachat"))
                .build();
    }

    private String getAccessToken() {
        GigaChatTokenResponseDto responseDto = restClient.post()
                .uri(gigaChatProperties.authUrl())
                .header(HttpHeaders.AUTHORIZATION, "Basic " + gigaChatProperties.authorizationKey())
                .header("RqUID", UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body("scope=GIGACHAT_API_PERS")
                .retrieve()
                .body(GigaChatTokenResponseDto.class);
        Assert.notNull(responseDto, "GigaChatTokenResponseDto cannot be null");
        return responseDto.accessToken();
    }

    public String sendRequestForDailyReport(GigaChatRequestDto gigaChatRequestDto) {
        String accessToken = getAccessToken();
        GigaChatResponseDto gigaChatResponseDto = restClient.post()
                .uri(gigaChatProperties.chatUrl())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .body(gigaChatRequestDto)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(status -> !status.is2xxSuccessful(),
                        ((request, response) -> {
                            String body = new String(response.getBody().readAllBytes(), StandardCharsets.UTF_8);
                            log.error("Failed delete user tasks.Status: {} Body: {}", response.getStatusCode(),
                                    body);
                            throw new GigaChatResponseException("GigaChat return error");
                        }))
                .body(GigaChatResponseDto.class);
        return gigaChatResponseDto.choices().getFirst().message().content();
    }
}
