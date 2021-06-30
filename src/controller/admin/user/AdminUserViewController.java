package controller.admin.user;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dto.UserPoint;
import dto.Users;
import service.admin.user.face.UserActiveService;
import service.admin.user.face.UserService;
import service.admin.user.impl.UserActiveServiceImpl;
import service.admin.user.impl.UserServiceImpl;

@WebServlet("/admin/user/view")
public class AdminUserViewController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService = new UserServiceImpl();
	private UserActiveService userActive = new UserActiveServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//System.out.println("/admin/user/view - [GET]");
		//관리자 계정으로만 접근 가능 
		HttpSession session = req.getSession();
		if(session.getAttribute("login") == null || !session.getAttribute("u_grade").equals("M")) {
			resp.sendRedirect("/");
		}else {
		
		//유저번호를 받아 dto 저장하기
		Users users = userService.getUserno(req);
		
		//유저테이블 상세정보 조회
		Users userinfo = userService.getUserInfo(users);
		
		//활동정보 조회
		Map cntActive = userActive.getCntpost(userinfo);
		//System.out.println(cntActive);
		
		//결제정보 조회
		UserPoint cntPayment = userActive.getCntPoint(userinfo);
		
		//현재 참여중인 챌린지 조회
		String currentChallenge = userActive.getChallenge(userinfo);
		
		//상세정보
		req.setAttribute("userinfo", userinfo);
		//활동정보
		req.setAttribute("activeinfo", cntActive);
		//결제정보
		req.setAttribute("cntPayment", cntPayment);
		//참여중인 챌린지
		req.setAttribute("currChallenge", currentChallenge);
		
		req.getRequestDispatcher("/WEB-INF/views/admin/userView.jsp").forward(req, resp);
		}
	}
}
