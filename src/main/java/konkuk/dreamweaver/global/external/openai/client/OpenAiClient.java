package konkuk.dreamweaver.global.external.openai.client;

import konkuk.dreamweaver.global.exception.CustomException;
import konkuk.dreamweaver.global.external.openai.dto.request.ChatRequestMessage;
import konkuk.dreamweaver.global.external.openai.dto.request.OpenAiImageRequest;
import konkuk.dreamweaver.global.external.openai.dto.request.OpenAiTextRequest;
import konkuk.dreamweaver.global.external.openai.dto.response.OpenAiImageResponse;
import konkuk.dreamweaver.global.external.openai.dto.response.OpenAiTextResponse;
import konkuk.dreamweaver.global.external.openai.errorcode.OpenAiErrorCode;
import konkuk.dreamweaver.global.external.s3.client.S3ImageUploader;
import konkuk.dreamweaver.global.external.s3.errorcode.S3ErrorCode;
import konkuk.dreamweaver.global.properties.OpenAiProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Objects;

import static konkuk.dreamweaver.global.external.openai.errorcode.OpenAiErrorCode.*;
import static konkuk.dreamweaver.global.external.s3.errorcode.S3ErrorCode.*;

@Component
public class OpenAiClient {

    public static final String REQUEST_URI = "/chat/completions";
    public static final String IMAGE_REQUEST_URI = "/images/generations";

    private final RestClient restClient;
    private final OpenAiProperties properties;
    private final S3ImageUploader s3ImageUploader;

    public OpenAiClient(OpenAiProperties properties, S3ImageUploader s3ImageUploader) {
        this.properties = properties;
        this.s3ImageUploader = s3ImageUploader;
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
                throw new CustomException(INVALID_OPENAI_RESPONSE);
            }

            OpenAiImageResponse.Data data = response.data().get(0);

            if (data.url() != null && !data.url().isBlank()) {
                return data.url();
            }

            if (data.b64Json() != null && !data.b64Json().isBlank()) {
                return s3ImageUploader.uploadBase64Image(data.b64Json());
            }

            throw new CustomException(EMPTY_IMAGE_RESPONSE);

        } catch (CustomException ce) {
            throw ce;
        } catch (Exception e) {
            throw new CustomException(IMAGE_GENERATION_FAILED, e.getMessage());
        }
    }


    private String parseContent(OpenAiTextResponse response) {
        return response.choices().get(0).message().content().trim();
    }
}
