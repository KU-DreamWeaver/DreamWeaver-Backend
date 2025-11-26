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


    public String sendImageRequest(String prompt) {
        try {
            OpenAiImageRequest request = new OpenAiImageRequest(
                    properties.image().model(),
                    prompt,
                    1,
                    properties.image().size(),
                    properties.image().quality()
            );

            OpenAiImageResponse response = restClient.post()
                    .uri(IMAGE_REQUEST_URI)
                    .body(request)
                    .retrieve()
                    .body(OpenAiImageResponse.class);

            if (response == null || response.data() == null || response.data().isEmpty()) {
                throw new IllegalStateException("OpenAI 이미지 생성 실패: data 필드가 비어 있습니다.");
            }

            OpenAiImageResponse.Data data = response.data().get(0);

            if (data.url() != null && !data.url().isBlank()) {
                return data.url();
            }

            if (data.b64Json() != null && !data.b64Json().isBlank()) {
                return "data:image/png;base64," + data.b64Json();
            }

            throw new IllegalStateException("OpenAI 이미지 생성 실패: url과 b64_json이 모두 비어 있습니다.");

        } catch (Exception e) {
            throw new IllegalStateException("이미지 생성 요청 실패: " + e.getMessage());
        }
    }


    private String parseContent(OpenAiTextResponse response) {
        return response.choices().get(0).message().content().trim();
    }
}
