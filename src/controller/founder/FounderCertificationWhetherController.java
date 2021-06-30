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

/**
 * Servlet implementation class FounderCertificationWhetherController
 */
@WebServlet("/founder/certification/whather")
public class FounderCertificationWhetherController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private FounderService founderService = new FounderServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//세션에 있는 chNo을 가져온다
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
		Certification certification = founderService.getCertificationno(req);
		founderService.whatherUpdate(req);
		
		
		
		resp.sendRedirect("/founder/certification/view?ceNo="+certification.getCeNo());
	}
}
