package toy.board.service;

import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import toy.board.domain.dto.UserSignupRequest;
import toy.board.domain.dto.UserSignupResponse;
import toy.board.domain.entity.UserEntity;
import toy.board.mapper.UserMapper;
import toy.board.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;

    public UserSignupResponse signup(UserSignupRequest request) {
        String encryptedPassword = encryptPassword(request.getInputPassword());

        UserEntity entity = mapper.signupRequestToEntity(request, encryptedPassword);
        UserEntity saved = userRepository.save(entity);

        return mapper.entityToSignupResponse(saved);
    }

    public UserEntity findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow();
    }

    public String encryptPassword(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }
}
