package dao.user.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.JDBCTemplate;
import dao.user.face.UserChallengeDao;
import dto.Challenge;
import dto.Participation;
import dto.Payback;
import util.Paging;

public class UserChallengeDaoImpl implements UserChallengeDao {
	
	private PreparedStatement ps= null;
	private ResultSet rs = null;
	private SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
	@Override
	public List<Challenge> selectAll(Connection conn, Paging paging) {
		//SQL 작성
		String sql = "";
		sql += "SELECT * FROM (";
		sql += " 	SELECT rownum rnum, C.* FROM (";
		sql += " 		SELECT";
		sql += " 			ch_no, ch_title, ch_start_date, ch_stored_name";
		sql += " 			, ch_end_date, ch_money, ch_likes";
		sql += " 		FROM challenge";
		sql += " 		ORDER BY ch_no DESC";
		sql += "	) C";
		sql += " ) CHALLENGE";
		sql += " WHERE rnum BETWEEN ? AND ?";

		//결과 저장할 List
		List<Challenge> challengeList = new ArrayList<>();

		try {
			ps = conn.prepareStatement(sql); //SQL수행 객체

			ps.setInt(1, paging.getStartNo());
			ps.setInt(2, paging.getEndNo());

			rs = ps.executeQuery(); //SQL 수행 및 결과집합 저장

			//조회 결과 처리
			while(rs.next()) {
				Challenge ch = new Challenge(); //결과값 저장 객체

				//결과값 한 행 처리
				ch.setChNo(rs.getInt("ch_no"));
				ch.setChTitle(rs.getString("ch_title"));
				ch.setChStartDate(rs.getDate("ch_start_date"));
				ch.setChEndDate(rs.getDate("ch_end_date"));
				ch.setChMoney(rs.getInt("ch_money"));
				ch.setChLikes(rs.getInt("ch_likes"));
				ch.setChStoredName(rs.getString("ch_stored_name"));


				//리스트에 결과값 저장
				challengeList.add(ch);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DB객체 닫기
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}

		//최종 결과 반환

		return challengeList;
	}
	
	@Override
	public List<Challenge> selectAll(Connection conn, Paging paging, Challenge challenge) {
		String sql = "";
		sql += "SELECT * FROM (";
		sql += " 	SELECT rownum rnum, C.* FROM (";
		sql += " 		SELECT";
		sql += " 			ch_no, ch_title, ch_start_date";
		sql += " 			, ch_end_date, ch_money, ch_likes, ch_stored_name";
		sql += " 		FROM challenge";
		sql += "		WHERE ca_no = ? ";
		sql += " 		ORDER BY ch_no DESC";
		sql += "	) C";
		sql += " ) CHALLENGE";
		sql += " WHERE rnum BETWEEN ? AND ?";

		//결과 저장할 List
		List<Challenge> challengeList = new ArrayList<>();

		try {
			ps = conn.prepareStatement(sql); //SQL수행 객체
			
			ps.setInt(1, challenge.getCaNo());
			ps.setInt(2, paging.getStartNo());
			ps.setInt(3, paging.getEndNo());

			rs = ps.executeQuery(); //SQL 수행 및 결과집합 저장

			//조회 결과 처리
			while(rs.next()) {
				Challenge ch = new Challenge(); //결과값 저장 객체

				//결과값 한 행 처리
				ch.setChNo(rs.getInt("ch_no"));
				ch.setChTitle(rs.getString("ch_title"));
				ch.setChStartDate(rs.getDate("ch_start_date"));
				ch.setChEndDate(rs.getDate("ch_end_date"));
				ch.setChMoney(rs.getInt("ch_money"));
				ch.setChLikes(rs.getInt("ch_likes"));
				ch.setCaNo(challenge.getCaNo());
				ch.setChStoredName(rs.getString("ch_stored_name"));
				//리스트에 결과값 저장
				challengeList.add(ch);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DB객체 닫기
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}

		//최종 결과 반환

		return challengeList;
	}
	@Override
	public List<Challenge> selectAll(Connection conn, Paging paging, String title) {
		
		String sql = "";
		sql += "SELECT * FROM (";
		sql += " 	SELECT rownum rnum, C.* FROM (";
		sql += " 		SELECT";
		sql += " 			ch_no, ch_title, ch_start_date";
		sql += " 			, ch_end_date, ch_money, ch_likes, ch_stored_name";
		sql += " 		FROM challenge";
		sql += "		WHERE ch_title like ? ";
		sql += " 		ORDER BY ch_no DESC";
		sql += "	) C";
		sql += " ) CHALLENGE";
		sql += " WHERE rnum BETWEEN ? AND ?";

		//결과 저장할 List
		List<Challenge> challengeList = new ArrayList<>();

		try {
			ps = conn.prepareStatement(sql); //SQL수행 객체
			
			ps.setString(1,"%"+title+"%");
			ps.setInt(2, paging.getStartNo());
			ps.setInt(3, paging.getEndNo());

			rs = ps.executeQuery(); //SQL 수행 및 결과집합 저장

			//조회 결과 처리
			while(rs.next()) {
				Challenge ch = new Challenge(); //결과값 저장 객체

				//결과값 한 행 처리
				ch.setChNo(rs.getInt("ch_no"));
				ch.setChTitle(rs.getString("ch_title"));
				ch.setChStartDate(rs.getDate("ch_start_date"));
				ch.setChEndDate(rs.getDate("ch_end_date"));
				ch.setChMoney(rs.getInt("ch_money"));
				ch.setChLikes(rs.getInt("ch_likes"));
				ch.setChStoredName(rs.getString("ch_stored_name"));
				//리스트에 결과값 저장
				challengeList.add(ch);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DB객체 닫기
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}

		//최종 결과 반환

		return challengeList;
	}
	
	@Override
	public List<Challenge> selectAll(Connection conn, Paging paging, Challenge challenge, String title) {
		String sql = "";
		sql += "SELECT * FROM (";
		sql += " 	SELECT rownum rnum, C.* FROM (";
		sql += " 		SELECT";
		sql += " 			ch_no, ch_title, ch_start_date";
		sql += " 			, ch_end_date, ch_money, ch_likes, ch_stored_name";
		sql += " 		FROM challenge";
		sql += "		WHERE ch_title like ? ";
		sql += "		and ca_no =?";
		sql += " 		ORDER BY ch_no DESC";
		sql += "	) C";
		sql += " ) CHALLENGE";
		sql += " WHERE rnum BETWEEN ? AND ?";

		//결과 저장할 List
		List<Challenge> challengeList = new ArrayList<>();

		try {
			ps = conn.prepareStatement(sql); //SQL수행 객체
			
			ps.setString(1,"%"+title+"%");
			ps.setInt(2, challenge.getCaNo());
			ps.setInt(3, paging.getStartNo());
			ps.setInt(4, paging.getEndNo());

			rs = ps.executeQuery(); //SQL 수행 및 결과집합 저장

			//조회 결과 처리
			while(rs.next()) {
				Challenge ch = new Challenge(); //결과값 저장 객체

				//결과값 한 행 처리
				ch.setChNo(rs.getInt("ch_no"));
				ch.setChTitle(rs.getString("ch_title"));
				ch.setChStartDate(rs.getDate("ch_start_date"));
				ch.setChEndDate(rs.getDate("ch_end_date"));
				ch.setChMoney(rs.getInt("ch_money"));
				ch.setChLikes(rs.getInt("ch_likes"));
				ch.setChStoredName(rs.getString("ch_stored_name"));
				//리스트에 결과값 저장
				challengeList.add(ch);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DB객체 닫기
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}

		//최종 결과 반환

		return challengeList;
	}
	
	@Override
	public int selectCntAll(Connection conn) {
		String sql = "";
		sql += "SELECT count(*) cnt FROM challenge";
		
		//총 챌린지 수
		int cnt = 0;
		
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				cnt = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return cnt;
	}
	//전체 제목 조회 개수
	@Override
	public int selectCntAll(Connection conn, Challenge challenge) {
		String sql = "";
		sql += "SELECT count(*) cnt FROM challenge where ca_no=?";
		
		//총 게시글 수
		int cnt = 0;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, challenge.getCaNo());
			rs = ps.executeQuery();
			
			while(rs.next()) {
				cnt = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return cnt;
	}
	//전체 제목 조회 개수
	
	
	@Override
	public int selectCntAll(Connection conn, String title) {
		String sql = "";
		sql += "SELECT count(*) cnt FROM challenge where ch_title like ?";

		//총 게시글 수
		int cnt = 0;

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, "%"+title+"%");
			rs = ps.executeQuery();

			while(rs.next()) {
				cnt = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		return cnt;
	}
	@Override
	public int selectCntAll(Connection conn, Challenge challenge, String title) {
		String sql = "";
		sql += "SELECT count(*) cnt FROM challenge where ca_no=? and ch_title like ?";

		//총 게시글 수
		int cnt = 0;

		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, challenge.getCaNo());
			ps.setString(2, "%"+title+"%");
			rs = ps.executeQuery();
			
			while(rs.next()) {
				cnt = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		return cnt;
	}
	@Override
	public Challenge selectChallengeByChNo(Connection conn, Challenge challenge) {
		//SQL 작성
		String sql = "";
		sql += "select";
		sql	+= " ch_no,ca_no ,";
		sql	+= " cec_no,";
		sql	+= " u_no,";
		sql	+= " ch_title,";
		sql	+= " ch_content,";
		sql	+= " ch_start_date,";
		sql	+= " ch_end_date,";
		sql	+= " ch_money,";
		sql	+= " to_char(ch_start_time,'HH24:MI') as ch_start_time,";
		sql	+= " to_char(ch_end_time,'HH24:MI') as ch_end_time,";
		sql	+= " ch_way,";
		sql	+= " ch_caution,";
		sql	+= " ch_likes,";
		sql	+= " ch_origin_name,";
		sql	+= " ch_stored_name, ";
		sql += " ch_create_date,";
		sql += " ch_state";
		sql	+= " from challenge";
		sql += " WHERE ch_no = ?";

		//결과 저장할 Board객체
		Challenge result = null;

		try {
			ps = conn.prepareStatement(sql); //SQL수행 객체

			ps.setInt(1, challenge.getChNo()); //조회할 게시글 번호 적용

			rs = ps.executeQuery(); //SQL 수행 및 결과집합 저장

			//조회 결과 처리
			if(rs.next()) {
				result = new Challenge(); //결과값 저장 객체
				//결과값 한 행 처리
				result.setChNo(rs.getInt("ch_no"));
				result.setCaNo(rs.getInt("ca_no"));
				result.setuNo(rs.getInt("u_no"));
				result.setCecNo(rs.getInt("cec_no"));
				result.setChTitle(rs.getString("ch_title"));
				result.setChContent(rs.getString("ch_content"));
				result.setChStartDate(rs.getDate("ch_start_date"));
				result.setChEndDate(rs.getDate("ch_end_date"));
				result.setChCaution(rs.getInt("ch_caution"));
				result.setChMoney(rs.getInt("ch_money"));
				result.setChStartTime(rs.getString("ch_start_time"));
				result.setChEndTime(rs.getString("ch_end_time"));
				result.setChWay(rs.getString("ch_way"));
				result.setChLikes(rs.getInt("ch_likes"));
				result.setChOriginName(rs.getString("ch_origin_name"));
				result.setChStoredName(rs.getString("ch_stored_name"));
				result.setChCreateDate(rs.getDate("ch_create_date"));
				result.setChState(rs.getString("ch_state"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DB객체 닫기
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}

		//최종 결과 반환
		return result;
	}
	@Override
	public Map<String, String> selectNameTitle(Connection conn, Challenge challenge) {
		String sql="";
		sql+="select";
		sql+=" u_id,";
		sql+=" u_name,";
		sql+=" cec_title,";
		sql+=" ca_title";
		sql+=" from challenge";
		sql+=" inner join users";
		sql+=" ON challenge.u_no = users.u_no";
		sql+=" inner join certification_cycle";
		sql+=" on challenge.cec_no = certification_cycle.cec_no";
		sql+=" inner join category";
		sql+=" on challenge.ca_no = category.ca_no ";
		sql+=" where ch_no=?";
		
		Map<String, String> result = null;
		
		try {
			ps = conn.prepareStatement(sql); //SQL수행 객체

			ps.setInt(1, challenge.getChNo()); //조회할 게시글 번호 적용

			rs = ps.executeQuery(); //SQL 수행 및 결과집합 저장

			//조회 결과 처리
			if(rs.next()) {
				result= new HashMap<>();
				result.put("name", rs.getString("u_name"));
				result.put("title", rs.getString("cec_title"));
				result.put("category", rs.getString("ca_title"));
				result.put("u_id",rs.getString("u_id"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DB객체 닫기
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		
		return result;
	}
	@Override
	public String selectCategory(Connection conn, Challenge challenge) {
		String sql="";
		sql+=" select";
		sql+=" ca_title";
		sql+=" from category";
		sql+=" where ca_no=?";
		
		String category= null;
		
		try {
			ps = conn.prepareStatement(sql); //SQL수행 객체

			ps.setInt(1, challenge.getCaNo()); //조회할 게시글 번호 적용

			rs = ps.executeQuery(); //SQL 수행 및 결과집합 저장

			//조회 결과 처리
			if(rs.next()) {
				category=rs.getString("ca_title");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DB객체 닫기
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		
		return category;
	}
	@Override
	public String selectName(Connection conn, String u_id) {
		String sql="";
		sql += "select u_name";
		sql += " from users";
		sql += " where u_id=?";
		
		String result =null;
		try {
			ps = conn.prepareStatement(sql); //SQL수행 객체

			ps.setString(1, u_id); //조회할 게시글 번호 적용

			rs = ps.executeQuery(); //SQL 수행 및 결과집합 저장

			//조회 결과 처리
			if(rs.next()) {
				result= rs.getString("u_name");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DB객체 닫기
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return result;
	}
	@Override
	public List<String> selectCategory(Connection conn) {
		String sql="";
		sql += "select ca_title";
		sql += " from category";
		
		List<String> result =new ArrayList<String>();
		try {
			ps = conn.prepareStatement(sql); //SQL수행 객체
			rs = ps.executeQuery(); //SQL 수행 및 결과집합 저장

			//조회 결과 처리
			while(rs.next()) {
				result.add(rs.getString("ca_title"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DB객체 닫기
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		
		return result;
	}
	//인증 주기 목록 가져오기
	@Override
	public List<String> selectCycle(Connection conn) {
		String sql="";
		sql += "select cec_title";
		sql += " from CERTIFICATION_CYCLE";
		
		List<String> result =new ArrayList<String>();
		try {
			ps = conn.prepareStatement(sql); //SQL수행 객체
			rs = ps.executeQuery(); //SQL 수행 및 결과집합 저장

			//조회 결과 처리
			while(rs.next()) {
				result.add(rs.getString("cec_title"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DB객체 닫기
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return result;
	}
	@Override
	public int selectChNo(Connection conn) {
		String sql="";
		sql += "select challenge_seq.nextval";
		sql += " from dual";
		
		int chNo = 0;
		try {
			ps = conn.prepareStatement(sql); //SQL수행 객체
			rs = ps.executeQuery(); //SQL 수행 및 결과집합 저장
			//조회 결과 처리
			if(rs.next()) {
				chNo=rs.getInt(1);			
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DB객체 닫기
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		
		return chNo;
	}
	
	
	@Override
	public int selectuNo(Connection conn, String u_id) {
		String sql="";
		sql += "select u_no";
		sql += " from users";
		sql += " where u_id=?";
		
		int uNo = 0;
		try {
			ps = conn.prepareStatement(sql); //SQL수행 객체
			ps.setString(1, u_id);
			rs = ps.executeQuery(); //SQL 수행 및 결과집합 저장
			
			//조회 결과 처리
			if(rs.next()) {
				uNo=rs.getInt("u_no");			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DB객체 닫기
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		
		return uNo;
	}
	
	@Override
	public int selectCecNo(Connection conn, String cycle) {
		String sql="";
		sql += "select cec_no";
		sql += " from certification_cycle";
		sql += " where cec_title=?";
		
		int cecNo = 0;
		try {
			ps = conn.prepareStatement(sql); //SQL수행 객체
			ps.setString(1, cycle);
			rs = ps.executeQuery(); //SQL 수행 및 결과집합 저장
			
			//조회 결과 처리
			if(rs.next()) {
				cecNo=rs.getInt("cec_no");			
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DB객체 닫기
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return cecNo;
	}
	@Override
	public int selectCaNo(Connection conn, String category) {
		String sql="";
		sql += "select ca_no";
		sql += " from category";
		sql += " where ca_title=?";
		
		int caNo = 0;
		try {
			ps = conn.prepareStatement(sql); //SQL수행 객체
			ps.setString(1, category);
			rs = ps.executeQuery(); //SQL 수행 및 결과집합 저장
			
			//조회 결과 처리
			if(rs.next()) {
				caNo=rs.getInt("ca_no");			
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DB객체 닫기
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return caNo;
	}
	@Override
	public int selectParticipation(Connection conn, Participation participation) {
		String sql="";
		sql += "SELECT count(*) cnt";
		sql += " FROM participation";
		sql += " where ch_no=? AND u_no=?";
		
		//참여중 여부
		int cnt = 0;

		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, participation.getChNo());
			ps.setInt(2, participation.getuNo());
			rs = ps.executeQuery();

			while(rs.next()) {
				cnt = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}

		return cnt;
	}
	
	@Override
	public int insert(Connection conn, Challenge challenge) {
		//다음 챌린지 삽입 쿼리
		
		String sql = "";
		sql += "insert into challenge(";
		sql += " ch_no,";
		sql += " ca_no,";
		sql += " cec_no,";
		sql += " u_no,";
		sql += " ch_title,";
		sql += " ch_content,";
		sql += " ch_start_date,";
		sql += " ch_end_date,";
		sql += " ch_money,";
		sql += " ch_start_time,";
		sql += " ch_end_time,";
		sql += " ch_way,";
		sql += " ch_caution,";
		sql += " ch_likes,";
		sql += " ch_origin_name,";
		sql += " ch_stored_name)";
		sql += "  VALUES(?, ?, ?, ?, ?, ?, to_date(?), to_date(?), ?, to_date(?,'HH24:MI:SS'),to_date(?,'HH24:MI:SS'),?, 0, 0, ?, ?)";
				
		int res = 0;

		try {
			
			ps = conn.prepareStatement(sql);
			ps.setInt(1, challenge.getChNo());
			ps.setInt(2, challenge.getCaNo());
			ps.setInt(3, challenge.getCecNo());
			ps.setInt(4, challenge.getuNo());
			ps.setString(5, challenge.getChTitle());
			ps.setString(6, challenge.getChContent());
			ps.setString(7, transFormat.format(challenge.getChStartDate()));
			ps.setString(8, transFormat.format(challenge.getChEndDate()));
			ps.setInt(9, challenge.getChMoney());
			ps.setString(10, challenge.getChStartTime());
			ps.setString(11, challenge.getChEndTime());
			ps.setString(12, challenge.getChWay());
			ps.setString(13, challenge.getChOriginName());
			ps.setString(14, challenge.getChStoredName());
			res = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(ps);
		}

		return res;
	}
	@Override
	public int update(Connection conn, Challenge challenge) {
		//챌린지 수정 쿼리
		String sql = "";
		sql += "update challenge set";
		sql += " ca_no=?,";
		sql += " cec_no=?,";
		sql += " ch_title=?,";
		sql += " ch_content=?,";
		sql += " ch_start_date=to_date(?),";
		sql += " ch_end_date=to_date(?),";
		sql += " ch_money=?,";
		sql += " ch_start_time=to_date(?,'HH24:MI:SS'),";
		sql += " ch_end_time=to_date(?,'HH24:MI:SS'),";
		sql += " ch_way=?,";
		sql += " ch_origin_name=?,";
		sql += " ch_stored_name=?";
		sql += " where ch_no=?";
		
		System.out.println(transFormat.format(challenge.getChStartDate()));
		System.out.println(transFormat.format(challenge.getChEndDate()));
		
		int res = 0;

		try {
			//DB작업
			ps = conn.prepareStatement(sql);
			ps.setInt(1, challenge.getCaNo());
			ps.setInt(2, challenge.getCecNo());
			ps.setString(3, challenge.getChTitle());
			ps.setString(4, challenge.getChContent());
			ps.setString(5, transFormat.format(challenge.getChStartDate()));
			ps.setString(6, transFormat.format(challenge.getChEndDate()));
			ps.setInt(7, challenge.getChMoney());
			ps.setString(8, challenge.getChStartTime());
			ps.setString(9, challenge.getChEndTime());
			ps.setString(10, challenge.getChWay());
			ps.setString(11, challenge.getChOriginName());
			ps.setString(12, challenge.getChStoredName());
			ps.setInt(13, challenge.getChNo());
			res = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(ps);
		}

		return res;
	}
	@Override
	public Challenge selectByStoredName(Connection conn, Challenge challenge) {
		String sql="";
		sql += "select ch_no, ch_stored_name";
		sql += " from challenge";
		sql += " where ch_no=?";
		
		Challenge result=null;
		try {
			ps = conn.prepareStatement(sql); //SQL수행 객체
			ps.setInt(1, challenge.getChNo());
			rs = ps.executeQuery(); //SQL 수행 및 결과집합 저장
			
			//조회 결과 처리
			if(rs.next()) {
				result = new Challenge();
				result.setChNo(rs.getInt("ch_no"));
				result.setChStoredName(rs.getString("ch_stored_name"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DB객체 닫기
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return result;
	}
	@Override
	public int delete(Connection conn, Challenge challenge) {
		String sql="";
		sql += "delete";
		sql += " from challenge";
		sql += " where ch_no=?";
		
		int res=-1;
		try {
			ps = conn.prepareStatement(sql); //SQL수행 객체
			ps.setInt(1, challenge.getChNo());
			res=ps.executeUpdate(); //SQL 수행 및 결과집합 저장
			

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DB객체 닫기
			JDBCTemplate.close(ps);
		}
		return res;
	}
	@Override
	public String selectChState(Connection conn, Challenge challenge) {
		String sql="";
		sql += "SELECT ch_state";
		sql += " from challenge";
		sql += " where ch_no=?";
		
		String chState = null;
		try {
			ps = conn.prepareStatement(sql); //SQL수행 객체
			ps.setInt(1, challenge.getChNo());
			rs=ps.executeQuery(); //SQL 수행 및 결과집합 저장
			if(rs.next()) {
				chState = rs.getString("ch_state");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DB객체 닫기
			JDBCTemplate.close(ps);
		}
		return chState;
	}
	@Override
	public List<Payback> selectAllPayback(Connection conn, Challenge challenge) {
		//환급자를 만들기 위해 결제 테이블을 조회
		String sql="";
		sql += "SELECT *";
		sql += " from payment P";
		sql += " inner join users U";
		sql += " on P.u_no = U.u_no";
		sql += " where ch_no=?";
		
		List<Payback> result = new ArrayList<>();
		Payback payback = null;
		try {
			ps = conn.prepareStatement(sql); //SQL수행 객체
			ps.setInt(1, challenge.getChNo());
			rs=ps.executeQuery(); //SQL 수행 및 결과집합 저장
			while(rs.next()) {
				payback = new Payback();
				payback.setChNo(rs.getInt("ch_no")); //챌린지 번호
				payback.setuNo(rs.getInt("u_no")); //유저 번호
				payback.setImpUid(rs.getString("imp_uid")); //아임포트 고유 번호
				payback.setMerchantUid(rs.getString("merchant_uid")); //가맹점 고유번호
				payback.setPaybAmount(rs.getInt("paym_amount")); // 환불 금액
				payback.setPaybChecksum(rs.getInt("paym_amount")); //환불 확인 금액
				payback.setPaybRefundHolder(rs.getString("u_name")); //예금주
				payback.setPaybReason("챌린지삭제"); //환불 사유
				payback.setPaybRefundBank(rs.getString("u_bank")); //은행 service에서 은행코드로 변경
				payback.setPaybTaxFree(0); //면세금액
				payback.setPaybReFundAccount(rs.getString("u_account"));
				result.add(payback);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DB객체 닫기
			JDBCTemplate.close(ps);
		}
		
		
		
		return result;
	}
	@Override
	public int selectPaybNo(Connection conn) {
		//환급 번호 반환
		String sql="";
		sql += "select payback_seq.nextval";
		sql += " from dual";
		
		int paybNo = 0;
		try {
			ps = conn.prepareStatement(sql); //SQL수행 객체
			rs = ps.executeQuery(); //SQL 수행 및 결과집합 저장
			//조회 결과 처리
			if(rs.next()) {
				paybNo=rs.getInt(1);			
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DB객체 닫기
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		
		return paybNo;
	}
	@Override
	public int paybInsert(Connection conn, Payback payback) {
		//환급자 삽입 쿼리
		
		String sql = "";
		sql += "insert into payback(";
		sql += " payb_no,";
		sql += " u_no,";
		sql += " ch_no,";
		sql += " payb_amount,";
		sql += " payb_tax_free,";
		sql += " payb_checksum,";
		sql += " payb_reason,";
		sql += " payb_refund_holder,";
		sql += " payb_refund_bank,";
		sql += " payb_refund_account,";
		sql += " imp_uid,";
		sql += " merchant_uid)";
		sql += "  VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

		int res = 0;

		try {

			ps = conn.prepareStatement(sql);
			ps.setInt(1, payback.getPaybNo());
			ps.setInt(2, payback.getuNo());
			ps.setInt(3, payback.getChNo());
			ps.setInt(4, payback.getPaybAmount());
			ps.setInt(5, payback.getPaybTaxFree());
			ps.setInt(6, payback.getPaybChecksum());
			ps.setString(7, payback.getPaybReason());
			ps.setString(8, payback.getPaybRefundHolder());
			ps.setString(9, payback.getPaybRefundBank());
			ps.setString(10, payback.getPaybReFundAccount());
			ps.setString(11, payback.getImpUid());
			ps.setString(12, payback.getMerchantUid());
		
			res = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(ps);
		}

		return res;
	}
	@Override
	public int selectReviewCntAll(Connection conn, Challenge challenge) {
		String sql = "";
		sql += "select count(*) as cnt from participation";
		sql += " where not (pa_review = '후기없음') and ch_no = ?";
		
		//총 챌린지 수
		int cnt = 0;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, challenge.getChNo());
			rs = ps.executeQuery();
			
			while(rs.next()) {
				cnt = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return cnt;
	}
	@Override
	public List<Participation> selectParticipationAll(Connection conn, Paging paging, Challenge challenge) {
		String sql = "";
		sql += "SELECT * FROM (";
		sql += " 	SELECT rownum rnum, PA.* FROM (";
		sql += " 		SELECT";
		sql += " 			pa_no, ch_no, u_no, pa_create_date, pa_is_success,";
		sql += " 			 pa_review, pa_like";
		sql += " 		 FROM participation";
		sql += "		 where not (pa_review = '후기없음') and ch_no = ?";
		sql += " 		order by pa_no desc";
		sql += "	) PA";
		sql += " ) participation";
		sql += " where rnum between ? and ?";

		//결과 저장할 List
		List<Participation> participationList = new ArrayList<>();
		try {
			ps = conn.prepareStatement(sql); //SQL수행 객체
			
			ps.setInt(1, challenge.getChNo());
			ps.setInt(2, paging.getStartNo());
			ps.setInt(3, paging.getEndNo());

			rs = ps.executeQuery(); //SQL 수행 및 결과집합 저장

			//조회 결과 처리
			while(rs.next()) {
				Participation pa = new Participation();

				//결과값 한 행 처리
				pa.setPaNo(rs.getInt("pa_no"));
				pa.setuNo(rs.getInt("u_no"));
				pa.setChNo(rs.getInt("ch_no"));
				pa.setPaCreateDate(rs.getDate("pa_create_date"));
				pa.setPaReview(rs.getString("pa_review"));
				pa.setPaIsSuccess(rs.getString("pa_is_success"));
				pa.setPaLike(rs.getString("pa_like"));
				//리스트에 결과값 저장
				participationList.add(pa);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DB객체 닫기
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}

		//최종 결과 반환

		return participationList;
	}
	//참가자 조회
	@Override
	public Participation selectParticipationReview(Connection conn, Participation participation) {
		String sql = "";
		sql += "SELECT * FROM ";
		sql += " participation";
		sql += " where pa_no = ?";

		//결과 저장할 객체
		Participation result = null;
		try {
			ps = conn.prepareStatement(sql); //SQL수행 객체
			
			ps.setInt(1, participation.getPaNo());
			rs = ps.executeQuery(); //SQL 수행 및 결과집합 저장

			//조회 결과 처리
			if(rs.next()) {
				result = new Participation();
				//결과값 한 행 처리
				result.setPaNo(rs.getInt("pa_no"));
				result.setuNo(rs.getInt("u_no"));
				result.setChNo(rs.getInt("ch_no"));
				result.setPaCreateDate(rs.getDate("pa_create_date"));
				result.setPaReview(rs.getString("pa_review"));
				result.setPaIsSuccess(rs.getString("pa_is_success"));
				result.setPaLike(rs.getString("pa_like"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DB객체 닫기
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}

		//최종 결과 반환

		return result;
	}
}
