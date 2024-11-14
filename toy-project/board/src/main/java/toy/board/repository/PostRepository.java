package toy.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import toy.board.domain.entity.Post;
import toy.board.domain.dto.SearchCond;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    Post save(Post post);

    Optional<Post> findById(Long id);

    List<Post> findAll(SearchCond cond);

    Page<Post> findAll(Pageable pageable);

    Page<Post> findAll(SearchCond cond, Pageable pageable);

}
