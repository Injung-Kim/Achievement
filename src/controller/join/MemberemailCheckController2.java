package controller.join;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.join.face.JoinService;
import service.join.impl.JoinServiceImpl;




@WebServlet("/member/emailcheck")
public class MemberemailCheckController2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private JoinService joinService = new JoinServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		boolean result = joinService.emailCheck(req.getParameter("email"));
		
		req.setAttribute("result", result);
		
		req.getRequestDispatcher("/WEB-INF/views/join/emailcheck.jsp")
		     .forward(req, resp);
	}
	
}
