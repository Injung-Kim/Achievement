package dao.join.face;

import java.sql.Connection;

import dto.Member;

public interface JoinDao {

	
	/** 회원가입 처리
	 * 
	 * @param conn  -DB연결
	 * @param mem  - 조회할 회원정보 
	 * @return - 조회객체
	 */
	public int insertJoin(Connection conn, Member mem);

	/**
	 * 아이디가 존재하는지 조회한다
	 * 	-> count를 이용한다
	 * 
	 * @param uid - 존재하는지 확인할 아이디
	 * @return 아이디를 이용하여 조회된 결과 행 수, 0 - 아이디 없음, 1 - 아이디 존재함
	 */
	public int selectCntById(Connection conn, String uid);
    /**
     *  별명이 존재하는지 조회한다
     *  ->count를 이용한다
     *  
     * @param nick - 존재하는지 확인할 별명
     * @return 별명을 이용하여 조회된 결과 행 수, 0 - 아이디 없음, 1 - 아이디 존재함
     */
	public int selectCntByNick(Connection conn, String nick);
	/**
     *  이메일이 존재하는지 조회한다
     *  ->count를 이용한다
     *  
     * @param email - 존재하는지 확인할 이메일
     * @return 이메일을 이용하여 조회된 결과 행 수, 0 - 아이디 없음, 1 - 아이디 존재함
     */
	public int selectCntByEmail(Connection conn, String email);
    /**
     *   username 와 phone이 일치하는 회원의 수 조회
     *   
     * @param connection - DB연결 객체
     * @param mem   -조회할 회원의 정보
     * @return   -int - 1(존재하는 회원), 0(존재하지 않은 회원), -1(에러)
     */
	public int selectCntMemberByUsernameUserphone(Connection conn, Member mem);
	/**
	 * username과 phone을 이용해 회원정보 조회
	 * 
	 * @param conn - DB연결 객체
	 * @param mem - 조회할 회원
	 * @return member - 조회 객체
	 */
	public Member selectMemberByUsernameUserphone(Connection conn, Member mem);
    /**  username, uid,email이 일치하는 회원의 수 조회
     * 
     * @param connection - DB연결객체
     * @param mem 조회할 회원의 정보
     * @return -  -int - 1(존재하는 회원), 0(존재하지 않은 회원), -1(에러)
     */
	public int selectCntMemberByUserpw(Connection conn, Member mem);
    /**  username, uid,email을 이용해 회원정보 조회
     * 
     * @param connection - DB연결객체
     * @param mem -조회할 회원
     * @return  조회 객체
     */
	public Member selectMemberByUserpw(Connection conn, Member mem);
}
