package konkuk.dreamweaver.domain.dreamhistory.dto.response;

public record DreamHistoryResponse(
        String description,
        String imageUrl
) {
    public static DreamHistoryResponse of(String description, String imageUrl) {
        return new DreamHistoryResponse(description, imageUrl);
    }
}
