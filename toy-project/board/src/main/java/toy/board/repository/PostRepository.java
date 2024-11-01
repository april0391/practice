package toy.board.repository;

import toy.board.domain.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    List<Post> getPosts();

    Optional<Post> findById(Long id);

    Post save(Post post);

    void deleteById(Long id);
}
