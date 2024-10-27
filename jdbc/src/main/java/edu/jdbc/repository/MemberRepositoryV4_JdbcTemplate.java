package edu.jdbc.repository;


import edu.jdbc.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
@RequiredArgsConstructor
public class MemberRepositoryV4_JdbcTemplate {

    private final JdbcTemplate template;

    public Member save(Member member) {
        String sql = "INSERT INTO member VALUES (?, ?)";
        template.update(sql, member.getUsername(), member.getMoney());
        return member;
    }

    public Member updateMoney(Member member, int money) {
        if (member.getMoney() < money) {
            throw new IllegalStateException("잔액 부족");
        }
        String sql = "UPDATE member SET money = money + ? WHERE username = ?";
        template.update(sql, money, member.getUsername());
        return member;
    }

    public Member findByUsername(String username) {
        String sql = "SELECT * FROM member WHERE username = ?";
        return template.queryForObject(sql, rowMapper(), username);
    }

    private RowMapper<Member> rowMapper() {
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setUsername(rs.getString("username"));
            member.setMoney(rs.getInt("money"));
            return member;
        };
    }

    public void deleteAll() {
        String sql = "DELETE FROM member";
        template.update(sql);
    }

}
