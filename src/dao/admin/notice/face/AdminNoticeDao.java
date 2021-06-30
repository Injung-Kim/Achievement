
package dao.admin.notice.face;


import java.sql.Connection;

import java.util.List;

import dto.Notice;
import util.Paging;

public interface AdminNoticeDao {
	/**
	 *  공지사항테이블 전체 조회
	 * @param conn
	 * @return
	 */
	public List<Notice> selectAll(Connection conn);
	/**
	 * 공지사항 테이블 페이징 조회
	 * 	 
	 * @param paging
	 * @return
	 */
	public List<Notice> selectAll(Connection conn, Paging paging);
	
	/**
	 * 총 공지사항 수 조회
	 * @param conn
	 * @return
	 */
	public int selectCntAll(Connection conn);
	
	/**
	 * 특정 공지사항 조회
	 * 
	 * @param N_no
	 * @return
	 */
	public Notice selectNoticeByN_no(Connection conn, Notice n_no);
	
	/**
	 * 조회된 공지사항 조회수 증가시키기
	 * 
	 * @param N_no
	 * @return
	 */
	public int updateN_views(Connection conn, Notice n_no);
	
	/**
	 * 공지사항 입력
	 * @param conn
	 * @param board
	 * @return
	 */
	public int insert(Connection conn, Notice board);
	
	/**
	 * 공지사항 수정
	 * 
	 * 
	 * @param notice
	 * @return
	 */
	public int update(Connection conn, Notice notice);
	
	/**
	 * 공지사항 삭제
	 * 
	 * @param conn
	 * @param notice
	 * @return
	 */
	public int delete(Connection conn, Notice notice);
	
	
	//제목 검색 조회
	public List<Notice> TSearch(Connection conn, Paging paging, String keyword);
	
	//내용 검색 조회
	public List<Notice> CSearch(Connection conn, Paging paging, String keyword);
	

}

