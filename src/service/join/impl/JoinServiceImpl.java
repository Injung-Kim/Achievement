package service.join.impl;

import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import common.JDBCTemplate;
import dao.join.face.JoinDao;
import dao.join.impl.JoinDaoImpl;
import dto.Member;
import service.join.face.JoinService;

public class JoinServiceImpl implements JoinService {
	
	private JoinDao joinDao = new JoinDaoImpl();

	@Override
	public Member getJoinMember(HttpServletRequest req) {
		Member member = new Member();
		SimpleDateFormat afterFormat = new SimpleDateFormat("yyyy-mm-dd");
		
		
		    String birthYear = req.getParameter("birthYear");
			String birthMonth = req.getParameter("birthMonth");
			String birthDay = req.getParameter("birthDay");
			//생년월일 총합 
			String birth = birthYear + "-" + birthMonth +"-"+ birthDay;
			
		    member.setUid(req.getParameter("uid"));
			member.setUpassword(req.getParameter("upassword"));
			member.setUsername(req.getParameter("username"));
			member.setNick(req.getParameter("nick"));
			member.setPhone(req.getParameter("phone"));
			member.setEmail(req.getParameter("email"));
			member.setGender(req.getParameter("gender"));

				
			try {
				if( birthYear != null ) {
					member.setBirth(afterFormat.parse(birth));
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			member.setAccount(req.getParameter("account"));
			member.setBank(req.getParameter("bank"));
			member.setChallenge(req.getParameter("challenge"));
			member.setPost(req.getParameter("post"));
			member.setAddress(req.getParameter("address"));
			member.setUgrade(req.getParameter("ugrade"));
		
            System.out.println(member);
		
		
		return member;
	}

	@Override
	public boolean join(Member mem) {
		
		Connection conn = JDBCTemplate.getConnection();

		
		if( joinDao.insertJoin(conn, mem) > 0 ) {
			JDBCTemplate.commit(conn);
            return true;
			
		} else {
        	JDBCTemplate.rollback(conn);
			return false;
		}

	}

	
	
	@Override
	public boolean idCheck(String uid) {
		
		if( joinDao.selectCntById(JDBCTemplate.getConnection(), uid) > 0 ) {
			return true;
		} else {
			return false;
			
		}
	}

	@Override
	public boolean nickCheck(String nick) {
		
		if( joinDao.selectCntByNick(JDBCTemplate.getConnection(), nick) > 0 ) {
			return true;
		} else {
			return false;
			
		}
	}

	@Override
	public boolean emailCheck(String email) {
		if( joinDao.selectCntByEmail(JDBCTemplate.getConnection(), email) > 0 ) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Member idFindMember(HttpServletRequest req) {
		//member객체 생성
        Member member = new Member();
        
		
		member.setUsername(req.getParameter("username"));
		member.setPhone(req.getParameter("phone"));
		
		return member;
	}

	@Override
	public boolean infind(Member mem) {
		if(joinDao.selectCntMemberByUsernameUserphone(JDBCTemplate.getConnection(), mem)>0) {
			return true; //아이디찾기 성공
		}else {
			return false; //아이디찾기 실패
		}
	}

	@Override
	public Member idinfo(Member mem) {
		//조회된  정보
				return joinDao.selectMemberByUsernameUserphone(JDBCTemplate.getConnection(), mem);
	}

	@Override
	public Member pwFindMember(HttpServletRequest req) {
		//member객체 생성
        Member member = new Member();
        
		
		member.setUsername(req.getParameter("username"));
		member.setUid(req.getParameter("uid"));
		member.setEmail(req.getParameter("email"));
		
		return member;
	}

	@Override
	public boolean pwfind(Member mem) {
		if(joinDao.selectCntMemberByUserpw(JDBCTemplate.getConnection(), mem)>0) {
			return true; //아이디찾기 성공
		}else {
			return false; //아이디찾기 실패
		}
	}

	@Override
	public Member pwinfo(Member mem) {
		//조회된  정보
		return joinDao.selectMemberByUserpw(JDBCTemplate.getConnection(), mem);
	}

	
}
