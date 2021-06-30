
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


@WebServlet("/admin/deletenot")
public class ADminNoticeDeleteController extends HttpServlet {
	
	
	private static final long serialVersionUID = 1L;
	
	private AdminNoticeService adminNoticeService = new AdminNoticeServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if(req.getSession().getAttribute("login") == null) {
			 resp.sendRedirect("/member/login");
			 
			 return;
		 }
		
		Notice notice = adminNoticeService.getN_no(req);
		
		adminNoticeService.delte(notice);
		
		resp.sendRedirect("/admin/notice");
	}
			

}
