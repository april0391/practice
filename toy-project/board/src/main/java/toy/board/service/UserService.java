package toy.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.board.domain.dto.UserForm;
import toy.board.domain.entity.User;
import toy.board.exception.UserException;
import toy.board.repository.UserRepository;
import toy.board.security.PasswordEncoder;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static toy.board.exception.UserException.ErrorCode.INVALID_PASSWORD;
import static toy.board.exception.UserException.ErrorCode.INVALID_USERNAME;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User save(UserForm userForm) {
        User user = dtoToEntity(userForm);
        return userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow();
    }

    @Transactional
    public User authenticate(String username, String rawPassword) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserException("등록되지 않은 아이디", INVALID_USERNAME));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new UserException("비밀번호 불일치", INVALID_PASSWORD);
        }
        return user;
    }

    private User dtoToEntity(UserForm userForm) {
        return new User(
                userForm.getUsername(),
                passwordEncoder.encode(userForm.getRawPassword()),
                userForm.getName(),
                userForm.getEmail(),
                userForm.getNickname(),
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)
        );
    }

}
