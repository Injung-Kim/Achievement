package controller.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.Comment;
import service.board.face.PostService;
import service.board.impl.PostServiceImpl;

/**
 * Servlet implementation class CommentListController
 */
@WebServlet("/comment")
public class CommentListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PostService postService = new PostServiceImpl();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<Comment> comment = postService.SelectComment(request);
		request.setAttribute("cList", comment);
		
		request.getRequestDispatcher("/WEB-INF/views/post/comment.jsp")
			.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
