package controller.join;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/certification")
public class CertificationController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.setContentType("application/json");
		
		if( Integer.parseInt(req.getParameter("num")) != (int)req.getSession().getAttribute("random") ) {
			System.out.println("인증번호 다름");
			resp.getWriter().println("{\"result\":false}");
			
		}else {
			System.out.println("인증번호 같음");
			resp.getWriter().println("{\"result\":true}");
			
		}
	}
}
