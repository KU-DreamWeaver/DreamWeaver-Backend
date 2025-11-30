package konkuk.dreamweaver.domain.dreamhistory.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record DreamListResponse(
        Long dreamHistoryId,
        Long userId,
        List<String> keywords,
        String description,
        String emotion,
        String imageUrl,
        LocalDateTime createdAt
) {
    public static DreamListResponse of(
            Long dreamHistoryId,
            Long userId,
            List<String> keywords,
            String description,
            String emotion,
            String imageUrl,
            LocalDateTime createdAt
    ) {
        return new DreamListResponse(
                dreamHistoryId,
                userId,
                keywords,
                description,
                emotion,
                imageUrl,
                createdAt
        );
    }
}
