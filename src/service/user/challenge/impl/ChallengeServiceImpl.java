package service.user.challenge.impl;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import common.JDBCTemplate;
import dao.user.face.ChallengeDao;
import dao.user.impl.ChallengeDaoImpl;
import dto.Challenge;
import dto.ChallengeCategory;
import dto.ChallengeList;
import service.user.challenge.face.ChallengeService;
import util.Paging;

public class ChallengeServiceImpl implements ChallengeService {
	private ChallengeDao challengeDao = new ChallengeDaoImpl();
	@Override
	public ChallengeCategory getCategory(HttpServletRequest req) { //dto에 요청받은 카테고리 번호 저장
		String param = req.getParameter("subject");
		//System.out.println(param);
		
		ChallengeCategory category = new ChallengeCategory(); 
		int categoryNo = 0;
		if(param!=null && !param.equals("")) {
			if(param.equals("0")) {
				categoryNo = 1;
				category.setCategoryNo(categoryNo);
			}else {
			categoryNo = Integer.parseInt(param);
			category.setCategoryNo(categoryNo);
			}
		}
		return category;
	}
	@Override
	public ChallengeCategory getCategoryInfo(ChallengeCategory number) {
		Connection conn =  JDBCTemplate.getConnection();
		 //카테고리 이름 조회하기
		ChallengeCategory info = challengeDao.getCategorybyNum(conn, number);
		return info;
	}
	@Override
	public List<ChallengeList> getNewChallenges(HttpServletRequest req,ChallengeCategory number) {
		Connection conn =  JDBCTemplate.getConnection();
		//조회된 챌린지 갯수
		int cntList = challengeDao.getCntnewChallenges(conn, number); 
		//보여질 게시글 갯수
		int listCount = 4; 		
		//현재 페이지 정보 가져오기(페이징)
    	String param = req.getParameter("curPage");
    	int curPage = 0;
    	if(param!=null && !param.equals("")) {
    		curPage = Integer.parseInt(param);
    	}
		//페이징하기
		Paging newPage = new Paging(cntList, curPage, listCount);
		
		//페이징 전달
		req.setAttribute("newPage", newPage);
		
		// 신규챌린지 리스트 조회하기
		List<ChallengeList>list = challengeDao.newChallengebyCategory(conn, number, newPage);
		return list;
	}
	@Override
	public List<Integer> getCntParticipant(List<ChallengeList> list) {
		Connection conn = JDBCTemplate.getConnection();
		//챌린지별 참여자 수 조회하기
		List<Integer>cntParticipant = challengeDao.countParticipants(conn,list);
		return cntParticipant;
	}
	@Override
	public List<Integer> getCntLikes(List<ChallengeList> list) {
		Connection conn = JDBCTemplate.getConnection();
		//챌린지별 좋아요 수 조회하기
		List<Integer>cntLikes = challengeDao.countLikes(conn,list);
		return cntLikes;
	}
	@Override
	public List<ChallengeList> getPopularChallenges(HttpServletRequest req, ChallengeCategory subject) {
		Connection conn = JDBCTemplate.getConnection();
		
		//현재 페이지 정보 가져오기(페이징)
    	String param = req.getParameter("curPage");
    	int curPage = 0;
    	if(param!=null && !param.equals("")) {
    		curPage = Integer.parseInt(param);
    	}
    	//보여질 챌린지 수 
    	int listCount = 4;
    	
    	//총 챌린지 수
    	int totalCount = challengeDao.getCntPopularChallenges(conn,subject);
    	Paging paging = new Paging(totalCount, curPage, listCount);
    	//페이지 전달
    	req.setAttribute("popularPaging", paging);
    	
    	//챌린지 리스트 조회하기
    	List<ChallengeList> list = challengeDao.popularChallenges(conn, subject, paging);
		return list;
	}
}
