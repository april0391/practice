package toy.board.exception;

import lombok.Getter;
import org.springframework.validation.BindingResult;

import java.util.Set;

@Getter
public class SignupException extends RuntimeException {

    private final ErrorCode errorCode;
    private Set<String> duplicateFields;
    private BindingResult bindingResult;

    public SignupException(ErrorCode errorCode, BindingResult bindingResult) {
        this.errorCode = errorCode;
        this.bindingResult = bindingResult;
    }

    public SignupException(ErrorCode errorCode, Set<String> duplicateFields) {
        this.errorCode = errorCode;
        this.duplicateFields = duplicateFields;
    }

    public enum ErrorCode {
        BINDING,
        DUPLICATE
    }
}
