package edu.jdbc.service;

import edu.jdbc.domain.Member;
import edu.jdbc.repository.MemberRepositoryV1_Jdbc;
import edu.jdbc.repository.MemberRepositoryV2_DataSource;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;

@RequiredArgsConstructor
public class MemberServiceV2 {

    private final MemberRepositoryV2_DataSource memberRepository;

    public Member save(Member member) throws SQLException {
        return memberRepository.save(member);
    }

    public Member findByUsername(String username) throws SQLException {
        return memberRepository.findByUsername(username);
    }

    public void transfer(Member fromMember, Member toMember, int money) throws SQLException {
        memberRepository.updateMoney(fromMember, -money);
        memberRepository.updateMoney(toMember, money);
    }
}
