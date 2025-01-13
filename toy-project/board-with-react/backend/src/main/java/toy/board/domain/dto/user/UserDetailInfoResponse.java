package toy.board.domain.dto.user;

import lombok.Data;
import toy.board.domain.entity.PostEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserDetailInfoResponse {

    private Long id;

    private String email;

    private String nickname;

    private List<PostEntity> posts = new ArrayList<>();

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

    private LocalDateTime lastLoginDate;

}
