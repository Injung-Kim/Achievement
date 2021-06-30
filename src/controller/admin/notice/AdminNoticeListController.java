
package controller.admin.notice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.Notice;
import service.admin.notice.face.AdminNoticeService;
import service.admin.notice.impl.AdminNoticeServiceImpl;
import util.Paging;

@WebServlet("/admin/notice")
public class AdminNoticeListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private AdminNoticeService adminNoticeService = new AdminNoticeServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		Paging paging = adminNoticeService.getPaging(req);
		
		String type = req.getParameter("select");
		String keyword = req.getParameter("search");
		List<Notice> noticeList = new ArrayList<Notice>();
		
		//검색여부 IF문
		if(type == null) {
			//공지사항 전체 조회
			noticeList = adminNoticeService.getList(paging);
		}else if("제목".equals(type)) {
			noticeList = adminNoticeService.TSearch(paging, keyword);
			
		}else if("내용".equals(type)) {
			noticeList = adminNoticeService.CSearch(paging, keyword);
		}
		
		
		
				
		req.setAttribute("paging", paging);
		
		req.setAttribute("noticeList", noticeList);
		
		req.getRequestDispatcher("/WEB-INF/views/notice/list.jsp").forward(req, resp);
	}
	
	

}

