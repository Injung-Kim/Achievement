
package service.admin.comment.face;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import dto.AdminComment;
import dto.Notice;
import util.Paging;

public interface AdminCommentService {
	
	/**
	 * 댓글 전체 조회 
	 * 
	 * @return List<Board> - 게시글 전체 조회 결과 리스트
	 */
	public List<AdminComment> getList();
	
	/**
	 * 댓글 전체 조회
	 * 	페이징 처리 추가
	 * 
	 * @param 
	 * @return
	 */
	public List<AdminComment> getList(Paging paging);
	
	/**
	 * 페이징 객체 생성
	 * 
	 * 요청파라미터 curPage를 구한다
	 * 
	 * 
	 * @param 
	 * @return 
	 */	
	public Paging getPaging(HttpServletRequest req);
	
	/**
	 * 요청파라미터 얻기
	 * 
	 * @param 
	 * @return 
	 */
	public AdminComment getC_no(HttpServletRequest req);
	
	/**
	 * 주어진 c_no를 이용하여 게시글을 조회한다
	 * 조회된 게시글의 조회수를 1 증가시킨다
	 * 
	 * @param 
	 * @return 
	 */	
	public AdminComment view(AdminComment c_no);
	
	/**
	 * 댓글 삭제
	 * 
	 * @param adminComment
	 */
	public void delete(AdminComment adminComment);
	
//	제목 검색 리스트 조회
	public List<AdminComment> TSearch(Paging paging, String keyword);
	
//	내용 검색 리스트 조회
	public List<AdminComment> CSearch(Paging paging, String keyword);
	


}

