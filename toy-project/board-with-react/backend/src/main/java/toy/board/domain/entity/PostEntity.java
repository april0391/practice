package toy.board.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Table(name = "post")
@EntityListeners(AuditingEntityListener.class)
@Entity
public class PostEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String content;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    private UserEntity user;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}
