
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



@WebServlet("/admin/commentview")
public class AdminCommentViewController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private AdminCommentService adminCommentService = new AdminCommentServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		AdminComment c_no = adminCommentService.getC_no(req);
		
		AdminComment viewComment = adminCommentService.view(c_no);
		
		req.setAttribute("viewComment", viewComment);
						
		req.getRequestDispatcher("/WEB-INF/views/adminComment/view.jsp").forward(req, resp);
	
}
}
