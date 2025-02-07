package toy.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import toy.board.domain.entity.PostEntity;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
}
