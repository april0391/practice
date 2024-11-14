package toy.board.repository.jpa;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import toy.board.domain.dto.SearchCond;
import toy.board.domain.entity.Post;
import toy.board.domain.entity.QPost;
import toy.board.repository.PostRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class PostJpaRepositoryV1 implements PostRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public PostJpaRepositoryV1(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

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
                    .setFirstResult(0)
                    .setMaxResults(10)
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

    @Override
    public Page<Post> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Page<Post> findAll(SearchCond cond, Pageable pageable) {
        String searchTarget = cond.getSearchTarget();
        String searchValue = cond.getSearchValue();
        QPost post = QPost.post;

        // QueryDSL 페이징을 위한 기본 쿼리 설정
        JPAQuery<Post> query = queryFactory.select(post)
                .from(post)
                .where(likeSearchValue(searchTarget, searchValue));

        // 페이징 처리 및 결과 조회
        QueryResults<Post> results = query.offset(pageable.getOffset())  // 페이지 시작 인덱스
                .limit(pageable.getPageSize())  // 페이지 크기
                .fetchResults();  // 결과 및 전체 개수를 포함한 QueryResults 반환

        List<Post> posts = results.getResults();  // 현재 페이지에 해당하는 데이터 목록
        long totalCount = results.getTotal();  // 전체 데이터 개수

        return new PageImpl<>(posts, pageable, totalCount);  // PageImpl을 사용해 Page 객체 반환
    }

    private BooleanExpression likeSearchValue(String searchTarget, String searchValue) {
        if (searchTarget == null) {
            return null;
        }
        QPost post = QPost.post;
        return switch (searchTarget) {
            case "title" -> post.title.like("%" + searchValue + "%");
            case "content" -> post.content.like("%" + searchValue + "%");
            case "author" -> post.user.nickname.like("%" + searchValue + "%");
            default -> null;
        };
    }

}
