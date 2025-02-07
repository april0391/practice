package toy.board.domain.dto;

import lombok.Data;
import toy.board.exception.BasicErrorCode;

@Data
public class BasicErrorResponseDetail {

    private BasicErrorCode code;
    private String message;
    private Object detail;

}
