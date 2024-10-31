package hello.itemservice.repository.jpa;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.*;

@RequiredArgsConstructor
@Repository
public class JpaItemRepositoryV1 implements ItemRepository {

    private final EntityManager em;

    @Override
    public Item save(Item item) {
        em.persist(item);
        return item;
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        Item item = em.find(Item.class, itemId);
        item.setItemName(updateParam.getItemName());
        item.setPrice(updateParam.getPrice());
        item.setQuantity(updateParam.getQuantity());
    }

    @Override
    public Optional<Item> findById(Long id) {
        Item item = em.find(Item.class, id);
        return Optional.ofNullable(item);
    }

    @Override
    public List<Item> findAll(ItemSearchCond cond) {
        String jpql = "SELECT i FROM Item i WHERE 1=1 ";
        String itemName = cond.getItemName();
        Integer maxPrice = cond.getMaxPrice();
        Map<String, Object> params = new HashMap<>();

        if (!StringUtils.isEmpty(itemName)) {
            jpql += "AND i.itemName LIKE CONCAT ('%', :itemName, '%') ";
            params.put("itemName", itemName);
        }
        if (maxPrice != null) {
            jpql += "AND i.price <= :maxPrice";
            params.put("maxPrice", maxPrice);
        }

        TypedQuery<Item> query = em.createQuery(jpql, Item.class);
        params.forEach((k, v) -> query.setParameter(k, v));
        return query.getResultList();
    }
}
