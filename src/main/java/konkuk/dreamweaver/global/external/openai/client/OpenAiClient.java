package konkuk.dreamweaver.global.external.openai.client;

import konkuk.dreamweaver.global.external.openai.dto.request.ChatRequestMessage;
import konkuk.dreamweaver.global.external.openai.dto.request.OpenAiImageRequest;
import konkuk.dreamweaver.global.external.openai.dto.request.OpenAiTextRequest;
import konkuk.dreamweaver.global.external.openai.dto.response.OpenAiImageResponse;
import konkuk.dreamweaver.global.external.openai.dto.response.OpenAiTextResponse;
import konkuk.dreamweaver.global.properties.OpenAiProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Objects;

@Component
public class OpenAiClient {

    public static final String REQUEST_URI = "/chat/completions";
    public static final String IMAGE_REQUEST_URI = "/images/generations";

    private final RestClient restClient;
    private final OpenAiProperties properties;

    public OpenAiClient(OpenAiProperties properties) {
        this.properties = properties;
        this.restClient = RestClient.builder()
                .baseUrl(properties.baseUrl())
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + properties.secretKey())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public String sendTextRequest(List<ChatRequestMessage> messages) {
        OpenAiTextRequest request = new OpenAiTextRequest(
                properties.chat().model(),
                messages,
                properties.chat().maxTokens(),
                properties.chat().temperature()
        );

        OpenAiTextResponse response = restClient.post()
                .uri(REQUEST_URI)
                .body(request)
                .retrieve()
                .body(OpenAiTextResponse.class);

        return parseContent(Objects.requireNonNull(response));
    }


    public String sendImageReqeust(List<ChatRequestMessage> messages) {
        OpenAiImageRequest request = new OpenAiImageRequest(
                properties.image().model(),
                messages,
                1,
                properties.image().size()
        );

        OpenAiImageResponse response = restClient.post()
                .uri(IMAGE_REQUEST_URI)
                .body(request)
                .retrieve()
                .body(OpenAiImageResponse.class);

        OpenAiImageResponse safeResponse = Objects.requireNonNull(response);

        return safeResponse.data().get(0).url();
    }


    private String parseContent(OpenAiTextResponse response) {
        return response.choices().get(0).message().content().trim();
    }
}
