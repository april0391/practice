package edu.jwtadvanced.repository;

import edu.jwtadvanced.entity.Refresh;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Repository
public class RefreshRepository {

    private final EntityManager em;

    public boolean existsByRefresh(String refresh) {
        String jpql = "SELECT COUNT(r) FROM Refresh r WHERE r.refresh = :refresh";
        Long count = em.createQuery(jpql, Long.class)
                .setParameter("refresh", refresh)
                .getSingleResult();
        return count > 0;
    }

    @Transactional
    public void deleteByRefresh(String refresh) {
        String jpql = "SELECT r FROM Refresh r WHERE r.refresh = :refresh";
        Refresh findRefresh = em.createQuery(jpql, Refresh.class)
                .setParameter("refresh", refresh)
                .getSingleResult();
        em.remove(findRefresh);
    }

    @Transactional
    public void save(Refresh refreshEntity) {
        em.persist(refreshEntity);
    }
}
