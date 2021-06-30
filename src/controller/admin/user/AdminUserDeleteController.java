package controller.admin.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.Users;
import service.admin.user.face.UserService;
import service.admin.user.impl.UserServiceImpl;


@WebServlet("/admin/user/delete")
public class AdminUserDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService = new UserServiceImpl();
	 @Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		 System.out.println("user/delete - [POST]");
		 //파라미터 저장하기
		 Users users =userService.selectUser(req);
		 //유저 삭제하기
		 int result = userService.deleteUser(users);
		 PrintWriter writer = resp.getWriter();
		 //삭제에 성공할 경우
		 if(result == 1) {
			 writer.print("success");
		 }else {
			 writer.print("failed");
		 }
		 
	}
}
