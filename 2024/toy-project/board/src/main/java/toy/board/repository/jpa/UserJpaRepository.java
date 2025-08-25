package toy.board.repository.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toy.board.domain.entity.User;
import toy.board.exception.UserException;
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
    public Optional<User> findByUsername(String username) {
        String jpql = "SELECT u FROM User u WHERE u.username = :username";
        try {
            User find = em.createQuery(jpql, User.class)
                    .setParameter("username", username)
                    .getSingleResult();
            return Optional.of(find);

        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
