package toy.board.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String title;

    @Setter
    private String content;

    private LocalDateTime registeredAt;

    @Setter
    private LocalDateTime updatedAt;

    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    public Post(String title, String content, LocalDateTime registeredAt, LocalDateTime updatedAt, User user) {
        this.title = title;
        this.content = content;
        this.registeredAt = registeredAt;
        this.updatedAt = updatedAt;
        this.user = user;
    }

}
