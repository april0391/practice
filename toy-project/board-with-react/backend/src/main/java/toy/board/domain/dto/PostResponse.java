package toy.board.domain.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostResponse {

    private Long id;
    private String title;
    private String content;

    private Long userId;
    private String writer;

    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
