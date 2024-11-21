package toy.board.util.cache;

import lombok.RequiredArgsConstructor;
import toy.board.domain.entity.User;
import toy.board.repository.UserRepository;

@RequiredArgsConstructor
public class NoCacheUserCache implements UserCache {

    private final UserRepository userRepository;

    @Override
    public void putSession(User user) {

    }

    @Override
    public User getSession(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public void removeSession(Long id) {

    }
}
