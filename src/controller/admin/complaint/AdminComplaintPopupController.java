package controller.admin.complaint;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.Challenge;
import dto.ChallengeList;
import service.admin.complaint.face.AdminComplaintService;
import service.admin.complaint.impl.AdminComplaintServiceImpl;
import service.user.search.face.SearchService;
import service.user.search.impl.SearchServiceImpl;

/**
 * Servlet implementation class AdminComplaintPopupController
 */
@WebServlet("/popup/challenge")
public class AdminComplaintPopupController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SearchService searchService = new SearchServiceImpl();
	@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			//챌린지 리스트 조회하기
			List<ChallengeList> list = searchService.searchChallenge(req);
			//조회한 리스트 model 전달
			req.setAttribute("challengeList", list);
			req.getRequestDispatcher("/WEB-INF/views/adminComplaint/ChallengePopup.jsp").forward(req, resp);
		}
}
