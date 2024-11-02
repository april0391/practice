package toy.board.repository.jpa;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toy.board.domain.entity.User;
import toy.board.repository.UserRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class JpaUserRepository implements UserRepository {

    private final EntityManager em;

    @Override
    public User save(User user) {
        em.persist(user);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        User user = em.find(User.class, id);
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByUsernameAndPassword(String username, String encodedPassword) {
        String jpql = "SELECT u FROM User u WHERE u.username = :username AND u.password = :password";
        return em.createQuery(jpql, User.class)
                .setParameter("username", username)
                .setParameter("password", encodedPassword)
                .getResultStream()
                .findFirst();
    }
}
