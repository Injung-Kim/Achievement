package service.admin.user.impl;


import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import common.JDBCTemplate;
import dao.admin.user.face.ActiveDao;
import dao.admin.user.impl.ActiveDaoImpl;
import dto.UserPoint;
import dto.Users;
import service.admin.user.face.UserActiveService;

public class UserActiveServiceImpl implements UserActiveService{
	private ActiveDao activeDao = new ActiveDaoImpl();
	@Override
	public Map getCntpost(Users userinfo) {
		Connection conn = JDBCTemplate.getConnection();
		Map<String, Integer> map = new HashMap<>();
		//작성 게시글 수 
		int cntPost = activeDao.getCntpostByUserno(conn, userinfo);
		//작성 댓글 수
		int cntComment = activeDao.getCntComment(conn, userinfo);
		//누른 좋아요 수
		int likes = activeDao.getCntlikes(conn, userinfo);
		//신고 수
		int cntComplaint = activeDao.getCntComplaints(conn, userinfo);
		
		
		map.put("cntPost",cntPost);
		map.put("cntComment", cntComment);
		map.put("cntLikes", likes);
		map.put("cntComplaint", cntComplaint);
		return map;
	}
	@Override
	public UserPoint getCntPoint(Users userinfo) {
		Connection conn = JDBCTemplate.getConnection();
		UserPoint userpoint = new UserPoint();
		//누적 결제금액
		int sumPayments = activeDao.getSumPayments(conn, userinfo); 
		//최근 포인트 적립일
		Date earningDate = activeDao.getearnedDate(conn, userinfo);
		//최근 포인트 차감일
		Date usedDate = activeDao.getUsedDate(conn, userinfo);
		//누적 포인트
		int getPoint = activeDao.sumPoints(conn, userinfo);
		//차감 포인트
		int usedPoint = activeDao.getUsedPoints(conn,userinfo);
		
		//현재 포인트
		int currentP = getPoint - usedPoint;
		
		userpoint.setPayments(sumPayments);
		userpoint.setEarnedDate(earningDate);
		userpoint.setUsedDate(usedDate);
		userpoint.setGetPoint(getPoint);
		userpoint.setUsedPoint(usedPoint);
		userpoint.setSumPoint(currentP);
		
		return userpoint;
	}
	@Override
	public String getChallenge(Users userinfo) {
		Connection conn = JDBCTemplate.getConnection();
		
		String challenge = activeDao.currChallenge(conn, userinfo);
		return challenge;
	}
	
}
