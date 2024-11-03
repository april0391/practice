package toy.board.repository.jpa;

import lombok.RequiredArgsConstructor;
import toy.board.domain.entity.User;
import toy.board.repository.UserRepository;

import java.util.Optional;

@RequiredArgsConstructor
public class IntegratedJpaUserRepository implements UserRepository {

    private final SpringDataJpaUserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
