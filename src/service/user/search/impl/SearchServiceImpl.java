package service.user.search.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import common.JDBCTemplate;
import dao.user.search.face.SearchDao;
import dao.user.search.impl.SearchDaoImpl;
import dto.Challenge;
import dto.ChallengeList;
import service.user.search.face.SearchService;
import util.Paging;

public class SearchServiceImpl implements SearchService {
	private SearchDao searchDao = new SearchDaoImpl();
	@Override
	public List<ChallengeList> searchName(HttpServletRequest req) {
		Connection conn = JDBCTemplate.getConnection(); 
		//검색 파라미터 저장 변수 생성
		String param = req.getParameter("key"); //단어검색
		String page = req.getParameter("curPage");//현재 페이지 정보
		//검색어 정보
		String word = "";
		if(param!=null && !param.equals("")) { word = param; }
		//페이지 정보
		int curPage = 0;
		if(page!=null && !page.equals("")) {curPage = Integer.parseInt(page);}
		int listCount = 9;
		
		
		//조회된 리스트 갯수 구하기	
		int totalCount = searchDao.cntAll(conn, word);
		Paging paging  = new Paging(totalCount, curPage, listCount);
		
		req.setAttribute("searchPaging", paging); //페이지 정보 전달
		
		//챌린지 리스트 조회하기
		List<ChallengeList> list  = searchDao.getAllList(conn, word, paging);
	return list;	
	}
	@Override
	public List<ChallengeList> searchDetail(HttpServletRequest req) {
		Connection conn = JDBCTemplate.getConnection();
		// 카테고리 정보 가져오기
		String[] category = req.getParameterValues("ca_no[]");
		// 정렬 기준 가져오기
		String param = req.getParameter("alignby");
		// 검색 키워드 가져오기
		String name = req.getParameter("key");
		// 페이지 정보 가져오기
		String page = req.getParameter("curPage");
		
		//정렬 기준 정보
		String orderby = "";
		if(param!=null && !param.equals("")) { 
			if(param.equals("u_no")){
				orderby = "count(P.u_no)"; //참여자수 기준
			}else {
				orderby = param;
			}
		}
		
		
		//검색 키워드 정보
		String word = "";
		if(name!=null && !name.equals("")) { word = name; }
		
		//페이지 정보
		int curPage = 0;
		if(page!=null && !page.equals("")) {curPage = Integer.parseInt(page);}
		int listCount = 9;
		
		//조회된 리스트 갯수 구하기	
		int totalCount = searchDao.cntLists(conn, word, category);
		int countList = 9;
		
		Paging paging = new Paging(totalCount, curPage, countList);
		req.setAttribute("detailSearch", paging); // 페이징 정보 전달
		
		//리스트 조회하기
		List<ChallengeList> detail = searchDao.getDetailList(conn, word, orderby, category, paging);
		
		return detail;
	}
	@Override
	public List<ChallengeList> searchChallenge(HttpServletRequest req) {
		Connection conn =  JDBCTemplate.getConnection();//db연결 객체 생성
		
		String param1 = req.getParameter("word"); //검색어
		String param2 = req.getParameter("curPage"); //현재 페이지
		System.out.println(param1);
		
		String keyword = "";
		if(param1!=null && !param1.equals("")) { keyword = param1;}
		int curPage = 0;
		if(param2!=null && !param2.equals("")) { curPage = Integer.parseInt(param2);}
		
		// 조회한 리스트 갯수 
		int totalCount = searchDao.cntAll(conn, keyword);
		
		// 페이징 객체 생성
		Paging paging = new Paging(totalCount, curPage);
		
		// 페이징 적용 - 검색어 리스트 조회
		List<ChallengeList> list  = searchDao.getAllList(conn, keyword, paging);
		
		//페이징 정보 전달
		req.setAttribute("SearchPaging", paging);
		
		//System.out.println(list);
		return list;
	}
	
}
