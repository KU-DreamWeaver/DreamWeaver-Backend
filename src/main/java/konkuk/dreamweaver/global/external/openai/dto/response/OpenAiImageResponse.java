package konkuk.dreamweaver.global.external.openai.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record OpenAiImageResponse(
        List<Data> data
) {
    public record Data(
            String url
    ) {
    }
}
