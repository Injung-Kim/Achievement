
package dao.admin.notice.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.JDBCTemplate;
import dao.admin.notice.face.AdminNoticeDao;
import dto.Notice;
import util.Paging;



public class AdminNoticeDaoImpl implements AdminNoticeDao {
	
	Connection conn = JDBCTemplate.getConnection();
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	@Override
	public List<Notice> selectAll(Connection conn) {
		
		String sql = "";
		sql += "SELECT n_no, n_title, u_no, n_views, n_create_date FROM notice";
		sql += " ORDER BY n_no DESC";
		
		List<Notice> noticeList = new ArrayList<>();
		
		try {
			ps = conn.prepareStatement(sql);
			
			rs= ps.executeQuery();
			
			while(rs.next()) {
				
				Notice n = new Notice();
				
				n.setN_no(rs.getInt("n_no"));
				n.setN_title(rs.getString("n_title"));
				n.setU_no(rs.getInt("u_no"));
				n.setN_views(rs.getInt("n_views"));
				n.setN_create_date(rs.getDate("n_create_date"));
				
				noticeList.add(n);
				
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return noticeList;
		
		
	}
	@Override
	public List<Notice> selectAll(Connection conn, Paging paging) {
		String sql = "";
		sql += "SELECT * FROM (";
		sql += " SELECT rownum rnum, N.* FROM (";
		sql += " SELECT";
		sql += " n_no, n_title, u_no";
		sql += " , n_views, n_create_date";
		sql += " FROM notice";
		sql += " ORDER BY n_no DESC";
		sql += " ) N";
		sql += " ) NOTICE";
		sql += " WHERE rnum BETWEEN ? AND ?";
		
	List<Notice> noticeList = new ArrayList<>();
		
		try {
			ps = conn.prepareStatement(sql); //SQL수행 객체
			
			ps.setInt(1, paging.getStartNo());
			ps.setInt(2, paging.getEndNo());
			
			rs = ps.executeQuery(); //SQL 수행 및 결과집합 저장
			
			
			while(rs.next()) {
				Notice n = new Notice(); //결과값 저장 객체
				
				
				n.setN_no( rs.getInt("n_no") );
				n.setN_title( rs.getString("n_title") );
				n.setU_no( rs.getInt("u_no") );
				n.setN_views( rs.getInt("n_views") );
				n.setN_create_date( rs.getDate("n_create_date") );
				
				
				noticeList.add(n);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		//최종 결과 반환
		return noticeList;
	}
	
	
	@Override
	public int selectCntAll(Connection conn) {
		String sql = "";
		sql += "SELECT count(*) cnt FROM notice";
		
		//총 게시글 수
		int cnt = 0;
		
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				cnt = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return cnt;
	}
	@Override
	public Notice selectNoticeByN_no(Connection conn, Notice n_no) {
		
		String sql = "";
		sql += "SELECT * FROM notice";
		sql += " WHERE n_no = ?";
		
		Notice viewNotice = null;
		
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, n_no.getN_no());
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				
				viewNotice = new Notice();
				
				viewNotice.setN_no(rs.getInt("n_no"));
				viewNotice.setN_title(rs.getString("n_title"));
				viewNotice.setU_no(rs.getInt("u_no"));
				viewNotice.setN_content(rs.getString("n_content"));
				viewNotice.setN_views(rs.getInt("n_views"));
				viewNotice.setN_create_date(rs.getDate("n_create_date"));
			}
					
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return viewNotice;
	}
	@Override
	public int updateN_views(Connection conn, Notice n_no) {
		
		String sql = "";
		sql += "UPDATE notice";
		sql += " SET n_views = n_views + 1";
		sql += " WHERE n_no = ?";
		
		int res = 0;
		
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, n_no.getN_no());
			
			res = ps.executeUpdate();
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(ps);
		}
		
		return res;
	}
	@Override
	public int insert(Connection conn, Notice notice) {
		String sql = "";
		sql += "INSERT INTO notice(N_NO, N_TITLE, U_NO, N_CONTENT, N_VIEWS)";
		sql += " VALUES (notice_seq.nextval, ?, ?, ?, 0)";
		
		int res = 0;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, notice.getN_title());
			ps.setInt(2, notice.getU_no());
			ps.setString(3, notice.getN_content());
			
			res = ps.executeUpdate();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(ps);
		}
		
		return res;
	}
	@Override
	public int update(Connection conn, Notice notice) {
		
		String sql = "";
		sql += "UPDATE notice";
		sql += " SET n_title = ?,";
		sql += " n_content = ?";
		sql += " WHERE n_no = ?";
		
		PreparedStatement ps = null;
		
		int res = -1;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, notice.getN_title());
			ps.setString(2, notice.getN_content());
			ps.setInt(3, notice.getN_no());
			
			res = ps.executeUpdate();
			
			conn.commit();
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			try {
			if(ps!=null) ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	return res;
	
	
}
	@Override
	public int delete(Connection conn, Notice notice) {
		
		String sql = "";
		sql += "DELETE notice";
		sql += " WHERE n_no = ?";
		
		PreparedStatement ps = null;
		
		int res = -1;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, notice.getN_no());
			
			res = ps.executeUpdate();
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			
				try {
					if(ps!=null) ps.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
		}
		
		return res;
	}
	@Override
	public List<Notice> TSearch(Connection conn, Paging paging, String keyword) {
		List<Notice> searchList = new ArrayList<Notice>();
		String sql = "";
		sql += "SELECT * FROM (";
		sql += " SELECT rownum rnum, N.* FROM (";
		sql += " SELECT n_no, n_title, u_no , n_views, n_create_date FROM notice";
		sql += " where n_title like '%";
		sql += keyword;
		sql += "%'";
		sql += " ORDER BY n_no DESC) N) NOTICE";
		sql += " WHERE rnum BETWEEN 1 AND 50";
		System.out.println(sql);
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				Notice viewNotice = new Notice();
				
				viewNotice.setN_no(rs.getInt("n_no"));
				viewNotice.setN_title(rs.getString("n_title"));
				viewNotice.setU_no(rs.getInt("u_no"));
				viewNotice.setN_views(rs.getInt("n_views"));
				viewNotice.setN_create_date(rs.getDate("n_create_date"));
				
				searchList.add(viewNotice);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return searchList;
	}
	
	@Override
	public List<Notice> CSearch(Connection conn, Paging paging, String keyword) {
		List<Notice> searchList = new ArrayList<Notice>();
		String sql = "";
		sql += "SELECT * FROM (";
		sql += " SELECT rownum rnum, N.* FROM (";
		sql += " SELECT n_no, n_title, u_no , n_views, n_create_date FROM notice";
		sql += " where n_content like '%";
		sql += keyword;
		sql += "%'";
		sql += " ORDER BY n_no DESC) N) NOTICE";
		sql += " WHERE rnum BETWEEN 1 AND 50";
		System.out.println(sql);
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				Notice viewNotice = new Notice();
				
				viewNotice.setN_no(rs.getInt("n_no"));
				viewNotice.setN_title(rs.getString("n_title"));
				viewNotice.setU_no(rs.getInt("u_no"));
				viewNotice.setN_views(rs.getInt("n_views"));
				viewNotice.setN_create_date(rs.getDate("n_create_date"));
				
				searchList.add(viewNotice);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return searchList;
	}
	

	
}

	