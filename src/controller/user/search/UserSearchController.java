package controller.user.search;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.Challenge;
import dto.ChallengeList;
import service.user.search.face.SearchService;
import service.user.search.impl.SearchServiceImpl;

/**
 * Servlet implementation class UserSearchController
 */
@WebServlet("/search")
public class UserSearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SearchService searchService = new SearchServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 단어로 챌린지 검색하기 - 단어가 포함된 챌린지 정보 반환
		List<ChallengeList> name = 	searchService.searchName(req);
		
		req.setAttribute("searchWord", name);
		if(name.size() != 0) {
		req.getRequestDispatcher("/WEB-INF/views/userSearch/SearchView.jsp").forward(req, resp);
		}else {
		req.getRequestDispatcher("/WEB-INF/views/userSearch/viewFailed.jsp").forward(req, resp);	
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//카테고리와 정렬 기준 적용하여 상세 조회하기
		List<ChallengeList> detail = searchService.searchDetail(req);
		if(detail.size() != 0) {
		req.setAttribute("DetailSearch", detail);
		req.getRequestDispatcher("/WEB-INF/views/userSearch/detailSearch.jsp").forward(req, resp);
		}else {
		req.setAttribute("DetailSearch", detail);
		req.getRequestDispatcher("/WEB-INF/views/userSearch/searchFailed.jsp").forward(req, resp);
		}
	}
}
