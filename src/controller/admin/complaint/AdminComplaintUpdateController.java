package controller.admin.complaint;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.AdminComplaint;
import service.admin.complaint.face.AdminComplaintService;
import service.admin.complaint.impl.AdminComplaintServiceImpl;

@WebServlet("/admin/complaint/update")
public class AdminComplaintUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	//ComplaintService 객체 생성
	private AdminComplaintService complaintService = new AdminComplaintServiceImpl();
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			
		//로그인 되어있지 않으면 리다이렉트 
		if( req.getSession().getAttribute("login") == null ) {
			resp.sendRedirect("/member/login");
				
			return;
		}

				
		AdminComplaint comNo = complaintService.getComNo(req); //comNo 가져오기
		
		AdminComplaint updateComplaint = complaintService.view(comNo); //상세보기 결과 조회
		
		String chUid = complaintService.getChUid(updateComplaint); //챌린지개설자 아이디 조회
		
		//public String getChUid(AdminComplaint viewComplaint);
		//public String selectChUidByComNo(Connection conn, AdminComplaint viewComplaint);
		
		
		int cntCompalint = complaintService.count(chUid); //개설자 경고수 조회
		
		//public int count(String chUid);
		//public int getNumber(Connection conn, String chUid);
		
		int chNo = complaintService.getChNo(updateComplaint);
		
		int cntChCaution = complaintService.getChCaution(chNo);
		
		req.setAttribute("updateComplaint", updateComplaint); //조회결과 model값 전달
		
		req.setAttribute("chUid", chUid);  //챌린지개설자 아이디 전달
		
		req.setAttribute("chUcaution",cntCompalint); //개설자 경고횟수 전달
		
		req.setAttribute("cntChCaution", cntChCaution); //챌린지 누적 경고 조회 전달
		
		req.getRequestDispatcher("/WEB-INF/views/adminComplaint/adminComplaintUpdate.jsp").forward(req, resp);
		
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			
		complaintService.update(req); //수정글 등록
		
		resp.sendRedirect("/admin/complaint/list"); //목록으로 리디렉션
	}
	
	
}
