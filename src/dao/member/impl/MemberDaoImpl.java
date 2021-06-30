package dao.member.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import common.JDBCTemplate;
import dao.member.face.MemberDao;
import dto.Member;

public class MemberDaoImpl implements MemberDao {

	private PreparedStatement ps = null; //SQL수행 객체
	private ResultSet rs = null; //SQL조회 결과 객체
	
	@Override
	public int selectCntMemberByUseridUserpw(Connection conn, Member member) {
		
		//SQL 작성
		String sql = "";
		sql += "SELECT count(*) FROM users";
		sql += " WHERE 1=1";
		sql += "	AND u_id = ?";
		sql += "	AND u_password = ?";
		
		//결과 저장할 변수
		int cnt = -1;
		
		try {
			ps = conn.prepareStatement(sql); //SQL수행 객체
			
			ps.setString(1, member.getUid());
			ps.setString(2, member.getUpassword());
			
			rs = ps.executeQuery(); //SQL 수행 및 결과집합 저장
			
			//조회 결과 처리
			while(rs.next()) {
				cnt = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DB객체 닫기
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		//최종 결과 반환
		return cnt;
	}

	@Override
	public Member selectMemberByUserid(Connection conn, Member member) {
		
		//SQL 작성
		String sql = "";
		sql += "SELECT * FROM users";
		sql += " WHERE 1=1";
		sql += "	AND u_id = ?";
		
		//조회결과를 저장할 객체
		Member result = null;
		
		try {
			ps = conn.prepareStatement(sql); //SQL수행 객체
			
			ps.setString(1, member.getUid());
			
			rs = ps.executeQuery(); //SQL 수행 및 결과집합 저장
			
			//조회 결과 처리
			while(rs.next()) {
				result = new Member();
				result.setUid( rs.getString("u_id") );
				result.setUpassword( rs.getString("u_password") );
				result.setUgrade(rs.getString("u_grade"));
				result.setUno(rs.getInt("u_no"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DB객체 닫기
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		//최종 결과 반환
		return result;
		
	}
	@Override
	public Member selectInfoAll(Connection conn, Member mem) {
		//SQL 작성
		String sql = "";
		sql += "SELECT * FROM users";
		sql += " WHERE 1=1";
		sql += "	AND u_no = ?";

		//조회결과를 저장할 객체
		Member result = null;

		try {
			ps = conn.prepareStatement(sql); //SQL수행 객체

			ps.setInt(1, mem.getUno());

			rs = ps.executeQuery(); //SQL 수행 및 결과집합 저장

			//조회 결과 처리
			if(rs.next()) {
				result = new Member();
				result.setUno(rs.getInt("u_no"));
				result.setUid( rs.getString("u_id") );
				result.setUgrade(rs.getString("u_grade"));
			    result.setUsername(rs.getString("u_name"));
			    result.setEmail(rs.getString("u_email"));
			    result.setChallenge("u_challenge");
			    result.setNick("u_nick");
			    result.setGender("u_gender");
			    result.setBirth(rs.getDate("u_birth"));
			    result.setSingup(rs.getDate("u_signup"));
			    result.setAccount(rs.getString("u_account"));
			    result.setBank(rs.getString("u_bank"));
			    result.setPhone(rs.getString("u_phone"));
			    result.setAddress(rs.getString("u_address"));
			    result.setPost(rs.getString("u_post"));
			    result.setAddress(rs.getString("u_address"));
			    result.setCaution(rs.getInt("u_caution"));
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DB객체 닫기
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}

		//최종 결과 반환
		return result;
	}

}
