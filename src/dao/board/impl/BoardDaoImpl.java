package dao.board.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import common.JDBCTemplate;
import dao.board.face.BoardDao;
import dto.Board;
import util.Paging;

public class BoardDaoImpl implements BoardDao {
	Connection conn = JDBCTemplate.getConnection();
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	@Override
	public List<Board> Select(Board board, Paging paging) {
		String sql = "";
		sql += "select * from (";
		sql += " select rownum rnum, B.* from (";
		sql += " select p_no, u_nick, b_name, p_title, p_views, p_create_date";
		sql += " from boards, post, users";
		sql += " where boards.b_no = post.b_no and post.u_no = users.u_no and boards.b_no = " + board.getBno();
		sql += " order by p_no desc) B";
		sql += " ) board where rnum BETWEEN ? and ?";
		
		List<Board> boardList = new ArrayList<Board>();
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1,  paging.getStartNo());
			ps.setInt(2, paging.getEndNo());
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				Board data = new Board();
				data.setPno(rs.getInt("p_no"));
				data.setBno(board.getBno());
				data.setP_Create_Date(rs.getDate("P_create_date"));
				data.setP_Title(rs.getString("p_title"));
				data.setP_views(rs.getInt("p_views"));
				data.setU_nick(rs.getString("U_nick"));
				data.setB_name(rs.getString("B_name"));;
				
				boardList.add(data);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return boardList;
	}

	@Override
	public List<Board> MostViewList(Board board) {
		String sql ="";
		sql += "select * from";
		sql += " (select * from post where b_no=" + board.getBno() + " and p_create_date >= sysdate-2 order by p_views desc)";
		sql += " where rownum <= 10";
		
		List<Board> MvList = new ArrayList<Board>();
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				Board b = new Board();
				b.setPno(rs.getInt("P_no"));
				b.setBno(board.getBno());
				b.setP_Create_Date(rs.getDate("P_create_date"));
				b.setP_Title(rs.getString("p_title"));
				b.setP_views(rs.getInt("p_views"));
				
				MvList.add(b);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return MvList;
	}

	@Override
	public List<Board> cSearch(Board board, Paging paging, String word) {
		String sql = "";
		sql += "select * from (";
		sql += " select rownum rnum, B.* from (";
		sql += " select p.p_no, u.u_nick, bo.b_name, p.p_title, p.p_views, p.p_create_date";
		sql += " from boards bo, post p, users u, comments c";
		sql += " where bo.b_no = p.b_no and p.u_no = u.u_no and p.p_no = c.p_no and";
		sql += " c.c_content like '%" + word + "%' and bo.b_no = " + board.getBno();
		sql += " order by p_no desc) B";
		sql += " ) board where rnum BETWEEN ? and ?";
		
		List<Board> boardList = new ArrayList<Board>();
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1,  paging.getStartNo());
			ps.setInt(2, paging.getEndNo());
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				Board data = new Board();
				
				data.setBno(board.getBno());
				data.setP_Create_Date(rs.getDate("P_create_date"));
				data.setP_Title(rs.getString("p_title"));
				data.setP_views(rs.getInt("p_views"));
				data.setPno(rs.getInt("P_no"));
				data.setU_nick(rs.getString("U_nick"));
				data.setB_name(rs.getString("B_name"));;
				
				boardList.add(data);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return boardList;
	}
	
	@Override
	public List<Board> pSearch(Board board, Paging paging, String word) {
		String sql = "";
		sql += "select * from (";
		sql += " select rownum rnum, B.* from (";
		sql += " select p_no, u_nick, b_name, p_title, p_views, p_create_date";
		sql += " from boards, post, users";
		sql += " where boards.b_no = post.b_no and post.u_no = users.u_no and";
		sql += " (post.p_content like '%" + word + "%' or post.p_title like '%"+ word +"%') and boards.b_no = " + board.getBno();
		sql += " order by p_no desc) B";
		sql += " ) board where rnum BETWEEN ? and ?";
		
		List<Board> boardList = new ArrayList<Board>();
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1,  paging.getStartNo());
			ps.setInt(2, paging.getEndNo());
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				Board data = new Board();
				
				data.setBno(board.getBno());
				data.setP_Create_Date(rs.getDate("P_create_date"));
				data.setP_Title(rs.getString("p_title"));
				data.setP_views(rs.getInt("p_views"));
				data.setPno(rs.getInt("P_no"));
				data.setU_nick(rs.getString("U_nick"));
				data.setB_name(rs.getString("B_name"));;
				
				boardList.add(data);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return boardList;
	}
	
	

	@Override
	public int selectCnt(int bno) {//게시판별 게시글수 검색
		String sql = "select count(*) cnt from post where post.b_no = "+ bno;
		int cnt = 0;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				cnt = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(ps);
			JDBCTemplate.close(rs);
		}
		return cnt;
	}

	@Override
	public String[] boardData(int bno) {//게시판 정보 검색
		String sql = "select * from boards where b_no="+bno;
		String[] boardData = new String[2];
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				boardData[0] = rs.getString("b_no");
				boardData[1] = rs.getString("b_name");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(ps);
			JDBCTemplate.close(rs);
		}
		
		
		return boardData;
	}

	@Override
	public int TSearchAndCnt(int bno, String keyword) {// 제목및 내용 검색 게시글수 검색
		String sql = "select count(*) cnt from post where post.b_no =" + bno;
		sql += " and (post.p_content like '%" + keyword + "%' or post.p_title like '%"+ keyword +"%')";
		int cnt = 0;
	
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				cnt = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(ps);
			JDBCTemplate.close(rs);
		}
		return cnt;
	}

	@Override
	public int CSearchAndCnt(int bno, String keyword) {//댓글 검색 게시글수 검색
		String sql = "select count(*) cnt from post, comments where post.p_no = comments.p_no and post.b_no =" + bno;
		sql += " and comments.c_content like '%" + keyword + "%'";
		int cnt = 0;
	
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				cnt = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(ps);
			JDBCTemplate.close(rs);
		}
		return cnt;
	}
	

}
