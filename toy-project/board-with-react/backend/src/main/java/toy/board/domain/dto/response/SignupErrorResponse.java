package toy.board.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import toy.board.exception.SignupException;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class SignupErrorResponse {

    private SignupException.ErrorCode code;
    private String message;
    private Object detail;

    @Data
    private class ValidationError {
        private String field;
        private List<String> messages;
    }

    @Data
    private class DuplicateError {
    }
}
