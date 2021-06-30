
package dao.admin.comment.face;

import java.sql.Connection;
import java.util.List;

import dto.AdminComment;
import dto.Notice;
import util.Paging;

public interface AdminCommentDao {
	
	/**
	 * 댓글 테이블 전체 조회 (페이징 없음)
	 * 
	 * @return List<Board> - Board테이블 전체 조회 결과 리스트
	 */
	List<AdminComment> selectAll(Connection conn);
	
	/**
	 * 댓글테이블 전체 조회
	 *	페이징 처리 추가
	 * 
	 * @param paging - 페이징 정보 객체
	 * @return List<Board> - Board테이블 전체 조회 결과 리스트
	 */
	List<AdminComment> selectAll(Connection conn, Paging paging);
	
	/**
	 * 총 댓굴 수 조회
	 * 
	 * @return 총 게시글 수
	 */
	public int selectCntAll(Connection conn);
	
	/**
	 * 특정 댓글 조회
	 * 
	 * @param 
	 * @return
	 */
	public AdminComment selectCommentByC_no(Connection conn, AdminComment c_no);
	
	/**
	 * 댓글 삭제
	 * 
	 * @param 
	 * @return
	 */
	public int delte(Connection conn, AdminComment adminComment);
	
	public List<AdminComment> TSearch(Connection conn, Paging paging, String keyword);
	
	//내용 검색 조회
	public List<AdminComment> CSearch(Connection conn, Paging paging, String keyword);

}

