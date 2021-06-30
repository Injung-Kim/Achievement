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




@WebServlet("/member/idfind")
public class MemberIdfindController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private JoinService joinService = new JoinServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    
		System.out.println("/member/idfind [GET] ");
		
		
		
		req.getRequestDispatcher("/WEB-INF/views/find/idfind.jsp")
		   .forward(req, resp);
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	    //전달파라미터얻어오기 - 로그인정보
		Member mem = joinService.idFindMember(req);
		
		//아이디찾기 인증
		boolean idfind = joinService.infind(mem);
		
		if(idfind) {
			
			//아이디찾기 사용자 정보 얻어오기
			mem = joinService.idinfo(mem);
			
			//얻어온 아이디 정보 저장하기
			req.setAttribute("idfind", idfind);
			req.setAttribute("userid", mem.getUid());
			req.setAttribute("username", mem.getUsername());
			req.setAttribute("phone", mem.getPhone());
			
			req.getRequestDispatcher("/WEB-INF/views/find/idfindsuccess.jsp")
			.forward(req, resp);
		} else {
			req.getRequestDispatcher("/WEB-INF/views/find/idfindfail.jsp")
			.forward(req, resp);
			
		}
		
		
		
//		if(idfind) {
//			
//			//아이디찾기 사용자 정보 얻어오기
//			mem = memberService.idinfo(mem);
//			
//			//세션정보 저장하기
//			HttpSession session = req.getSession();
//			session.setAttribute("infind", idfind);
//			session.setAttribute("username", mem.getUsername());
//			session.setAttribute("phone", mem.getPhone());
//			
//		}
	    
		//아이디성공페이지로 리다이렉트
//		resp.sendRedirect("/member/idfind/success");
		

		

	}
}
