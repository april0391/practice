package toy.board.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@Table(name = "users")
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String email;

    private String password;

    private String nickname;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

}
