package toy.board.service;

import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import toy.board.domain.dto.UserSignupRequest;
import toy.board.domain.dto.UserSignupResponse;
import toy.board.domain.entity.UserEntity;
import toy.board.exception.UserException;
import toy.board.mapper.UserMapper;
import toy.board.repository.UserRepository;

import java.util.*;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;

    public UserSignupResponse signup(UserSignupRequest request) {
        validateDuplicateFields(request);

        String encryptedPassword = encryptPassword(request.getInputPassword());

        UserEntity entity = mapper.signupRequestToEntity(request, encryptedPassword);
        UserEntity saved = userRepository.save(entity);

        return mapper.entityToSignupResponse(saved);
    }

    private void validateDuplicateFields(UserSignupRequest request) {
        Set<String> duplicateFields = new HashSet<>();

        String email = request.getEmail();
        if (userRepository.existsByEmail(email)) {
            duplicateFields.add("email");
        }

        String nickname = request.getNickname();
        if (userRepository.existsByNickname(nickname)) {
            duplicateFields.add("nickname");
        }

        if (!duplicateFields.isEmpty()) {
            throw new UserException(UserException.ErrorCode.DUPLICATE, duplicateFields);
        }
    }

    public UserEntity findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow();
    }

    public String encryptPassword(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }
}
