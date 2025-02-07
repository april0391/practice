package toy.board.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.board.domain.entity.UserSession;

import java.util.Optional;

public interface UserSessionSpringJpaRepository extends JpaRepository<UserSession, Long> {

    Optional<UserSession> findByUserId(Long id);
}
