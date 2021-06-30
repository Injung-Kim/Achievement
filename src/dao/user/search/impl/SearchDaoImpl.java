package dao.user.search.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

import common.JDBCTemplate;
import dao.user.search.face.SearchDao;
import dto.Challenge;
import dto.ChallengeList;
import util.Paging;

public class SearchDaoImpl implements SearchDao {
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	@Override
	public int cntAll(Connection conn, String word) {
		String sql = "";
		sql += "SELECT count(*) FROM CHALLENGE " + 
				"WHERE CH_TITLE LIKE '%'|| ? || '%' " + 
				"OR CH_CONTENT LIKE '%'|| ? || '%'";
		//결과를 저장할 변수 생성
		int num = 0;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, word);
			ps.setString(2, word);
			rs = ps.executeQuery();
			while(rs.next()) {
				num = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		return num;
	}
	@Override
	public List<ChallengeList> getAllList(Connection conn, String word, Paging paging) {
		String sql = "";
		sql += "SELECT * FROM(  " + 
				"SELECT ROWNUM rnum, list.*  FROM(  " + 
				"SELECT C.ch_no,ch_title,C.ca_no, CA.ca_title ,C.u_no,U.u_id,ch_stored_name,ch_start_date, ch_likes FROM CHALLENGE C  " + 
				"INNER JOIN users U  " + 
				"on U.u_no = C.u_no  " + 
				"INNER JOIN category CA " + 
				"ON C.ca_no = CA.ca_no " + 
				"WHERE CH_TITLE LIKE '%'|| ? || '%'  " + 
				"OR CH_CONTENT LIKE '%'|| ? || '%' "+
				"ORDER BY ch_no DESC)list)  " + 
				"WHERE rnum between ? and ? ";
		
		
		List<ChallengeList> list = new ArrayList<ChallengeList>();
		ChallengeList challenge = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, word);
			ps.setString(2, word);
			ps.setInt(3, paging.getStartNo());
			ps.setInt(4, paging.getEndNo());
			
			rs = ps.executeQuery();
			while(rs.next()) {
				challenge = new ChallengeList();
				challenge.setChNo(rs.getInt("ch_no"));
				challenge.setChTitle(rs.getString("ch_title"));
				challenge.setCaTitle(rs.getString("ca_title"));
				challenge.setuNo(rs.getInt("u_no"));
				challenge.setuId(rs.getString("u_id"));
				challenge.setChStoredName(rs.getString("ch_stored_name"));
				challenge.setChStartDate(rs.getDate("ch_start_date"));
				challenge.setChLikes(rs.getInt("ch_likes"));
				
				list.add(challenge);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public int cntLists(Connection conn, String word,  String[] category) {
		String sql = "";
		//결과를 저장할 변수(초기값) 
		int cntall = 0; 
		//카테고리 , 문자열 추가
		
		String strCate = String.join(",", category);
		 //체크한 카테고리 갯수만큼 반복
			sql += "SELECT count(*) FROM( " + 
					"SELECT * FROM challenge  " + 
					"WHERE ca_no IN ( "+strCate+" ) )CA  " + 
					"WHERE CH_TITLE LIKE '%' || ? || '%'  " + 
					"OR CH_CONTENT LIKE '%' || ? || '%'  ";
		for(int i=0; i<category.length; i++){
			try {
				ps = conn.prepareStatement(sql);
					ps.setString(1, word);
					ps.setString(2, word);
					//ps.setString(4, orderby);		
					rs = ps.executeQuery();
					
					while(rs.next()) {
						cntall = rs.getInt(1);
					}//카테고리별 조회하여 나온값 더하기
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				JDBCTemplate.close(rs);
				JDBCTemplate.close(ps);
			}
		}
		return cntall;
	}
	//검색어별, 카테고리별, 정렬기준별 리스트 조회
	@Override
	public List<ChallengeList> getDetailList(Connection conn, String word, String orderby, String[] category,
			Paging paging) {
		
		System.out.println("["+orderby+"]"); // 정렬기준 
		
		//카테고리에 , 추가하기
		String strCate = String.join(",", category);
		String sql = "";
		
		sql += "SELECT * FROM(  " + 
				"SELECT ROWNUM rnum, list.*  FROM(  " + 
				"SELECT * FROM(   " + 
				"SELECT C.ch_no, ch_title,ch_content,C.u_no, U.u_id, ch_stored_name, ch_start_date, ch_likes, ch_create_date, count(P.u_no)   " + 
				"FROM CHALLENGE C  " + 
				"INNER JOIN users U  " + 
				"on U.u_no = C.u_no   " + 
				"INNER JOIN PARTICIPATION P  " + 
				"on C.ch_no = P.ch_no  " + 
				"WHERE ca_no IN(" + strCate + ")  " + 
				"GROUP BY C.ch_no, ch_title, ch_content, C.u_no, U.u_id, ch_stored_name,ch_start_date, ch_likes, ch_create_date  " + 
				"ORDER BY "+ orderby +" DESC)CA   " + 
				"WHERE CH_TITLE LIKE '%' || ? || '%'   " + 
				"OR CH_CONTENT LIKE '%' || ? || '%'   " + 
				")list)   " + 
				"WHERE rnum between ? and ?  "; 
		
		//전체 결과를 저장할 리스트
		List<ChallengeList> listAll = new ArrayList<>();
		//카테고리별 조회결과를 저장할 리스트 
		List<ChallengeList> detailList  = new ArrayList<>();
		ChallengeList challenge = null;
		
			try {
				ps = conn.prepareStatement(sql);
				ps.setString(1, word);
				ps.setString(2, word);
				ps.setInt(3, paging.getStartNo());
				ps.setInt(4, paging.getEndNo());
				
				rs = ps.executeQuery();
				while(rs.next()) {
					challenge = new ChallengeList();
					challenge.setChNo(rs.getInt("ch_no"));
					challenge.setChTitle(rs.getString("ch_title"));
					challenge.setChContent(rs.getString("ch_content"));
					challenge.setuNo(rs.getInt("u_no"));
					challenge.setuId(rs.getString("u_id"));
					challenge.setChStoredName(rs.getString("ch_stored_name"));
					challenge.setChStartDate(rs.getDate("ch_start_date"));
					challenge.setChLikes(rs.getInt("ch_likes"));
					
					detailList.add(challenge); //category[i] 조회 결과 리스트
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				JDBCTemplate.close(rs);
				JDBCTemplate.close(ps);
			}
		
		//전체 결과	
		
		// for(int i = 0; i< detailList.size(); i++) { System.out.println(detailList); }
		 
		return detailList;
	}
	
}
