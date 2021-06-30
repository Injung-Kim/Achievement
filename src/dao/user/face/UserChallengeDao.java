package dao.user.face;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import dto.Challenge;
import dto.Participation;
import dto.Payback;
import util.Paging;

public interface UserChallengeDao {
	
	/**
	 * Challenge테이블 전체 조회
	 *	페이징 처리 추가
	 * 
	 * @param paging - 페이징 정보 객체
	 * @return List<Challenge> - Challenge테이블 전체 조회 결과 리스트
	 */
	public List<Challenge> selectAll(Connection conn, Paging paging);
	
	/**
	 * Challenge테이블 제목을 통한 전체 조회
	 *	페이징 처리 추가
	 * 
	 * @param paging - 페이징 정보 객체
	 * @return List<Challenge> - Challenge테이블 전체 제목을 통한 조회 결과 리스트
	 */
	public List<Challenge> selectAll(Connection conn, Paging paging, Challenge challenge);
	
	/**
	 * Challenge테이블 제목을 통한 전체 조회
	 *	페이징 처리 추가
	 * 
	 * @param paging - 페이징 정보 객체
	 * @return List<Challenge> - Challenge테이블 전체 제목을 통한 조회 결과 리스트
	 */
	public List<Challenge> selectAll(Connection conn, Paging paging, Challenge challenge, String title);
	
	/**
	 * 총 챌린지 수 조회
	 * 
	 * @return 총 챌린지 수
	 */
	public int selectCntAll(Connection conn);
	
	
	/**
	 * 카테고리 챌린지 수 조회
	 * 
	 * @return 총 챌린지 카테고리를 포함한
	 */
	public int selectCntAll(Connection conn, Challenge challenge);
	
	
	/**
	 * 카테고리 챌린지 수 조회
	 * 
	 * @return 총 챌린지 제목을 포함한
	 */
	public int selectCntAll(Connection conn, String title);
	
	/**
	 * 카테고리 챌린지 수 조회
	 * 
	 * @return 총 챌린지 카테고리를 포함한
	 */
	public int selectCntAll(Connection conn, Challenge challenge, String title);
	
	/**
	 * Challenge테이블 제목을 통한 전체 조회
	 *	페이징 처리 추가
	 * 
	 * @param paging - 페이징 정보 객체
	 * @return List<Challenge> - Challenge테이블 전체 제목을 통한 조회 결과 리스트
	 */
	public List<Challenge> selectAll(Connection conn, Paging paging, String title);
	/**
	 * 특정 챌린지 조회
	 * 
	 * @param challenge - 조회할 ch_no를 가진 객체
	 * @return Challenge - 조회된 결과 객체
	 */
	public Challenge selectChallengeByChNo(Connection conn, Challenge challenge);
	
	
	/**
	 *카테고리 조회
	 *
	 *@param challenge - 조회할 ca_no을 가진 객체
	 *@return category 개설자와 인증 주기
	 */
	
	public String selectCategory(Connection conn, Challenge challenge);
	
	/**
	 *개설자와 인증주기 조회
	 *
	 *@param challenge - 조회할 ch_no을 가진 객체
	 *@return Map<String, String> 개설자와 인증 주기
	 */
	public Map<String, String> selectNameTitle(Connection conn, Challenge challenge);
	
	/**
	 *id로 개설자 이름 가져오기
	 * 
	 *@param u_id - 조회할 id
	 *@return String 개설자 이름
	 */
	public String selectName(Connection conn, String u_id);
	
	/**
	 *카테고리 목록 가져오기
	 * 
	 *
	 *@return List<String> 카테고리 목록
	 */
	public List<String> selectCategory(Connection conn);
	
	/**
	 *인증주기 목록 가져오기
	 * 
	 *
	 *@return List<String> 카테고리 목록
	 */
	public List<String> selectCycle(Connection conn);
	
	/**
	 *챌린지 번호 가져오기
	 * 
	 *
	 *@return int - 챌린지 번호 반환
	 */
	public int selectChNo(Connection conn);
	
	/**
	 *id를 이용하여 챌린지 작성자 번호 찾아오기 
	 * 
	 * @param u_id id정보를 가지고 있다
	 * @return 작성자 번호
	 */
	public int selectuNo(Connection conn, String u_id);
	
	/**
	 *ch_cycle를 이용하여 챌린지 인증 주기 번호 찾아오기 
	 * 
	 * @param cycle - 인증정보를 가지고 있다
	 * @return 인증 주기 번호
	 */
	public int selectCecNo(Connection conn, String cycle);
	
	/**
	 *ca_no를 이용하여 챌린지 작성자 번호 찾아오기 
	 * 
	 * @param category - 카테고리 정보를 가지고 있다
	 * @return 카테고리 번호
	 */
	public int selectCaNo(Connection conn, String category);
	
	/**
	 *ch_no 와 u_no을 통하여 해당 챌린지 참여 여부 확인 
	 * 
	 * @param participation - 조회정보가 담겨져 있는 객체
	 * @return - 1를 반환되면 참여중 아니면 0을 반환
	 */
	public int selectParticipation(Connection conn, Participation participation);
	
	/**
	 *새로운 challenge을 table의 저장
	 *
	 *@param challenge - 챌린지 정보를 가지고 있다  
	 *@return int - (삽입 성공했을때 1이 반환되고 실패했을시 0이 반환된다) 
	 */
	public int insert(Connection conn, Challenge challenge);
	
	/**
	 *수정할 challenge을 table의 저장
	 *
	 *@param challenge - 수정할 챌린지 정보를 가지고 있다  
	 *@return int - (수정 성공했을때 1이 반환되고 실패했을시 0이 반환된다) 
	 */
	public int update(Connection conn, Challenge challenge);
	
	/**
	 *저장 파일 이름 조회
	 * 
	 *@param Challenge - 조회할 ChallengeNo을 가지고 있다
	 *@return  challenge - 삭제할 storedName을 가지고 있다
	 */
	public Challenge selectByStoredName(Connection conn, Challenge challenge);
	
	/**
	 *삭제 challenge
	 *
	 *@param challenge - 삭제할 챌린지 정보를 가지고 있다  
	 *@return int - (삭제 성공했을때 1이 반환되고 실패했을시 0이 반환된다) 
	 */
	public int delete(Connection conn, Challenge challenge);
	
	/**
	 * 챌린지 상태 검색
	 * 
	 * @param conn - DB연결 객체
	 * @param challenge - chNo
	 * @return 챌린지 상태
	 */
	
	public String selectChState(Connection conn, Challenge challenge);
	
	/**
	 * 환급자들 반환
	 * 
	 * @param conn - DB연결 객체
	 * @param challenge - chNo
	 * @return 환급자들 반환
	 */
	
	public List<Payback> selectAllPayback(Connection conn, Challenge challenge);
	/**
	 * 환급자들 다음 번호
	 * @param conn - DB연결 객체
	 * @return 환급자 다음 번호
	 */
	public int selectPaybNo(Connection conn);
	
	/**
	 * 환급자들 삽입
	 * @param conn - DB연결 객체
	 * @param payback - 환급 정보
	 * @return - 1이상이면 데이터 삽입 완료 0
	 */
	public int paybInsert(Connection conn, Payback payback);
	/**
	 * 전체 페이징 구하기
	 * @param conn - DB연결 객체
	 * @param challenge - 챌린지 번호
	 * @return 전체 개수
	 */
	public int selectReviewCntAll(Connection conn, Challenge challenge);
	
	/**
	 * 전체 리스트 구하기
	 * @param conn - DB연결 객체
	 * @param paging - 페이징
	 * @param challenge- 챌린지 번호
	 * @return 참가자
	 */
	public List<Participation> selectParticipationAll(Connection conn, Paging paging, Challenge challenge);
	
	/**
	 * 참가자 구해오기
	 * @param conn DB연결 객체
	 * @param participation - 참가자 번호를 가진 객체
	 * @return 참가자 반환
	 */
	public Participation selectParticipationReview(Connection conn, Participation participation);
	
	
	
	
}
