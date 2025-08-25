package edu.jdbc.service;

import edu.jdbc.domain.Member;
import edu.jdbc.repository.MemberRepositoryV3_DataSource_Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.SQLException;

@RequiredArgsConstructor
public class MemberServiceV3_4_Transactional {

    private final MemberRepositoryV3_DataSource_Transaction memberRepository;

    public Member save(Member member) throws SQLException {
        return memberRepository.save(member);
    }

    public Member findByUsername(String username) throws SQLException {
        return memberRepository.findByUsername(username);
    }

    @Transactional
    public void transfer(Member fromMember, Member toMember, int money) throws SQLException {
        memberRepository.updateMoney(fromMember, -money);
        memberRepository.updateMoney(toMember, money);
    }
}
