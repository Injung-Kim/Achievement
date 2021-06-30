package controller.admin.complaint;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.AdminComplaint;
import service.admin.complaint.face.AdminComplaintService;
import service.admin.complaint.impl.AdminComplaintServiceImpl;

@WebServlet("/admin/complaint/delete")
public class AdminComplaintDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	//ComplaintService 객체 생성
	private AdminComplaintService complaintService = new AdminComplaintServiceImpl();

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		AdminComplaint complaint = complaintService.getComNo(req);  //comNo 가져오기
		
		complaintService.removeChCaution(req); // 신고 감소 및 유지
		
		complaintService.delete(complaint); //해당글 삭제
		
	    resp.sendRedirect("/admin/complaint/list"); //목록으로 리디렉션

	}
}
