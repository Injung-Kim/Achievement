package controller.main;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.Challenge;
import dto.Users;
import service.mainajax.face.AjaxMainService;
import service.mainajax.impl.AjaxMainServiceImpl;

/**
 * Servlet implementation class PopularityAjaxController
 */
@WebServlet("/popularity/ajax")
public class PopularityAjaxController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AjaxMainService service = new AjaxMainServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Challenge> popularList = service.getPopularChallenge();
		//챌린지 개설자 등급 정보 조회
		List<Users> userList = service.getpublisher(popularList); 
		//조회값 전달하기
		req.setAttribute("popularList",popularList);
		req.setAttribute("userList", userList);
		req.getRequestDispatcher("/WEB-INF/views/mainAjax/popularity.jsp")
			.forward(req, resp);
	}
}
