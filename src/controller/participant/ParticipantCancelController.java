package controller.participant;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.Payback;
import service.participant.face.ParticipantService;
import service.participant.impl.ParticipantServiceImpl;

/**
 * Servlet implementation class ParticipantCancelController
 */
@WebServlet("/participant/cancel")
public class ParticipantCancelController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ParticipantService participantService = new ParticipantServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//로그인이 되어있지 않으면 리다이렉트
		if(req.getSession().getAttribute("login")==null) {
			resp.sendRedirect("/");
			return;
		}
		//uNo와 chNo을 가져온다
		Map<String, Integer> paMap = participantService.getChNoUno(req);
		
		
		//환불을 위한 토큰 반환
		String token=participantService.refundsToken();
		//환급자을 반환
		Payback payback = participantService.getPayback(paMap);
		//최종 환급
		participantService.payback(payback, token);
		System.out.println(payback);
		//환급 정보 테이블에 저장
		participantService.paybackInsert(payback);
		
		//partipation정보 삭제
		int paNo=participantService.getParticipationno(req);
		participantService.participationDelete(paNo);
		
		if(req.getSession().getAttribute("u_grade")!=null) { //등급에 따른 분류
			if("M".equals(String.valueOf(req.getSession().getAttribute("u_grade")))) { //관리자
				resp.sendRedirect("/user/challenge/list");
			}else { //사용자
				resp.sendRedirect("/admin/challenge/list");
			}
			return;
		}
		resp.sendRedirect("/");
		
	}
}
