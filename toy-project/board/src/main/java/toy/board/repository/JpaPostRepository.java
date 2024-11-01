package toy.board.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toy.board.domain.entity.Post;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class JpaPostRepository implements PostRepository {

    private final EntityManager em;

    @Override
    public List<Post> getPosts() {
        String jpql = "SELECT p FROM Post p";
        return em.createQuery(jpql, Post.class)
                .getResultList();
    }

    @Override
    public Optional<Post> findById(Long id) {
        Post post = em.find(Post.class, id);
        return Optional.ofNullable(post);
    }

    @Override
    public Post save(Post post) {
        em.persist(post);
        return post;
    }

    @Override
    public void deleteById(Long id) {
        Post post = em.find(Post.class, id);
        em.remove(post);
    }

}
