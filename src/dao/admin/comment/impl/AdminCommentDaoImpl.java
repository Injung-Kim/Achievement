
package dao.admin.comment.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.JDBCTemplate;
import dao.admin.comment.face.AdminCommentDao;
import dto.AdminComment;
import dto.Notice;
import util.Paging;



public class AdminCommentDaoImpl implements AdminCommentDao {
	
	private PreparedStatement ps = null; 
	private ResultSet rs = null; 

	@Override
	public List<AdminComment> selectAll(Connection conn) {
		
		String sql = "";
		sql += "SELECT c_no, c_content, u_no, c_create_date FROM comments";
		sql += " ORDER BY c_no DESC";
		
		List<AdminComment> commentList = new ArrayList<>();
		
		
		try {
			ps = conn.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				
				AdminComment c = new AdminComment();
				
				c.setC_no(rs.getInt("c_no"));
				c.setC_content(rs.getString("c_content"));
				c.setU_no(rs.getInt("u_no"));
				c.setC_create_date(rs.getDate("c_create_date"));
				
				commentList.add(c);
				
				
				
			}
		} catch (SQLException e) {


			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return commentList;
	}

	@Override
	public List<AdminComment> selectAll(Connection conn, Paging paging) {
		
		String sql = "";
		sql += "SELECT * FROM (";
		sql += " 	SELECT rownum rnum, C.* FROM (";
		sql += " 		SELECT";
		sql += " 			c_no, c_content, u_no";
		sql += " 			, c_create_date";
		sql += " 		FROM comments";
		sql += " 		ORDER BY c_no DESC";
		sql += "	) C";
		sql += " ) COMMENTS";
		sql += " WHERE rnum BETWEEN ? AND ?";
		
		List<AdminComment> commentList = new ArrayList<>();
		
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, paging.getStartNo());
			ps.setInt(2, paging.getEndNo());
			rs = ps.executeQuery();
			
			while(rs.next()) {
				
				AdminComment c = new AdminComment();
				
				c.setC_no(rs.getInt("c_no"));
				c.setC_content(rs.getString("c_content"));
				c.setU_no(rs.getInt("u_no"));
				c.setC_create_date(rs.getDate("c_create_date"));
				
				commentList.add(c);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return commentList;
	}

	@Override
	public int selectCntAll(Connection conn) {
		String sql = "";
		sql += "SELECT count(*) cnt FROM COMMENTS";
		
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
	public AdminComment selectCommentByC_no(Connection conn, AdminComment c_no) {
		
		String sql = "";
		sql += "SELECT * FROM comments";
		sql += " WHERE c_no = ?";
		
		AdminComment viewComment = null;
		
		 try {
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, c_no.getC_no());
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				
				viewComment = new AdminComment();
				
				viewComment.setC_no(rs.getInt("c_no"));
				viewComment.setC_content(rs.getString("c_content"));
				viewComment.setU_no(rs.getInt("u_no"));
				viewComment.setC_create_date(rs.getDate("c_create_date"));
				
				
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		 
		 return viewComment;
	}

	@Override
	public int delte(Connection conn, AdminComment adminComment) {
		
		String sql = "";
		sql += "DELETE comments";
		sql += " WHERE c_no = ?";
		
		PreparedStatement ps = null; 

		int res = -1;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, adminComment.getC_no());
			
			res = ps.executeUpdate();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			try {
				
				if(ps!=null)	ps.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return res;
		}
	
	@Override
	public List<AdminComment> TSearch(Connection conn, Paging paging, String keyword) {
		List<AdminComment> searchList = new ArrayList<AdminComment>();
		String sql = "";
		sql += "SELECT * FROM (";
		sql += " SELECT rownum rnum, C.* FROM (";
		sql += " SELECT c_no, c_content, u_no , c_create_date FROM comments";
		sql += " where c_content like '%";
		sql += keyword;
		sql += "%'";
		sql += " ORDER BY c_no DESC) C) COMMENTS";
		sql += " WHERE rnum BETWEEN 1 AND 50";
		System.out.println(sql);
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				
				AdminComment viewComment = new AdminComment();
				
				viewComment.setC_no(rs.getInt("c_no"));
				viewComment.setC_content(rs.getString("c_content"));
				viewComment.setU_no(rs.getInt("u_no"));
				viewComment.setC_create_date(rs.getDate("c_create_date"));
				
				searchList.add(viewComment);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return searchList;
	}
	
	@Override
	public List<AdminComment> CSearch(Connection conn, Paging paging, String keyword) {
		List<AdminComment> searchList = new ArrayList<AdminComment>();
		String sql = "";
		sql += "SELECT * FROM (";
		sql += " SELECT rownum rnum, C.* FROM (";
		sql += " SELECT c_no, c_content, u_no , c_create_date FROM comments";
		sql += " where u_no like '%";
		sql += keyword;
		sql += "%'";
		sql += " ORDER BY c_no DESC) C) COMMENTS";
		sql += " WHERE rnum BETWEEN 1 AND 50";
		System.out.println(sql);
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				AdminComment viewComment = new AdminComment();
				
				viewComment.setC_no(rs.getInt("c_no"));
				viewComment.setC_content(rs.getString("c_content"));
				viewComment.setU_no(rs.getInt("u_no"));
				viewComment.setC_create_date(rs.getDate("c_create_date"));
				
				searchList.add(viewComment);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return searchList;
	}
		
	}
	

