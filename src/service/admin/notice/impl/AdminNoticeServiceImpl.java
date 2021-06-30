
package service.admin.notice.impl;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import common.JDBCTemplate;
import dao.admin.notice.face.AdminNoticeDao;
import dao.admin.notice.impl.AdminNoticeDaoImpl;
import dto.Notice;
import service.admin.notice.face.AdminNoticeService;
import util.Paging;

public class AdminNoticeServiceImpl implements AdminNoticeService {
	
	private AdminNoticeDao adminNoticeDao = new AdminNoticeDaoImpl();

	@Override
	public List<Notice> getList() {
		return adminNoticeDao.selectAll(JDBCTemplate.getConnection());
	}

	@Override
	public List<Notice> getList(Paging paging) {
		return adminNoticeDao.selectAll(JDBCTemplate.getConnection(), paging);
	}

	@Override
	public Paging getPaging(HttpServletRequest req) {
		
		String param = req.getParameter("curPage");
		int curPage = 0;
		if(param != null && !"".equals(param)) {
			curPage = Integer.parseInt(param);
		}
		
		int totalCount = adminNoticeDao.selectCntAll(JDBCTemplate.getConnection());
		
		Paging paging = new Paging(totalCount,curPage);
		
		return paging;
		
	}

	@Override
	public Notice getN_no(HttpServletRequest req) {
		
		Notice n_no = new Notice();
		
		String param = req.getParameter("n_no");
		if(param!=null && !"".equals(param)) {
			
			n_no.setN_no(Integer.parseInt(param));
		}
		return n_no;
	}

	@Override
	public Notice view(Notice n_no) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		if(adminNoticeDao.updateN_views(conn, n_no) == 1) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
		
		Notice notice = adminNoticeDao.selectNoticeByN_no(conn, n_no);
		
		return notice;
	}

	@Override
	public void write(HttpServletRequest req) {
		
		Notice notice = new Notice();
		
		notice.setN_title(req.getParameter("n_title"));
		notice.setN_content(req.getParameter("n_content"));
		
		notice.setU_no((int)req.getSession().getAttribute("u_no"));
		
		if(notice.getN_title()==null || "".equals(notice.getN_title())) {
			notice.setN_title("(제목없음)");
		}
		
		Connection conn = JDBCTemplate.getConnection();
		if(adminNoticeDao.insert(conn, notice) >0 ) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
		
	}

	@Override
	public void update(HttpServletRequest req) {
		
		Notice notice = null;
		
		notice = new Notice();
		
		notice.setN_no(Integer.parseInt(req.getParameter("n_no")));
		notice.setN_title(req.getParameter("n_title"));
		notice.setU_no((int)req.getSession().getAttribute("u_no"));
		notice.setN_content(req.getParameter("n_content"));
		System.out.println( notice );
		Connection conn = JDBCTemplate.getConnection();
		
		if(notice != null) {
			if( adminNoticeDao.update(conn, notice) > 0 ) {
				JDBCTemplate.commit(conn);
			} else {
				JDBCTemplate.rollback(conn);
			}
		}
		
		
		
	}

	@Override
	public void delte(Notice notice) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		if(adminNoticeDao.delete(conn, notice) > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
		
	}

	@Override
	public List<Notice> TSearch(Paging paging, String keyword) {
		// TODO Auto-generated method stub
		Connection conn = JDBCTemplate.getConnection();
		return adminNoticeDao.TSearch(conn, paging, keyword);
		
	}
	
	@Override
	public List<Notice> CSearch(Paging paging, String keyword) {
		// TODO Auto-generated method stub
		Connection conn = JDBCTemplate.getConnection();
		return adminNoticeDao.CSearch(conn,paging,keyword);
		
	}

	
		
	

}

