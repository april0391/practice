package toy.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.board.domain.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
