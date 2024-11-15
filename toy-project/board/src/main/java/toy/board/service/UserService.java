package toy.board.service;

import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.board.domain.dto.UserForm;
import toy.board.domain.entity.User;
import toy.board.repository.UserRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User save(UserForm userForm) {
        return userRepository.save(createUserEntity(userForm));
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByUsername(String username) {
//        userRepository.
        return null;
    }

    private User createUserEntity(UserForm userForm) {
        return new User(
                userForm.getUsername(),
                generateHashedPassword(userForm.getPassword()),
                userForm.getNickname()
        );
    }

    private String generateHashedPassword(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }


}
