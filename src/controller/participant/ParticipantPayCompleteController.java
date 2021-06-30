package controller.participant;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.Participation;
import dto.Payment;
import service.participant.face.ParticipantService;
import service.participant.impl.ParticipantServiceImpl;

/**
 * Servlet implementation class UserChallengePayCompleteController
 */
@WebServlet("/participant/pay/complete")
public class ParticipantPayCompleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ParticipantService participantService = new ParticipantServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Participation participation = null;
		Payment payment = null;
		
		//참가자 저장
		participation = participantService.getParticipation(req);
		participantService.attendWrite(participation);
		
		//결제 내역 저장
		payment = participantService.getPayment(req);
		participantService.paymentWrite(payment);
		
		
		resp.sendRedirect("/user/challenge/view?chNo="+participation.getChNo());
	}
}
