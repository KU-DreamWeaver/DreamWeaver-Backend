package konkuk.dreamweaver.global.external.openai.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@ToString
public class OpenAiImageRequest {
    private String model;
    private String prompt;
    private int n;
    private String size;
}
