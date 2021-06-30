package controller.user.challenge;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.Participation;
import service.user.challenge.face.UserChallengeService;
import service.user.challenge.impl.UserChallengeServiceImpl;

/**
 * Servlet implementation class UserChallengeReviewViewController
 */
@WebServlet("/user/challenge/review/view")
public class UserChallengeReviewViewController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserChallengeService userChallengeService = new UserChallengeServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Participation participation = userChallengeService.getPaNo(req);
		
		participation = userChallengeService.getParticipation(participation);
		
		//객체 저장
		req.setAttribute("participation", participation);
		
		req.getRequestDispatcher("/WEB-INF/views/userChallenge/reviewView.jsp")
		.forward(req, resp);
	}
}
