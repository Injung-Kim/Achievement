package dao.user.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.JDBCTemplate;
import dao.user.face.ChallengeDao;
import dto.Challenge;
import dto.ChallengeCategory;
import dto.ChallengeList;
import util.Paging;

public class ChallengeDaoImpl implements ChallengeDao {
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	@Override
	public ChallengeCategory getCategorybyNum(Connection conn, ChallengeCategory number) {
		String sql = "";
		sql += "SELECT ca_no, ca_title FROM category WHERE ca_no = ?";
		
		ChallengeCategory category = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, number.getCategoryNo());
			
			rs = ps.executeQuery();
			while(rs.next()) {
				category = new ChallengeCategory();
				category.setTitle(rs.getString("ca_title"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		return category;
	}
	@Override
	public int getCntnewChallenges(Connection conn, ChallengeCategory number) { 
		String sql = "";
		sql += "SELECT count(*) FROM challenge "
				+ "WHERE months_between(sysdate, ch_create_date) <= 1 "
				+ "and ca_no = ? ";
		//결과를 저장할 int변수
		int challenges = 0;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, number.getCategoryNo());
			
			rs = ps.executeQuery();
			while(rs.next()) {
			challenges = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		return challenges;
	}
	@Override//신규 챌린지
	public List<ChallengeList> newChallengebyCategory(Connection conn, ChallengeCategory number, Paging paging) {
		String sql = "";
		sql = "SELECT * FROM( " + 
				"SELECT ROWNUM rnum, C.* FROM( " + 
				"SELECT ch_no, ch_title, ch_start_date, ch_end_date, ch_likes, ch_origin_name, ch_stored_name " + 
				"FROM challenge " + 
				"where months_between(sysdate, ch_create_date) <= 1  " + 
				"and ca_no = ?  " + 
				"ORDER BY ch_no DESC)C " + 
				")CHALLENGE " + 
				"WHERE rnum between ? and ?";
		
		
		//결과 저장할 dto객체 생성
		ChallengeList challenge = null;
		//반환할 list객체 생성
		List<ChallengeList> list = new ArrayList<>();
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, number.getCategoryNo()); //카테고리 번호
			ps.setInt(2, paging.getStartNo()); //페이징 처음번호
			ps.setInt(3, paging.getEndNo()); //페이징 끝번호
			
			rs = ps.executeQuery();
			while(rs.next()) {
				challenge = new ChallengeList();
				
				challenge.setChNo(rs.getInt("ch_no"));
				challenge.setChTitle(rs.getString("ch_title"));
				challenge.setChStartDate(rs.getDate("ch_start_date"));
				challenge.setChEndDate(rs.getDate("ch_end_date"));
				challenge.setChLikes(rs.getInt("ch_likes"));
				challenge.setChOriginName(rs.getString("ch_origin_name"));
				challenge.setChStoredName(rs.getString("ch_stored_name"));
				
				list.add(challenge);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		return list;
	}
	@Override
	public List<Integer> countParticipants(Connection conn, List<ChallengeList> list) {
		String sql = "";
		sql += "SELECT count(*) FROM participation WHERE ch_no = ?";
		//결과를 저장할 개별 변수 
		int participants = 0;
		List<Integer> chNo = new ArrayList<>();
		try {
			ps = conn.prepareStatement(sql);
			for(int i = 0; i< list.size(); i++) {
				ps.setInt(1, list.get(i).getChNo());
				rs = ps.executeQuery();
				while(rs.next()) {
					participants = rs.getInt(1);
					chNo.add(participants);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		return chNo;
	}
	@Override
	public List<Integer> countLikes(Connection conn, List<ChallengeList> list) {
		String sql = "";
		sql += "SELECT count(pa_like) FROM participation "
				+ "WHERE pa_like = 'Y' and ch_no= ?";
		//결과를 저장할 개별 변수
		int likes = 0;
		List<Integer> likeList = new ArrayList<>();
		try {
			ps = conn.prepareStatement(sql);
			for(int i = 0; i< list.size(); i++) {
				ps.setInt(1, list.get(i).getChNo());
				rs = ps.executeQuery();
				while(rs.next()) {
					likes = rs.getInt(1);
					likeList.add(likes);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		return likeList;
	}
	@Override
	public int getCntPopularChallenges(Connection conn, ChallengeCategory subject) {
		String sql = "";
		sql += "SELECT COUNT(*) FROM( " + 
				"SELECT * FROM challenge WHERE ch_likes >= 100 and ca_no = ?)POPULAR";
		//결과를 저장할 변수 
		int popular = 0;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, subject.getCategoryNo());
			
			rs = ps.executeQuery();
			while(rs.next()) {
				popular = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(ps);
			JDBCTemplate.close(rs);
		}
		return popular;
	}
	@Override//인기 챌린지
	public List<ChallengeList> popularChallenges(Connection conn, ChallengeCategory subject, Paging paging) {
		String sql = "";
		sql += "SELECT * FROM( " + 
				"SELECT ROWNUM rnum, POPULAR.* FROM( " + 
				"SELECT ch_no, ch_title,CH.u_no, U.u_id, U.u_grade, ch_start_date, ch_end_date, ch_likes, ch_origin_name, ch_stored_name " + 
				"FROM challenge CH " + 
				"INNER JOIN users U " +
				"ON CH.u_no = U.u_no " +
				"WHERE ch_likes >= 100 AND ca_no = ? " + 
				"ORDER BY ch_no DESC " + 
				")POPULAR)R " + 
				"WHERE rnum between ? and ?";
		
		List<ChallengeList> list = new ArrayList<>();
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, subject.getCategoryNo());
			ps.setInt(2, paging.getStartNo());
			ps.setInt(3, paging.getEndNo());
			
			rs = ps.executeQuery();
			while(rs.next()) {
				ChallengeList popular = new ChallengeList();
				
				popular.setChNo(rs.getInt("ch_no"));
				popular.setChTitle(rs.getString("ch_title"));
				popular.setuId(rs.getString("u_id"));
				popular.setuGrade(rs.getString("u_grade"));
				popular.setChStartDate(rs.getDate("ch_start_date"));
				popular.setChEndDate(rs.getDate("ch_end_date"));
				popular.setChLikes(rs.getInt("ch_likes"));
				popular.setChOriginName(rs.getString("ch_origin_name"));
				popular.setChStoredName(rs.getString("ch_stored_name"));
				
				list.add(popular);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(ps);
			JDBCTemplate.close(rs);
		}
		return list;
	}
}
