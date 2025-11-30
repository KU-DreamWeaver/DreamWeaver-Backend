package konkuk.dreamweaver.domain.dreamhistory.dto.response;

import konkuk.dreamweaver.domain.dreamhistory.entity.DreamHistory;
import lombok.Builder;

@Builder
public record DreamHistoryResponse(
        Long dreamHistoryId,
        String description,
        String imageUrl
) {
    public static DreamHistoryResponse from(DreamHistory dreamHistory) {
        return DreamHistoryResponse.builder()
                .dreamHistoryId(dreamHistory.getId())
                .description(dreamHistory.getDescription())
                .imageUrl(dreamHistory.getImageUrl())
                .build();
    }
}
