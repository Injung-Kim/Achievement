package controller.user.challenge;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dto.Challenge;
import service.user.challenge.face.UserChallengeService;
import service.user.challenge.impl.UserChallengeServiceImpl;

/**
 * Servlet implementation class UserChallengeUpdateController
 */
@WebServlet("/user/challenge/update")
public class UserChallengeUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserChallengeService userChallengeService = new UserChallengeServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//로그인이 되어있지 않으면 리다이렉트
		if(req.getSession().getAttribute("login")==null) {
			resp.sendRedirect("/");
			return;
		}
		
		//잘 못된 접근 방식
		if(req.getParameter("chState")==null) {
			String text = "잘 못된 접근 방식입니다.";
			req.setAttribute("text", text);		
			req.getRequestDispatcher("/WEB-INF/views/userChallenge/error.jsp")
			.forward(req, resp);
			return;
			//대기 이외에 수정 및 삭제가 있을시
		}else {
			if(!"W".equals(req.getParameter("chState"))) {
				String text = "잘 못된 접근 방식입니다.";
				req.setAttribute("text", text);		
				req.getRequestDispatcher("/WEB-INF/views/userChallenge/error.jsp")
				.forward(req, resp);
				return;
			}

		}
		//Challenge 가 포함된 객체
		Challenge challenge=userChallengeService.getChallengeno(req);
		//상세보기 결과 조회
		challenge = userChallengeService.view(challenge);
		
		
		//개설자와 인증주기 카테고리 조회
		Map<String, String> result = userChallengeService.getNameTitle(challenge);
		
		
		//로그인이 되어있지 않으면 리다이렉트
		if(req.getSession().getAttribute("login")==null) {
			resp.sendRedirect("/");
			return;
		}
		
		//세션 객체 받아오기
		HttpSession session = req.getSession();

		String name = userChallengeService.getName((String)session.getAttribute("u_id")); //개설자 이름 가져오기
		List<String> category = userChallengeService.getCategory(); //카테고리 목록
		List<String> cycle =  userChallengeService.getCycle(); //인증 주기 목록


		//조회결과 MODEL값 전달
		req.setAttribute("updateChallenge", challenge);
		req.setAttribute("result", result);
		
		req.setAttribute("name", name);
		req.setAttribute("category", category);
		req.setAttribute("cycle", cycle);
		
	
		req.getRequestDispatcher("/WEB-INF/views/userChallenge/update.jsp")
			.forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		userChallengeService.update(req);
		
		resp.sendRedirect("/user/challenge/list");
	}
}
