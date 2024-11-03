package toy.board.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "users")
@Setter
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String name;
    private String email;
    private String nickname;
    private LocalDateTime registeredAt;
    private LocalDateTime lastLoginAt;

    public User(String username, String password, String name, String email, String nickname, LocalDateTime registeredAt) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        this.registeredAt = registeredAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
