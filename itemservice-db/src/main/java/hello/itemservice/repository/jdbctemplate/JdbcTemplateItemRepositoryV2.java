package hello.itemservice.repository.jdbctemplate;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.thymeleaf.util.StringUtils;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * NamedParameterJdbcTemplate
 */
@Slf4j
@RequiredArgsConstructor
@Repository
public class JdbcTemplateItemRepositoryV2 implements ItemRepository {

    private final NamedParameterJdbcTemplate template;

    @Override
    public Item save(Item item) {
        String sql = "INSERT INTO item (item_name, price, quantity) VALUES (:itemName, :price, :quantity)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(item);
        template.update(sql, param, keyHolder, new String[]{"id"});
        long id = keyHolder.getKey().longValue();
        item.setId(id);
        return item;
    }

    @Override
    public void update(Long id, ItemUpdateDto updateParam) {
        String sql = "UPDATE item SET item_name = :itemName, price = :price, quantity = :quantity WHERE id = :id";
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("itemName", updateParam.getItemName())
                .addValue("price", updateParam.getPrice())
                .addValue("quantity", updateParam.getQuantity())
                .addValue("id", id);
        template.update(sql, param);
    }

    @Override
    public Optional<Item> findById(Long id) {
        String sql = "SELECT * FROM item WHERE id = :id";
        Item item = template.queryForObject(sql, Map.of("id", id), rowMapper());
        return Optional.ofNullable(item);
    }

    @Override
    public List<Item> findAll(ItemSearchCond cond) {
        String sql = "SELECT * FROM item WHERE 1=1 ";
        String itemName = cond.getItemName();
        Integer maxPrice = cond.getMaxPrice();
        MapSqlParameterSource param = new MapSqlParameterSource();
        if (!StringUtils.isEmpty(itemName)) {
            sql += "AND item_name LIKE CONCAT ('%', :itemName, '%') ";
            param.addValue("itemName", itemName);
        }
        if (maxPrice != null) {
            sql += "AND price <= :maxPrice";
            param.addValue("maxPrice", maxPrice);
        }
        return template.query(sql, param, rowMapper());
    }

    private RowMapper<Item> rowMapper() {
        return BeanPropertyRowMapper.newInstance(Item.class);
    }
}
