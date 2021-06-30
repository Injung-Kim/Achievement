package dao.participant.face;

import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import dto.Certification;
import dto.CertificationCycle;
import dto.Challenge;
import dto.Complaint;
import dto.Member;
import dto.Participation;
import dto.Payback;
import dto.Payment;
import util.Paging;

public interface ParticipantDao {
	/**
	 *다음 참가 번호 가져오기
	 * 
	 *
	 *@return int - 다음 참가 번호 반환
	 */
	public int selectPaNo(Connection conn);
	
	/**
	 *다음 인증 번호 가져오기
	 * 
	 *
	 *@return int - 다음 인증 번호 반환
	 */
	public int selectCeNo(Connection conn);
	/**
	 * 현재 참가 번호 가져오기
	 * 
	 * @return int - 현재 참가 번호 가져오기
	 */
	
	public int selectByPaNo(Connection conn, int chNo, int uNo);
	/**
	 * 챌린지 제목 얻어오기
	 * 
	 * @return String - 챌린지 이름 가져오기
	 */
	
	public String selectByTitle(Connection conn, int chNo);
	
	/**
	 * 챌린지 인증 방법 얻어오기
	 * 
	 * @return String - 챌린지 이름 가져오기
	 */
	public String selectByChWay(Connection conn, int chNo);

	/**
	 *결제 번호 가져오기
	 * 
	 *
	 *@return int - 챌린지 번호 반환
	 */

	public int selectPaymNo(Connection conn);
	
	/***
	 * 저장된 파일 이름 가져오기
	 * 
	 * @param connection - DB연결 객체
	 * @param certification 저장 파일 일므 추출
	 * @return 저장파일 이름 리턴
	 */
	
	public Certification selectByStoredName(Connection conn, Certification certification);

	/**
	 * 참가자 저장
	 * @param DB에 저장할 객체
	 * @return 저장성공 여부
	 * 
	 */
	public int attendInsert(Connection conn, Participation participation);
	
	/**
	 * 결제 내역 저장 
	 * 
	 * @param DB에 저장할 객체
	 * @return 저장 성공 여부
	 */
	public int paymentInsert(Connection conn, Payment payment);
	
	
	/**
	 *  전체 개수 구하기
	 *  자신의 인증 챌린지 전체 구하기
	 * 
	 * @return 전체 개수 구하기
	 */
	public int selectCntAll(Connection conn, Participation participation);
	
	/**
	 *  전체 개수 구하기
	 *  참가한 챌린지의 인원 전체 구하기
	 * 
	 * @return 전체 개수 구하기
	 */
	
	public int selectCntAll(Connection conn, Challenge challenge);
	
	
	/***
	 * 참가자 리스트 조회하기
	 * 
	 * @param conn - DB 연결 객체
	 * @param paging - 페이징 처리
	 * @param challenge - 참가자 리스트에 같은 ch_no을 찾기 위해
	 * @return
	 */
	public List<Member> selectAllPartification(Connection conn, Paging paging, int chNo);
	
	
	/**
	 * 자신의 인증 리스트 구하기
	 * 
	 * @param conn - DB연결 객체
	 * @param paging - 페이징 처리
	 * @return certification 자신의 인증 리스트 반환
	 */
	public List<Certification> selectAllCertification(Connection conn, Paging paging, int paNo);
	/**
	 * 인증글 저장
	 * @param DB에 저장할 객체
	 * @return 저장성공 여부
	 * 
	 */
	public int certificationInsert(Connection conn, Certification certification);

	
	/**
	 * 인증글 조회
	 * @param DB에 조회할 객체
	 * @return 조회 객체
	 * 
	 */
	
	public Certification selectCertification(Connection conn, Certification certification);
	
	/**
	 * 인증글 수정
	 * @param DB에 수정할 객체
	 * @return 1 이상은 성공
	 * 
	 */
	public int certificationUpdate(Connection conn, Certification certification);
	
	/**
	 * 인증글 삭제
	 * @param DB에 삭제할 객체
	 * @return 1 이상은 성공
	 * 
	 */
	public int certificationDelete(Connection conn, Certification certification);

	/***
	 * 챌린지 참가 테이블에 좋아요 여부 조회
	 * 
	 * @param connection - DB연결 객체
	 * @param chNo - 챌린지 번호
	 * @param uNo - 유저 번호
	 * @return 좋아요 여부 (true 좋아요 누른 상태, false 좋아요 누르지 않은 상태)  
	 */
	
	public boolean selectByPaLike(Connection conn, int chNo, int uNo);
	
	
	/***
	 * 신고 여부 테이블 조회
	 * 
	 * @param connection - DB연결 객체
	 * @param chNo - 챌린지 번호
	 * @param uNo - 유저 번호
	 * @return 신고 여부(true 신고한 상태, false 신고하지않은 상태)
	 */
	
	public boolean selectByComplaint(Connection conn, int chNo, int uNo);
	
	
	/***
	 * 좋아요 여부 바꾸기
	 * 
	 * @param conn - DB연결 객체
	 * @param participation
	 * @return - 1이상이면 성공 0이하이면 실패
	 */
	public int updatePaLike(Connection conn, Participation participation);
	
	/***
	 * 챌린지 좋아요 수 증감
	 * 
	 * @param conn - DB연결 객체
	 * @param participation - chNo와 챌린지 좋아요
	 * @return - 1이상이면 성공 0 이하이면 실패
	 */
	public int increaseChLike(Connection conn, Participation participation);
	
	/***
	 * 마이페이지 좋아요 수 증감
	 * 
	 * @param conn - DB연결 객체
	 * @param participation - chNo와 좋아요
	 * @return 1이상이면 성공 0이하이면 실패
	 */
	public int increaseMypageLike(Connection conn, Participation participation);
	/***
	 * 다음 신고 번호 받아오기
	 * 
	 * @param conn - DB연결 객체
	 * @return - 다음 신고 번호
	 */
	public int selectComNo(Connection conn);
	
	/***
	 * 신고 저장하기 
	 * 
	 * @param conn - DB연결 객체
	 * @param complaint - 신고내역 저장 객체
	 * @return - 1이상이면 성공 0 이하이면 실패
	 */
	public int complaintInsert(Connection conn, Complaint complaint);
	
	/***
	 * 리뷰 불러오기 
	 * 
	 * @param connection - DB 연결 객체
	 * @param paNo - 참가한 챌린지 번호
	 * @return review를 반환
	 */
	public String selectByReview(Connection conn , int paNo);
	
	/**
	 * 리뷰 저장하기
	 * 
	 * @param conn - DB 연결 객체
	 * @param participation - 입력/수정할 리뷰 객체
	 * @return 1이상이면 성공 0 이하이면 실패
	 */
	
	public int reviewInsert(Connection conn, Participation participation);
	
	/** 
	 * payment를 조회하여 payback를 만들어서 가져온다
	 * @param conn - DB연결 객체
	 * @param paMap - 유저 번호와 챌린지 번호
	 * @return 환급자 정보
	 */
	public Payback selectPayback(Connection conn, Map<String, Integer> paMap);
	
	/**
	 * 다음 환급 번호 가져오기 
	 * @param conn - DB연결 객체
	 * @return 다음 환급 번호
	 */
	
	public int selectByPaybNo(Connection conn);
	
	
	/**
	 * 환급자 정보 테이블에 삽입
	 * @param conn - DB연결 객체
	 * @param payback - 환급자 정보
	 * @return 1이상이면 성공 0 이하이면 실패
	 */
	public int paybackInsert(Connection conn, Payback payback);
	
	/**
	 * 참가 취소에 따른 참가자 삭제
	 * @param conn - DB연결 객체
	 * @param paNo - 참가자 번호
	 * @return 1이상이면 성공 0 이하이면 실패
	 */
	public int participationDelete(Connection conn, int paNo);
	
	/**
	 * 참가 챌린지의 인증 주기 가져오기
	 * @param connection
	 * @param chNo
	 * @return 인증 주기 
	 */
	public CertificationCycle selectCertificationCycle(Connection conn, int cecNo);
	
	/**
	 * 참가 챌린지의 인증 주기 번호 가져오기
	 * @param conn - DB연결 객체
	 * @param chNo - 챌린지 번호
	 * @return 인증 주기 번호
	 */
	
	public int getCecNo(Connection conn, int chNo);
	
	/**
	 * 챌린지 시작 날짜 및 끝나는 날짜 가져오기
	 * @param conn - DB연결 객체
	 * @param chNo - 챌린지 번호
	 * @return 챌린지 시작 날짜, 끝나는 날짜
	 */
	public Map<String, Date> selectChallengeDate(Connection connection, int chNo);
	/**
	 * 현재 구간 인증 횟수
	 * @param conn - DB연결 객체
	 * @param curSection - 현재 구간들
	 * @param paNo - 참가자 챌린지 번호
	 * @return 구간별 인증 횟수
	 */
	public int getCerCount(Connection conn, Map<String, Date> curSection, int paNo);

	
	
	
	

	
	
	
	
}
