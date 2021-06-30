package dao.admin.user.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import common.JDBCTemplate;
import dao.admin.user.face.ActiveDao;
import dto.Users;
import oracle.sql.converter.JdbcCharacterConverters;

public class ActiveDaoImpl implements ActiveDao {
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	@Override
	public int getCntpostByUserno(Connection conn, Users userinfo) {
		String sql = "";
		sql += "SELECT COUNT(*) FROM post P, users U";
		sql += "	 WHERE P.u_no  = U.u_no";
		sql += "	 AND U.u_no = ?";
		
		//결과를 저장할 변수
		int post = 0;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userinfo.getUserNo());
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
			post = rs.getInt(1);	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return post;
	}
	@Override
	public int getCntComment(Connection conn, Users userinfo) {
		String sql = "";
		sql += "SELECT COUNT(*) FROM comments C, users U";
		sql += "	 WHERE C.u_no  = U.u_no";
		sql += "	 AND U.u_no = ?";
		
		//결과 저장변수
		int comment = 0;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userinfo.getUserNo());
			
			rs = ps.executeQuery();
			while(rs.next()) {
				comment = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return comment;
	}
	@Override
	public int getCntlikes(Connection conn, Users userinfo) {
		String sql = "";
		sql += "SELECT count(*) FROM participation "
				+ "WHERE u_no = ? AND pa_like = 'Y'";
		//결과를 저장할 변수 생성
		int likes = 0;
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, userinfo.getUserNo());
			rs = ps.executeQuery();
			while(rs.next()) {
				likes = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		return likes;
	}
	@Override
	public int getCntComplaints(Connection conn, Users userinfo) {
		String sql = "";
		sql += "SELECT COUNT(*) FROM complaint C, users U";
		sql += "	 WHERE C.u_no  = U.u_no";
		sql += "	 AND U.u_no = ?";
		//결과 저장 변수
		int complaints = 0;
		
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, userinfo.getUserNo());
			rs = ps.executeQuery();
			while(rs.next()) {
				complaints = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		return complaints;
	}
	@Override
	public int getSumPayments(Connection conn, Users userinfo) {
		String sql = "";
		sql +=  "SELECT sum(paym_amount) FROM PAYMENT WHERE u_no = ?";
		
		//결과를 저장할 변수 생성
		int payments = 0;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userinfo.getUserNo());
			
			rs = ps.executeQuery();
			while(rs.next()) {
				payments = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		return payments;
	}
	@Override
	public Date getearnedDate(Connection conn, Users userinfo) {
		String sql = "";
		sql += "SELECT * FROM(" + 
				"    SELECT ROWNUM rnum, POINT.* FROM (" + 
				"    SELECT po.po_create_date FROM PARTICIPATION P" + 
				"    INNER JOIN POINT PO" + 
				"    ON P.PA_NO = PO.PA_NO" + 
				"    AND P.U_NO = ?" + 
				"    ORDER BY PO.pa_no DESC)POINT" + 
				"    )NUM" + 
				"    WHERE rnum = 1";
		
		//결과를 저장할 변수
		Date gotPoint = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userinfo.getUserNo());
			
			rs = ps.executeQuery();
			while(rs.next()) {
				gotPoint = rs.getDate("po_create_date");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		return gotPoint;
	}
	@Override
	public Date getUsedDate(Connection conn, Users userinfo) {
		String sql = "";
		sql += "SELECT ROWNUM rnum, REFUND.* FROM(" + 
				"    SELECT r.re_point, p.u_no, p.pa_is_success, r.payb_date" + 
				"    FROM PARTICIPATION P" + 
				"    INNER JOIN REFUNDS R" + 
				"    ON P.pa_no = R.pa_no" + 
				"    ORDER BY P.pa_no DESC)REFUND" + 
				"    WHERE REFUND.u_no = ?" + 
				"    AND pa_is_success = 'N'";
		
		Date usedPoint = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userinfo.getUserNo());
			
			rs = ps.executeQuery();
			while(rs.next()) {
				usedPoint = rs.getDate("payb_date");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		return usedPoint;
	}
	@Override
	public int sumPoints(Connection conn, Users userinfo) {
		String sql = "";
		sql += "SELECT sum(po_point) FROM PARTICIPATION P" + 
				"    INNER JOIN POINT PO" + 
				"    ON P.PA_NO = PO.PA_NO" + 
				"    AND P.U_NO = ?";
		
		//적립한 포인트 총합을 저장할 변수
		int sumPoint = 0;
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, userinfo.getUserNo());
			rs = ps.executeQuery();
			while(rs.next()) {
				sumPoint = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		return sumPoint;
	}
	@Override
	public int getUsedPoints(Connection conn, Users userinfo) {
		String sql = "";
		sql += "SELECT sum(r.re_point) " + 
				"    FROM PARTICIPATION P, REFUNDS R" + 
				"    WHERE P.pa_no = R.pa_no" + 
				"    AND p.u_no = ?" + 
				"    AND p.pa_is_success = 'N'";
		int used = 0;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userinfo.getUserNo());
			
			rs = ps.executeQuery();
			while(rs.next()){
				used = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		return used;
	}
	@Override
	public String currChallenge(Connection conn, Users userinfo) {
		String sql = "";
		sql += "SELECT ch_title" + 
				"   FROM CHALLENGE C, PARTICIPATION P" + 
				"   WHERE C.ch_no = P.ch_no" + 
				"   AND p.u_no = ?" + 
				"   AND p.pa_is_success = 'W'";
		
		String challenge = "";
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userinfo.getUserNo());
			
			rs = ps.executeQuery();
			while(rs.next()) {
				challenge = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return challenge;
	}
}
