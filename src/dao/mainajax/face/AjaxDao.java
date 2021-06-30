package dao.mainajax.face;

import java.sql.Connection;
import java.util.List;

import dto.Challenge;
import dto.Participation;
import dto.Users;

public interface AjaxDao {
	/**
	 * 참여자 수가 가장 많은 4개 챌린지 정보 조회
	 * 참여 회원 수가 100명 이상인 챌린지
	 * 
	 * @param conn - DB접속
	 * @return 베스트 챌린지 정보
	 */
	List<Object> getBestChallenge(Connection conn);
	/**
	 * 챌린지 개설자 정보 조회
	 * 
	 * 
	 * @param conn - DB접속
	 * @param challengeList 베스트 챌린지 리스트
	 * @return 챌린지 개설자 정보
	 */
	List<Users> getGradeByUno(Connection conn, List<Object> challengeList);
	/**
	 * 좋아요 수가 가장 많은 4개 챌린지 정보 조회
	 * 좋아요 수가 100개 이상인 챌린지
	 * 
	 * @param conn - DB접속
	 * @return 인기 챌린지 정보
	 */
	List<Challenge> getPopularChallenge(Connection conn);
	/**
	 * 챌린지 개설자 정보 조회
	 * 
	 * 
	 * @param conn DB접속
	 * @param popularList 인기챌린지 리스트
	 * @return 챌린지 개설자 정보
	 */
	List<Users> getPublisher(Connection conn, List<Challenge> popularList);
	/**
	 * 새로 생성된 4개 챌린지 정보 조회
	 * 한달 이내 생성된 챌린지
	 * 
	 * @param conn -DB접속
	 * @return 신규 챌린지 정보
	 */
	List<Challenge> getNewChallenge(Connection conn);
	
	

}
