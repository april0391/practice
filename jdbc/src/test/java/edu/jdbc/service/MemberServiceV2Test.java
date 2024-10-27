package edu.jdbc.service;

import com.zaxxer.hikari.HikariDataSource;
import edu.jdbc.domain.ConnectionConst;
import edu.jdbc.domain.Member;
import edu.jdbc.repository.MemberRepositoryV2_DataSource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

import java.sql.SQLException;

import static edu.jdbc.domain.ConnectionConst.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceV2Test {

    DataSource dataSource;
    MemberRepositoryV2_DataSource memberRepository;
    MemberServiceV2 memberService;

    @BeforeEach
    void init() throws SQLException {
        dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        memberRepository = new MemberRepositoryV2_DataSource(dataSource);
        memberService = new MemberServiceV2(memberRepository);
        deleteAll();
    }

    void deleteAll() throws SQLException {
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

}