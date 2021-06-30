package controller.user.challenge;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.Challenge;
import dto.Participation;
import dto.Payback;
import service.user.challenge.face.UserChallengeService;
import service.user.challenge.impl.UserChallengeServiceImpl;

/**
 * Servlet implementation class UserChallengeDeleteController
 */
@WebServlet("/user/challenge/delete")
public class UserChallengeDeleteController extends HttpServlet {
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
		
		//챌린지 번호조회
		Challenge challenge = userChallengeService.getChallengeno(req);
		//환급자 목록
		List<Payback> paybList = userChallengeService.getPaybList(challenge);
		
		//토큰 발급
		String token=userChallengeService.refundsToken();
		//삭제에 따른 참여자들 환급
		userChallengeService.payback(paybList, token);
		
		//환급완료후 테이블에 저장
		userChallengeService.paybackInsert(paybList);
		//챌린지 삭제
		userChallengeService.delete(req);
		
		
		
		
		resp.sendRedirect("/user/challenge/list");
	}
	
}
