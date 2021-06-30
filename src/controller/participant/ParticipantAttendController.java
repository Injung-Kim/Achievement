package controller.participant;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.Challenge;
import dto.Member;
import service.member.face.MemberService;
import service.member.impl.MemberServiceImpl;
import service.user.challenge.face.UserChallengeService;
import service.user.challenge.impl.UserChallengeServiceImpl;

/**
 * Servlet implementation class UserChallengeAttendController
 */
@WebServlet("/participant/attend")
public class ParticipantAttendController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private MemberService memberService = new MemberServiceImpl();
	private UserChallengeService userChallengeService = new UserChallengeServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Member member = null;
		Challenge challenge = userChallengeService.getChallengeno(req);
		challenge = userChallengeService.view(challenge);
		member = memberService.infoAll(req);
		
		req.setAttribute("member", member);
		req.setAttribute("challenge", challenge);
		
		req.getRequestDispatcher("/WEB-INF/views/payment/payment.jsp")
			.forward(req, resp);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
}
