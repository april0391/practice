package toy.board.repository.mybatis;

import lombok.RequiredArgsConstructor;
import toy.board.domain.entity.User;
import toy.board.repository.UserRepository;

import java.util.Optional;

@RequiredArgsConstructor
public class MybatisUserRepository implements UserRepository {

    private final UserMapper mapper;

    @Override
    public User save(User user) {
        mapper.save(user);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        return mapper.findById(id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return mapper.findByUsername(username);
    }
}
