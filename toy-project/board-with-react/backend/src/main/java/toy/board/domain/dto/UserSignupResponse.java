package toy.board.domain.dto;

import lombok.Data;

@Data
public class UserSignupResponse {
    private Long id;
    private String email;
    private String nickname;
}
