package edu.jdbc.service;

import edu.jdbc.domain.Member;
import edu.jdbc.repository.MemberRepositoryV4_JdbcTemplate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

import static edu.jdbc.domain.ConnectionConst.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberServiceV4_1JdbcTemplateTest_DirectConfig {

    DataSource dataSource;
    JdbcTemplate jdbcTemplate;
    MemberRepositoryV4_JdbcTemplate memberRepository;
    MemberServiceV4_JdbcTemplate memberService;

    @BeforeEach
    void init() throws SQLException {
        dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        jdbcTemplate = new JdbcTemplate(dataSource);
        memberRepository = new MemberRepositoryV4_JdbcTemplate(jdbcTemplate);
        memberService = new MemberServiceV4_JdbcTemplate(memberRepository);
        deleteAll();
    }

    void deleteAll() {
        memberRepository.deleteAll();
    }

    @Test
    void save() throws SQLException {
        Member user = new Member("user1", 10000);
        memberService.save(user);

        Member find = memberService.findByUsername("user1");
        assertThat(user).isEqualTo(find);
    }

    @Test
    void transfer() throws SQLException {
        Member user1 = new Member("user1", 10000);
        Member user2 = new Member("user2", 10000);
        memberService.save(user1);
        memberService.save(user2);

        memberService.transfer(user1, user2, 5000);

        Member findUser1 = memberService.findByUsername("user1");
        Member findUser2 = memberService.findByUsername("user2");

        assertThat(findUser1.getMoney()).isEqualTo(5000);
        assertThat(findUser2.getMoney()).isEqualTo(15000);
    }

    @Test
    void transferEx() throws SQLException {
        Member user1 = new Member("user1", 10000);
        Member user2 = new Member("user2", 10000);
        memberService.save(user1);
        memberService.save(user2);

        Assertions.assertThatThrownBy(() -> memberService.transfer(user1, user2, 20000))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("잔액 부족");
    }

}