
package controller.admin.comment;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.AdminComment;
import service.admin.comment.face.AdminCommentService;
import service.admin.comment.impl.AdminCommentServiceImpl;


@WebServlet("/admin/commentdelete")
public class AdminCommentDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private AdminCommentService adminCommentService = new AdminCommentServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if(req.getSession().getAttribute("login") == null) {
			 resp.sendRedirect("/member/login");
			 
			 return;
		 }
		
		AdminComment adminComment = adminCommentService.getC_no(req);
		
		adminCommentService.delete(adminComment);
		
		resp.sendRedirect("/admin/commentlist");
	}

}
