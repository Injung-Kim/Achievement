package controller.admin.challenge;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dto.Challenge;
import service.admin.challenge.face.AdminChallengeService;
import service.admin.challenge.impl.AdminChallengeServiceImpl;
import util.Paging;

/**
 * Servlet implementation class ChallengeListController
 */
@WebServlet("/admin/challenge/list")
public class AdminChallengeListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private AdminChallengeService adminChallengeService = new AdminChallengeServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//로그인이 되어있지 않으면 리다이렉트
		
		if(req.getSession().getAttribute("login")==null || !"M".equals(req.getSession().getAttribute("u_grade"))) {
			resp.sendRedirect("/");
			return;
		}

		String title = adminChallengeService.getTitle(req);
		Paging paging =null;
		List<Challenge> challengeList = null;
		
		HttpSession session = req.getSession();		
		
		//요청파라미터를 전달하여 Paging객체 생성하기
		if(title==null) { //title 값이 없을경우
			paging = adminChallengeService.getPaging(req);
			challengeList = adminChallengeService.getList(paging);
			session.removeAttribute("ch_admin_title");
		}else {
			paging = adminChallengeService.getPaging(req, title);
			challengeList = adminChallengeService.getList(paging,title);
			session.setAttribute("ch_admin_title", title);
		}


		//페이징 객체를 MODEL값으로 전달
		req.setAttribute("paging", paging);

		//조회결과 MODEL값 전달
		req.setAttribute("challengeList", challengeList);
		
		
		req.getRequestDispatcher("/WEB-INF/views/adminChallenge/list.jsp")
			.forward(req, resp);
		
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
}
