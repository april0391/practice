package toy.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import toy.board.domain.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT u.email, u.nickname FROM UserEntity u WHERE u.email = :email OR u.nickname = :nickname")
    List<Object> findDuplicateFields(@Param("email") String email, @Param("nickname") String nickname);

    Optional<UserEntity> findByEmail(String email);

}
