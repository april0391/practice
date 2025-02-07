package edu.jwtyummi.service;

import edu.jwtyummi.model.dto.JoinDto;
import edu.jwtyummi.model.entity.User;
import edu.jwtyummi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class JoinService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User join(JoinDto joinDto) {
        String username = joinDto.getUsername();
        checkDuplicateUsername(username);

        User user = new User();
        user.setUsername(username);
        String rawPw = joinDto.getPassword();
        String encodedPw = passwordEncoder.encode(rawPw);
        user.setPassword(encodedPw);
        user.setRole("ROLE_ADMIN");

        return userRepository.save(user);
    }

    private void checkDuplicateUsername(String username) {
        /*userRepository.findByUsername(username).ifPresent(user -> {
            throw new IllegalStateException("Username already exists: " + username);
        });*/
        if (userRepository.existsByUsername(username)) {
            throw new IllegalStateException("Username already exists: " + username);
        }
    }
}
