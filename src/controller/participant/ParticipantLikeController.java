package controller.participant;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.Participation;
import service.participant.face.ParticipantService;
import service.participant.impl.ParticipantServiceImpl;

/**
 * Servlet implementation class ParticipantLikeController
 */
@WebServlet("/participant/like")
public class ParticipantLikeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ParticipantService participantService = new ParticipantServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//좋아요가 비활성화 되었을때 false
		//좋아요가 활성화 되어있을때 true
		//로그인이 되어있지 않으면 리다이렉트
		if(req.getSession().getAttribute("login")==null) {
			resp.sendRedirect("/");
			return;
		}
		
		//좋아요 수와 챌린지 번호 반환
		Participation participation = participantService.getLike(req);		
		int paNo=participantService.getParticipationno(req);
		participation.setPaNo(paNo); //paNo 저장 
		participation.setuNo((Integer)req.getSession().getAttribute("u_no")); // uNo저장
		
		
		//좋아요 여부 변경
		participantService.updatePaLike(participation);
		
		//좋아요 증감
		participantService.increaseLike(participation);
		
		//좋아요 증감(마이페이지)
		participantService.increaseLikeMypage(participation);
		
		
		//좋아요 등록 여부와 신고 등록 여부를 가져온다
		Map<String, Boolean> whethers = participantService.getWhethers(req);
		
		req.setAttribute("whethers", whethers);
		
		req.getRequestDispatcher("/WEB-INF/views/participantCertification/like.jsp").forward(req, resp);
		
	}
	
}
