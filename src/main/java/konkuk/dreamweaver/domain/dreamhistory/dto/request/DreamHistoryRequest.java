package konkuk.dreamweaver.domain.dreamhistory.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record DreamHistoryRequest(

        @Schema(description = "유저 ID", example = "1")
        Long userId,

        List<String> keywords,

        @Schema(description = "꿈에 대한 설명", example = "절벽에서 떨어지는 꿈을 꿨어요")
        String description,

        @Schema(description = "감정", example = "무서움")
        String emotion
) {
}
