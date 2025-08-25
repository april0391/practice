package hello.itemservice.repository.mybatis;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class MyBatisItemRepository implements ItemRepository {

    private final ItemMapper mapper;

    @Override
    public Item save(Item item) {
        mapper.save(item);
        return item;
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        mapper.update(itemId, updateParam);
    }

    @Override
    public Optional<Item> findById(Long id) {
        return mapper.findById(id);
    }

    @Override
    public List<Item> findAll(ItemSearchCond cond) {
        return mapper.findAll(cond);
    }
}
