package service.admin.complaint.impl;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import common.JDBCTemplate;
import dao.admin.complaint.face.AdminComplaintDao;
import dao.admin.complaint.impl.AdminComplaintDaoImpl;
import dto.AdminComplaint;
import dto.Challenge;
import dto.Users;
import service.admin.complaint.face.AdminComplaintService;
import util.AdminComplaintPaging;
import util.Paging;

public class AdminComplaintServiceImpl implements AdminComplaintService {

	//ComplaintDao 객체 생성
	private AdminComplaintDao complaintDao = new AdminComplaintDaoImpl();
	
	
	//-------------신고 목록--------------
		@Override
		public List<AdminComplaint> getList() {
			
			//신고글 전체 조회 결과 처리
			return complaintDao.selectAll(JDBCTemplate.getConnection());
		}
	
	
	//-------------신고 목록(페이징 적용)--------------
	@Override
	public List<AdminComplaint> getList(AdminComplaintPaging complaintPaging) {
		
		//신고글 전체 조회결과 처리(페이징)
		return complaintDao.selectAll(JDBCTemplate.getConnection(), complaintPaging);
	}
	
	
	//-----------------페이징 처리-----------------
	@Override
	public AdminComplaintPaging getComplaintPaging(HttpServletRequest req) {
		
		//전달파라미터 curPage 파싱
		String param = req.getParameter("curPage");
		int curPage = 0;
		if(param != null && !"".equals(param)) {
			curPage = Integer.parseInt(param);
		}
		
		//Complaint 테이블에 등록된 총 신고글 수 조회
		int totalCount = complaintDao.selectCountAll(JDBCTemplate.getConnection());
		
		//Paging객체 생성
		AdminComplaintPaging complaintPaging = new AdminComplaintPaging(totalCount, curPage);
		
		return complaintPaging;
	}
	
	
	//-------------신고 리스트 검색------------------
		@Override
		public List<AdminComplaint> searchList(HttpServletRequest req) {
			Connection conn =  JDBCTemplate.getConnection();//db연결 객체 생성
			
			String param1 = req.getParameter("searchType"); //검색조건
			String param2 = req.getParameter("searchKeyword"); //검색어
			String param3 = req.getParameter("curPage"); //현재 페이지
			
			String search_type = "ch_title";
			if(param1!=null && !param1.equals("")) { search_type = param1;}
			String search_keyword = "";
			if(param2!=null && !param2.equals("")) { search_keyword = param2;}
			int curPage = 0;
			if(param3!=null && !param3.equals("")) { curPage = Integer.parseInt(param3);}
			
			// 조회한 리스트 갯수 
			int totalCount = complaintDao.getCntList(conn, search_type, search_keyword);
			int listCount = 10; //페이지당 보여질 리스트 개수
			
			// 페이징 객체 생성
			Paging paging = new Paging(totalCount, curPage, listCount);
			
			// 페이징 적용 - 검색어 리스트 조회
			List<AdminComplaint> list = complaintDao.getSearchComplaints(conn, search_type,search_keyword,paging);
			
			//페이징 정보 전달
			req.setAttribute("SearchPaging", paging);
			return list;
		}
		
		
	//-------------전달파라미터 - comNo--------------
	@Override
	public AdminComplaint getComNo(HttpServletRequest req) {
		
		//comNo를 저장할 객체 생성
		AdminComplaint comNo = new AdminComplaint();
		
		//comNo 전달파라미터 검증
		String param = req.getParameter("comNo");
		if(param!=null && !"".equals(param)) {
		
			comNo.setComNo(Integer.parseInt(param)); //comNo 전달파라미터 추출
		}
		
		return comNo; //결과 객체 반환
	}

	
	//-------------신고 상세조회--------------
	@Override
	public AdminComplaint view(AdminComplaint comNo) {
		Connection conn = JDBCTemplate.getConnection();
		
		AdminComplaint complaint = complaintDao.selectComplaintByComNo(conn, comNo);
		
		return complaint;
	}
	
	
	//-----------챌린지개설자 아이디 조회-------
	@Override
	public String getChUid(AdminComplaint viewComplaint) {
		
		return complaintDao.selectChUidByComNo(JDBCTemplate.getConnection(), viewComplaint);
		
	}
	
	
	//------------개설자가 받은 경고 수 조회-----
	@Override
	public int count(String chUid) {
		
		Connection conn = JDBCTemplate.getConnection();
			
		//chUid로 챌린지 개설자가 현재까지 받은 신고 수 조회하기
		int cntCom = complaintDao.getNumber(conn, chUid);
			
		return cntCom;
	}
			
	
	//-------------신고 수정--------------
	@Override
	public void update(HttpServletRequest req) {
		
		AdminComplaint complaint = null;
		complaint = new AdminComplaint();
		
		complaint.setComAdminContent(req.getParameter("comAdminContent"));
		complaint.setComManage(req.getParameter("comManage"));
		complaint.setComNo(Integer.valueOf(req.getParameter("comNo")));
		
		Connection conn = JDBCTemplate.getConnection();
			
		if(complaint != null) {
			if(complaintDao.update(conn, complaint) > 0) {
				JDBCTemplate.commit(conn);
			
			} else {
				JDBCTemplate.rollback(conn);
			}
		}
	}
	
	
	//-------------신고 삭제--------------
	@Override
	public void delete(AdminComplaint comNo) {
		Connection conn = JDBCTemplate.getConnection();
		
		
		if(complaintDao.delete(conn, comNo) > 0) {
			JDBCTemplate.commit(conn);
			
		} else {
			JDBCTemplate.rollback(conn);
		}
	}
	

	//--------------신고 등록--------------
	@Override
	public void insert(HttpServletRequest req) {
		
		AdminComplaint complaint = new AdminComplaint();
	
		complaint.setComUno(Integer.valueOf(req.getParameter("uNo")));
		complaint.setChNo(Integer.valueOf(req.getParameter("chNo")));
		complaint.setComContent(req.getParameter("comContent"));
		complaint.setComAdminContent(req.getParameter("comAdminContent"));
		complaint.setComManage(req.getParameter("comManage"));
		//System.out.println("complaint : "+complaint);
		
		Connection conn = JDBCTemplate.getConnection();
			
	
			if(complaintDao.insert(conn, complaint) > 0) {
				JDBCTemplate.commit(conn);
			
			} else {
				JDBCTemplate.rollback(conn);
			}
	}
	
	
	
	//-----------챌린지 및 유저 경고횟수 증가---------
	@Override
	public void addChCaution(HttpServletRequest req) {
		int comNo=Integer.parseInt(req.getParameter("comNo")); //경고 번호
		int chNo = Integer.parseInt(req.getParameter("ch_no"));
		
		Connection conn = JDBCTemplate.getConnection();
			
		if(complaintDao.upChallengeCaution(conn,  chNo) > 0) {
			JDBCTemplate.commit(conn);
			
		} else {
				JDBCTemplate.rollback(conn);
		}
		
		if(complaintDao.updateComplaint(conn, comNo) >0){
			JDBCTemplate.commit(conn);
			
		} else {
			JDBCTemplate.rollback(conn);
		}
		
		if(complaintDao.updateUsersCaution(conn, chNo) >0){
			JDBCTemplate.commit(conn);
			
		} else {
			JDBCTemplate.rollback(conn);
		}
	
	}
	//-----------챌린지 및 유저 경고횟수 증가---------
		@Override
		public void removeChCaution(HttpServletRequest req) {
			int comNo=Integer.parseInt(req.getParameter("comNo")); //경고 번호
			int chNo = Integer.parseInt(req.getParameter("ch_no"));
			String comManage = req.getParameter("comManage");
			Connection conn = JDBCTemplate.getConnection();
			int caution =0;
			
			if("Y".equals(comManage)) {
				caution = -1;
			}
			else {
				caution = 0;
			}

			if(complaintDao.downChallengeCaution(conn,  chNo, caution) > 0) {
				JDBCTemplate.commit(conn);
				
			} else {
					JDBCTemplate.rollback(conn);
			}
			
			if(complaintDao.downUsersCaution(conn, chNo, caution) >0){
				JDBCTemplate.commit(conn);
				
			} else {
				JDBCTemplate.rollback(conn);
			}
		
		}
	
	
	@Override
	public int getChNo(AdminComplaint viewComplaint) {
		
		return complaintDao.selectChNoByComNo(JDBCTemplate.getConnection(), viewComplaint);
	}
	
	@Override
	public int getChCaution(int chNo) {
		return complaintDao.selectChByCaution(JDBCTemplate.getConnection(), chNo);
	}
	
}

