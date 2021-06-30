package dao.member.face;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import dto.Challenge;
import dto.Member;
import dto.Mypage;
import dto.Participation;
import dto.Payback;
import dto.Payment;
import dto.Refunds;

public interface MypageDao {

	/**
	 * 유저 아이디를 전달받아 유저의 정보를 db에서 가져오는 메소드
	 * 
	 * @param conn
	 * @param uNo - 유저아이디
	 * @return
	 */
	public Member selectInfo(Connection conn, int uNo);

	/**
	 * 유저 번호를 전달받아 마이페이지 정보를 db에서 가져오는 메소드
	 * 
	 * 
	 * @param conn
	 * @param getuNo - 유저 번호
	 * @return
	 */
	public Mypage selectMypageInfo(Connection conn, int getuNo);

	/**
	 * 유저 정보를 삭제하는 메소드
	 * 
	 * 
	 * @param conn
	 * @param req - request 객체
	 * @return
	 */
	public int deleteUser(Connection conn, String mId, String mPw);

	
	/**
	 * 유저 번호를 입력받아 mypage db도 삭제하는 메소드
	 * 
	 * 
	 * @param conn
	 * @param userNo
	 * @return
	 */
	public int deleteMypage(Connection conn, int userNo);

	
	/**
	 * 마이페이지 수정에서 전달받은 파라미터로 유저 테이블을 업데이트
	 * 하는 메소드
	 * @param conn
	 * @param member - 유저 정보
	 * @return
	 */
	public int update(Connection conn, Member member);

	/**
	 * 마이페이지에서 파일을 정보를 최신화하는
 	 * 
	 * 
	 * @param conn
	 * @param mypage
	 * @return
	 */
	public int updateFile(Connection conn, Mypage mypage);

	/**
	 * 이메일이 있는지 체크하는 select 쿼리
	 * 
	 * @param email
	 * @return
	 */
	public int selectEmailCheck(Connection conn, String email);

	/**
	 * 닉네임이 있는지 체크하는 select 쿼리
	 * 
	 * @param nick
	 * @return
	 */
	public int selectNickCheck(Connection conn, String nick);

	
	/**
	 * 전화번호가 있는지 체크하는 select 쿼리
	 * 
	 * @param phone
	 * @return
	 */
	public int selectPhoneCheck(Connection conn, String phone);

	
	/**
	 * 유저의 챌린지 정보를 db에서 모두 가져오는 쿼리 메소드
	 * 
	 * @param conn
	 * @param uNo
	 * @return
	 */
	public List<Challenge> selectAllUserChall(Connection conn, int uNo);

	/**
	 * 유저의 payment 정보를 가져오는 쿼리 메소드
	 * 
	 * 
	 * @param conn
	 * @param uNo
	 * @return
	 */
	public List<Payment> selectUserPayment(Connection conn, int uNo);

	
	/**
	 * 유저의 payback 정보를 가져오는 쿼리 메소드
	 * 
	 * 
	 * @param conn
	 * @param uNo
	 * @return
	 */
	public List<Payback> selectUserPayback(Connection conn, int uNo);

	
	/**
	 * 유저가 성공한 챌린지 정보를 가져오는 쿼리 메소드
	 * 
	 * 
	 * @param conn
	 * @param uNo
	 * @return
	 */
	public List<Participation> selectSuccessChall(Connection conn, int uNo);

	
	/**
	 * 성공챌린지 번호를 받아 상금을 추출하는 쿼리 메소드
	 * 
	 * @param conn
	 * @param paNo
	 * @return
	 */
	public Refunds selectSuccesChallRefunds(Connection conn, int paNo);

//	/**
//	 * 이번달 누적 상금을 구하는 쿼리 메소드
//	 * 
//	 * @param refunds
//	 * @return
//	 */
//	public int selectMonthTotalRe(Connection conn, Refunds refunds);
//
//	
//	/**
//	 * 이번달 누적 결재를 구하는 쿼리 메소드
//	 * 
//	 * @param refunds
//	 * @return
//	 */
//	public int selectMonthTotalPa(Connection conn, Payment payment);

	/**
	 * 지정된 년/월 상금데이터를 가져오는 메소드
	 * 
	 * @param conn
	 * @param paNo
	 * @param period
	 * @return
	 */
	public Refunds selectPeriodFunds(Connection conn, int paNo, String period);

	
	/**
	 * 지정된 년/월 결재데이터를 가져오는 메소드
	 * 
	 * @param conn
	 * @param getuNo
	 * @param period
	 * @return
	 */
	public List<Payment> selectPeriodPayment(Connection conn, int getuNo, String period);

	/**
	 * 지정된 년/월 환급데이터를 가져오는 메소드
	 * 
	 * @param conn
	 * @param getuNo
	 * @param period
	 * @return
	 */
	public List<Payback> selectPeriodPayback(Connection conn, int getuNo, String period);

	/**
	 * 유저번호를 확인해 현재 번호와 일치하는지 체크
	 * 
	 * 
	 * @param conn
	 * @param phone - 받은 번호
	 * @param getuNo - 유저번호
	 * @return
	 */
	public int selectPhone(Connection conn, String phone, int getuNo);

	/**
	 * 유저닉네임를 확인해 현재 닉네임과 일치하는지 체크
	 * 
	 * @param conn
	 * @param nick
	 * @param getuNo
	 * @return
	 */
	public int selectNick(Connection conn, String nick, int getuNo);

	/**
	 * 유저이메일을 확인해 현재 닉네임과 일치하는지 체크
	 * 
	 * @param conn
	 * @param email
	 * @param getuNo
	 * @return
	 */
	public int selectEmail(Connection conn, String email, int getuNo);

	
//	/**
//	 * 결재 항목에 따라 챌린지 제목을 가져오는 메소드
//	 * 
//	 * 
//	 * @param conn
//	 * @param chNo
//	 * @return
//	 */
//	public Challenge selectChallTitle(Connection conn, int chNo);



}
