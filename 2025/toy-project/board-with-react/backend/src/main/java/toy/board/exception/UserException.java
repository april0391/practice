package toy.board.exception;

import lombok.Getter;

@Getter
public abstract class UserException extends RuntimeException {

    private final BasicErrorCode errorCode;

    public UserException(BasicErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
