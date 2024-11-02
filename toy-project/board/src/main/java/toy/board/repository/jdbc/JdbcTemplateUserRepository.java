package toy.board.repository.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import toy.board.domain.entity.User;
import toy.board.repository.UserRepository;

import java.util.Optional;

@RequiredArgsConstructor
public class JdbcTemplateUserRepository implements UserRepository {

    private final JdbcTemplate template;

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByUsernameAndPassword(String username, String password) {
        return Optional.empty();
    }
}
