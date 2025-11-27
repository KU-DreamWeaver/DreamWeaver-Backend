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
    private String description;

    @Column(columnDefinition = "LONGTEXT")
    private String imageUrl;

    @CreatedDate
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public static DreamHistory create(String description, String imageUrl, User user) {
        return DreamHistory.builder()
                .description(description)
                .imageUrl(imageUrl)
                .user(user)
                .build();
    }
}
