package service.participant.face;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import dto.Certification;
import dto.CertificationCycle;
import dto.Complaint;
import dto.Member;
import dto.Participation;
import dto.Payback;
import dto.Payment;
import util.Paging;

public interface ParticipantService {
	
	/**
	 * participation 반환
	 * @param req - 참가자 정보를 가지고 있는 요청 객체
	 * @return 참가자 정보를 반환하는 객체
	 */
	public Payment getPayment(HttpServletRequest req);
	
	
	/**
	 * participation 반환
	 * @param req - 참가자 정보를 가지고 있는 요청 객체
	 * @return 참가자 정보를 반환하는 객체
	 */
	public Participation getParticipation(HttpServletRequest req);
	/***
	 * 참여중인 챌린지 이름 얻어오기
	 * @param req - 챌린지 번호 
	 * @return 제목 반환
	 */
	public String getTitle(HttpServletRequest req);
	
	/***
	 * 참여중인 챌린지 이름 얻어오기
	 * @param int - 챌린지 번호 
	 * @return 제목 반환
	 */
	public String getTitle(int chNo);
	/***
	 * 참여중인 챌린지 인증 방법 얻어오기
	 * @param int - 챌린지 번호 
	 * @return 인증 방법 반환
	 */
	public String getChway(int chNo);
	
	/***
	 * 챌린지 번호 추출
	 * 
	 * @param req - 챌린지 번호
	 * @return 챌린지 번호 
	 */
	
	public int getChallengeno(HttpServletRequest req);
	
	/***
	 * 참여 챌린지 번호 추출
	 * 
	 * @param req - 참여 챌린지 번호
	 * @return 참여 챌린지 번호 
	 */
	public int getParticipationno(HttpServletRequest req);
	
	/***
	 * 인증 번호 추출
	 * 
	 * @param req - 인증 번호
	 * @return 인증 번호 
	 */
	public Certification getCertificationno(HttpServletRequest req);
	/**
	 * 참가자 삽입 
	 * 참가자 삽입하기
	 * @param 참가자 정보를 가지고 있는 객체
	 */
	public void attendWrite(Participation participation);
	
	/**
	 * 결제 내역 삽입 
	 * 결제 삽입하기
	 * @param 결제 정보를 가지고 있는 객체
	 */
	
	public void paymentWrite(Payment payment);
	/**
	 * 인증 페이징 가져오기
	 * 
	 * @param 페이징 정보를 가지고 있는 객체
	 */

	public Paging getCertificaitonPaging(HttpServletRequest req);
	
	/**
	 * 참가자 페이징 가져오기
	 * 
	 * @param 페이징 정보를 가지고 있는 객체
	 */
	public Paging getParticipantPaging(HttpServletRequest req);
	/**
	 * 인증 전체 조회
	 * 	페이징 처리 추가
	 * 
	 * @param paging - 페이징 정보 객체
	 * @return List<Certification> - 자신의 인증 조회 결과 리스트
	 */
	public List<Certification> getList(Paging paging,int paNo);

	/**
	 * 인증 내역 삽입 
	 * 인증 삽입하기
	 * @param 인증 정보를 가지고 있는 객체
	 */
	
	public void certificationWrite(HttpServletRequest req);

	/**
	 * 인증 내역 조회 
	 * 
	 * @param 조회된 인증 정보를 가지고 있는 객체
	 */
	public Certification certificationView(Certification certification);
	
	/**
	 * 인증 내역 수정 
	 * 
	 * @param 수정할 인증 정보를 가지고 있는 객체
	 */

	public void certificationUpdate(HttpServletRequest req);

	/**
	 * 인증 내역 삭제 
	 * 
	 * @param 삭제할 인증 정보를 가지고 있는 객체
	 */

	public void certificationDelete(HttpServletRequest req);

	/***
	 * 
	 * @param paging -참가자 조회
	 * @return 참가자 목록
	 */
	public List<Member> getParticipantList(Paging paging, int chNo);
	
	
	/***
	 * 좋아요 여부와 신고 여부를 가져온다
	 * @param req - chNo와 uNo을 가지고 있다
	 * @return 좋아요 여부와 신고 여부 확인
	 */

	public Map<String, Boolean> getWhethers(HttpServletRequest req);
	/***
	 * 좋아요 상태 가져오기
	 * 
	 * @param req - 좋아요 상태를 추출한다
	 * @return 좋아요 상태를 반환
	 */

	public Participation getLike(HttpServletRequest req);
	
	
	
	/***
	 * 현상태로 좋아요를 증가 혹은 감소 시킨다
	 * 
	 * @param participation - 좋아요 정보
	 */
	
	public void increaseLike(Participation participation);
	
	/***
	 * 마이페이지의 현상태로 좋아요를 증가 혹은 감소시킨다
	 * 
	 * @param participation - 좋아요 정보
	 */
	public void increaseLikeMypage(Participation participation);
	
	/***
	 * 좋아요 상태를 바꾼다
	 * 
	 * @param participation - 좋아요 정보
	 */
	public void updatePaLike(Participation participation);
	
	/***
	 * 신고 내역 가져온다
	 * 
	 * @return 신고 내역 
	 */
	public Complaint getComplaint(HttpServletRequest req);
	/***
	 * 신고 내역 저장
	 * 
	 * @param complaint - 신고 정보가 들어있다
	 */

	public void insertComplaint(Complaint complaint);
	
	/***
	 * DB에 저장된 후기 불러오기
	 * 
	 * 
	 * @param paNo - 참가한 챌린지
	 * @return review - 불러오기
	 */

	public String getReview(int paNo);

	/***
	 * 요청 객체에서 review 추출
	 * 
	 * @param req - 요청 객체에 review 추출
	 * @return participation - review 수정할 객체 반환
	 */
	public Participation getReview(HttpServletRequest req);

	/**
	 * 리뷰 등록/수정
	 * 
	 * @param participation - 리뷰 정보와 paNo의 정보를 가지고 있음 
	 */
	public void insertReview(Participation participation);

	/**
	 * 환불을 위한 토큰 반환
	 * @return accessToken
	 * @throws IOException 
	 */
	public String refundsToken() throws IOException;
	
	/**
	 * 유저번호와 챌린지 번호를 가져온다
	 * @param req - 유저번호 챌린지 번호
	 * @return 유저번호와 챌린지 번호를 가져온다
	 */

	public Map<String, Integer> getChNoUno(HttpServletRequest req);

	/**
	 * 환급자 정보를 가져온다
	 * 
	 * @param paMap - 유저번호와 챌린지 번호의 정보
	 * @return 환급자 정보
	 */
	public Payback getPayback(Map<String, Integer> paMap);

	
	/**
	 * 최종 환급
	 * 
	 * @param payback - 환급자 정보
	 * @param token - 환불을 위한 토큰
	 * @throws IOException 
	 */
	
	public void payback(Payback payback, String token) throws IOException;

	/**
	 * 환급 정보 테이블에 저장
	 * @param payback - 저장할 환급 정보
	 */
	public void paybackInsert(Payback payback);
	/**
	 * 참가 취소에 따른 참가자 삭제
	 * @param paNo - 참가자 번호
	 */

	public void participationDelete(int paNo);


	/**
	 * 해당 챌린지의 인증 주기 번호 가져오기
	 * @param chNo - 챌린지 번호 가져오기
	 * @return 인증 주기 번호
	 */
	public int getCecNo(int chNo);

	
	/**
	 * 인증 주기 목록 가져오기
	 * @param cecNo - 인증 주기 번호
	 * @return 인증주기 반환
	 */
	public CertificationCycle getCertificationCycle(int cecNo);

	/**
	 * 챌린지 시작 날짜 및 끝나는 날짜 얻어오기
	 * @param chNo - 챌린지 번호
	 * @return 챌린지 시작 날짜 및 끝나는 날짜
	 */
	public Map<String, Date> getChallengeDate(int chNo);

	/**
	 * 챌린지를 몇 주간 진행하는지
	 * @param challengeDate
	 * @param 몇 일간 인증하는지
	 * @return week
	 */
	public int getWeeks(Map<String, Date> challengeDate , int cycle);

	/**
	 * 전체 구간 날짜별로 구하기
	 * @param challengeDate - 챌린지 날짜
	 * @param cecCycle - 인증 주기
	 * @param section - 전체 구간 
	 * @return 전체 구간 날짜별로 구하기 
	 */
	public List<Map<String, Date>> getSectionAll(Map<String, Date> challengeDate, int cecCycle, int section);

	/**
	 * 자신의 현재 구간 구하기
	 * @param challengeDate - 챌린지 날짜
	 * @param cecCycle - 인증 주기
	 * @param section - 전체 구간
	 * @return 현재 구간 구하기
	 */
	public Map<String, Date> getCurSection(Map<String, Date> challengeDate, int cecCycle, int section);

	/**
	 * 현재 구간 인증 횟수
	 * @param curSection - 현재 구간
	 * @param paNo - 참가 챌린지 번호
	 * @return 인증 횟수
	 */
	public int getCerCount(Map<String, Date> curSection, int paNo);


	


	


	

}
