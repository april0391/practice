package toy.board.repository.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import toy.board.domain.entity.Post;

public interface PostSpringDataJpaRepository extends JpaRepository<Post, Long> {

    @Override
    Page<Post> findAll(Pageable pageable);
}
