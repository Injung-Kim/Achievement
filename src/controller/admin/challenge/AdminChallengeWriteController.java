package controller.admin.challenge;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.admin.challenge.face.AdminChallengeService;
import service.admin.challenge.impl.AdminChallengeServiceImpl;

/**
 * Servlet implementation class AdminChallengeWriteController
 */
@WebServlet("/admin/challenge/write")
public class AdminChallengeWriteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private AdminChallengeService adminChallengeService = new AdminChallengeServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//로그인이 되어있지 않으면 리다이렉트
		
		if(req.getSession().getAttribute("login")==null || !"M".equals(req.getSession().getAttribute("u_grade"))) {
			resp.sendRedirect("/");
			return;
		}
		
		
		//세션 객체 받아오기
		HttpSession session = req.getSession();
		
		String name = adminChallengeService.getName((String)session.getAttribute("u_id")); //개설자 이름 가져오기
		List<String> category = adminChallengeService.getCategory(); //카테고리 목록
		List<String> cycle =  adminChallengeService.getCycle(); //인증 주기 목록
		
		
		req.setAttribute("name", name);
		req.setAttribute("category", category);
		req.setAttribute("cycle", cycle);
		
		

		req.getRequestDispatcher("/WEB-INF/views/adminChallenge/write.jsp")
			.forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		adminChallengeService.write(req);
		
		
		resp.sendRedirect("/admin/challenge/list");
	}
}
