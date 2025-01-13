package toy.board.service;

import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import toy.board.domain.dto.user.LoginRequest;
import toy.board.domain.dto.user.UserSimpleInfoResponse;
import toy.board.domain.entity.UserEntity;
import toy.board.exception.AuthException;
import toy.board.mapper.UserMapper;
import toy.board.repository.UserRepository;
import toy.board.util.JwtUtils;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final JwtUtils jwtUtils;

    public UserSimpleInfoResponse loginProcess(LoginRequest request) {
        UserEntity entity = repository.findByEmail(request.email())
                .orElseThrow(() -> new AuthException(AuthException.ErrorCode.EMAIL_NOT_FOUND));

        if (!BCrypt.checkpw(request.password(), entity.getPassword())) {
            throw new AuthException(AuthException.ErrorCode.INVALID_PASSWORD);
        }

        return mapper.entityToSimpleInfoResponse(entity);
    }

    public String createToken(Long id) {
        return jwtUtils.createToken(String.valueOf(id));
    }

}
