package edu.jdbc.service;

import edu.jdbc.domain.Member;
import edu.jdbc.repository.MemberRepositoryV1_Jdbc;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;

@RequiredArgsConstructor
public class MemberServiceV1 {

    private final MemberRepositoryV1_Jdbc memberRepository;

    public Member save(Member member) throws SQLException {
        return memberRepository.save(member);
    }

    public Member findByUsername(String username) throws SQLException {
        return memberRepository.findByUsername(username);
    }
}
