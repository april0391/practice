package toy.board.exception;

import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class AuthException extends RuntimeException {

    private final ErrorCode errorCode;
    private JWTVerificationException jwtVerificationException;

    @Getter
    public enum ErrorCode implements BasicErrorCode {
        EMAIL_NOT_FOUND(""),
        INVALID_PASSWORD(""),
        AUTHORIZATION_HEADER_NOT_FOUND(""),
        INVALID_TOKEN_FORMAT(""),
        WRONG_TOKEN_VALUE("");

        private final String messageKey;

        ErrorCode(String messageKey) {
            this.messageKey = messageKey;
        }
    }
}
