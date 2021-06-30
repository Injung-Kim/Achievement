package dao.admin.complaint.face;

import java.sql.Connection;
import java.util.List;

import dto.AdminComplaint;
import dto.Challenge;
import dto.Users;
import util.AdminComplaintPaging;
import util.Paging;


public interface AdminComplaintDao {
	
	
	/**
	 * 신고 목록 조회
	 * 
	 * @param conn
	 * @return
	 */	
	public List<AdminComplaint> selectAll(Connection conn);
	
		
	/**
	 * 신고 목록 조회 (페이징 적용)
	 * 
	 * @param conn
	 * @param paging - 페이징 정보 객체
	 * @return List<Complaint> - Complaint 전체 조회 결과 목록
	 */
	public List<AdminComplaint> selectAll(Connection conn, AdminComplaintPaging complaintPaging);
	
	
	/**
	 * 총 신고글 수 조회
	 * 
	 * @param conn
	 * @return 총 신고글 수
	 */
	public int selectCountAll(Connection conn);
	
	
	/**
	 * 검색된 신고글 수 조회
	 * 
	 * @param conn -DB접속
	 * @param search_type - 검색조건
	 * @param search_keyword - 검색어
	 * @return 검색된 게시글 갯수
	 */
	public int getCntList(Connection conn, String search_type, String search_keyword);
	
	
	/**
	 * 검색어에 맞는 신고글 조회
	 * 
	 * @param conn -DB접속
	 * @param search_type  검색조건(필드값)
	 * @param search_keyword 검색어(파라미터)
	 * @param paging 보여질 페이징 정보
	 * @return
	 */
	public List<AdminComplaint> getSearchComplaints(Connection conn, String search_type, String search_keyword, Paging paging);
	
	
	/**
	 * 특정 게시글 조회
	 * 
	 * @param conn
	 * @param comNo - 조회할 comNo를 가진 객체
	 * @return Complaint - 조회된 결과 객체
	 */
	public AdminComplaint selectComplaintByComNo(Connection conn, AdminComplaint comNo);
	
	
	/**
	 * 챌린지개설자 아이디(chUid)를 조회
	 * 
	 * @param conn
	 * @param viewComplaint - 조회할 comNo를 가진 객체
	 * @return chUid - 챌린지개설자 아이디
	 */
	public String selectChUidByComNo(Connection conn, AdminComplaint viewComplaint);
	
	
	/**
	 * 개설자 경고 수 조회
	 * 
	 * @param conn - DB접속
	 * @param chUid - 챌린지개설자 아이디
	 * @return Number - 개설자가 받은 경고 수 
	 */
	public int getNumber(Connection conn, String chUid);

	
	/**
	 * 신고 수정
	 * 
	 * @param conn
	 * @param complaint - 수정할 내용을 담은 객체
	 * @return
	 */
	public int update(Connection conn, AdminComplaint complaint);
	
	
	/**
	 * 신고 삭제
	 * 
	 * @param conn 
	 * @param complaint - 삭제할 신고글번호를 담은 객체
	 * @return
	 */
	public int delete(Connection conn, AdminComplaint complaint);
	
	
	/**
	  * 신고 등록
	  * 
	  * @param conn
	  * @param complaint
	  * @return
	  */
	public int insert(Connection conn, AdminComplaint complaint);
	
	
	/**
	 * 챌린지 경고횟수 증가시키기
	 * 
	 * @param conn
	 * @param complaint
	 * @return
	 */
	public int chCaution(Connection conn,  AdminComplaint complaint);
	

	/**
	 * 개설자(회원) 경고횟수 증가시키기
	 * 
	 * @param conn 
	 * @param ChUid - 챌린지개설자 아이디
	 */
	public int chUcaution(Connection conn, String chUid);
	
	/**
	 * 경고 횟수 증가
	 * @param conn - DB연결 객체
	 * @param comNo - 경고 횟수 추가
	 * @return DB가 변경 되었는지 안 되었는지 (1 : commit  0: rollback)
	 */
	
	public int upChallengeCaution(Connection conn, int chNo);

	/**
	 * 신고된 챌린지 번호 가져오기
	 *@param conn - DB연결 객체
	 * @param comNo - 신고 번호
	 * @return 챌린지 번호
	 */
	
	public int selectChNoByComNo(Connection conn, AdminComplaint viewComplaint);
	
	/**
	 * 신고 상태 변경하기
	 * @param conn - DB연결 객체
	 * @param comNo - 신고 번호
	 * @return DB가 변경 되었는지 안 되었는지 (1 : commit  0: rollback)
	 */

	public int updateComplaint(Connection conn, int comNo);

	/**
	 * 유저 신고 횟수 누적
	 * @param conn - DB연결 객체
	 * @param chNo - 챌린지 번호
	 * @return DB가 변경 되었는지 안 되었는지 (1 : commit  0: rollback)
	 */
	
	public int updateUsersCaution(Connection conn, int chNo);
	
	/**
	 * 챌린지 신고 횟수 누적 가져오기
	 * @param conn - DB연결 객체
	 * @param chNo - 챌린지 번호
	 * @return 챌린지 누적 경고
	 */

	public int selectChByCaution(Connection conn, int chNo);

	/**
	 * 챌린지 신고 횟수 감소 및 유지
	 * @param conn - DB연결 객체
	 * @param chNo - 챌린지 번호 
	 * @param caution - 신고 감소 및 유지
	 * @return - DB가 변경 되었는지 안 되었는지 (1 : commit  0: rollback)
	 */
	public int downChallengeCaution(Connection conn, int chNo, int caution);

	/**
	 * 사용자 신고 횟수 감소 및 유지
	 * @param conn - DB연결 객체
	 * @param chNo - 챌린지 번호
	 * @param caution - 신고 감소 및 유지
	 * @return - DB가 변경 되었는지 안 되었는지 (1 : commit  0: rollback)
	 */
	public int downUsersCaution(Connection conn, int chNo, int caution);
	
}	