package controller.admin.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dto.Users;
import service.admin.user.face.UserService;
import service.admin.user.impl.UserServiceImpl;

/**
 * Servlet implementation class AdminUserInsertController
 */
@WebServlet("/admin/update")
public class AdminUserUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService = new UserServiceImpl();
       @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	  HttpSession session = req.getSession();
    	  //관리자 계정 외 진입금지
    	  if(!session.getAttribute("u_grade").equals("M")) { 
    		  resp.sendRedirect("/");
    	  //최고 관리자 계정만 관리자 추가 페이지 진입가능
    	  }else if(!session.getAttribute("u_id").equals("manager1") && session.getAttribute("u_grade").equals("M")) {
    		  resp.sendRedirect("/admin/user/list");
    	  }else {
    		 // req.getRequestDispatcher("/WEB-INF/views/admin/userUpdate.jsp").forward(req, resp);
    	  }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	//System.out.println("/admin/update - [POST]");
    	//파라미터 저장하기
    	Users users = userService.selectUser(req);
    	//조회한 회원 등급 전환하기
    	int result = userService.updateUser(users);
    	PrintWriter writer = resp.getWriter();
    	//성공여부 뷰 전송하기
    	if(result >= 1) {
    		writer.print("success");
    	}else {
    		writer.print("failed");
    	}
    	
    }
}
