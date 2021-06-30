
package dao.admin.post.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.JDBCTemplate;
import dao.admin.post.face.AdminPostDao;
import dto.AdminPost;
import dto.Notice;
import util.Paging;

public class AdminPostDaoImpl implements AdminPostDao {
	
	private PreparedStatement ps = null; 
	private ResultSet rs = null; 

	@Override
	public List<AdminPost> selectAll(Connection conn) {
		
		String sql = "";
		sql += "SELECT p_no, p_title, u_id, p_views, p_create_date FROM post";
		sql += "ORDER BY p_no DESC";
		
		List<AdminPost> postList = new ArrayList<>();
		
		try {
			ps = conn. prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				
				AdminPost p = new AdminPost();
				
				p.setP_no(rs.getInt("p_no"));
				p.setP_title(rs.getString("p_title"));
				p.setU_no(rs.getInt("u_no"));
				p.setP_views(rs.getInt("p_views"));
				p.setP_create_date(rs.getDate("p_create_date"));
				
				postList.add(p);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return postList;
	}

	@Override
	public List<AdminPost> selectAll(Connection conn, Paging paging) {
		String sql = "";
		sql += "SELECT * FROM (";
		sql += " 	SELECT rownum rnum, A.* FROM (";
		sql += " 		SELECT";
		sql += " 			p_no, p_title, u_no";
		sql += " 			, p_views, P_create_date";
		sql += " 		FROM post";
		sql += " 		ORDER BY p_no DESC";
		sql += "	) A";
		sql += " ) POST";
		sql += " WHERE rnum BETWEEN ? AND ?";
		
		List<AdminPost> postList = new ArrayList<>();
		
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, paging.getStartNo());
			ps.setInt(2, paging.getEndNo());
			
			rs = ps.executeQuery(); 
			
			while(rs.next()) {
				
				AdminPost p = new AdminPost();
				
				p.setP_no(rs.getInt("p_no"));
				p.setP_title(rs.getString("p_title"));
				p.setU_no(rs.getInt("u_no"));
				p.setP_views(rs.getInt("p_views"));
				p.setP_create_date(rs.getDate("p_create_date"));
				
				postList.add(p);
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return postList;
	}

	@Override
	public int selectCntAll(Connection conn) {
		String sql = "";
		sql += "SELECT count(*) cnt FROM post";
		
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
	public AdminPost selectPostByP_no(Connection conn, AdminPost p_no) {
		
		String sql = "";
		sql += "SELECT * FROM post";
		sql += " WHERE p_no = ?";
		
		AdminPost viewPost = null;
		
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, p_no.getP_no());;
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				
				viewPost = new AdminPost();
				
				viewPost.setP_no(rs.getInt("p_no"));
				viewPost.setP_title(rs.getString("p_title"));
				viewPost.setU_no(rs.getInt("u_no"));
				viewPost.setP_content(rs.getString("p_content"));
				viewPost.setP_views(rs.getInt("p_views"));
				viewPost.setP_create_date(rs.getDate("p_create_date"));
				
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return viewPost;
	}

	@Override
	public int updateP_views(Connection conn, AdminPost p_no) {
		
		String sql = "";
		sql += "UPDATE post";
		sql += " SET p_views = p_views + 1";
		sql += " WHERE p_no = ?";
		
		int res = 0;
		
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, p_no.getP_no());
			
			res = ps.executeUpdate();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(ps);
		}
		return res;
	}

	@Override
	public int delete(Connection conn, AdminPost adminPost) {
		
		String sql = "";
		sql += "DELETE post";
		sql += " WHERE p_no = ?";
		
		PreparedStatement ps = null; 
		
		int res = -1;
		try {
			//DB작업
			ps = conn.prepareStatement(sql);
			ps.setInt(1, adminPost.getP_no());

			res = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			try {
				//DB객체 닫기
				if(ps!=null)	ps.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return res;
	}
	
	@Override
	public List<AdminPost> TSearch(Connection conn, Paging paging, String keyword) {
		List<AdminPost> searchList = new ArrayList<AdminPost>();
		String sql = "";
		sql += "SELECT * FROM (";
		sql += " SELECT rownum rnum, A.* FROM (";
		sql += " SELECT p_no, p_title, u_no , p_views, p_create_date FROM post";
		sql += " where p_title like '%";
		sql += keyword;
		sql += "%'";
		sql += " ORDER BY p_no DESC) A) POST";
		sql += " WHERE rnum BETWEEN 1 AND 50";
		
		System.out.println(sql);
		
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				AdminPost viewPost = new AdminPost();
				
				viewPost.setP_no(rs.getInt("p_no"));
				viewPost.setP_title(rs.getString("p_title"));
				viewPost.setU_no(rs.getInt("u_no"));
				viewPost.setP_views(rs.getInt("p_views"));
				viewPost.setP_create_date(rs.getDate("p_create_date"));
				
				searchList.add(viewPost);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return searchList;
	}
	
	@Override
	public List<AdminPost> CSearch(Connection conn, Paging paging, String keyword) {
		List<AdminPost> searchList = new ArrayList<AdminPost>();
		String sql = "";
		sql += "SELECT * FROM (";
		sql += " SELECT rownum rnum, A.* FROM (";
		sql += " SELECT p_no, p_title, u_no , p_views, p_create_date FROM post";
		sql += " where p_content like '%";
		sql += keyword;
		sql += "%'";
		sql += " ORDER BY p_no DESC) A) POST";
		sql += " WHERE rnum BETWEEN 1 AND 50";
		System.out.println(sql);
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				AdminPost viewPost = new AdminPost();
				
				viewPost.setP_no(rs.getInt("p_no"));
				viewPost.setP_title(rs.getString("p_title"));
				viewPost.setU_no(rs.getInt("u_no"));
				viewPost.setP_views(rs.getInt("p_views"));
				viewPost.setP_create_date(rs.getDate("p_create_date"));
				
				searchList.add(viewPost);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return searchList;
	}
	
	

	
}
