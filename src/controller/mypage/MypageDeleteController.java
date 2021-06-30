package controller.mypage;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dto.Member;
import dto.Mypage;
import service.member.face.MypageService;
import service.member.impl.MypageServiceImpl;

@WebServlet("/mypage/delete")
public class MypageDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// memberService 공용 객체
	private MypageService mypageService = new MypageServiceImpl();
	Member member= null;
	Mypage mypage = null;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(); // 세션 객체 생성
		int uNo = (Integer)session.getAttribute("u_no"); // 현재 로그인된 유저아이디를 가져온다.	
		
		// 유저 정보를 가져오는 메소드
		Member member = mypageService.getUserInfo(uNo); 
		req.setAttribute("memberInfo", member);
		
		// 유저 정보(마이페이지)를 가져오는 메소드
		Mypage mypage = mypageService.getMypageInfo(member.getUno());
		req.setAttribute("mypageInfo", mypage); 
				
		req.getRequestDispatcher("/WEB-INF/views/mypage/myDelete.jsp")
		   .forward(req, resp);	
	}
		
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("/mypage/delete [POST]");
		HttpSession session = req.getSession(); // 세션 객체 생성	
		
		// 전달 파라미터 가져옴
		String paramId = req.getParameter("mId"); 
		String paramPw = req.getParameter("mPW"); 

			int check = mypageService.deleteInfo(req);
			if(check == 1) { 
				session.invalidate(); // 세션 끊음
				System.out.println("정상적으로 유저 탈퇴 완료");
			}else {
				System.out.println("유저 탈퇴 오류 발생");
			}		
			resp.sendRedirect("/"); // 홈으로 보냄

	}
	
}
