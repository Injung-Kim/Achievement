package service.join.face;

import javax.servlet.http.HttpServletRequest;

import dto.Member;

public interface JoinService {

	/** 가입 정보 추출
     * 
     * @param req - 요청 정보 객체
     * @return  - 가입 정보
     */
	public Member getJoinMember(HttpServletRequest req);
    /** 가입 처리
     * 
     * @param mem - 
     * @return - 조회된 회원 정보
     */
	public boolean join(Member mem);
    /** 회원 아이디 찾기 처리
     * 
     * @param req - 요청정보 객체
     * @return - 로그인 정보
     */
	

	public boolean idCheck(String uid);
	/** 별명 중복 체크
	 * 
	 * @param nick - AJAX로 전달된 중복체크할 별명
	 * @return - true - 중복값을 가지고 있음, false - 중복값이 없음
	 */

	public boolean nickCheck(String nick);
	/** 이메일 중복 체크
	 * 
	 * @param email - AJAX로 전달된 중복체크할 별명
	 * @return - true - 중복값을 가지고 있음, false - 중복값이 없음
	 */
	public boolean emailCheck(String email);
    /** 회원 아이디찾기 정보 추출
     * 
     * @param req - 요청객체 정보
     * @return -아이디 찾기 정보
     */
	public Member idFindMember(HttpServletRequest req);
    /**   아이디 찾기 처리
     *   
     * @param mem - 회원 아이디를 가진 객체
     * @return -조회된회원정보
     */
	public boolean infind(Member mem);
	/**
	 * 유저 정보 가져오기
	 * 
	 *  @param member - 회원 이름과 핸드폰을 정보를가진 객체
	 *  @return member - 조회된 회원 정보
	 * 
	 */
	public Member idinfo(Member mem);
    /**   회원 비밀번호찾기 정보 추출
     * 
     * @param req -요청정보 객체
     * @return - 비밀번호 찾기 정보
     */
	public Member pwFindMember(HttpServletRequest req);
    /**   비밀번호 찾기 처리
     * 
     * @param mem -회원비밀번호를 가진 객체
     * @return - 조회된회원정보
     */
	public boolean pwfind(Member mem);
     /**  유저정보 가져오기
      * 
      * @param mem 회원이름,아이디,이메일 정보를 가진 객체
      * @return 조회된 회원 정보
      */
	public Member pwinfo(Member mem);

	


	
	
}
