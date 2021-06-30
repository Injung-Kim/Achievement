package controller.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.Comment;
import dto.Post;
import service.board.face.BoardService;
import service.board.face.PostService;
import service.board.impl.BoardServiceImpl;
import service.board.impl.PostServiceImpl;

/**
 * Servlet implementation class PostViewController
 */
@WebServlet("/board/view")
public class PostViewController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private PostService postService = new PostServiceImpl();
    private BoardService boardService = new BoardServiceImpl();
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		postService.UpdateViews(request);

		Post post = postService.SelectPost(request);

		String[] boardData = boardService.BoardData(Integer.parseInt(request.getParameter("mid")));
		
		request.setAttribute("boardData", boardData);
		
		request.setAttribute("post", post);
		
		
		request.getRequestDispatcher("/WEB-INF/views/post/post.jsp")
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
