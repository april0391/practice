package toy.board.exception;

import lombok.Getter;

import java.util.Set;

@Getter
public class UserException extends RuntimeException {

    public final ErrorCode errorCode;
    public final Set<String> errorFields;

    public UserException(ErrorCode errorCode, Set<String> errorFields) {
        this.errorCode = errorCode;
        this.errorFields = errorFields;
    }

    public enum ErrorCode {
        DUPLICATE
    }
}
