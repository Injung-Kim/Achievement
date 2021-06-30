package controller.admin.complaint;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.AdminComplaint;
import service.admin.complaint.face.AdminComplaintService;
import service.admin.complaint.impl.AdminComplaintServiceImpl;
import util.AdminComplaintPaging;

@WebServlet("/admin/complaint/list")
public class AdminComplaintListController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
		//ComplaintService 객체 생성
		public AdminComplaintService complaintService = new AdminComplaintServiceImpl();
	
		
		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			
			//로그인 되어있지 않으면 리다이렉트 
			if( req.getSession().getAttribute("login") == null ) {
				resp.sendRedirect("/member/login");
					
				return;
			}

			//요청파라미터를 전달하여 Paging객체 생성하기
			AdminComplaintPaging complaintPaging = complaintService.getComplaintPaging(req);
						
			//페이징 적용한 신고글 조회
			List<AdminComplaint> complaintList = complaintService.getList(complaintPaging);
			
			//페이징 객체를 model값으로 전달
			req.setAttribute("complaintPaging", complaintPaging);
			
			//조회결과 model값 전달
			req.setAttribute("complaintList", complaintList);
			
			//검색
			List<AdminComplaint> searchComplaint = complaintService.searchList(req);
			//System.out.println(searchComplaint);
			//검색리스트 결과 model값 전달
			req.setAttribute("searchComplaint", searchComplaint);
		
			
			//View 지정 및 응답
			req.getRequestDispatcher("/WEB-INF/views/adminComplaint/adminComplaintList.jsp").forward(req, resp);
		
		}
	
}
