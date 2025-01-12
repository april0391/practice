package toy.board.exception;

import lombok.Getter;
import toy.board.domain.dto.BasicErrorCode;

@Getter
public class UserException extends RuntimeException {

    private UserErrorCode userErrorCode;

    public UserException() {
    }

    public UserException(UserErrorCode userErrorCode) {
        this.userErrorCode = userErrorCode;
    }

    public enum UserErrorCode implements BasicErrorCode {
        ID_NOT_FOUND
    }
}
