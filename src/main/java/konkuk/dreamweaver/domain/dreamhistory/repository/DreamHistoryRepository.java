package konkuk.dreamweaver.domain.dreamhistory.repository;

import konkuk.dreamweaver.domain.dreamhistory.entity.DreamHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DreamHistoryRepository extends JpaRepository<DreamHistory, Long> {
}
