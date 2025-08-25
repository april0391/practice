package toy.board.domain.dto;

import lombok.Data;

@Data
public class UserForm {

    private String username;
    private String password;
    private String nickname;

    public UserForm(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }
}
