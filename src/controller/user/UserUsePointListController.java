package controller.user;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.RefundPoint;
import service.user.face.UserService;
import service.user.impl.UserServiceImpl;
import util.Paging;

/**
 * Servlet implementation class UserUsePointList
 */
@WebServlet("/user/use/point/list")
public class UserUsePointListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserService userService = new UserServiceImpl();
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Paging paging =null;
		int uNo =0;
		//로그인이 안 되어 있을시 메인으로
		if(req.getSession().getAttribute("login")==null) {
			resp.sendRedirect("/");
			return;
		}
		
		paging = userService.getPaging(req);
		if(req.getSession().getAttribute("u_no")!=null) {
			uNo = (Integer)req.getSession().getAttribute("u_no");
		}
		
		List<RefundPoint> list = userService.getList(paging, uNo);
		
		//페이징 객체를 MODEL값으로 전달
		req.setAttribute("paging", paging);

		//조회결과 MODEL값 전달
		req.setAttribute("list", list);
		
		
		req.getRequestDispatcher("/WEB-INF/views/userUsePoint/list.jsp")
			.forward(req, resp);
		
	}
}
