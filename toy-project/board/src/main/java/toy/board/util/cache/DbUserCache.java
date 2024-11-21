package toy.board.util.cache;

import lombok.RequiredArgsConstructor;
import toy.board.domain.entity.User;
import toy.board.domain.entity.UserSession;
import toy.board.repository.jpa.UserSessionSpringJpaRepository;

import java.util.Optional;

@RequiredArgsConstructor
public class DbUserCache implements UserCache {

    private final UserSessionSpringJpaRepository repository;

    @Override
    public void putSession(User user) {
        UserSession session = new UserSession(
                user.getId(),
                user.getUsername(),
                user.getNickname()
        );
        repository.save(session);
    }

    @Override
    public User getSession(Long id) {
        UserSession userSession = repository.findByUserId(id).orElseThrow();

        return null;
    }

    @Override
    public void removeSession(Long id) {

    }
}
