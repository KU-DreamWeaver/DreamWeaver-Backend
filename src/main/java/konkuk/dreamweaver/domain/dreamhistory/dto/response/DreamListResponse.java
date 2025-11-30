package konkuk.dreamweaver.domain.dreamhistory.dto.response;

import konkuk.dreamweaver.domain.dreamhistory.entity.DreamHistory;

import java.time.LocalDateTime;

public record DreamListResponse(
        String aiSummary,
        String imageUrl,
        LocalDateTime createdAt
) {
    public static DreamListResponse of(String description, String imageUrl, LocalDateTime createdAt) {
        return new DreamListResponse(description, imageUrl, createdAt);
    }

    public static DreamListResponse from(DreamHistory dreamHistory) {
        return new DreamListResponse(
                dreamHistory.getAiSummary(),
                dreamHistory.getImageUrl(),
                dreamHistory.getCreatedAt()
        );
    }
}
