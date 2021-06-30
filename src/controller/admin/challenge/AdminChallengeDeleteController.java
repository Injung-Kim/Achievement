package controller.admin.challenge;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.admin.challenge.face.AdminChallengeService;
import service.admin.challenge.impl.AdminChallengeServiceImpl;

/**
 * Servlet implementation class AdminChallengeDeleteController
 */
@WebServlet("/admin/challenge/delete")
public class AdminChallengeDeleteController extends HttpServlet {
	private static final  long serialVersionUID = 1L;	
	
	private AdminChallengeService adminChallengeService = new AdminChallengeServiceImpl();
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//로그인이 되어있지 않으면 리다이렉트
		
		if(req.getSession().getAttribute("login")==null || !"M".equals(req.getSession().getAttribute("u_grade"))) {
			resp.sendRedirect("/");
			return;
		}
		//잘 못된 접근 방식
		if(req.getParameter("chState")==null) {
			String text = "잘 못된 접근 방식입니다.";
			req.setAttribute("text", text);		
			req.getRequestDispatcher("/WEB-INF/views/adminChallenge/error.jsp")
			.forward(req, resp);
			return;
			//대기 이외에 수정 및 삭제가 있을시
		}else {
			if(!"W".equals(req.getParameter("chState"))) {
				String text = "잘 못된 접근 방식입니다.";
				req.setAttribute("text", text);		
				req.getRequestDispatcher("/WEB-INF/views/adminChallenge/error.jsp")
				.forward(req, resp);
				return;
			}

		}
		
				
		adminChallengeService.delete(req);
		resp.sendRedirect("/admin/challenge/list");
	}
	

}
