package dao.user.face;

import java.sql.Connection;
import java.util.List;

import dto.Challenge;
import dto.ChallengeCategory;
import dto.ChallengeList;
import util.Paging;

public interface ChallengeDao {
	/**
	 * 카테고리 번호로 카테고리 전체 정보 조회
	 * 
	 * @param conn - DB접속
	 * @param number - 저장된 카테고리번호
	 * @return - 번호에 해당하는 카테고리 정보
	 */
	ChallengeCategory getCategorybyNum(Connection conn, ChallengeCategory number);
	/**
	 * 카테고리별 신규 챌린지 갯수 조회
	 * 
	 * @param conn - DB접속
	 * @param number - 저장된 카테고리 번호
	 * @return - 신규 챌린지 갯수
	 */
	int getCntnewChallenges(Connection conn, ChallengeCategory number);
	/**
	 * 카테고리별 신규 챌린지 정보 조회
	 *  조건 : 생성날짜가 한달 이내인 챌린지
	 * 
	 * @param conn - DB접속
	 * @param number - 저장된 카테고리번호
	 * @param paging - 보여질 페이지 객체
	 * @return - 신규 챌린지 정보 리스트
	 */
	List<ChallengeList> newChallengebyCategory(Connection conn, ChallengeCategory number, Paging paging);
	/**
	 *챌린지별 참여하는 회원 수 조회
	 * 
	 * @param conn - DB접속
	 * @param list - 챌린지 정보가 담긴 리스트
	 * @return - 챌린지 번호별 참여 회원 수 리스트
	 */
	List<Integer> countParticipants(Connection conn, List<ChallengeList> list);
	/**
	 * 챌린지별 좋아요 수 조회
	 * 
	 * @param conn - DB접속
	 * @param list - 챌린지 정보가 담긴 리스트
	 * @return - 챌린지 번호별 좋아요 수 리스트
	 */
	List<Integer> countLikes(Connection conn, List<ChallengeList> list);
	/**
	 * 인기 챌린지 갯수 조회
	 * 조건 : 좋아요수 100개 이상을 받은 챌린지
	 * 
	 * @param conn - DB접속
	 * @param subject - 카테고리 정보가 담긴 dto객체
	 * @return 인기챌린지 개수
	 */
	int getCntPopularChallenges(Connection conn, ChallengeCategory subject);
	/**
	 * 인기 챌린지 리스트 조회
	 * 조건 : 좋아요 수 100개 이상인 챌린지
	 * 
	 * @param conn - DB접속
	 * @param subject - 카테고리 정보가 담긴 dto객체
	 * @param paging 페이지 정보
	 * @return 인기 챌린지 리스트 정보
	 */
	List<ChallengeList> popularChallenges(Connection conn, ChallengeCategory subject, Paging paging);

}
