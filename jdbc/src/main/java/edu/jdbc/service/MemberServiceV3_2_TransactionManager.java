package edu.jdbc.service;

import edu.jdbc.domain.Member;
import edu.jdbc.repository.MemberRepositoryV3_DataSource_Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.SQLException;

@RequiredArgsConstructor
public class MemberServiceV3_2_TransactionManager {

    private final MemberRepositoryV3_DataSource_Transaction memberRepository;
    private final PlatformTransactionManager transactionManager;

    public Member save(Member member) throws SQLException {
        return memberRepository.save(member);
    }

    public Member findByUsername(String username) throws SQLException {
        return memberRepository.findByUsername(username);
    }

    public void transfer(Member fromMember, Member toMember, int money) throws SQLException {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            memberRepository.updateMoney(fromMember, -money);
            memberRepository.updateMoney(toMember, money);
            transactionManager.commit(status);

        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
    }
}
