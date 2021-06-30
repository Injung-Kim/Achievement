package controller.admin.complaint;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.admin.complaint.face.AdminComplaintService;
import service.admin.complaint.impl.AdminComplaintServiceImpl;

@WebServlet("/admin/complaint/insert")
public class AdminComplaintInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//ComplaintService 객체
	private AdminComplaintService complaintService = new AdminComplaintServiceImpl();
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		
		//로그인 되어있지 않으면 리다이렉트 
		if( req.getSession().getAttribute("login") == null ) {
			resp.sendRedirect("/member/login");
			
			return;
		}
		
		//View 지정
		req.getRequestDispatcher("/WEB-INF/views/adminComplaint/adminComplaintInsert.jsp").forward(req, resp);
	
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		//작성글 등록
		complaintService.insert(req);
		
		//목록으로 리디렉션
		resp.sendRedirect("/admin/complaint/list");

	}
	
}
