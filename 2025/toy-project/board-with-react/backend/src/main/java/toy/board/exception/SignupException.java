package toy.board.exception;

import lombok.Getter;
import org.springframework.validation.BindingResult;

import java.util.Set;

@Getter
public class SignupException extends UserException {

    private Set<String> duplicateFields;
    private BindingResult bindingResult;

    public SignupException(ErrorCode errorCode, BindingResult bindingResult) {
        super(errorCode);
        this.bindingResult = bindingResult;
    }

    public SignupException(ErrorCode errorCode, Set<String> duplicateFields) {
        super(errorCode);
        this.duplicateFields = duplicateFields;
    }

    @Getter
    public enum ErrorCode implements BasicErrorCode {
        VALIDATION("userSignupRequest.validation"),
        DUPLICATE("userSignupRequest.duplicate");

        private final String messageKey;

        ErrorCode(String messageKey) {
            this.messageKey = messageKey;
        }
    }

}
