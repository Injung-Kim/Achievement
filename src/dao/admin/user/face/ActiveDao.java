package dao.admin.user.face;

import java.sql.Connection;
import java.util.Date;

import dto.Users;

public interface ActiveDao {
	/**
	 * 회원이 작성한 게시글 수 조회
	 * 
	 * @param conn - DB접속
	 * @param userinfo - 회원번호가 담긴 user객체
	 * @return 게시글 작성 수
	 */
	public int getCntpostByUserno(Connection conn, Users userinfo);
	/**
	 * 회원이 작성한 댓글 수 조회
	 * 
	 * @param conn - DB접속
	 * @param userinfo -회원번호가 담긴 user객체
	 * @return 댓글 작성 수
	 */
	public int getCntComment(Connection conn, Users userinfo);
	/**
	 * 회원이 누른 좋아요 수 조회 
	 * 
	 * @param conn - DB접속
	 * @param userinfo - 회원번호가 담긴 user객체
	 * @return 누른 좋아요 횟수
	 */
	public int getCntlikes(Connection conn, Users userinfo);
	/**
	 * 회원이 신고한 횟수 조회
	 * 
	 * @param conn - DB접속
	 * @param userinfo - 회원번호가 담긴 user객체
	 * @return 챌린지 신고횟수
	 */
	public int getCntComplaints(Connection conn, Users userinfo);
	/**
	 * 회원이 결제한 총 금액 조회 
	 * 
	 * @param conn - DB접속
	 * @param userinfo - 회원번호가 담긴 Users객체
	 * @return - 회원별 누적 결제금액
	 */
	public int getSumPayments(Connection conn, Users userinfo);
	/**
	 * 최근 포인트를 적립받은 날짜 조회
	 * 
	 * 
	 * @param conn - DB접속
	 * @param userinfo - 회원번호가 담긴 Users객체
	 * @return 포인트 적립일
	 */
	public Date getearnedDate(Connection conn, Users userinfo);
	/**
	 * 최근 포인트를 차감한 날짜 조회
	 * 
	 * @param conn - DB접속
	 * @param userinfo - 회원번호가 담긴 Users객체
	 * @return 포인트 차감일
	 */
	public Date getUsedDate(Connection conn, Users userinfo);
	/**
	 * 현재 보유한 포인트 조회
	 * 적립한 포인트 - 지급한 포인트
	 * 
	 * @param conn - DB접속
	 * @param userinfo - 회원번호가 담긴 Users객체
	 * @return 현재 보유한 포인트
	 */
	public int sumPoints(Connection conn, Users userinfo);
	/**
	 * 차감된 포인트 조회
	 * 
	 * @param conn - DB접속
	 * @param userinfo - 회원번호가 담긴 Users객체
	 * @return 포인트 점수 반환
	 */
	public int getUsedPoints(Connection conn, Users userinfo);
	/**
	 * 현재 참여중인 챌린지 조회
	 * 
	 * @param conn - DB접속
	 * @param userinfo - 회원번호가 담긴 Users객체
	 * @return 챌린지명 반환
	 */
	public String currChallenge(Connection conn, Users userinfo);

}
