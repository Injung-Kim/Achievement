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
import service.user.challenge.face.UserChallengeService;
import service.user.challenge.impl.UserChallengeServiceImpl;
import util.Paging;

/**
 * Servlet implementation class UserChallengeReviewListController
 */
@WebServlet("/user/challenge/review/list")
public class UserChallengeReviewListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserChallengeService userChallengeService = new UserChallengeServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//전달파라미터 얻기 - chno
		Challenge challenge = userChallengeService.getChallengeno(req);
		Paging paging = null;
		//상세보기 결과 조회
		challenge = userChallengeService.view(challenge);
		if(!"N".equals(challenge.getChState())) {
			String text = "아직 종료되지 않은 챌린지입니다.";
			req.setAttribute("text", text);
			req.getRequestDispatcher("/WEB-INF/views/userChallenge/error.jsp")
				.forward(req, resp);
			return;
		}
		//리스트 불러오기
		paging = userChallengeService.getPagingReview(req, challenge);
		List<Participation> reviewList = userChallengeService.getParticipationList(challenge, paging);
		
		//페이징 객체를 MODEL값으로 전달
		req.setAttribute("paging", paging);
		req.setAttribute("chNo", challenge.getChNo());
	
		req.setAttribute("reviewList", reviewList);
		req.getRequestDispatcher("/WEB-INF/views/userChallenge/reviewList.jsp")
		.forward(req, resp);
	}
}
