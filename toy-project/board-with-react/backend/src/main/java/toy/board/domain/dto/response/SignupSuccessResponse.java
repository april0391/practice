package toy.board.domain.dto.response;

import lombok.Data;

@Data
public class SignupSuccessResponse {
    private Long id;
    private String email;
    private String nickname;
}
