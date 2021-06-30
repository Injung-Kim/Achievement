
package service.admin.comment.impl;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import common.JDBCTemplate;
import dao.admin.comment.face.AdminCommentDao;
import dao.admin.comment.impl.AdminCommentDaoImpl;
import dto.AdminComment;
import dto.Notice;
import service.admin.comment.face.AdminCommentService;
import util.Paging;


public class AdminCommentServiceImpl implements AdminCommentService {
	
	private AdminCommentDao adminCommentDao = new AdminCommentDaoImpl();

	@Override
	public List<AdminComment> getList() {
		
		return adminCommentDao.selectAll(JDBCTemplate.getConnection());
	}

	@Override
	public List<AdminComment> getList(Paging paging) {
		
		return adminCommentDao.selectAll(JDBCTemplate.getConnection(), paging);
	}

	@Override
	public Paging getPaging(HttpServletRequest req) {
		
		
				String param = req.getParameter("curPage");
				int curPage = 0;
				if(param != null && !"".equals(param)) {
					curPage = Integer.parseInt(param);
				}
				
				//Board 테이블의 총 게시글 수를 조회한다
				int totalCount = adminCommentDao.selectCntAll(JDBCTemplate.getConnection());

				//Paging객체 생성
				Paging paging = new Paging(totalCount, curPage);
				
				return paging;
	}

	@Override
	public AdminComment getC_no(HttpServletRequest req) {
		
		AdminComment c_no = new AdminComment();
		
		String param = req.getParameter("c_no");
		if(param!=null && !"".equals(param)) {
			
			//boardno 전달파라미터 추출
			c_no.setC_no( Integer.parseInt(param) );
		}
		
		//결과 객체 반환
		return c_no;
	}

	@Override
	public AdminComment view(AdminComment c_no) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		AdminComment adminComment = adminCommentDao.selectCommentByC_no(conn, c_no);
		
		return adminComment;
	}

	@Override
	public void delete(AdminComment adminComment) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		if(adminCommentDao.delte(conn, adminComment) > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
		
	}
	
	@Override
	public List<AdminComment> TSearch(Paging paging, String keyword) {
		// TODO Auto-generated method stub
		Connection conn = JDBCTemplate.getConnection();
		return adminCommentDao.TSearch(conn, paging, keyword);
		
	}
	
	@Override
	public List<AdminComment> CSearch(Paging paging, String keyword) {
		// TODO Auto-generated method stub
		Connection conn = JDBCTemplate.getConnection();
		return adminCommentDao.CSearch(conn,paging,keyword);
		

	
}
}

