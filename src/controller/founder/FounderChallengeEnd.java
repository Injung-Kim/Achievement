package controller.founder;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.JDBCTemplate;
import service.founder.face.FounderService;
import service.founder.impl.FounderServiceImpl;

/**
 * Servlet implementation class FounderChallengeEnd
 */
@WebServlet("/founder/challenge/end")
public class FounderChallengeEnd extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private FounderService founderService = new FounderServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//인증이 모두 처리되지 않으면 false 반환
		//인증이 모두 처리되면 true가 반환
		boolean certificationCheck=founderService.checkCertification(req);
		//마침 날짜가 이전일 경우 false 반환
		//마침 날짜가 이후일 경우 true가 반환
		String text=null;
		boolean endDateCheck = founderService.checkEndDate(req);
		if(!certificationCheck) {
			text = "모든 인증을 처리해주세요";	
		}
//		테스팅을 위해 잠시 제외
		if(!endDateCheck) {
			text="챌린지 날짜를 다시 확인해주세요.";
		}
		
		//둘 중 하나라도 false일 경우 /*테스팅을 위한 잠시 제외*/
		
		if ((!certificationCheck)  || (!endDateCheck)  ) { //에러 처리
			req.setAttribute("text", text);
			if(req.getSession().getAttribute("u_grade")!=null) {
				if("M".equals(String.valueOf(req.getSession().getAttribute("u_grade")))) {
					req.getRequestDispatcher("/WEB-INF/views/adminFounderCertification/error.jsp").forward(req, resp);
				}else {
					req.getRequestDispatcher("/WEB-INF/views/userFounderCertification/error.jsp").forward(req, resp);
				}
				return;
			}
		}
		//상태 업데이트
		founderService.chStateUpdate(req);
		
		if(req.getSession().getAttribute("u_grade")!=null) { //등급에 따른 분류
			if("M".equals(String.valueOf(req.getSession().getAttribute("u_grade")))) { //관리자
				resp.sendRedirect("/admin/challenge/list");
			}else { //사용자
				resp.sendRedirect("/user/challenge/list");
			}
			return;
		}
		
		
		
		resp.sendRedirect("/user/challenge/list");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
}
