package toy.board.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserSignupRequest {

    @Email
    @NotBlank
    private String email;

    @JsonProperty("password")
    @NotBlank
    @Size(min = 4, max = 20)
    private String inputPassword;

    @NotBlank
    private String nickname;
}
