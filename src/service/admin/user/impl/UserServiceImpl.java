package service.admin.user.impl;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import common.JDBCTemplate;
import dao.admin.user.face.UserDao;
import dao.admin.user.impl.UserDaoImpl;
import dto.Users;
import service.admin.user.face.UserService;
import util.Paging;

public class UserServiceImpl implements UserService{
	UserDao userDao = new UserDaoImpl();
	@Override
	public List<Users> searchUser(HttpServletRequest req) {
		
		
		//검색 파라미터 저장할 변수 생성
		String isUser = req.getParameter("grade");
		String field = req.getParameter("field");
		String param = req.getParameter("search");
		String page = req.getParameter("curPage"); 
		
		//등급정보
		String grade = "U";
		if(isUser!=null && !isUser.equals("")){ grade = isUser;}
		//아이디 정보
		String fieldVal = "u_id";
		if(field!=null && !field.equals("")) {fieldVal = field;}
		//검색한 단어
		String search = "";
		if(param!=null && !param.equals("")) {search = param;}
		//현재페이지 정보
		int curPage = 0;
		if(page!=null && !page.equals("")) {curPage = Integer.parseInt(page);}
		
		
		
		
		//DB연결하기
		Connection conn = JDBCTemplate.getConnection();
		
		//검색한 총 회원 리스트 갯수 가져오기
		int cntAll = userDao.selectCntAll(conn, grade, fieldVal, search);
		
		//Paging 객체 생성
		Paging paging =  new Paging(cntAll, curPage);
		
		//페이징을 적용한 회원 리스트 가져오기
		List<Users> userlist = userDao.getUserList(conn, grade, fieldVal, search, paging);
		
//		System.out.println(cntAll);
//		for(Users users : userlist) System.out.println(users);
		
		req.setAttribute("paging", paging);
		
		return userlist;
	}
	@Override
	public Users getUserno(HttpServletRequest req) {
		String no = req.getParameter("userno");
		int userno = 0;
		//파라미터 값이 존재할 경우 파싱
		if(no!=null && !no.equals("")) {
			userno = Integer.parseInt(no);
		}
		
		//정보를 담을 객체 생성
		Users user = new Users();
		user.setUserNo(userno);
		
		return user;
	}
	@Override
	public Users getUserInfo(Users users) {
		Connection conn = JDBCTemplate.getConnection();
		
		Users user = userDao.getUserInfo(conn, users);
		
		return user;
	}
	@Override
	public Users selectUser(HttpServletRequest req) {
		String param = req.getParameter("userno");
		String param2 = req.getParameter("grade");
		
		int userno = 0;
		if(param!=null && !param.equals("")) {
			userno = Integer.parseInt(param);
		}
		String grade = "";
		if(param2!=null && !param2.equals("")) {
			grade = param2;
		}
		Users users = new Users();
		users.setUserNo(userno);
		users.setGrade(grade);
		return users;
	}
	@Override
	public int deleteUser(Users users) {
		Connection conn = JDBCTemplate.getConnection();
		int result = userDao.deleteUser(conn, users);
		
		if(result >= 1) {
			JDBCTemplate.commit(conn);
			return 1;
		}else {
			JDBCTemplate.rollback(conn);
			return 0;
		}
	}
	@Override
	public int updateUser(Users users) {
		Connection conn = JDBCTemplate.getConnection();
		int result = userDao.updateUser(conn, users);
		if(result >= 1) {
			JDBCTemplate.commit(conn);
			return result;
		}else {
			JDBCTemplate.rollback(conn);
			return result;
		}
	}
}
