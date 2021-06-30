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

@WebServlet("/admin/complaint/caution")
public class AdminComplaintCautionController extends HttpServlet {
	private static final long serialVersionUID = 1L;


	//ComplaintService 객체 생성
	private AdminComplaintService complaintService = new AdminComplaintServiceImpl();
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("진입 성공");

		//경고 수
		
		AdminComplaint comNo = complaintService.getComNo(req); //comNo 가져오기
		
		AdminComplaint complaint = complaintService.view(comNo); //상세보기 결과 조회
		
		complaintService.addChCaution(req); //챌린지 경고 수 추가
		
		//complaintService.addChUcaution(req);  //개설자 경고 수 추가
			
		resp.sendRedirect("/admin/complaint/view?comNo="+comNo.getComNo());
		
		}
		
	
}
