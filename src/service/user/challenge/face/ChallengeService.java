package service.user.challenge.face;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import dto.Challenge;
import dto.ChallengeCategory;
import dto.ChallengeList;

public interface ChallengeService {
	/**
	 * 카테고리 정보 dto 저장하기
	 * 
	 * @param req 카테고리 번호가 담긴 요청객체
	 * @return dto에 저장된 카테고리 번호
	 */
	ChallengeCategory getCategory(HttpServletRequest req);
	/**
	 * 카테고리 정보 불러오기
	 * 
	 * @param number 파라미터로 요청받아 dto에 저장된 카테고리 번호
	 * @return 카테고리 정보
	 */
	ChallengeCategory getCategoryInfo(ChallengeCategory number);
	/**
	 * 카테고리별 신규챌린지 리스트 조회하기
	 * 조건 : 한달 이내 생성된 챌린지만 가져오기
	 * 
	 * @param req - 페이지 정보가 담긴 요청객체
	 * @param number 파라미터로 요청받아 dto에 저장된 카테고리 번호
	 * @return 조회된 챌린지 리스트
	 */
	List<ChallengeList> getNewChallenges(HttpServletRequest req, ChallengeCategory number);
	/**
	 * 챌린지별 참여자 수 조회
	 * 
	 * 
	 * @param list - 카테고리별 챌린지 정보가 담긴 리스트
	 * @return 챌린지 번호별 참여하는 회원 수
	 */
	List<Integer> getCntParticipant(List<ChallengeList> list);
	/**
	 * 챌린지별 좋아요 수 조회
	 * 
	 * 
	 * @param list - 카테고리별 챌린지 정보가 담긴 리스트
	 * @return 카테고리별로 분류된 챌린지 좋아요 리스트
	 */
	List<Integer> getCntLikes(List<ChallengeList> list);
	/**
	 * 카테고리별 인기 챌린지 리스트 조회
	 * 
	 * 
	 * @param req - 페이지 정보가 담긴 요청객체
	 * @param subject - 카테고리 정보가 저장된 dto객체
	 * @return 카테고리별 인기 챌린지 리스트
	 */
	List<ChallengeList> getPopularChallenges(HttpServletRequest req, ChallengeCategory subject);

}
