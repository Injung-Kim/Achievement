package dao.admin.user.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.JDBCTemplate;
import dao.admin.user.face.UserDao;
import dto.Users;
import util.Paging;

public class UserDaoImpl implements UserDao{
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	@Override
	public List<Users> getUserList(Connection conn, String grade, String fieldVal, String search, Paging paging) {
		String sql = "";

		sql += "SELECT * FROM(";
		sql += "	SELECT rownum rnum, U.* FROM(";
		sql += "	SELECT * FROM users";
		sql += " 	ORDER BY u_no DESC)U";
		sql += " 	WHERE u_grade = ?";
		sql += " 	AND " +fieldVal+ " like '%'|| ? || '%')USERS";
		sql += "	WHERE rnum BETWEEN ? AND ?";
		
		//결과를 리스트에 저장
		List<Users> list = new ArrayList<>();
		Users users = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, grade);
			ps.setString(2, search);
			ps.setInt(3, paging.getStartNo()); //페이징 처음번호
			ps.setInt(4, paging.getEndNo()); //페이징 끝번호
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				users = new Users();
				users.setUserNo(rs.getInt("u_no"));
				users.setUserId(rs.getString("u_id"));
				users.setName(rs.getString("u_name"));
				users.setUserEmail(rs.getString("u_email"));
				users.setUserNick(rs.getString("u_nick"));
				users.setGender(rs.getString("u_gender"));
				users.setBirth(rs.getDate("u_birth"));
				users.setSignupDate(rs.getDate("u_signup"));
				users.setGrade(rs.getString("u_grade"));
				
				list.add(users);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		return list;
	}
	@Override
	public int selectCntAll(Connection conn, String grade, String fieldVal, String search) {
		String sql = "";
		sql += "SELECT count(*) FROM users";
		sql += " 	WHERE u_grade = ?";
		sql += " 	AND " +fieldVal+ " like '%'|| ? || '%'";
		
		int cntAll = 0;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, grade);
			ps.setString(2, search);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				cntAll = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
			
		}
		return cntAll;
	}
	@Override
	public Users getUserInfo(Connection conn, Users users) {
		String sql = "";
		sql += "SELECT * FROM users";
		sql += "	where u_no = ?";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, users.getUserNo());
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				users = new Users();
				users.setUserNo(rs.getInt("u_no"));
				users.setUserId(rs.getString("u_id"));
				users.setUserPw(rs.getString("u_password"));
				users.setName(rs.getString("u_name"));
				users.setUserEmail(rs.getString("u_email"));
				users.setUserChallenge(rs.getString("u_challenge"));
				users.setUserNick(rs.getString("u_nick"));
				users.setGender(rs.getString("u_gender"));
				users.setBirth(rs.getDate("u_birth"));
				users.setSignupDate(rs.getDate("u_signup"));
				users.setAccount(rs.getString("u_account"));
				users.setBank(rs.getString("u_bank"));
				users.setGrade(rs.getString("u_grade"));
				users.setPostNum(rs.getInt("u_post"));
				users.setAddress(rs.getString("u_address"));
				users.setPhone(rs.getString("u_phone"));
				users.setCaution(rs.getInt("u_caution"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		return users;
	}
	@Override
	public int deleteUser(Connection conn, Users users) {
		String sql ="";
		sql += "DELETE users WHERE u_no = ?";
		int userno = users.getUserNo();
		int res = -1;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userno);
			res = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(ps);
		}
		return res;
	}
	@Override
	public int updateUser(Connection conn, Users users) {
		String sql = "";
		if(users.getGrade().equals("U")) {
			sql += "UPDATE users SET u_grade='M',u_nick='매니저' " + 
			   " WHERE u_no = ?";
		} else if( users.getGrade().equals("M")) {
			sql += "UPDATE users SET u_grade='U',u_nick='회원' " + 
				" WHERE u_no = ?";
		}
		int res = -1;
			try {
				ps = conn.prepareStatement(sql);
				ps.setInt(1, users.getUserNo());
				res = ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		//System.out.println(res);
		return res;
	}
}
