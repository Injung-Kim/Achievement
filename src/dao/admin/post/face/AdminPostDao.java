
package dao.admin.post.face;

import java.sql.Connection;
import java.util.List;

import dto.AdminPost;
import dto.Notice;
import util.Paging;

public interface AdminPostDao {
	
	/**
	 * 테이블 전체 조회
	 * @param conn
	 * @return
	 */
	List<AdminPost> selectAll(Connection conn);
	
	/**
	 * 테이블 전체 조회 페이징
	 * @param conn
	 * @return
	 */
	List<AdminPost> selectAll(Connection conn, Paging paging);
	
	/**
	 * 총 게시글 조회
	 * @param conn
	 * @return
	 */
	public int selectCntAll(Connection conn);
	
	/**
	 * 특정 게시글 조회
	 * @param conn
	 * @param p_no
	 * @return
	 */
	public AdminPost selectPostByP_no(Connection conn, AdminPost p_no);
	
	/**
	 * 조회된 게시글 조회수 증가
	 */
	public int updateP_views(Connection conn, AdminPost p_no);
	
	/**
	 * 게시글 삭제
	 * 
	 * @param board - 삭제할 게시글번호를 담은 객체
	 */
	int delete(Connection conn, AdminPost adminPost);
	
	public List<AdminPost> TSearch(Connection conn, Paging paging, String keyword);
	
	//내용 검색 조회
	public List<AdminPost> CSearch(Connection conn, Paging paging, String keyword);
	
	
	

}
