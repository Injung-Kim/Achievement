package dao.founder.face;

import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import dto.Certification;
import dto.Challenge;
import dto.Mypage;
import dto.Participation;
import dto.Point;
import dto.Refunds;
import util.Paging;

public interface FounderDao {
	/***
	 * 인증게시물들을 불러온다
	 * 
	 * @param conn DB연결 객체
	 * @param challenge - chNo가 담겨져 있는 객체
	 * @return 총 인증 수
	 */
	public int selectCntAll(Connection conn, Challenge challenge);
	/***
	 * 
	 * @param connection DB연결 객체
	 * @param paging
	 * @param chNo - 데이터
	 * @return member와 certification을 받아온다
	 */
	public Map<String, Object> selectAll(Connection conn, Paging paging, int chNo);
	
	
	/***
	 * chNo을 통하여 개설자 아이디를 반환한다
	 * 
	 * @param connection - DB연결 객체
	 * @param chNo - 챌린지 번호
	 * @return 개설자 아이디
	 */
	public Map<String,Integer> selectByUno(Connection conn, int chNo);
	
	/***
	 * 인증 내역 조회
	 * 
	 * @param connection - DB연결 객체 
	 * @param certification - 인증 번호가 담겨져 있는 객체
	 * @return 조회된 인증
	 */
	public Certification selectCertification(Connection conn, Certification certification);
	
	/***
	 * 인증 수정
	 * 
	 * @param conn - DB연결 객체
	 * @param certification - 인증 정보를 수정할 데이터가 담겨져 있는 객체
	 * @return 인증 수정 여부
	 */
	public int whatherUpdate(Connection conn, Certification certification);
	
	/***
	 * 모든 인증 처리
	 * @param 챌린지 번호를 반환한다
	 * @return 1 이상이 반환될경우 모든 인증이 처리되어있지 않다
	 */
	public int selectCeIsSuccess(Connection conn, int chNo);
	
	/***
	 * 챌린지 끝나는 날짜 반환
	 * 
	 * @param conn - DB연결 객체
	 * @param chNo - 조회할 챌린지 번호
	 * @return endDate 반환
	 */
	
	public Date selectByEndDate(Connection conn, int chNo);
	
	/***
	 * 챌린지 시작하는 날짜 반환
	 * 
	 * @param conn - DB연결 객체
	 * @param chNo - 조회할 챌린지 번호
	 * @return endDate 반환
	 */
	
	public Date selectByStartDate(Connection conn, int chNo);
	
	/***
	 * 현재 챌린지 상태 업데이트
	 * 
	 * @param conn - DB연결 객체
	 * @param chNo - 바꿀 쿼리문
	 * @return 1 이상이면 성공 0이하이면 실패
	 */
	
	public int chStateUpdate(Connection conn, int chNo);
	
	/***
	 * 참가자 불러오기
	 * 
	 * @param conn - DB연결 객체
	 * @param chNo - 챌린지 조회 번호
	 * @return - 참가자 반환
	 */
	
	public List<Integer> selectAllByPaNo(Connection conn, int chNo);
	
	/***
	 * 인증 주기 번호 불러오기
	 * @param conn - DB연결 객체
	 * @param chNo - 챌린지 번호
	 * @return cec - 인증 주기 버호
	 */
	
	public int selectByCecNo(Connection conn, int chNo);
	
	/***
	 * 인증 주기 불러오기
	 * @param conn - DB연결 객체
	 * @param cecNo - 인증 주기 번호
	 * @return cycle - 인증 주기
	 */
	
	public int selectByCycle(Connection conn, int cecNo);
	
	/***
	 * 인증 횟수 불러오기
	 * @param conn
	 * @param cecNo
	 * @return count cycle 인증 횟수
	 */
	public int selectByCount(Connection conn, int cecNo);
	
	/***
	 * DB에서 참가자별 인증 성공 횟수 불러오기
	 * @param conn - DB연결 객체
	 * @param paNoList - 참가자 번호
	 * @param chNo - 조회할 챌린지 번호
	 * @return - 참가자 번호, 참가자 인증 성공 횟수
	 */
	public Map<Integer, Integer> selectPaCount(Connection conn, List<Integer> paNoList, int chNo);
	
	/***
	 * 다음 포인트 번호 불러오기
	 * 	  
	 * @param conn -DB연결 객체
	 * @return 다음 포인트 번호
	 */
	
	public int selectByPoNo(Connection conn);
	
	/***
	 * 포인트 삽입
	 * 
	 * @param conn - DB연결 객체
	 * @param point - point 정보
	 * @return DB에 삽입이 성공했을때 1 실패했을때 0이 반환된다
	 */
	public int insertPoint(Connection conn, Point point);
	
	/***
	 * 유저 번호 추출
	 * @param conn - DB연결 객체
	 * @param key - paNo값
	 * @return - 유저번호
	 */
	
	public int getuNo(Connection conn, Integer key);
	
	/***
	 * 업데이트 마이 페이지
	 * @param conn - DB연결 객체
	 * @param mypage - 업데이트할 데이터들
	 * @return DB에 수정이 성공했을때 1 실패했을때 0이 반환된다
	 */
	
	public int updateMypage(Connection conn, Mypage mypage);
	
	/***
	 * 인증 성공
	 * 
	 * @param conn - DB연결 객체
	 * @param key - pa 번호
	 * @param isSuccess - 인증 성공
	 * @return DB에 수정이 성공했을때 1 실패했을때 0이 반환된다
	 */
	public int updatePaIsSuccess(Connection conn, Integer key, String isSuccess);
	
	/***
	 * 성공자 리스트를 불러온다
	 * 
	 * @param key - paNo를 불러온다
	 * @return DB에서 성공한 유저번호와 챌린지 번호를 불러온다
	 */
	public Participation selectPaIsSuccess(Connection conn,Integer key);
	
	/***
	 * 환불자를 반환
	 * @param conn - DB연결 객체
	 * @param participation - 챌린지를 성공한 참여자
	 * @return 환급자를 반환
	 */
	public Refunds selectRefunds(Connection conn, Participation participation);
	
	/***
	 * 환불 다음 번호
	 * @param conn - DB연결 객체
	 * @return - 환불 다음 번호
	 */
	
	public int selectByReno(Connection conn);
	
	/**
	 * 환불자 정보에 따른 DB저장
	 * @param conn - DB연결 객체
	 * @param refunds - 환불자 정보
	 * @return - 1이상이면 성공 이하이면 실패
	 */
	public int insertRefunds(Connection conn, Refunds refunds);
	
	/**
	 * 챌린지에 상태를 가져온다
	 * @param conn - DB연결객체
	 * @param chNo - 챌린지 번호
	 * @return 챌린지 상태
	 */
	public String selectChallengeCheck(Connection conn, int chNo);
	
	
	
	
	
	
	

}
