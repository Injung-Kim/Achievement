package dao.board.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import common.JDBCTemplate;
import dao.board.face.PostDao;
import dto.Comment;
import dto.Post;

public class PostDaoImpl implements PostDao {
	Connection conn = JDBCTemplate.getConnection();
	PreparedStatement ps = null;
	ResultSet rs = null;
	@Override
	public void Update(Post post) {
		String sql = "";
		sql += "UPDATE post set p_title='" + post.getP_title() + "', p_content='" + post.getP_content() + "'";
		sql += ", p_stored_name='" + post.getP_stored_name() + "', p_origin_name='" + post.getP_origin_name() + "' where p_no=" + post.getP_no();
		
		try {
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
	}

	@Override
	public Post Select(Post post) {
		String sql = "select * from post, users where post.u_no=users.u_no and P_NO = " + post.getP_no();
		Post p = new Post();
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				p.setB_no(rs.getInt("b_no"));
				p.setP_content(rs.getString("p_content"));
				p.setP_create_date(rs.getDate("p_create_date"));
				p.setP_title(rs.getString("p_title"));
				p.setP_update_date(rs.getDate("P_Update_date"));
				p.setP_views(rs.getInt("P_views"));
				p.setP_no(rs.getInt("p_no"));
				p.setU_no(rs.getInt("u_no"));
				p.setU_nick(rs.getString("U_nick"));
				p.setU_id(rs.getString("u_id"));
				p.setP_origin_name(rs.getString("p_origin_name"));
				p.setP_stored_name(rs.getString("p_stored_name"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		
		return p;
	}

	@Override
	public void Insert(Post post) {
		String sql ="";
		sql += "insert into post(p_no, u_no, b_no, p_title, p_content, p_views, p_origin_name, p_stored_name) values(post_seq.nextval, " + post.getU_no() + ","
				+ post.getB_no() + ", '" + post.getP_title() + "', '" + post.getP_content() + "', 0, '" + post.getP_origin_name() + "', '" + post.getP_stored_name() + "')";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
	}

	@Override
	public void Delete(Post post) {
		String sql ="";
		sql += "DELETE POST where P_no=" + post.getP_no();
		
		try {
			ps=conn.prepareStatement(sql);
			ps.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		
	}

	@Override
	public void UpdateViews(Post post) {
		String sql = "update post set p_views=p_views+1 where p_no = " + post.getP_no();
		
		try {
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
	}

	@Override
	public List<Comment> SelectComment(Comment comment) {
		String sql = "select c.C_content, c.c_create_date, c.C_no, p.P_no, u.U_nick, u.U_no, u.U_id, c.c_group, c.depth"
				+ " from comments c, post p, users u"
				+ " where p.p_no = c.p_no and c.u_no=u.u_no and p.p_no = " + comment.getPno() + " order by c.c_group asc, c.c_no asc";
		List<Comment> cList = new ArrayList<Comment>();
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				Comment c = new Comment();
				c.setC_content(rs.getString("C_content"));
				c.setC_Create_date(rs.getDate("C_create_date"));
				c.setCno(rs.getInt("C_no"));
				c.setPno(rs.getInt("P_no"));
				c.setU_nick(rs.getString("U_nick"));
				c.setUno(rs.getInt("u_no"));
				c.setU_id(rs.getString("u_id"));
				c.setFk_Cno(rs.getInt("c_group"));
				c.setDepth(rs.getInt("depth"));
				cList.add(c);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return cList;
	}

	
	@Override
	public void DeleteComment(Comment comment) {
		String sql = "";
		sql += "DELETE COMMENTS where C_no = " + comment.getCno();

		try {
			ps =  conn.prepareStatement(sql);
			ps.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
	}

	
	@Override
	public void InsertComment(Comment comment) {
		String sql = "insert into COMMENTS(c_no, p_no, u_no, c_content, c_create_date, c_group, depth)"
				+ " values(comments_seq.nextval, "+ comment.getPno() 
				+ ", " + comment.getUno() + ", '" 
				+ comment.getC_content() + "', sysdate, cmtgroup_seq.nextval, 0)";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
	}

	@Override
	public void UpdateComment(Comment comment) {
		// TODO Auto-generated method stub
		String sql = "";
		sql += "UPDATE COMMENTS set c_content='" + comment.getC_content() + "' where c_no ="+comment.getCno();
		
		try {
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
	}

	@Override
	public void InsertCIC(Comment comment) {
		String sql = "insert into COMMENTS(c_no, p_no, u_no, c_content, c_create_date, c_group, depth)"
				+ " values(comments_seq.nextval, "+ comment.getPno() 
				+ ", " + comment.getUno() + ", '" 
				+ comment.getC_content() + "', sysdate, (select c_group from comments where c_no=" + comment.getFk_Cno() +"), 1)";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
	}

	@Override
	public String getNick(String uid) {
		String sql = "";
		sql += "select u_nick from users where u_no='" + uid + "'";
		String u_nick = "";
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				u_nick = rs.getString("U_nick");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return u_nick;
	}

	@Override
	public void PlusMyPost(Post post) {
		String sql = "";
		sql += "Update myPage set m_post = m_post + 1 where m_no=" + post.getU_no();
		try {
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
	}
	
	@Override
	public void MinusMyPost(Post post) {
		String sql = "";
		sql += "Update myPage set m_post = m_post - 1 where m_no=" + post.getU_no();
		try {
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
	}

	@Override
	public void PlusMyComment(Comment comment) {
		String sql = "";
		sql += "Update myPage set m_comment = m_comment + 1 where m_no=" + comment.getUno();
		try {
			ps = conn.prepareStatement(sql);

			ps.executeUpdate();
			conn.commit();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
	}
	
	@Override
	public void MinusMyComment(Comment comment) {
		String sql = "";
		sql += "Update myPage set m_comment = m_comment - 1 where m_no=" + comment.getUno();

		try {
			ps = conn.prepareStatement(sql);

			ps.executeUpdate();
			conn.commit();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
	}
	
	
}
