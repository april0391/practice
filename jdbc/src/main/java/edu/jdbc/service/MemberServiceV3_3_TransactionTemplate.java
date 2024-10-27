package edu.jdbc.service;

import edu.jdbc.domain.Member;
import edu.jdbc.repository.MemberRepositoryV3_DataSource_Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.SQLException;

@RequiredArgsConstructor
public class MemberServiceV3_3_TransactionTemplate {

    private final MemberRepositoryV3_DataSource_Transaction memberRepository;
    private final TransactionTemplate transactionTemplate;

    public Member save(Member member) throws SQLException {
        return memberRepository.save(member);
    }

    public Member findByUsername(String username) throws SQLException {
        return memberRepository.findByUsername(username);
    }

    public void transfer(Member fromMember, Member toMember, int money) throws SQLException {
        transactionTemplate.executeWithoutResult(transactionStatus -> {
            try {
                memberRepository.updateMoney(fromMember, -money);
                memberRepository.updateMoney(toMember, money);
            } catch (IllegalStateException e) {
                throw e;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
