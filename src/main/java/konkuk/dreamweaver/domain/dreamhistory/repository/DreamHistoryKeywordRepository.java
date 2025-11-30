package konkuk.dreamweaver.domain.dreamhistory.repository;

import konkuk.dreamweaver.domain.dreamhistory.entity.DreamHistory;
import konkuk.dreamweaver.domain.dreamhistory.entity.DreamHistoryKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DreamHistoryKeywordRepository extends JpaRepository<DreamHistoryKeyword, Long> {
    List<DreamHistoryKeyword> findAllByDreamHistory(DreamHistory dreamHistory);
}
