package controller.founder;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dto.Certification;
import service.founder.face.FounderService;
import service.founder.impl.FounderServiceImpl;
import service.participant.face.ParticipantService;
import service.participant.impl.ParticipantServiceImpl;

/**
 * Servlet implementation class FounderCertificationViewController
 */
@WebServlet("/founder/certification/view")
public class FounderCertificationViewController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private FounderService founderService = new FounderServiceImpl();
	private ParticipantService participantService = new ParticipantServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int chNo = founderService.getChallengeno(req);
		Map<String, Integer> uNo = founderService.getuId(chNo);
		HttpSession session = req.getSession();
		
		
		//매니저 혹은 번호가 같지 않을때 
		if(!(uNo.get("uNo")==(Integer)session.getAttribute("u_no") || "M".equals(String.valueOf(session.getAttribute("u_grade"))))) { //번호가 같지 않을떄(개설자가 아닐때)
			String text = "개설자 혹인 매니저만 가능합니다.";
			req.setAttribute("text", text);		
			req.getRequestDispatcher("/WEB-INF/views/userChallenge/error.jsp")
						.forward(req, resp);
					return;
		}
		String title = participantService.getTitle(chNo); //chNo을 통한 제목 반환
		String chWay = participantService.getChway(chNo); //chNo을 통한 인증 방법 반환
		
		//인증 번호 추출 하기
		Certification certification = founderService.getCertificationno(req);
		
		certification = founderService.certificationView(certification);
		
		req.setAttribute("certification", certification);
		req.setAttribute("title", title);
		req.setAttribute("chWay", chWay);
		
		//등급에 따라 페이지가 다르게 간다
		if(session.getAttribute("u_grade")!=null) {
			if("M".equals(String.valueOf(session.getAttribute("u_grade")))) {
				req.getRequestDispatcher("/WEB-INF/views/adminFounderCertification/view.jsp").forward(req, resp);
			}else {
				req.getRequestDispatcher("/WEB-INF/views/userFounderCertification/view.jsp").forward(req, resp);
			}
			return;
		}
		
		resp.sendRedirect("/");
	}
}
