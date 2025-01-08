package toy.board.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserSignupRequest {

    @Email
    @NotBlank
    private String email;

    @JsonProperty("password")
    private String inputPassword;

    private String nickname;
}
