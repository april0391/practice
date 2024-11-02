package toy.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.board.domain.dto.UserForm;
import toy.board.domain.entity.User;
import toy.board.repository.UserRepository;
import toy.board.security.PasswordEncoder;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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

    public User authenticate(String username, String rawPassword) {
        return userRepository.findByUsernameAndPassword(username, passwordEncoder.encode(rawPassword))
                .orElseThrow();
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
