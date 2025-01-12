package toy.board.domain.dto;

import lombok.Data;

@Data
public class BasicErrorResponseDetail {

    private BasicErrorCode code;
    private String message;
    private Object detail;


}
