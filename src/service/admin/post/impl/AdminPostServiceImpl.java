
package service.admin.post.impl;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import common.JDBCTemplate;
import dao.admin.post.face.AdminPostDao;
import dao.admin.post.impl.AdminPostDaoImpl;
import dto.AdminPost;
import dto.Notice;
import service.admin.post.face.AdminPostService;
import util.Paging;

public class AdminPostServiceImpl implements AdminPostService {
	
	private AdminPostDao adminPostDao = new AdminPostDaoImpl();

	@Override
	public List<AdminPost> getList() {
		return adminPostDao.selectAll(JDBCTemplate.getConnection());
	}

	@Override
	public List<AdminPost> getList(Paging paging) {
		return adminPostDao.selectAll(JDBCTemplate.getConnection(), paging);
	}

	@Override
	public Paging getPaging(HttpServletRequest req) {
		
		String param = req.getParameter("curPage");
		int curPage = 0;
		if(param != null && !"".equals(param)) {
			curPage = Integer.parseInt(param);
		}
		
		int totalCount =  adminPostDao.selectCntAll(JDBCTemplate.getConnection());
		
		Paging paging = new Paging(totalCount, curPage);
		
		return paging;
	}

	@Override
	public AdminPost getP_no(HttpServletRequest req) {
		
		AdminPost p_no = new AdminPost();
		
		String param = req.getParameter("p_no");
		if(param!=null && !"".equals(param)) {
			p_no.setP_no(Integer.parseInt(param));
		}
		
		return p_no;
	}

	@Override
	public AdminPost view(AdminPost p_no) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		if(adminPostDao.updateP_views(conn, p_no) == 1) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
		
		AdminPost adminPost = adminPostDao.selectPostByP_no(conn, p_no);
		
		return adminPost;
	}

	@Override
	public void delete(AdminPost adminPost) {
		
		Connection conn = JDBCTemplate.getConnection();
		
				
		if( adminPostDao.delete(conn, adminPost) > 0 ) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
		
	}
	
	@Override
	public List<AdminPost> TSearch(Paging paging, String keyword) {
		// TODO Auto-generated method stub
		Connection conn = JDBCTemplate.getConnection();
		return adminPostDao.TSearch(conn, paging, keyword);
		
	}
	
	@Override
	public List<AdminPost> CSearch(Paging paging, String keyword) {
		// TODO Auto-generated method stub
		Connection conn = JDBCTemplate.getConnection();
		return adminPostDao.CSearch(conn,paging,keyword);
		
	}

		
	}


