package toy.board.repository.jpa;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toy.board.domain.dto.SearchCond;
import toy.board.domain.entity.Post;
import toy.board.repository.PostRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class PostRepositoryJpa implements PostRepository {

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
    public List<Post> findAll(SearchCond cond) {
        String jpql = "SELECT p FROM Post p JOIN FETCH p.user u";
        if (cond.getSearchTarget() == null) {
            return em.createQuery(jpql, Post.class)
                    .getResultList();
        }

        switch (cond.getSearchTarget()) {
            case "title" -> jpql += " WHERE p.title LIKE :searchValue";
            case "content" -> jpql += " WHERE p.content LIKE :searchValue";
            case "author" -> jpql += " WHERE p.user.nickname LIKE :searchValue";
        }
        return em.createQuery(jpql, Post.class)
                .setParameter("searchValue", "%" + cond.getSearchValue() + "%")
                .getResultList();
    }
}
