package edu.jdbc.service;

import edu.jdbc.domain.Member;
import edu.jdbc.repository.MemberRepositoryV3_DataSource_Transaction;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;

@RequiredArgsConstructor
public class MemberServiceV3_1_ConnParam {

    private final MemberRepositoryV3_DataSource_Transaction memberRepository;

    public Member save(Member member) throws SQLException {
        return memberRepository.save(member);
    }

    public Member findByUsername(String username) throws SQLException {
        return memberRepository.findByUsername(username);
    }

    public void transfer(Member fromMember, Member toMember, int money) throws SQLException {
        Connection conn = memberRepository.getConnection();
        try {
            conn.setAutoCommit(false);
            memberRepository.updateMoney(conn, fromMember, -money);
            memberRepository.updateMoney(conn, toMember, money);
            conn.commit();
            conn.close();
        } catch (Exception e) {
            conn.rollback();
            conn.close();
            throw e;
        }
    }
}
