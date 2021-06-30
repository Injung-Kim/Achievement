package controller.board;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dto.Comment;
import service.board.face.PostService;
import service.board.impl.PostServiceImpl;

/**
 * Servlet implementation class CicInsertController
 */
@WebServlet("/comment/cic")
public class CicInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PostService postService = new PostServiceImpl(); 
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Comment cmt = new Comment();
		
		int u_no = (int)session.getAttribute("u_no");
		String c_content = request.getParameter("content");
		int fk_c_no = Integer.parseInt(request.getParameter("cno"));

		
		int pno = Integer.parseInt(request.getParameter("pno"));
		
		cmt.setFk_Cno(fk_c_no);
		cmt.setC_content(c_content);
		cmt.setUno(u_no);
		cmt.setPno(pno);
		
		postService.InsertCIC(cmt);
		
		response.encodeRedirectURL("/WEB-INF/views/post/comment.jsp");
	}

}
