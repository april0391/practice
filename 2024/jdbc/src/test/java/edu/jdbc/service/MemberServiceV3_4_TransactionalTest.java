package edu.jdbc.service;

import edu.jdbc.domain.Member;
import edu.jdbc.repository.MemberRepositoryV3_DataSource_Transaction;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;

import static edu.jdbc.domain.ConnectionConst.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberServiceV3_4_TransactionalTest {

    DataSource dataSource;
    MemberRepositoryV3_DataSource_Transaction memberRepository;
    MemberServiceV3_4_Transactional memberService;

    @BeforeEach
    void init() throws SQLException {
        dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        memberRepository = new MemberRepositoryV3_DataSource_Transaction(dataSource);
        memberService = new MemberServiceV3_4_Transactional(memberRepository);
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