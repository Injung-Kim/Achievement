package service.mainajax.impl;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import common.JDBCTemplate;
import dao.mainajax.face.AjaxDao;
import dao.mainajax.impl.AjaxDaoImpl;
import dto.Challenge;
import dto.Participation;
import dto.Users;
import service.mainajax.face.AjaxMainService;

public class AjaxMainServiceImpl implements AjaxMainService {
	private AjaxDao dao = new AjaxDaoImpl();
	@Override
	public List<Object> getBestChallenge() { //베스트 챌린지
		Connection conn = JDBCTemplate.getConnection();
		
		List<Object> list = dao.getBestChallenge(conn);
		return list;
	}
	@Override
	public List<Users> getGrade(List<Object> challengeList) {// 개설자 정보 조회
		Connection conn = JDBCTemplate.getConnection();
		
		List<Users> list = dao.getGradeByUno(conn, challengeList); 
		return list;
	}
	@Override
	public List<Challenge> getPopularChallenge() { //인기 챌린지
		Connection conn = JDBCTemplate.getConnection();
		
		List<Challenge> list = dao.getPopularChallenge(conn);
		return list;
	}
	@Override
	public List<Users> getpublisher(List<Challenge> popularList) {
		Connection conn = JDBCTemplate.getConnection();
		
		List<Users> list = dao.getPublisher(conn, popularList);
		return list;
	}
	@Override
	public List<Challenge> getNewChallenge() {//신규 챌린지
		Connection conn = JDBCTemplate.getConnection();
		
		List<Challenge> list = dao.getNewChallenge(conn);
		return list;
	}
}
