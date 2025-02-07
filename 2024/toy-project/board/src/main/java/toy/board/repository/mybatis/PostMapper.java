package toy.board.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import toy.board.domain.dto.SearchCond;
import toy.board.domain.entity.Post;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PostMapper {

    void save(Post post);

    Optional<Post> findById(@Param("id") Long id);

    List<Post> findAll(@Param("cond") SearchCond cond);
}
