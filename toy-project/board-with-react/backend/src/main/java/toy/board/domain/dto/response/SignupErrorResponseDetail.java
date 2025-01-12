package toy.board.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import toy.board.domain.dto.BasicErrorResponseDetail;
import toy.board.domain.dto.BasicErrorCode;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class SignupErrorResponseDetail extends BasicErrorResponseDetail {

    private BasicErrorCode code;
    private String message;
    private Object detail;

    @Data
    private static class ValidationError {
        private String field;
        private List<String> messages;
    }

    @Data
    private static class DuplicateError {
    }
}
