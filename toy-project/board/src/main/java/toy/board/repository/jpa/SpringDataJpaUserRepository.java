package toy.board.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.board.domain.entity.User;

import java.util.Optional;

public interface SpringDataJpaUserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
