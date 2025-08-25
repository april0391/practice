package edu.jdbc.repository;


import edu.jdbc.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.*;

import static edu.jdbc.domain.ConnectionConst.*;

@Slf4j
@RequiredArgsConstructor
public class MemberRepositoryV2_DataSource {

    private final DataSource dataSource;

    public Member save(Member member) throws SQLException {
        String sql = "INSERT INTO member VALUES (?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, member.getUsername());
            pstmt.setInt(2, member.getMoney());
            pstmt.executeUpdate();
            return member;

        } catch (SQLException e) {
            throw e;
        } finally {
            release(conn, pstmt, null);
        }

    }

    public Member updateMoney(Member member, int money) throws SQLException {
        String sql = "UPDATE member SET money = money + ? WHERE username = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, money);
            pstmt.setString(2, member.getUsername());
            pstmt.executeUpdate();
            return member;

        } catch (SQLException e) {
            throw e;
        } finally {
            release(conn, pstmt, null);
        }
    }

    public Member findByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM member WHERE username = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                String findUsername = rs.getString("username");
                int money = rs.getInt("money");
                return new Member(username, money);
            }
            throw new IllegalStateException();
        } catch (SQLException e) {
            throw e;
        } finally {
            release(conn, pstmt, rs);
        }
    }

    public void deleteAll() throws SQLException {
        String sql = "DELETE FROM member";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            release(conn, pstmt, rs);
        }
    }

    public void release(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                log.error("release error");
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                log.error("release error");
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                log.error("release error");
            }
        }
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}
