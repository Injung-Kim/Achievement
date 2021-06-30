package service.member.face;

import javax.servlet.http.HttpServletRequest;

import dto.Member;

public interface MemberService {
	/**
	 * 로그인 정보 추출
	 * 
	 *  @param req - 요청 정보 객체
	 *  @return Member - 로그인 정보
	 * 
	 */
	public Member getLoginMember(HttpServletRequest req);
	
	/**
	 * 로그인 처리
	 * 
	 * 
	 * @param member - 회원 아이디를 가진 객체
	 * @return Member - 조회된 회원 정보
	 **/
	
	public boolean login(Member mem);
	
	/**
	 * 유저의 간단한 정보 가져오기
	 * 
	 *  @param member - 회원 아이디를 가진 객체
	 *  @return member - 조회된 회원 정보
	 * 
	 */
	public Member info(Member mem);
	
	/**
	 * 유저의 모든 정보 가져오기
	 * 
	 * @param req - 회원 번호를 요청 객체
	 * @return member - 조회된 회원 정보
	 */
	
	public Member infoAll(HttpServletRequest req);

	
	
	
}
