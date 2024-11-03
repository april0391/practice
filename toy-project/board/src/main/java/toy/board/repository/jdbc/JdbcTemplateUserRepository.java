package toy.board.repository.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import toy.board.domain.entity.User;
import toy.board.repository.UserRepository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
public class JdbcTemplateUserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate parameterJdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcTemplateUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate parameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.parameterJdbcTemplate = parameterJdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
    }

    //    @Override
    public User save_old(User user) {
        String sql = "INSERT INTO users (username, password, name, email, nickname, registered_at) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getNickname());
            ps.setTimestamp(6, Timestamp.valueOf(user.getRegisteredAt()));
            return ps;
        }, keyHolder);

        user.setId(keyHolder.getKey().longValue());
        return user;
    }

    @Override
    public User save(User user) {
        String sql = "INSERT INTO users (username, password, name, email, nickname, registered_at) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(user);
        Number key = jdbcInsert.executeAndReturnKey(param);
        user.setId(key.longValue());
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, rowMapper(), id);
            return Optional.of(user);

        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

//    @Override
    public Optional<User> findByUsername_old(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, rowMapper(), username);
            return Optional.of(user);

        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = :username";
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("username", username);
        try {
            User user = parameterJdbcTemplate.queryForObject(sql, param, rowMapper());
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private RowMapper<User> rowMapper() {
        /*return (rs, rowNum) -> {
            User user = new User(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("nickname"),
                    rs.getTimestamp("registered_at").toLocalDateTime()
            );
            user.setId(rs.getLong("id"));
            return user;
        };*/
        return BeanPropertyRowMapper.newInstance(User.class);
    }
}
