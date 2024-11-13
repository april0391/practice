package toy.board.repository;

import toy.board.domain.entity.Post;
import toy.board.domain.dto.SearchCond;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    Post save(Post post);

    Optional<Post> findById(Long id);

    List<Post> findAll(SearchCond cond);
}
