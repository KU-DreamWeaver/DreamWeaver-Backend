package konkuk.dreamweaver.global.external.openai.dto.response;

import java.util.List;

public record OpenAiTextResponse(
        List<Choice> choices
) {

    public record Choice (
            int index,
            ResponseMessage message
    ){
    }
}
