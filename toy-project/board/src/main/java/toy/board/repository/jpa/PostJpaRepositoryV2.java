package toy.board.repository.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import toy.board.domain.dto.SearchCond;
import toy.board.domain.entity.Post;
import toy.board.repository.PostRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class PostJpaRepositoryV2 implements PostRepository {

    private final PostSpringDataJpaRepository repository;

    @Override
    public Post save(Post post) {
        return null;
    }

    @Override
    public Optional<Post> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Post> findAll(SearchCond cond) {
        return List.of();
    }

    @Override
    public Page<Post> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Page<Post> findAll(SearchCond cond, Pageable pageable) {
        return repository.findAll(pageable);
    }

}
