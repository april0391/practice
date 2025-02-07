package toy.board.util.cache;

import lombok.RequiredArgsConstructor;
import toy.board.domain.entity.User;
import toy.board.domain.entity.UserSession;
import toy.board.repository.UserRepository;
import toy.board.repository.jpa.UserSessionSpringJpaRepository;

import java.util.Optional;

@RequiredArgsConstructor
public class DbUserCache implements UserCache {

    private final UserRepository repository;
//    private final UserSessionSpringJpaRepository repository;

    @Override
    public void putUser(User user) {

    }

    @Override
    public User getUser(Long id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public void removeUser(Long id) {

    }
}
