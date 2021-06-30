
package service.admin.notice.face;

import java.util.List;


import javax.servlet.http.HttpServletRequest;

import dto.Notice;
import util.Paging;

public interface AdminNoticeService {
	
	/**
	 * 공지사항 전체 조회
	 * @return
	 */
	public List<Notice> getList();
	
	/**
	 * 공지사항 전체조회 페이징
	 * @param paging
	 * @return
	 */
	public List<Notice> getList(Paging paging);
	
	/**
	 * 페이징 객체 생성
	 * 
	 * @param req
	 * @return
	 */
	
	public Paging getPaging(HttpServletRequest req);
	
	
	
	/**
	 * 요청 파라미터 얻기
	 * 
	 * @param req
	 * @return
	 */
	public Notice getN_no(HttpServletRequest req);
	
	/**
	 * 주어진 N_NO를 이용하여 게시글 조회한다
	 * 조회된 게시글의 조회수를 1씩 증가 시킨다
	 * @param N_no
	 * @return
	 */
	public Notice view(Notice n_no);
	
	/**
	 * 게시글 작성
	 * 
	 * @param req
	 */
	public void write(HttpServletRequest req);
	
	/**
	 *  게시글 수정
	 * @param req
	 */
	public void update(HttpServletRequest req);
	
	/**
	 * 공지사항 삭제
	 * @param notice
	 */
	public void delte(Notice notice);
	
	
//	제목 검색 리스트 조회
	public List<Notice> TSearch(Paging paging, String keyword);
	
//	내용 검색 리스트 조회
	public List<Notice> CSearch(Paging paging, String keyword);
	

}

