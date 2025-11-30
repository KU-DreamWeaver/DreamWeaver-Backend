package konkuk.dreamweaver.domain.dreamhistory.repository;

import konkuk.dreamweaver.domain.dreamhistory.entity.DreamHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DreamHistoryRepository extends JpaRepository<DreamHistory, Long> {

    // 유저의 꿈 기록을 최근순으로 조회
    List<DreamHistory> findAllByUserIdOrderByCreatedAtDesc(Long userId);
}
