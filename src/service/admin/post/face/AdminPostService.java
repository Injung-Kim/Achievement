
package service.admin.post.face;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import dto.AdminPost;
import dto.Notice;
import util.Paging;

public interface AdminPostService {

	/**
	 * 모든 게시글 조회
	 * @return
	 */
	public List<AdminPost> getList();

	/**
	 * 페이징 처리 모든 게시글 조회
	 * @param paging
	 * @return
	 */
	public List<AdminPost> getList(Paging paging);

	/**
	 * 페이징 객체 생성
	 * @param req
	 * @return
	 */	
	public Paging getPaging(HttpServletRequest req);
	
	/**
	 * 요청한 파라미터 얻기
	 * @param req
	 * @return
	 */
	public AdminPost getP_no(HttpServletRequest req);
	
	/**
	 * 주어진 p_no로 조회수 증가
	 * @param p_no
	 * @return
	 */
	public AdminPost view(AdminPost p_no);
	
	/**
	 * 게시글 삭제
	 * @param adminPost
	 */
	public void delete(AdminPost adminPost);
	
//	제목 검색 리스트 조회
	public List<AdminPost> TSearch(Paging paging, String keyword);
	
//	내용 검색 리스트 조회
	public List<AdminPost> CSearch(Paging paging, String keyword);




}

