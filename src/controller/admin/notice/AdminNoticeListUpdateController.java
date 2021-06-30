
package controller.admin.notice;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.Notice;
import service.admin.notice.face.AdminNoticeService;
import service.admin.notice.impl.AdminNoticeServiceImpl;


@WebServlet("/admin/updatenot")
public class AdminNoticeListUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private AdminNoticeService adminNoticeService = new AdminNoticeServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if(req.getSession().getAttribute("login") == null) {
			 resp.sendRedirect("/member/login");
			 
			 return;
		 }
		
		Notice n_no = adminNoticeService.getN_no(req);
		
		Notice updateNotice = adminNoticeService.view(n_no);
		
		req.setAttribute("updateNotice", updateNotice);
		
		req.getRequestDispatcher("/WEB-INF/views/notice/update.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		adminNoticeService.update(req);
		
		resp.sendRedirect("/admin/notice");
	}
	

}

