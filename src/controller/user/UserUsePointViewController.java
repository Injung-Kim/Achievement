package controller.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.RefundPoint;
import service.user.face.UserService;
import service.user.impl.UserServiceImpl;

/**
 * Servlet implementation class UserUsePointViewController
 */
@WebServlet("/user/use/point/view")
public class UserUsePointViewController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService = new UserServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(req.getSession().getAttribute("login")==null) {
			resp.sendRedirect("/");
			return;
		}
		int uNo = userService.getuNo(req); 
		int chNo = userService.getChNo(req);
		RefundPoint refundPoint = userService.view(uNo,chNo);
		int curPoint  = userService.getMypagePoint(uNo); //현재 포인트 가져오기
		req.setAttribute("refundPoint", refundPoint);
		req.setAttribute("curPoint", curPoint);
		req.getRequestDispatcher("/WEB-INF/views/userUsePoint/view.jsp")
			.forward(req, resp);
	}
}
