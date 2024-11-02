package toy.board.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserForm {

    private String username;
    private String rawPassword;
    private String name;
    private String email;
    private String nickname;

}
