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






@WebServlet("/member/input")
public class MemberInputController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private JoinService joinService = new JoinServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		req.getRequestDispatcher("/WEB-INF/views/join/input.jsp")
		     .forward(req, resp);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		System.out.println("button start");
		
		System.out.println(req.getParameter("uid"));
		
		//전달파라미터 얻어오기
		Member mem = joinService.getJoinMember(req);
		
		
		System.out.println(mem);
		//회원가입 확인하는 절차
		boolean join = joinService.join(mem);
		
		//메인페이지로 리다이렉트
		resp.sendRedirect("/main");
	}
	
}
