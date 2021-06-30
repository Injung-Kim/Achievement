package controller.board;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.board.face.BoardService;
import service.board.face.PostService;
import service.board.impl.BoardServiceImpl;
import service.board.impl.PostServiceImpl;

/**
 * Servlet implementation class PostInsertController
 */
@WebServlet("/board/write")
public class PostInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private PostService postService = new PostServiceImpl();   
    private BoardService boardService = new BoardServiceImpl();
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(request.getSession().getAttribute("u_id") == null) {
			request.setAttribute("u_nick", "");
		}else {
			String u_nick = postService.getNick(request.getSession());
		
			request.setAttribute("u_nick", u_nick);
		}
		
		String[] boardData = boardService.BoardData(Integer.parseInt(request.getParameter("mid")));
		
		request.setAttribute("boardData", boardData);
		
		request.getRequestDispatcher("/WEB-INF/views/post/postWrite.jsp")
			.forward(request, response);
	}

	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int bno = postService.write(request);
		response.sendRedirect("/board/" + bno);
	}

}
