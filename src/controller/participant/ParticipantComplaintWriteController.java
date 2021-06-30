package controller.participant;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.Complaint;
import service.participant.face.ParticipantService;
import service.participant.impl.ParticipantServiceImpl;

/**
 * Servlet implementation class ParticipantComplaintWriteController
 */
@WebServlet("/participant/complaint/write")
public class ParticipantComplaintWriteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ParticipantService participantService = new ParticipantServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//chNo을 가져온다
		int chNo = participantService.getChallengeno(req); 
		//제목 가져오기
		String title = participantService.getTitle(chNo);
		
		req.setAttribute("title", title);
		
		req.getRequestDispatcher("/WEB-INF/views/participantComplaint/write.jsp")
			.forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//신고 가져오기
		Complaint complaint = participantService.getComplaint(req);
		
		participantService.insertComplaint(complaint);
		
		int chNo = participantService.getChallengeno(req);
		
		resp.sendRedirect("/participant/certification/list?chNo="+chNo);
	}

}
