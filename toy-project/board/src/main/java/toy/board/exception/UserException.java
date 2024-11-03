package toy.board.exception;

public class UserException extends RuntimeException {

    private final ErrorCode errorCode;

    public UserException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public UserException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public enum ErrorCode {
        INVALID_USERNAME, INVALID_PASSWORD
    }
}
