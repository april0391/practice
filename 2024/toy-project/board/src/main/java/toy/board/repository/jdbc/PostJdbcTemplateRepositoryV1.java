package toy.board.repository.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import toy.board.domain.dto.SearchCond;
import toy.board.domain.entity.Post;
import toy.board.repository.PostRepository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class PostJdbcTemplateRepositoryV1 implements PostRepository {

    private final JdbcTemplate template;

    @Override
    public Post save(Post post) {
        String sql = "INSERT INTO post (user_id, title, content)" +
                "        VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(con -> {
            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setLong(1, post.getUserId());
            pstmt.setString(2, post.getTitle());
            pstmt.setString(3, post.getContent());
            return pstmt;
        }, keyHolder);

        long id = keyHolder.getKey().longValue();
        post.setId(id);

        return post;
    }

    @Override
    public Optional<Post> findById(Long id) {
        String sql = "SELECT * FROM post WHERE id = ?";
        Post post = template.queryForObject(sql, rowMapper(), id);
        return Optional.ofNullable(post);
    }

    @Override
    public List<Post> findAll(SearchCond cond) {
        String sql = "SELECT p.*, u.nickname" +
                "FROM post " +
                "INNER JOIN users u ON p.user_id = u.id WHERE 1=1 ";
        List<String> params = new ArrayList<>();
        if (cond.getSearchTarget() != null && cond.getSearchValue() != null) {
            switch(cond.getSearchTarget()) {
                case "title" -> sql += "AND p.title = ?";
                case "content" -> sql += "AND p.content = ?";
                case "author" -> sql += "AND u.nickname = ?";
            }
            params.add(cond.getSearchValue());
        }
        return template.query(sql, rowMapper(), params.toArray());
    }

    @Override
    public Page<Post> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Page<Post> findAll(SearchCond cond, Pageable pageable) {
        return null;
    }

    private RowMapper<Post> rowMapper() {
        return (rs, rowNum) -> {
            Post post = new Post();
            post.setId(rs.getLong("id"));
            post.setUserId(rs.getLong("user_id"));
//            post.setNickname(rs.getString("nickname"));
            post.setTitle(rs.getString("title"));
            post.setContent(rs.getString("content"));
            return post;
        };
    }
}
