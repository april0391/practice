package toy.board.repository.jpa;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toy.board.domain.entity.User;
import toy.board.repository.UserRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class UserJpaRepository implements UserRepository {

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
    public User findByUsername(String username) {
        String jpql = "SELECT u FROM user u WHERE u.username = :username";
        try {
            return em.createQuery(jpql, User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
