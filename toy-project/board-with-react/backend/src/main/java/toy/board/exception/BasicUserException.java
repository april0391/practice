package toy.board.exception;

import lombok.Getter;

@Getter
public class BasicUserException extends UserException {

    private final Long id;

    public BasicUserException(BasicErrorCode errorCode, Long id) {
        super(errorCode);
        this.id = id;
    }

    @Getter
    public enum ErrorCode implements BasicErrorCode {
        ID_NOT_FOUND("user.notFound.id");

        private final String messageKey;

        ErrorCode(String messageKey) {
            this.messageKey = messageKey;
        }
    }
}
