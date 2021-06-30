package service.member.face;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import dto.Challenge;
import dto.Member;
import dto.Mypage;
import dto.Participation;
import dto.Payback;
import dto.Payment;
import dto.Refunds;

public interface MypageService {
	/**
	 * 세션에 저장된 유저아이디를 통해 유저정보를 가져오는 메소드
	 * 
	 * 
	 * @param uNo - 세션에 저장된 아이디
	 * @return
	 */
	public Member getUserInfo(int uNo);

	/**
	 * 유저번호를 통해 유저정보를 가져오는 메소드
	 * 
	 * 
	 * @param getuNo - 유저번호
	 * @return
	 */
	public Mypage getMypageInfo(int getuNo);

	/**
	 * 유저정보를 수정하는 메소드
	 * 
	 * 
	 * @param req
	 * @param mypage 
	 * @param member 
	 * @return
	 */
	public int updateInfo(HttpServletRequest req, Member memberParam, Mypage mypageParam);

	
	/**
	 * 유저 정보를 삭제하는 메소드
	 * 
	 * 
	 * @param req
	 * @return
	 */
	public int deleteInfo(HttpServletRequest req);

	/**
	 * 유저 번호를 입력받아 mypage db도 삭제하는 메소드
	 * 
	 * 
	 * 
	 * @param userNo
	 * @return
	 */
	public int deleteMypage(int userNo);

	/**
	 * 이메일 파라미터 값을 전달 받아 중복을 체크한다. 
	 * 
	 * @param req
	 * @param member 
	 * @return
	 */
	public int checkOverlapEmail(HttpServletRequest req, Member member);

	/**
	 * 닉네임 파라미터 값을 전달 받아 중복을 체크한다.
	 * 
	 * 
	 * @param req
	 * @param member 
	 * @return
	 */
	public int checkOverlapNick(HttpServletRequest req, Member member);

	/**
	 * 전화번호 파라미터 값을 전달 받아 중복을 체크한다.
	 * 
	 * 
	 * @param req
	 * @param member 
	 * @return
	 */
	public int checkOverlapPhone(HttpServletRequest req, Member member);

	/**
	 * 유저의 챌린지 정보를 가져오는 메소드
	 * 
	 * @param uNo - 챌린지의 외래키 유저번호
	 * @return
	 */
	public List<Challenge> getUserChallInfo(int uNo);

	/**
	 * 유저의 결제 정보를 가져오는 메소드
	 * @param uNo
	 * 
	 * @return
	 */
	public List<Payment> getPaymentInfo(int uNo);

	
	/**
	 * 유저의 결제 정보를 가져오는 메소드
	 * @param uNo
	 * 
	 * @return
	 */
	public List<Payback> getPaybackInfo(int uNo);

	
	/**
	 * 유저가 참여한 챌린지중 성공한 챌린지 정보를 가져오는 메소드
	 * 
	 * @param getuNo
	 * @return
	 */
	public List<Participation> getSuccessChallInfo(int getuNo);

	/**
	 * 유저가 성공한 챌린지 정보를 받아 받을 상금을 추출하는 메소드
	 * 
	 * @param parList
	 * @return
	 */
	public List<Refunds> getRefundAmount(List<Participation> parList);

	/**
	 * 누적 총상금을 구하는 메소드
	 * 
	 * @param refundsList
	 * @return
	 */
	public int refundsSum(List<Refunds> refundsList);

	/**
	 * 누적 결재금액을 구하는 메소드
	 * 
	 * @param refundsList
	 * @return
	 */
	public int paymentSum(List<Payment> paymentList);

	/**
	 * 이번달 총상금을 구하는 메소드
	 * 
	 * 
	 * @param refundsList
	 * @return
	 */
	public int refundsMonthSum(List<Refunds> refundsList);

	/**
	 * 이번달 총결재를 구하는 메소드
	 * 
	 * 
	 * @param paymentList
	 * @return
	 */
	public int paymentMonthSum(List<Payment> paymentList);
	
	
	/**
	 * 이번달 총환급을 구하는 메소드
	 * 
	 * @param reqPaybackList
	 * @return
	 */
	public int paybackMonthSum(List<Payback> reqPaybackList);

	/**
	 * 년도 월을 입력받아 해당기간의 상금목록을 가져오는 코드
	 * 
	 * 
	 * @param req
	 * @param refundsList
	 * @return
	 */
	public List<Refunds> getReqFunds(HttpServletRequest req, List<Refunds> refundsList);


	/**
	 * 년도 월을 입력받아 해당기간의 상금목록을 가져오는 코드
	 * 
	 * 
	 * @param req
	 * @param paymentList
	 * @return
	 */
	public List<Payment> getReqPayment(HttpServletRequest req, List<Payment> paymentList);

	/**
	 * 년도 월을 입력받아 해당기간의 환급목록을 가져오는 코드
	 * 
	 * 
	 * @param req
	 * @param paybackList
	 * @return
	 */
	public List<Payback> getReqPayback(HttpServletRequest req, List<Payback> paybackList);
	
	/**
	 * 이번달의 상금목록을 가져오는 코드
	 * 
	 * 
	 * @param req
	 * @param refundsList
	 * @return
	 */
	public List<Refunds> getReqFunds(List<Refunds> refundsList);

	/**
	 * 이번달의 결재목록을 가져오는 코드
	 * 
	 * 
	 * @param paymentList
	 * @return
	 */
	public List<Payment> getReqPayment(List<Payment> paymentList);

	/**
	 * 이번달의 환급목록을 가져오는 코드
	 * 
	 * @param paybackList
	 * @return
	 */
	public List<Payback> getReqPayback(List<Payback> paybackList);
	

//	/**
//	 * 결제 리스트를 받아 해당 결제 챌린지 제목을 가져온다
//	 * 
//	 * @param paymentList
//	 * @return
//	 */
//	public List<Challenge> getChallTitles(List<Payment> paymentList);

	
	/**
	 * 요청한 기간의 상금목록을 받아 기간내의 총상금을 구한다.
	 * 
	 * 
	 * @param refundsList
	 * @return
	 */
	public int sumReqFunds(List<Refunds> refundsList);

	/**
	 * 요청한 기간의 결제목록을 받아 기간내의 총상금을 구한다.
	 * 
	 * @param reqPaymentPeriodList
	 * @return
	 */
	public int sumReqPayment(List<Payment> reqPaymentPeriodList);

	/**
	 * 요청한 기간의 환급목록을 받아 기간내의 총상금을 구한다.
	 * 
	 * @param reqPaybackPeriodList
	 * @return
	 */
	public int sumReqPayback(List<Payback> reqPaybackPeriodList);


	


}
