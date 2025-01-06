package toy.board.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@Entity
public class Post {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String content;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}
