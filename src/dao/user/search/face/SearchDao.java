package dao.user.search.face;

import java.sql.Connection;
import java.util.List;

import dto.Challenge;
import dto.ChallengeList;
import util.Paging;

public interface SearchDao {
	/**
	 * 검색한 단어를 포함한 챌린지 리스트 갯수 조회
	 * 
	 * 
	 * @param conn - DB접속
	 * @param word - 검색어
	 * @return - 조회한 리스트 갯수
	 */
	int cntAll(Connection conn, String word);
	/**
	 * 검색어 단어를 포함한 챌린지 리스트
	 * 
	 * @param conn - DB접속
	 * @param word - 검색어
	 * @param paging - 현재 페이지 정보
	 * @return - 검색된 챌린지 리스트
	 */
	List<ChallengeList> getAllList(Connection conn, String word, Paging paging);
	/**
	 * 상세조건을 만족하는 챌린지 리스트 갯수 조회
	 * 
	 * 
	 * @param conn - DB접속
	 * @param word - 검색어
	 * @param category - 카테고리
	 * @return - 검색된 챌린지 갯수
	 */
	int cntLists(Connection conn, String word, String[] category);
	/**
	 * 상세 조건을 만족하는 첼린지 리스트 조회
	 * 
	 * 
	 * @param conn -DB접속
	 * @param word 검색어
	 * @param orderby 정렬기준
	 * @param category 카테고리
	 * @param paging 검색된 챌린지 리스트
	 * @return
	 */
	List<ChallengeList> getDetailList(Connection conn, String word, String orderby, String[] category, Paging paging);
	

}
