package service.admin.complaint.face;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import dto.AdminComplaint;
import dto.Challenge;
import dto.Users;
import util.AdminComplaintPaging;

public interface AdminComplaintService {


	/**
	 * 신고목록 전체 조회 (페이징 적용 안함)
	 * 
	 * @return List<AdminComplaint> - 신고목록 전체 조회 결과 리스트
	 */
	public List<AdminComplaint> getList();
	
		
	/**
	 * 신고목록 전체 조회(페이징 적용)
	 * 
	 * @param complaintPaging - 페이징 정보 객체
	 * @return List<AdminComplaint> - 신고목록 전체 조회결과
	 */
	public List<AdminComplaint> getList(AdminComplaintPaging complaintPaging);
	
	
	/**
	 * 페이징 객체 생성
	 * 
	 * 요청파라미터 curPage를 구한다
	 * Complaint 테이블과 curPage값을 이용하여 Paging 객체를 생성한다
	 * 
	 * @param req - curPage정보를 담고 있는 요청정보 객체
	 * @return 페이징 계산이 완료된 Paging 객체
	 */
	public AdminComplaintPaging getComplaintPaging(HttpServletRequest req);
	
	

	/**
	 * 검색어를 통한 신고 리스트 조회
	 * 
	 * 
	 * @return 검색어를 포함한 신고리스트 조회 결과
	 */
	public List<AdminComplaint> searchList(HttpServletRequest req);
	
	
	/**
	 * 요청파라미터 얻기
	 * 
	 * @param req - 요청정보객체
	 * @return Complaint - 전달파라미터 comNo를 포함한 객체
	 */
	public AdminComplaint getComNo(HttpServletRequest req);
	
	
	/**
	 * comNo로 신고 상세조회
	 * 
	 * @param comNo
	 * @return Complaint - 조회된 신고내용
	 */
	public AdminComplaint view(AdminComplaint comNo);
	
	
	/**
	 * 챌린지개설자 아이디(chUid)를 조회
	 * 
	 * @param viewComplaint - 조회할 신고글 정보
	 * @return String ChUid - 챌린지개설자 아이디
	 */
	public String getChUid(AdminComplaint viewComplaint);
	
	
	/**
	 * 챌린지 개설자 경고 수 조회
	 * 
	 * @param chUid - 개설자 아이디
	 * @return 개설자가 현재까지 받은 경고 수
	 */
	public int count(String chUid);

	
	/**
	 * 신고 수정
	 * 
	 * @param req - 요청 정보 객체
	 */
	public void update(HttpServletRequest req);
	
	
	/**
	 * 신고 삭제
	 * 
	 * @param req - 삭제할 신고글 번호를 가진 객체
	 */
	public void delete(AdminComplaint complaint);
	
	
	/**
	 * 신고 등록
	 * 
	 * @param req - 요청정보 객체(신고내용)
	 */
	public void insert(HttpServletRequest req);
	

	/**
	 * 신고된 챌린지의 경고 수 + 1
	 * 
	 * @param req - 요청 객체  
	 */
	public void addChCaution(HttpServletRequest req);
	

	/**
	 * 챌린지 번호 조회
	 * @parm viewComplaint - 신고 번호가 들어있음
	 * return  - 챌린지 번호
	 */

	public int getChNo(AdminComplaint viewComplaint);
	
	
	/**
	 * 챌린지 경고 횟수 반환
	 * 
	 * @param chNo - 챌린지 번호
	 * @return 챌린지 경고 횟수
	 */
	public int getChCaution(int chNo);
	
	
	/**
	 * 신고된 챌린지의 경고 감소 및 유지
	 * 
	 * @param req -요청 객체
	 */

	public void removeChCaution(HttpServletRequest req);

	
}
