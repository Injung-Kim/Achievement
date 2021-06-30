package controller.mypage;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/mypage/session")
public class MypageSessionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// 응답 형식 json으로 설정
		resp.setContentType("application/json; charset=UTF-8");
		// 파라미터 값 전달
		resp.getWriter().println("{\"intervalTime\": "+ req.getSession().getMaxInactiveInterval()+"}");
		
//		req.getRequestDispatcher("/WEB-INF/views/mypage/mySession.jsp");
	}
}
