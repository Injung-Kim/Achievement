package controller.join;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.join.face.JoinService;
import service.join.impl.JoinServiceImpl;



@WebServlet("/member/nickcheck")
public class MembernickCheckController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private JoinService joinService = new JoinServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		boolean result = joinService.nickCheck(req.getParameter("nick"));
		
		req.setAttribute("result", result);
		
		req.getRequestDispatcher("/WEB-INF/views/join/nickcheck.jsp")
		     .forward(req, resp);
	}
	
}
