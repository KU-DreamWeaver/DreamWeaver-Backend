package konkuk.dreamweaver.domain.user.entity.repository;

import konkuk.dreamweaver.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
