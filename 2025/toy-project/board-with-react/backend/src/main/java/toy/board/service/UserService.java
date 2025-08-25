package toy.board.service;

import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import toy.board.domain.dto.user.SignupRequest;
import toy.board.domain.dto.user.UserDetailInfoResponse;
import toy.board.domain.dto.user.UserSimpleInfoResponse;
import toy.board.domain.entity.UserEntity;
import toy.board.exception.BasicUserException;
import toy.board.mapper.UserMapper;
import toy.board.repository.UserRepository;

import java.util.*;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;

    public UserSimpleInfoResponse signup(SignupRequest request) {
        validateDuplicateFields(request);

        String encryptedPassword = encryptPassword(request.getPassword());

        UserEntity entity = mapper.signupRequestToEntity(request, encryptedPassword);
        UserEntity saved = userRepository.save(entity);

        return mapper.entityToSimpleInfoResponse(saved);
    }

    public UserDetailInfoResponse getUserInfo(Long id) {
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new BasicUserException(BasicUserException.ErrorCode.ID_NOT_FOUND, id));
        return mapper.entityToInfoResponse(entity);
    }

    private void validateDuplicateFields(SignupRequest request) {
        String email = request.getEmail();
        String nickname = request.getNickname();

        List<Object> duplicateFields = userRepository.findDuplicateFields(email, nickname);

        if (!duplicateFields.isEmpty()) {
//            throw new SignupException(SignupException.ErrorCode.DUPLICATE, duplicateFields);
        }
    }

    public String encryptPassword(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

}
