package service.admin.user.face;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import dto.Users;

public interface UserService {

	/**
	 * 회원목록 가져오기
	 * 검색한 단어가 없을 경우 전체 목록 출력
	 * 검색한 단어가 있을 경우 단어를 포함한 목록 출력
	 * 
	 * 
	 * @param req 요청 정보 객체
	 * @return - 회원목록
	 */
	public List<Users> searchUser(HttpServletRequest req);
	/**
	 * 파라미터 dto에 저장 후 반환받기
	 * 
	 * 
	 * @param req - 회원번호를 담은 요청객체
	 * @return - 회원번호에 해당하는 정보를 담은 dto객체
	 */
	public Users getUserno(HttpServletRequest req);
	/**
	 * 유저테이블 상세정보 조회
	 * 전달받은 유저번호에 해당하는 정보 조회하기
	 * 
	 * 
	 * @param users - 유저번호가 담긴 Users객체
	 * @return - 유저테이블 상세정보
	 */
	public Users getUserInfo(Users users);
	
	/**
	 * 변경할 회원의 회원번호를 전달받아 dto로 반환
	 * 
	 * @param req - 회원번호가 담긴 요청객체
	 * @return 회원번호가 저장된 Users객체
	 */
	public Users selectUser(HttpServletRequest req);
	/**
	 * 회원번호를 전달받아 해당 회원 삭제하기
	 * 
	 * @param users - 회원정보가 담긴 dto객체
	 * @return - 성공여부(성공시 1, 실패시 0)
	 */
	public int deleteUser(Users users);
	/**
	 * 회원번호를 전달받아 해당 회원 등급 전환하기
	 * 
	 * @param users - 회원정보가 담긴 dto객체
	 * @return 성공여부(성공시 1 실패시 0)
	 */
	public int updateUser(Users users);
	
}
