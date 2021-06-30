package controller.participant;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.Participation;
import service.participant.face.ParticipantService;
import service.participant.impl.ParticipantServiceImpl;

/**
 * Servlet implementation class ParticipantReviewWriteController
 */
@WebServlet("/participant/review/write")
public class ParticipantReviewWriteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ParticipantService participantService = new ParticipantServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//chNo을 가져온다
		int chNo = participantService.getChallengeno(req);
		//추출할 paNo
		int paNo = participantService.getParticipationno(req);
		//제목 가져오기
		String title = participantService.getTitle(chNo);
		
		//리뷰 가져오기
		String review = participantService.getReview(paNo);
		//review가 후기없음으로 등록되어 있거나 null일 경우
		if("후기없음".equals(review) || review==null) {
			review=""; //값을 없애준다
		}
		
		req.setAttribute("review", review);
		req.setAttribute("title", title);
		
		req.getRequestDispatcher("/WEB-INF/views/participantReview/write.jsp")
			.forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//리뷰와 수정할 paNo 추출
		Participation participation = participantService.getReview(req);
		
		participantService.insertReview(participation);
		
		int chNo = participantService.getChallengeno(req);
		
		resp.sendRedirect("/participant/certification/list?chNo="+chNo);
	}
}
