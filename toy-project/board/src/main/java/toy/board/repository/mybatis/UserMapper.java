package toy.board.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import toy.board.domain.entity.User;

import java.util.Optional;

@Mapper
public interface UserMapper {

    void save(User user);

    Optional<User> findById(@Param("id") Long id);

    Optional<User> findByUsername(@Param("username") String username);

}
