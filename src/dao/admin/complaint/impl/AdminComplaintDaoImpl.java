package dao.admin.complaint.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.JDBCTemplate;
import dao.admin.complaint.face.AdminComplaintDao;
import dto.AdminComplaint;
import dto.Challenge;
import dto.Users;
import util.AdminComplaintPaging;
import util.Paging;


public class AdminComplaintDaoImpl implements AdminComplaintDao {

	private PreparedStatement ps = null;	//SQL수행 객체
	private ResultSet rs = null;			//SQL조회 결과 객체
	
	
	//-----------------신고 목록 조회------------------
	@Override
	public List<AdminComplaint> selectAll(Connection conn) {
			
		//SQL 작성
		String sql = "";
		sql += "SELECT com_no, com_date, ch_title, ca_title, com_content, ch_caution, com_manage";
		sql += "  FROM complaint com join challenge ch on com.ch_no = ch.ch_no join category ca on ch.ca_no = ca.ca_no";
		sql += " 	ORDER BY com_no DESC";
		
		//결과 저장할 List
		List<AdminComplaint> complaintList = new ArrayList<>();
		
		try {
			ps = conn.prepareStatement(sql);  //SQL수행 객체
			rs = ps.executeQuery(); //SQL수행 및 결과집합 저장
			
			//조회 결과 처리
			while(rs.next()) {
	
				AdminComplaint complaint = new AdminComplaint(); //결과값 저장 객체
			
				//결과값 한 행 처리
				complaint.setComNo(rs.getInt("com_no"));
				complaint.setComDate(rs.getDate("com_date"));
				complaint.setChTitle(rs.getString("ch_title"));
				complaint.setCaTitle(rs.getString("ca_title"));
				complaint.setComContent(rs.getString("com_content"));
				complaint.setChCaution(rs.getInt("ch_caution"));
				complaint.setComManage(rs.getString("com_manage"));
					
				//리스트에 결과값 저장
				complaintList.add(complaint);
			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DB객체 닫기
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}	
		//최종 결과 반환
		return complaintList;
	}
	
	
	
	
	//-----------------신고 목록(페이징 적용)------------------
	@Override
	public List<AdminComplaint> selectAll(Connection conn, AdminComplaintPaging complaintPaging) {
		
		//SQL 작성
		String sql = "";
		sql += "SELECT * FROM (";
		sql += "	SELECT rownum rnum, COM.* FROM (";
		sql += "		SELECT com_no, com_date, ch_title, ca_title, com_content, ch_caution, com_manage";
		sql += "			FROM complaint com join challenge ch";
		sql += "       		 on com.ch_no = ch.ch_no join category ca";
		sql += "       		 on ch.ca_no = ca.ca_no";
		sql += "			ORDER BY com_no DESC";
		sql += "		) COM";
		sql += " 	) COMPLAINT";
		sql += "  WHERE rnum BETWEEN ? AND ?";
		
		//결과 저장할 complaintList
		List<AdminComplaint> complaintList = new ArrayList<>();
		
		try {
			ps = conn.prepareStatement(sql); //SQL수행 객체
		
			ps.setInt(1, complaintPaging.getStartNo()); //페이지 시작번호
			ps.setInt(2, complaintPaging.getEndNo());	//페이지 끝번호
			rs = ps.executeQuery(); //SQL수행 및 결과집합 저장
			
			//조회결과 처리
			while(rs.next()) {
				AdminComplaint listComplaint = new AdminComplaint(); //결과값 저장 객체
			
				//결과값 한 행 처리
				listComplaint.setComNo(rs.getInt("com_no"));
				listComplaint.setComDate(rs.getDate("com_date"));
				listComplaint.setChTitle(rs.getString("ch_title"));
				listComplaint.setCaTitle(rs.getString("ca_title"));
				listComplaint.setComContent(rs.getString("com_content"));
				listComplaint.setChCaution(rs.getInt("ch_caution"));
				listComplaint.setComManage(rs.getString("com_manage"));
				
				//리스트에 결과값 저장
				complaintList.add(listComplaint);
			}
		} catch (SQLException e) {
			System.out.println("list.SQLException e: "+e);
			e.printStackTrace();
		} finally {
			//DB객체 닫기
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		//최종 결과 반환
		return complaintList;
	}
	
	
	//-----------------총 신고글 수------------------
	@Override
	public int selectCountAll(Connection conn) {
			
		String sql = "";
		sql += "SELECT count(*) count FROM complaint";
		
		int count = 0;
		
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				count = rs.getInt(1);
			}
		
		} catch (SQLException e) {
			System.out.println("selectCount SQLException e: "+e);
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		return count;
	}
	
	
	//-----------------검색한 신고글 갯수 조회-----------
	@Override
	public int getCntList(Connection conn, String search_type, String search_keyword) {
		//결과를 저장할 변수 생성
		int cntNum = 0;
		
		String sql = "";
		sql += "SELECT count(*) FROM complaint COM "	+ 
				"INNER JOIN challenge C "+ 
				"ON COM.ch_no = C.ch_no " +
				"WHERE " + search_type + " like '%' || ? || '%'" ;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, search_keyword);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				cntNum = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		return cntNum;
	}
	
	
	@Override
	public List<AdminComplaint> getSearchComplaints(Connection conn, String search_type, String search_keyword,
			Paging paging) {
		String sql = "";
		sql += "SELECT * FROM  " + 
				"(SELECT ROWNUM rnum, LIST.* FROM  " + 
				"(SELECT COM.com_no, COM.com_date, C.ch_title, CA.ca_title  " + 
				", COM.com_content, C.ch_caution, COM.com_manage from complaint com  " + 
				"inner join challenge C  " + 
				"on com.ch_no = C.ch_no  " + 
				"inner join CATEGORY CA  " + 
				"ON CA.ca_no = C.ca_no  " + 
				"where " + search_type  + " like '%' || ? || '%'  " + 
				"ORDER BY COM.com_no DESC)LIST)R  " + 
				"WHERE rnum between ? and ?  ";
		
		List<AdminComplaint> list = new ArrayList<>();
		AdminComplaint adminComplaint = null;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, search_keyword);
			ps.setInt(2, paging.getStartNo());
			ps.setInt(3, paging.getEndNo());
			
			rs = ps.executeQuery();
			while(rs.next()) {
				adminComplaint = new AdminComplaint();
				adminComplaint.setComNo(rs.getInt("com_no"));
				adminComplaint.setComDate(rs.getDate("com_date"));
				adminComplaint.setChTitle(rs.getString("ch_title"));
				adminComplaint.setCaTitle(rs.getString("ca_title"));
				adminComplaint.setComContent(rs.getString("com_content"));
				adminComplaint.setChCaution(rs.getInt("ch_caution"));
				adminComplaint.setComManage(rs.getString("com_manage"));
				
				list.add(adminComplaint);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	//-----------------신고 상세조회------------------
	@Override
	public AdminComplaint selectComplaintByComNo(Connection conn, AdminComplaint comNo) {
		
		//SQL작성
		String sql = "";
		sql += 
		sql += "SELECT ch_title, ch_caution, ca_title, ch_content, ch_money, com_no, ch_create_date";
		sql += "       , com_date, ch_start_date, u_no, ch_end_date, u_name, u_id, cec_title, com_content";
		sql += "       , ch_start_time, ch_end_time, com_admin_content, ch_way, com_manage";
		sql += "   FROM complaint com join challenge ch";
		sql += "    on com.ch_no = ch.ch_no join category ca";
		sql += "    on ch.ca_no = ca.ca_no join certification_cycle cec";
		sql += "    on ch.cec_no = cec.cec_no join users u";
		sql += "  	on com.u_no = u.u_no";
		sql += " 	WHERE com_no = ?";
		
		AdminComplaint viewComplaint = null; 	//결과 저장할 viewComplaint 객체
		
		
		try {
			ps = conn.prepareStatement(sql); //SQL수행 객체
			ps.setInt(1, comNo.getComNo());	//조회할 신고글번호(comNo) 적용
			
			rs = ps.executeQuery(); //SQL수행 및 결과집합 저장
			
			//조회결과 처리
			while(rs.next()) {
				viewComplaint = new AdminComplaint();	//결과값 저장 객체

				//결과값 한 행 처리
				viewComplaint.setChTitle(rs.getString("ch_title"));
				viewComplaint.setChCaution(rs.getInt("ch_caution"));
				viewComplaint.setCaTitle(rs.getString("ca_title"));
				viewComplaint.setChContent(rs.getString("ch_content"));
				viewComplaint.setChMoney(rs.getInt("ch_money"));
				viewComplaint.setComNo(rs.getInt("com_no"));
				viewComplaint.setChCreateDate(rs.getDate("ch_create_date"));
				viewComplaint.setComDate(rs.getDate("com_date"));
				viewComplaint.setChStartDate(rs.getDate("ch_start_date"));
				viewComplaint.setComUno(rs.getInt("u_no"));
				viewComplaint.setChEndDate(rs.getDate("ch_end_date"));
				viewComplaint.setComUname(rs.getString("u_name"));
				viewComplaint.setComUid(rs.getString("u_id"));
				viewComplaint.setCecTitle(rs.getString("cec_title"));
				viewComplaint.setComContent(rs.getString("com_content"));
				viewComplaint.setChStartTime(rs.getTime("ch_start_time"));
				viewComplaint.setChEndTime(rs.getTime("ch_end_time"));
				viewComplaint.setComAdminContent(rs.getString("com_admin_content"));
				viewComplaint.setChWay(rs.getString("ch_way"));
				viewComplaint.setComManage(rs.getString("com_manage"));
	
			}
		} catch (SQLException e) {
			System.out.println("viewComplaint SQLException e: "+e);
			e.printStackTrace();
		} finally {
			//DB객체 닫기
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		//최종결과 반환
		return viewComplaint;
	}
	
	
	//-----------(comNo)로 챌린지개설자 아이디 조회-------
	@Override
	public String selectChUidByComNo(Connection conn, AdminComplaint viewComplaint) {
			
		//SQL작성
		String sql = "";
		sql += "SELECT u_id FROM users u JOIN challenge ch";
		sql += "	ON u.u_no = ch.u_no JOIN complaint com ON ch.ch_no = com.ch_no";
		sql += " where com_no = ?";
			
		String chUid = null;  //결과 저장할 String 변수

		try {
			ps = conn.prepareStatement(sql); //SQL수행 객체
			ps.setInt(1, viewComplaint.getComNo());  //조회할 comNo 적용
				
			rs = ps.executeQuery(); //SQL수행 및 결과집합 저장	
				
			//조회결과
			while(rs.next()) {
				chUid = rs.getString("u_id");
				viewComplaint.setChUid(rs.getString("u_id"));
			}	
		
		} catch (SQLException e) {
			System.out.println("selectChUid SQLException e:" +e);
			e.printStackTrace();
		} finally {
			//DB객체 닫기
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		return chUid;
	}
	
	@Override
	public int selectChNoByComNo(Connection conn, AdminComplaint viewComplaint) {
			
		//SQL작성
		String sql = "";
		sql += "SELECT u_id,ch.ch_no FROM users u JOIN challenge ch";
		sql += "	ON u.u_no = ch.u_no JOIN complaint com ON ch.ch_no = com.ch_no";
		sql += " where com_no = ?";
			
		int chNo= 0;  //결과 저장할 String 변수

		try {
			ps = conn.prepareStatement(sql); //SQL수행 객체
			ps.setInt(1, viewComplaint.getComNo());  //조회할 comNo 적용
				
			rs = ps.executeQuery(); //SQL수행 및 결과집합 저장	
				
			//조회결과
			while(rs.next()) {
				chNo = rs.getInt("ch_no");
			}	
		
		} catch (SQLException e) {
			System.out.println("selectChUid SQLException e:" +e);
			e.printStackTrace();
		} finally {
			//DB객체 닫기
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		return chNo;
	}
	


	//-----------(chUid)로 개설자 경고횟수 조회------------
	@Override
	public int getNumber(Connection conn, String chUid) {
			
		//SQL작성
		String sql = "";
		sql += "SELECT u_caution FROM USERS WHERE u_id = ?";
			
		int cntCaution = 0;  //결과 저장할 int 변수

		try {	
			ps = conn.prepareStatement(sql); //SQL수행 객체
			ps.setString(1, chUid);  //조회할 chUid 적용
					
			rs = ps.executeQuery(); //SQL수행 및 결과집합 저장	
					
			//조회결과				
			while(rs.next()) {
				cntCaution = rs.getInt(1);
			}	
			
		} catch (SQLException e) {
			System.out.println("selectChUcaution SQLException e:" +e);
			e.printStackTrace();
		} finally {
			//DB객체 닫기
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		return  cntCaution;
	}
	
		
	//-----------------신고 수정------------------
	@Override
	public int update(Connection conn, AdminComplaint complaint) {
		
		//수정 쿼리
		String sql = "";
		sql += "UPDATE complaint SET com_admin_content = ?, com_manage = ? WHERE com_no = ?";
			
		//DB객체
		PreparedStatement ps = null;
		int res = -1;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, complaint.getComAdminContent());
			ps.setString(2, complaint.getComManage());
			ps.setInt(3, complaint.getComNo());
				
			res = ps.executeUpdate();
						
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			try {
				if(ps!=null) 	ps.close(); //DB객체 닫기
				
			} catch (SQLException e) {
				System.out.println("update SQLException e: "+e);
				e.printStackTrace();
			}
		}
	return res;
	}
	
	
	
	
	//-----------------신고 삭제------------------
	@Override
	public int delete(Connection conn, AdminComplaint complaint) {
		
	//삭제 쿼리
	String sql = "";
	sql += "DELETE complaint WHERE com_no = ?";
			//DB객체
	PreparedStatement ps = null;
	int res = -1;
	
		try {
			//DB작업
			ps = conn.prepareStatement(sql);
			ps.setInt(1, complaint.getComNo());
			
			res = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		
		} finally {
				try {
					if(ps!=null)	ps.close(); //DB객체 닫기
					
				} catch (SQLException e) {
					System.out.println("delete SQLException e: "+e);
					e.printStackTrace();
				}
		}
		return res;
	}

	
	//----------------신고 등록-----------------------
	
		@Override
		public int insert(Connection conn, AdminComplaint complaint) {
			
			String sql = "";
			sql += "INSERT INTO complaint(com_no, u_no, ch_no, com_content, com_admin_content, com_manage)";
			sql += " VALUES(complaint_seq.nextval, ?, ?, ?, ?, ?)";
			
			int res = 0;
			
			try {
				ps = conn.prepareStatement(sql);
				ps.setInt(1, complaint.getComUno());
				ps.setInt(2, complaint.getChNo());
				ps.setString(3, complaint.getComContent());
				ps.setString(4, complaint.getComAdminContent());
				ps.setString(5, complaint.getComManage());
				
				res = ps.executeUpdate();
				
			} catch (SQLException e) {
				System.out.println("insert SQLException e:" +e);
				e.printStackTrace();
			} finally {
				JDBCTemplate.close(ps);
			}
			return res;
		}

		
	//----------------챌린지 경고횟수 증가-----------------------
		
	@Override
	public int chCaution(Connection conn,  AdminComplaint complaint) {
				
		//SQL작성
		String sql = "";
		sql += "UPDATE challenge SET ch_caution = ch_caution + 1 WHERE ch_no = ?";
		
		PreparedStatement ps = null;
		int res = -1;		
		
		try {
			ps = conn.prepareStatement(sql); //SQL수행 객체
			ps.setInt(1, complaint.getChNo());  //조회할 chNo 적용
				
			rs = ps.executeQuery(); //SQL수행 및 결과집합 저장	
					
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			try {
				if(ps!=null) 	ps.close(); //DB객체 닫기
				
			} catch (SQLException e) {
				System.out.println("chCaution SQLException e: "+e);
				e.printStackTrace();
			}
		}
		return res;
	}
	
			
	//----------------개설자(회원) 경고횟수 증가-----------------------	
	@Override
	public int chUcaution(Connection conn, String chUid) {
			
		//SQL작성
		String sql = "";
		sql += "UPDATE users SET u_caution = u_caution + 1 WHERE u_id = ?";
			
		PreparedStatement ps = null;
		int res = -1; 

		try {	
			ps = conn.prepareStatement(sql); //SQL수행 객체
			ps.setString(1, chUid);  //조회할 chUid 적용
					
			rs = ps.executeQuery(); //SQL수행 및 결과집합 저장	
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			try {
				if(ps!=null) 	ps.close(); //DB객체 닫기
				
			} catch (SQLException e) {
				System.out.println("chCaution SQLException e: "+e);
				e.printStackTrace();
			}
		}
		return res;
	}
	
	
	@Override
	public int upChallengeCaution(Connection conn, int chNo) {
		String sql="";
		sql +="update challenge";
		sql +=" set ch_caution = ch_caution+1";
		sql += "where ch_no =?";
		
		int res = 0;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, chNo);
			
			res = ps.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("insert SQLException e:" +e);
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(ps);
		}
		
		return res;
	}
	@Override
	public int updateComplaint(Connection conn, int comNo) {
		String sql="";
		sql +="update complaint";
		sql +=" set COM_MANAGE = 'Y'";
		sql += "where com_no =?";
		
		int res = 0;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, comNo);
			
			res = ps.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("insert SQLException e:" +e);
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(ps);
		}
		
		return res;
	}
	@Override
	public int updateUsersCaution(Connection conn, int chNo) {
		String sql="";
		sql +="update users";
		sql +=" set u_caution = u_caution+1 ";
		sql += " where u_no = (select u_no from challenge where ch_no = ?)";
		
		int res = 0;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, chNo);
			
			res = ps.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("insert SQLException e:" +e);
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(ps);
		}
		
		return res;
	}
	@Override
	public int selectChByCaution(Connection conn, int chNo) {
		String sql="";
		sql +="select ch_caution from challenge";
		sql +=" where ch_no=?";
		int count= 0;  //결과 챌린지 경고 누적 횟수

		try {
			ps = conn.prepareStatement(sql); //SQL수행 객체
			ps.setInt(1, chNo);  //조회할 comNo 적용
				
			rs = ps.executeQuery(); //SQL수행 및 결과집합 저장	
				
			//조회결과
			while(rs.next()) {
				count = rs.getInt("ch_caution");
			}	
		
		} catch (SQLException e) {
			System.out.println("selectChUid SQLException e:" +e);
			e.printStackTrace();
		} finally {
			//DB객체 닫기
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		return count;
	}
	@Override
	public int downChallengeCaution(Connection conn, int chNo, int caution) {
		String sql="";
		sql +="update challenge";
		sql +=" set ch_caution = ch_caution + (?)";
		sql += " where ch_no =?";
		
		int res = 0;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, caution);
			ps.setInt(2, chNo);
			
			res = ps.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("insert SQLException e:" +e);
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(ps);
		}
		
		return res;
	}
	@Override
	public int downUsersCaution(Connection conn, int chNo, int caution) {
		String sql="";
		sql +="update users";
		sql +=" set u_caution = u_caution+(?) ";
		sql += " where u_no = (select u_no from challenge where ch_no = ?)";
		
		int res = 0;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, caution);
			ps.setInt(2, chNo);
			
			res = ps.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("insert SQLException e:" +e);
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(ps);
		}
		
		return res;
	}
}

