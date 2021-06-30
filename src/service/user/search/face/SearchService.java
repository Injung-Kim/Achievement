package service.user.search.face;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import dto.Challenge;
import dto.ChallengeList;

public interface SearchService {
	/**
	 * 단어를 포함한 챌린지 정보 조회
	 * 
	 * @param req - 검색정보가 담긴 요청객체
	 * @return 조회된 챌린지 정보
	 */
	List<ChallengeList> searchName(HttpServletRequest req);
	/**
	 * 카테고리와 정렬 기준 적용하여 상세 조회하기
	 * 
	 * 
	 * @param req - 카테고리, 검색정보, 정렬조건이 담긴 요청객체
	 * @return 조회된 챌린지 정보
	 */
	List<ChallengeList> searchDetail(HttpServletRequest req);
	/**
	 * 신고할 챌린지 정보 조회
	 * 
	 * @param req 검색어 요청객체
	 * @return 조회된 챌린지 리스트
	 */
	public List<ChallengeList> searchChallenge(HttpServletRequest req);

}
