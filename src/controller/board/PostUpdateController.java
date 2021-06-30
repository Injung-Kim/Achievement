package controller.board;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.Post;
import service.board.face.BoardService;
import service.board.face.PostService;
import service.board.impl.BoardServiceImpl;
import service.board.impl.PostServiceImpl;

/**
 * Servlet implementation class PostUpdateController
 */
@WebServlet("/board/update")
public class PostUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private PostService postService = new PostServiceImpl();
    private BoardService boardService = new BoardServiceImpl();
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(request.getSession().getAttribute("u_id") == null) {
			request.setAttribute("u_nick", "");
		}else {
		Post post = postService.SelectPost(request);
		String u_nick = postService.getNick(request.getSession());
		
		request.setAttribute("u_nick", u_nick);
		request.setAttribute("post", post);
		
		String[] boardData = boardService.BoardData(Integer.parseInt(request.getParameter("mid")));
		
		request.setAttribute("boardData", boardData);
		}
		
		request.getRequestDispatcher("/WEB-INF/views/post/postWrite.jsp")
			.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		postService.UpdatePost(request);
		
	}

}