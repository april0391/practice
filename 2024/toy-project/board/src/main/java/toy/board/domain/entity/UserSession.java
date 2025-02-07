package toy.board.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.Data;
import lombok.NoArgsConstructor;
import toy.board.util.JwtConst;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class UserSession {

    @Id @GeneratedValue
    private Long id;

    private Long userId;

    private String username;

    private String nickname;

    private LocalDateTime registeredAt;

    private LocalDateTime expireAt;

    public UserSession(Long userId, String username, String nickname) {
        this.userId = userId;
        this.username = username;
        this.nickname = nickname;
    }

    @PrePersist
    private void register() {
        LocalDateTime now = LocalDateTime.now();
        this.registeredAt = now;
        this.expireAt = now.plusSeconds(JwtConst.EXPIRATION_TIME_SECOND_OF_ACCESS_TOKEN);
    }

}
