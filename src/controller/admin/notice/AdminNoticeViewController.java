
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


@WebServlet("/admin/view")
public class AdminNoticeViewController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private AdminNoticeService adminNoticeService = new AdminNoticeServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Notice n_no = adminNoticeService.getN_no(req);
		
		Notice viewNotice = adminNoticeService.view(n_no);
		
		req.setAttribute("viewNotice", viewNotice);
		
		req.getRequestDispatcher("/WEB-INF/views/notice/view.jsp").forward(req, resp);
		
	}

}

