
package controller.admin.post;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.AdminPost;
import service.admin.post.face.AdminPostService;
import service.admin.post.impl.AdminPostServiceImpl;


@WebServlet("/admin/postview")
public class AdminPostViewController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private AdminPostService adminPostSerivce = new AdminPostServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		AdminPost p_no = adminPostSerivce.getP_no(req);
		
		AdminPost viewPost = adminPostSerivce.view(p_no);
		
		req.setAttribute("viewPost", viewPost);
		
		req.getRequestDispatcher("/WEB-INF/views/adminPost/view.jsp").forward(req, resp);
		
	}
	

}

