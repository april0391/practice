package toy.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.board.domain.entity.Post;

public interface SpringDataJpaPostRepository extends JpaRepository<Post, Long> {
}
