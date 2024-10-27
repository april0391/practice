package edu.jdbc.service;

import edu.jdbc.domain.Member;
import edu.jdbc.repository.MemberRepositoryV1_Jdbc;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class MemberServiceV1Test {

    @Test
    void save() throws SQLException {
        MemberServiceV1 memberService = new MemberServiceV1(new MemberRepositoryV1_Jdbc());
        Member user = new Member("user1", 10000);
        memberService.save(user);

        Member find = memberService.findByUsername("user1");
        Assertions.assertThat(user).isEqualTo(find);
    }

}