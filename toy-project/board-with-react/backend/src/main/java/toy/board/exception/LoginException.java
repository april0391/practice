package toy.board.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoginException extends UserException {

    private final ErrorCode errorCode;

    public enum ErrorCode {
        USERNAME_INVALID,
        PASSWORD_INVALID
    }
}
