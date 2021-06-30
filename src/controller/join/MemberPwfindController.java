package controller.join;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.Member;
import service.join.face.JoinService;
import service.join.impl.JoinServiceImpl;



@WebServlet("/member/pwfind")
public class MemberPwfindController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private JoinService joinService = new JoinServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		System.out.println("/member/pwfind [GET] 요청완료");
		
		req.getRequestDispatcher("/WEB-INF/views/find/pwfind.jsp")
		    .forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		
		
		
		//전달파라미터얻어오기 -로그인 정보
		Member mem = joinService.pwFindMember(req);
		
		//비밀번호찾기 인증
		boolean pwfind = joinService.pwfind(mem);
		
		if(pwfind) {
			
			//비밀번호 찾기 사용자 정보 얻어오기
			mem = joinService.pwinfo(mem);
			
			req.setAttribute("pwfind", pwfind);
			req.setAttribute("upassword", mem.getUpassword());
			req.setAttribute("username", mem.getUsername());
			req.setAttribute("uid", mem.getUid());
			req.setAttribute("email", mem.getEmail());
			
			req.getRequestDispatcher("/WEB-INF/views/find/pwfindsuccess.jsp")
			    .forward(req, resp);
		}else {

			req.getRequestDispatcher("/WEB-INF/views/find/pwfindfail.jsp")
			    .forward(req, resp);
		
		
		}
		
		
		
	}
}
