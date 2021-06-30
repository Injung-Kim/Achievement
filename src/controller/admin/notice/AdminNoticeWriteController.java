
package controller.admin.notice;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.admin.notice.face.AdminNoticeService;
import service.admin.notice.impl.AdminNoticeServiceImpl;


@WebServlet("/admin/writenot")
public class AdminNoticeWriteController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private AdminNoticeService adminNoticeService = new AdminNoticeServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		 if(req.getSession().getAttribute("login") == null) {
			 resp.sendRedirect("/member/login");
			 
			 return;
		 }
		 
		 req.getRequestDispatcher("/WEB-INF/views/notice/write.jsp")
		 .forward(req, resp);

	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		adminNoticeService.write(req);
		
		resp.sendRedirect("/admin/notice");
	}

}

