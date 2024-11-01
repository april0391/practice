package toy.board.repository;

import toy.board.domain.entity.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(Long id);

    User save(User user);

}
