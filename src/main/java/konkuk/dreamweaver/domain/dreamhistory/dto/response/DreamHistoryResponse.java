package konkuk.dreamweaver.domain.dreamhistory.dto.response;

import konkuk.dreamweaver.domain.dreamhistory.entity.DreamHistory;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record DreamHistoryResponse(
        Long dreamHistoryId,
        Long userId,
        List<String> keywords,
        String aiSummary,
        String description,
        String emotion,
        String imageUrl,
        LocalDateTime createdAt
) {
    public static DreamHistoryResponse from(DreamHistory dreamHistory, List<String> keywords) {
        return DreamHistoryResponse.builder()
                .dreamHistoryId(dreamHistory.getId())
                .userId(dreamHistory.getUser().getId())
                .keywords(keywords)
                .aiSummary(dreamHistory.getAiSummary())
                .description(dreamHistory.getDescription())
                .emotion(dreamHistory.getEmotion())
                .imageUrl(dreamHistory.getImageUrl())
                .createdAt(dreamHistory.getCreatedAt())
                .build();
    }
}
