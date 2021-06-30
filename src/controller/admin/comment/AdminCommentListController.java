package controller.admin.comment;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.AdminComment;
import service.admin.comment.face.AdminCommentService;
import service.admin.comment.impl.AdminCommentServiceImpl;
import util.Paging;



@WebServlet("/admin/commentlist")
public class AdminCommentListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public AdminCommentService adminCommentService = new AdminCommentServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String type = req.getParameter("select");
		String keyword = req.getParameter("search");
		Paging paging = adminCommentService.getPaging(req);
		
		List<AdminComment> commentList = adminCommentService.getList(paging);
		
		if(type == null) {
			//공지사항 전체 조회
			commentList = adminCommentService.getList(paging);
		}else if("내용".equals(type)) {
			commentList = adminCommentService.TSearch(paging, keyword);
			
		}else if("아이디 번호".equals(type)) {
			commentList = adminCommentService.CSearch(paging, keyword);
		}
		
		req.setAttribute("paging", paging);
		
		req.setAttribute("commentList", commentList);
		
		req.getRequestDispatcher("/WEB-INF/views/adminComment/list.jsp").forward(req, resp);
		
		
	}

}

