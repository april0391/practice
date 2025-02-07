package toy.board.repository.mybatis;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
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
public class PostRepositoryMybatis implements PostRepository {

    private final PostMapper mapper;

    @Override
    public Post save(Post post) {
        mapper.save(post);
        return post;
    }

    @Override
    public Optional<Post> findById(Long id) {
        return mapper.findById(id);
    }

    @Override
    public List<Post> findAll(SearchCond cond) {
        return mapper.findAll(cond);
    }

    @Override
    public Page<Post> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Page<Post> findAll(SearchCond cond, Pageable pageable) {
        return null;
    }

}
