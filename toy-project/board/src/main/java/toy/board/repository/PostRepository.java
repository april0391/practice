package toy.board.repository;

import toy.board.domain.dto.PostWithUserDto;
import toy.board.domain.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    List<PostWithUserDto> findAll();

    Optional<Post> findById(Long id);

    Optional<PostWithUserDto> findById_query(Long id);

    Post save(Post post);

    void deleteById(Long id);
}
