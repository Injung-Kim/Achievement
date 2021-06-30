package dao.join.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import common.JDBCTemplate;
import dao.join.face.JoinDao;
import dto.Member;

public class JoinDaoImpl implements JoinDao {

	private PreparedStatement ps = null; //SQL수행 객체
	private ResultSet rs = null; //SQL조회 결과 객체
	
	@Override
	public int insertJoin(Connection conn, Member mem) {
		
		String sql ="";
		sql += "INSERT INTO USERS (U_NO, U_ID, U_PASSWORD, U_NAME, U_NICK, U_PHONE, U_EMAIL,";
		sql += " 	U_GENDER, U_BIRTH,U_ACCOUNT, U_BANK,U_CHALLENGE, U_POST, U_ADDRESS,U_CAUTION,U_GRADE ,U_SIGNUP )";
		sql += " VALUES(USERS_SEQ.nextval, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?,0,'U',sysdate)";
		
		int res = -1;
//		SimpleDateFormat afterFormat = new SimpleDateFormat("yyyy-mm-dd");
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, mem.getUid()); //아이디
			ps.setString(2, mem.getUpassword() ); //비밀번호
			ps.setString(3, mem.getUsername()); //이름
			ps.setString(4, mem.getNick()); //별명
			ps.setString(5, mem.getPhone()); //휴대전화
			ps.setString(6, mem.getEmail() ); //이메일
			ps.setString(7, mem.getGender()); //성별
			ps.setDate(8, new java.sql.Date(mem.getBirth().getTime())); //생일
			ps.setString(9, mem.getAccount()); //계좌
			ps.setString(10,mem.getBank() ); //은행
			ps.setString(11, mem.getChallenge() ); //도전하고 싶은 챌린져
			ps.setString(12, mem.getPost()); // 우편번호
			ps.setString(13, mem.getAddress() ); //주소
			
			
			//SQL수행객체 실행
			res = ps.executeUpdate();
					
		} catch (SQLException e) {
			System.out.println("에러 발생 시점.");
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(ps);
		}

		return res;
	}

	@Override
	public int selectCntById(Connection conn, String uid) {
		String sql = "";
		sql += "SELECT count(*) cnt FROM users";
		sql += " WHERE u_id = ?";
		
		int cnt = 0;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, uid);
			
			rs = ps.executeQuery();
			
			rs.next();
			
			cnt = rs.getInt("cnt");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return cnt;
	}

	@Override
	public int selectCntByNick(Connection conn, String nick) {
		String sql = "";
		sql += "SELECT count(*) cnt FROM users";
		sql += " WHERE u_nick = ?";
		
		int cnt = 0;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, nick);
			
			rs = ps.executeQuery();
			
			rs.next();
			
			cnt = rs.getInt("cnt");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return cnt;
	}

	@Override
	public int selectCntByEmail(Connection conn, String email) {
		String sql = "";
		sql += "SELECT count(*) cnt FROM users";
		sql += " WHERE u_email = ?";
		
		int cnt = 0;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			
			rs = ps.executeQuery();
			
			rs.next();
			
			cnt = rs.getInt("cnt");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return cnt;
	}

	@Override
	public int selectCntMemberByUsernameUserphone(Connection conn, Member mem) {
		//SQL 작성
				String sql = "";
				sql += "SELECT count(*) FROM users";
				sql += " WHERE 1=1";
				sql += "	AND U_NAME = ?";
				sql += "	AND U_PHONE = ?";
			
				
				//결과 저장할 변수
				int cnt = 0;
				
				try {
					ps = conn.prepareStatement(sql); //SQL수행 객체
					
					ps.setString(1, mem.getUsername());
					ps.setString(2, mem.getPhone());
					
					
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
	public Member selectMemberByUsernameUserphone(Connection conn, Member mem) {
		//SQL 작성
				String sql = "";
				sql += "SELECT * FROM users";
				sql += " WHERE 1=1";
				sql += "	AND U_NAME = ?";
				sql += "	AND U_PHONE = ?";
				
				
				
				//조회결과를 저장할 객체
				Member result = null;
				
				try {
					ps = conn.prepareStatement(sql); //SQL수행 객체
					
					ps.setString(1, mem.getUsername());
					ps.setString(2, mem.getPhone());
				
					
					rs = ps.executeQuery(); //SQL 수행 및 결과집합 저장
					
					//조회 결과 처리
					while(rs.next()) {
						result = new Member();
						
						result.setUsername( rs.getString("u_name") );
						result.setPhone( rs.getString("u_phone") );
						result.setUid(rs.getString("u_id"));
				
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
	public int selectCntMemberByUserpw(Connection conn, Member mem) {
		//SQL 작성
		String sql = "";
		sql += "SELECT count(*) FROM users";
		sql += " WHERE 1=1";
		sql += "	AND U_NAME = ?";
		sql += "	AND U_ID = ?";
		sql += "    AND U_EMAIL =?";
	
		
		//결과 저장할 변수
		int cnt = 0;
		
		try {
			ps = conn.prepareStatement(sql); //SQL수행 객체
			
			ps.setString(1, mem.getUsername());
			ps.setString(2, mem.getUid());
			ps.setString(3, mem.getEmail());
			
			
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
	public Member selectMemberByUserpw(Connection conn, Member mem) {
		//SQL 작성
		String sql = "";
		sql += "SELECT * FROM users";
		sql += " WHERE 1=1";
		sql += "	AND U_NAME = ?";
		sql += "	AND U_ID = ?";
		sql += "    AND U_EMAIL=?";
		
		
		
		//조회결과를 저장할 객체
		Member result = null;
		
		try {
			ps = conn.prepareStatement(sql); //SQL수행 객체
			
			ps.setString(1, mem.getUsername());
			ps.setString(2, mem.getUid());
			ps.setString(3, mem.getEmail());
		
			
			rs = ps.executeQuery(); //SQL 수행 및 결과집합 저장
			
			//조회 결과 처리
			while(rs.next()) {
				result = new Member();
				
				result.setUsername( rs.getString("u_name") );
				result.setUid( rs.getString("u_id") );
				result.setEmail(rs.getString("u_email"));
				result.setUpassword(rs.getString("u_password"));
		
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
