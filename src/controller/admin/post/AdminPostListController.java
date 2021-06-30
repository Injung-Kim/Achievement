
package controller.admin.post;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.AdminPost;
import service.admin.post.face.AdminPostService;
import service.admin.post.impl.AdminPostServiceImpl;
import util.Paging;


@WebServlet("/admin/postlist")
public class AdminPostListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private AdminPostService adminPostSerivce = new AdminPostServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String type = req.getParameter("select");
		String keyword = req.getParameter("search");
		Paging paging = adminPostSerivce.getPaging(req);
		
		List<AdminPost> postList = adminPostSerivce.getList(paging);
		
		//검색여부 IF문
				if(type == null) {
					//공지사항 전체 조회
					postList = adminPostSerivce.getList(paging);
				}else if("제목".equals(type)) {
					postList = adminPostSerivce.TSearch(paging, keyword);
					
				}else if("내용".equals(type)) {
					postList = adminPostSerivce.CSearch(paging, keyword);
				}
		
		req.setAttribute("paging", paging);
		
		req.setAttribute("postList", postList);
		
		req.getRequestDispatcher("/WEB-INF/views/adminPost/list.jsp").forward(req, resp);
	}
	
	
	  


}

