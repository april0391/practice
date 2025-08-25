package toy.board.service;

import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.board.domain.dto.UserForm;
import toy.board.domain.entity.User;
import toy.board.exception.UserException;
import toy.board.repository.UserRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User save(UserForm userForm) {
        User userEntity = createUserEntity(userForm);
        return userRepository.save(userEntity);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User signin(String username, String rawPassword) {
        User findUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserException("Username not found.", UserException.ErrorCode.USERNAME_INVALID));
        verifyPassword(rawPassword, findUser.getPassword());
        return findUser;
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

    private void verifyPassword(String rawPassword, String hashedPassword) {
        boolean checkPw = BCrypt.checkpw(rawPassword, hashedPassword);
        if (!checkPw) {
            throw new UserException("Password is incorrect.", UserException.ErrorCode.PASSWORD_INCORRECT);
        }
    }

}
