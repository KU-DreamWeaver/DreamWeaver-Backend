package konkuk.dreamweaver.domain.dreamhistory.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class DreamHistoryKeyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dream_history_keyword_id")
    Long id;

    private String keyword;

    @JoinColumn(name = "dream_history_id")
    @ManyToOne(fetch = FetchType.LAZY)
    DreamHistory dreamHistory;

    public static DreamHistoryKeyword create(String keyword, DreamHistory dreamHistory) {
        return DreamHistoryKeyword.builder()
                .keyword(keyword)
                .dreamHistory(dreamHistory)
                .build();
    }

}
