package konkuk.dreamweaver.domain.dreamhistory.dto.request;

import java.util.List;

public record DreamHistoryRequest(
        List<String> keywords,
        String description,
        String emotion
) {
}
