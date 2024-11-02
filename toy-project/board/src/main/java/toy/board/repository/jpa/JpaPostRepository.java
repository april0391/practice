package toy.board.repository.jpa;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toy.board.domain.dto.PostWithUserDto;
import toy.board.domain.entity.Post;
import toy.board.repository.PostRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class JpaPostRepository implements PostRepository {

    private final EntityManager em;

    @Override
    public Post save(Post post) {
        em.persist(post);
        return post;
    }

    @Override
    public Optional<Post> findById(Long id) {
        Post post = em.find(Post.class, id);
        return Optional.ofNullable(post);
    }

    @Override
    public Optional<PostWithUserDto> findById_query(Long id) {
        String jpql = "SELECT new toy.board.domain.dto.PostWithUserDto (p.id, p.title, p.content, p.registeredAt, p.updatedAt, u.id, u.username) " +
                "FROM Post p JOIN p.user u WHERE p.id = :postId";
        return em.createQuery(jpql, PostWithUserDto.class)
                .setParameter("postId", id)
                .getResultList().stream()
                .findFirst();
    }

    @Override
    public List<PostWithUserDto> findAll() {
        String jpql = "SELECT new toy.board.domain.dto.PostWithUserDto (p.id, p.title, p.content, p.registeredAt, p.updatedAt, u.id, u.username) " +
                "FROM Post p JOIN p.user u";
        return em.createQuery(jpql, PostWithUserDto.class)
                .getResultList();
    }

    @Override
    public void deleteById(Long id) {
        Post post = em.find(Post.class, id);
        em.remove(post);
    }

}
