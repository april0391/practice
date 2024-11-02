package toy.board.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PostWithUserDto {

    private Long postId;
    private String title;
    private String content;
    private LocalDateTime registeredAt;
    private LocalDateTime updatedAt;

    private Long userId;
    private String nickname;

}
