package service.mainajax.face;

import java.util.List;
import java.util.Map;

import dto.Challenge;
import dto.Participation;
import dto.Users;

public interface AjaxMainService {
	/**
	 * 참여자 수가 100명 이상인 챌린지 정보 조회
	 * 상위 4개만 가져오기
	 * 
	 * @return 챌린지 정보 리스트
	 */
	List<Object> getBestChallenge();
	/**
	 * 챌린지 개설자 정보 조회
	 * 
	 * @param challengeList 베스트 챌린지 리스트
	 * @return 챌린지 개설자
	 */
	List<Users> getGrade(List<Object> challengeList);
	
	/**
	 * 좋아요 수가 100개 이상인 챌린지 정보 조회
	 * 상위 4개만 가져오기
	 * 
	 * @return 챌린지 정보 리스트
	 */
	List<Challenge> getPopularChallenge();
	/**
	 * 챌린지 개설자 정보 조회
	 * 
	 * @param popularList 
	 * @return 챌린지 개설자
	 */
	List<Users> getpublisher(List<Challenge> popularList);
	/**
	 * 새로 생성된 챌린지 정보 조회
	 * 가장 최근 생성된 챌린지 4개만 가져오기(ch_no, createDate)
	 * 
	 * @return 챌린지 정보 리스트
	 */
	List<Challenge> getNewChallenge();
	
	
}
