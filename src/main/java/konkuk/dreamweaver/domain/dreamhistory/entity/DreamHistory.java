package konkuk.dreamweaver.domain.dreamhistory.entity;

import jakarta.persistence.*;
import konkuk.dreamweaver.domain.user.entity.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Builder
public class DreamHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dream_history_id")
    private Long id;

    @Column(columnDefinition = "LONGTEXT")
    private String aiSummary;

    @Column(columnDefinition = "LONGTEXT")
    private String imageUrl;

    private String description;

    @CreatedDate
    private LocalDateTime createdAt;

    private String emotion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public static DreamHistory create(String emotion, String aiSummary, String description, String imageUrl, User user) {
        return DreamHistory.builder()
                .emotion(emotion)
                .aiSummary(aiSummary)
                .description(description)
                .imageUrl(imageUrl)
                .user(user)
                .build();
    }
}
