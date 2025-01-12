package toy.board.exception;

import lombok.Getter;
import org.springframework.validation.BindingResult;
import toy.board.domain.dto.BasicErrorCode;

import java.util.Set;

@Getter
public class SignupException extends UserException {

    private final SignupErrorCode signupErrorCode;
    private Set<String> duplicateFields;
    private BindingResult bindingResult;

    public SignupException(SignupErrorCode signupErrorCode, BindingResult bindingResult) {
        this.signupErrorCode = signupErrorCode;
        this.bindingResult = bindingResult;
    }

    public SignupException(SignupErrorCode signupErrorCode, Set<String> duplicateFields) {
        this.signupErrorCode = signupErrorCode;
        this.duplicateFields = duplicateFields;
    }

    public enum SignupErrorCode implements BasicErrorCode {
        BINDING,
        DUPLICATE
    }
}
