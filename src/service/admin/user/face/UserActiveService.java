package service.admin.user.face;

import java.util.Map;

import dto.UserPoint;
import dto.Users;

public interface UserActiveService {
	/**
	 * 회원 한명에 대한 활동정보 조회
	 * 활동한 내용을 갯수로 조회해 반환받기
	 * 게시글 작성수, 댓글 작성 수, 신고수, 누른 좋아요 수
	 * 
	 * @param userinfo - 회원번호가 담긴 Users객체
	 * @return 회원 활동정보
	 */
	public Map getCntpost(Users userinfo);
	/**
	 * 회원이 참여한 챌린지 활동정보 조회
	 * 결제금액, 챌린지 포인트 정보 반환받기
	 *  
	 * 	 * @param userinfo - 회원번호가 담긴 Users객체
	 * @return 회원 결제정보
	 */
	public UserPoint getCntPoint(Users userinfo);
	/**
	 * 현재 참여중인 챌린지 정보 조회
	 * 
	 * @param userinfo
	 * @return 참여중인 챌린지 정보
	 */
	public String getChallenge(Users userinfo);

}
