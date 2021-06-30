package dao.mainajax.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.JDBCTemplate;
import dao.mainajax.face.AjaxDao;
import dto.Challenge;
import dto.Participation;
import dto.Users;

public class AjaxDaoImpl implements AjaxDao {
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	@Override
	public List<Object> getBestChallenge(Connection conn) {
		String sql = "";
		sql += "SELECT * FROM( " + 
				"SELECT ROWNUM rnum, BEST.* FROM  " + 
				"(SELECT C.ch_no,C.u_no, ch_title,ch_stored_name,ch_state, count(P.u_no) FROM PARTICIPATION P  " + 
				"INNER JOIN CHALLENGE C  " + 
				"ON C.ch_no = P.ch_no  " + 
				"GROUP BY c.ch_no,C.u_no, ch_title, ch_stored_name,ch_state  " + 
				"HAVING count(P.u_no) >= 100  " + 
				"AND ch_state != 'N' " + 
				"ORDER BY count(P.u_no) desc  " + 
				")BEST)  " + 
				"WHERE rnum between 1 and 4";
		
		List<Object> list = new ArrayList<>();
		Challenge challenge = null;
		int cntParticipant = 0;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {		
				challenge = new Challenge();
				challenge.setChNo(rs.getInt("ch_no"));
				challenge.setuNo(rs.getInt("u_no"));
				challenge.setChTitle(rs.getString("ch_title"));
				challenge.setChStoredName(rs.getString("ch_stored_name"));
				challenge.setChState(rs.getString("ch_state"));
				cntParticipant = rs.getInt(7);
				list.add(challenge);
				list.add(cntParticipant);
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
	public List<Users> getGradeByUno(Connection conn, List<Object> challengeList) {
		String sql = "";
		sql += "SELECT u_grade,u_nick FROM users WHERE u_no = ?";
		Users users = null;
		List<Users> list = new ArrayList<>();
		try {
			ps = conn.prepareStatement(sql);
			for(int i=0; i<challengeList.size(); i++) {
				if(i%2==0) {
					Challenge challenge = (Challenge)challengeList.get(i);
					ps.setInt(1, challenge.getuNo());
					rs = ps.executeQuery();
					while(rs.next()) {
						users = new Users();
						users.setGrade(rs.getString("u_grade"));
						users.setUserNick(rs.getString("u_nick"));
						list.add(users);
					}
				}
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
	public List<Challenge> getPopularChallenge(Connection conn) {
		String sql = "";
		sql += "SELECT * FROM(  " + 
				"SELECT ROWNUM rnum, PAPULAR.* FROM  " + 
				"(SELECT ch_no,u_no, ch_title, ch_stored_name, ch_likes, ch_state FROM challenge C   " + 
				"WHERE ch_state != 'N'  " + 
				"ORDER BY ch_likes DESC)PAPULAR)   " + 
				"WHERE rnum between 1 and 4";
		List<Challenge> list = new ArrayList<>();
		Challenge popular = null;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				popular = new Challenge();
				popular.setChNo(rs.getInt("ch_no"));
				popular.setuNo(rs.getInt("u_no"));
				popular.setChTitle(rs.getString("ch_title"));
				popular.setChStoredName(rs.getString("ch_stored_name"));
				popular.setChLikes(rs.getInt("ch_likes"));
				popular.setChState(rs.getString("ch_state"));
				
				list.add(popular);
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
	public List<Users> getPublisher(Connection conn, List<Challenge> popularList) {
		String sql = "";
		sql += "SELECT u_grade,u_nick FROM users WHERE u_no = ?";
		List<Users> list = new ArrayList<>();
		Users users = null;
		try {
			ps = conn.prepareStatement(sql);
			for(int i=0; i<popularList.size(); i++) {
					Challenge challenge = (Challenge)popularList.get(i);
					ps.setInt(1, challenge.getuNo());
					rs = ps.executeQuery();
					while(rs.next()) {
						users = new Users();
						users.setGrade(rs.getString("u_grade"));
						users.setUserNick(rs.getString("u_nick"));
						list.add(users);
				}
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
	public List<Challenge> getNewChallenge(Connection conn) {
		String sql = "";
		sql += "SELECT * FROM(  " + 
				"SELECT ROWNUM rnum, NEW.* FROM " + 
				"(SELECT ch_no,u_no,ch_title, ch_stored_name, ch_create_date, ch_state FROM challenge C   " + 
				"WHERE ch_state != 'N'  " + 
				"AND months_between(sysdate, ch_create_date) <= 1  " + 
				"ORDER BY ch_no DESC)NEW)   " + 
				"WHERE rnum between 1 and 4   ";
		
		List<Challenge> list = new ArrayList<>();
		Challenge newChallenge = null;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				newChallenge = new Challenge();
				newChallenge.setChNo(rs.getInt("ch_no"));
				newChallenge.setuNo(rs.getInt("u_no"));
				newChallenge.setChTitle(rs.getString("ch_title"));
				newChallenge.setChStoredName(rs.getString("ch_stored_name"));
				newChallenge.setChCreateDate(rs.getDate("ch_create_date"));
				newChallenge.setChState(rs.getString("ch_state"));
				
				list.add(newChallenge);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		return list;
	}
}
