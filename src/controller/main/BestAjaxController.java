package controller.main;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.Challenge;
import dto.Participation;
import dto.Users;
import service.mainajax.face.AjaxMainService;
import service.mainajax.impl.AjaxMainServiceImpl;

/**
 * Servlet implementation class BestAjaxController
 */
@WebServlet("/best/ajax")
public class BestAjaxController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AjaxMainService service = new AjaxMainServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//참여자가 100명 이상인 챌린지 정보 조회
		List<Object> challengeList = service.getBestChallenge();
		//챌린지 개설자 등급 정보 조회
		List<Users> userList = service.getGrade(challengeList); 
		//조회값 전달하기
		req.setAttribute("challengeList", challengeList);
		req.setAttribute("userList", userList);
		
	
		req.getRequestDispatcher("/WEB-INF/views/mainAjax/best.jsp")
			.forward(req, resp);
	}
}
