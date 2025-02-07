package edu.jdbc.service;

import edu.jdbc.domain.Member;
import edu.jdbc.repository.MemberRepositoryV4_JdbcTemplate;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

import static edu.jdbc.domain.ConnectionConst.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberServiceV4_1JdbcTemplateTest_AutoConfig {

    private final MemberRepositoryV4_JdbcTemplate memberRepository;
    private final MemberServiceV4_JdbcTemplate memberService;

    @Autowired
    public MemberServiceV4_1JdbcTemplateTest_AutoConfig(MemberRepositoryV4_JdbcTemplate memberRepository, MemberServiceV4_JdbcTemplate memberService) {
        this.memberRepository = memberRepository;
        this.memberService = memberService;
    }

    @BeforeEach
    void init() {
        memberRepository.deleteAll();
    }

    @Test
    void save() {
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

    @TestConfiguration
    static class Config {

        @Bean
        DataSource dataSource() {
            return new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        }

        @Bean
        JdbcTemplate jdbcTemplate() {
            return new JdbcTemplate(dataSource());
        }

        @Bean
        MemberRepositoryV4_JdbcTemplate memberRepository() {
            return new MemberRepositoryV4_JdbcTemplate(jdbcTemplate());
        }

        @Bean
        MemberServiceV4_JdbcTemplate memberService() {
            return new MemberServiceV4_JdbcTemplate(memberRepository());
        }
    }

}