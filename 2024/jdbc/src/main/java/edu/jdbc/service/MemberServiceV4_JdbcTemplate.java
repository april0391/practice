package edu.jdbc.service;

import edu.jdbc.domain.Member;
import edu.jdbc.repository.MemberRepositoryV3_DataSource_Transaction;
import edu.jdbc.repository.MemberRepositoryV4_JdbcTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@RequiredArgsConstructor
public class MemberServiceV4_JdbcTemplate {

    private final MemberRepositoryV4_JdbcTemplate memberRepository;

    public Member save(Member member) {
        return memberRepository.save(member);
    }

    public Member findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    @Transactional
    public void transfer(Member fromMember, Member toMember, int money) {
        memberRepository.updateMoney(fromMember, -money);
        memberRepository.updateMoney(toMember, money);
    }
}
