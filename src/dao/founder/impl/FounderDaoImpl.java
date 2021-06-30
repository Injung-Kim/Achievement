package dao.founder.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.JDBCTemplate;
import dao.founder.face.FounderDao;
import dto.Certification;
import dto.Challenge;
import dto.Member;
import dto.Mypage;
import dto.Participation;
import dto.Point;
import dto.Refunds;
import util.Paging;

public class FounderDaoImpl implements FounderDao {
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	
	@Override
	public int selectCntAll(Connection conn, Challenge challenge) {
		//sql 쿼리문
		String sql = "";
		sql += "select count(*) as cnt from participation P";
		sql += " inner join certification C";
		sql += " on P.pa_no = C.pa_no";
		sql += " where ch_no = ?";
		
		//총 인증글 수
		int cnt = 0;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1,challenge.getChNo());
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
	public Map<String, Object> selectAll(Connection conn, Paging paging, int chNo) {
		//SQL 작성
		String sql = "";
		sql += "SELECT * FROM (";
		sql += " 	SELECT rownum rnum, PCU.* FROM (";
		sql += " 		SELECT";
		sql += " 			ce_no, u_id, u_name, u_nick, ce_is_success";
		sql += " 		from participation P";
		sql += "		inner join certification C";
		sql += " 		on P.pa_no = C.pa_no";
		sql += " 		inner join users U";
		sql += " 		on U.u_no = P.u_no";
		sql += " 		where ch_no = ?";
		sql += " 		ORDER BY DECODE(ce_is_success,'W',1),ce_no DESC";
		sql += "	) PCU";
		sql += " ) participation";
		sql += " WHERE rnum BETWEEN ? AND ?";
		
		//결과 저장할 List
		Map<String, Object> result = new HashMap<String, Object>();
		
		List<Certification> certificationList = new ArrayList<>();
		List<Member> memberList = new ArrayList<>();
		try {
			ps = conn.prepareStatement(sql); //SQL수행 객체

			ps.setInt(1, chNo);
			ps.setInt(2, paging.getStartNo());
			ps.setInt(3, paging.getEndNo());

			rs = ps.executeQuery(); //SQL 수행 및 결과집합 저장

			//조회 결과 처리
			while(rs.next()) {
				Certification ce = new Certification();
				Member mem = new Member();
				//결과값 한 행 처리
				mem.setUid(rs.getString("u_id"));
				mem.setUsername(rs.getString("u_name"));
				mem.setNick(rs.getString("u_nick"));
				ce.setCeNo(rs.getInt("ce_no"));
				ce.setCeIsSuccess(rs.getString("ce_is_success"));
				//리스트에 결과값 저장
				certificationList.add(ce);
				memberList.add(mem);
			}
			result.put("memberList", memberList);
			result.put("certificationList", certificationList);
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
	public Map<String, Integer> selectByUno(Connection conn, int chNo) {
		String sql="";
		sql+="select U.u_no from users U";
		sql+=" inner join challenge C";
		sql+=" on C.u_no = U.u_no";
		sql+=" where ch_no = ?";
		
		Map<String, Integer> result = new HashMap<>();
		
		
		try {
			ps = conn.prepareStatement(sql); //SQL수행 객체
			ps.setInt(1, chNo);
			
			rs=ps.executeQuery();
			
			if(rs.next()) {
				result.put("uNo", rs.getInt("u_no"));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		} finally {
			//DB객체 닫기
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}

		return result;
	}
	@Override
	public Certification selectCertification(Connection conn, Certification certification) {
		String sql = "";
		sql += "select * from ";
		sql += " certification";
		sql += " where ce_no = ?";
		
		//인증 내역 결과
		Certification result = null;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1,certification.getCeNo());
			rs = ps.executeQuery();
			
			if(rs.next()) {
				result = new Certification();
				result.setCeNo(rs.getInt("ce_no"));
				result.setPaNo(rs.getInt("pa_no"));
				result.setCeOriginName(rs.getString("ce_origin_name"));
				result.setCeStoredName(rs.getString("ce_stored_name"));
				result.setCeCreateDate(rs.getDate("ce_create_date"));
				result.setCeUpdateDate(rs.getDate("ce_update_date"));
				result.setCeIsSuccess(rs.getString("ce_is_success"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace(); 
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		return result;
	}
	
	@Override
	public int whatherUpdate(Connection conn, Certification certification) {
		String sql = "";
		sql += "UPDATE certification ";
		sql += " set ce_is_success=?";
		sql += " where ce_no = ?";
		
		//인증 여부 수정 결과
		int res=-1;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, certification.getCeIsSuccess());
			ps.setInt(2,certification.getCeNo());
			res = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace(); 
		} finally {
			JDBCTemplate.close(ps);
		}
		
		return res;
	}
	@Override
	public int selectCeIsSuccess(Connection conn, int chNo) {
		String sql = "";
		sql += "select count(ce_is_success) as cnt from participation P";
		sql += " inner join certification C";
		sql += " on C.pa_no = P.pa_no";
		sql += " where ch_no = ? and ce_is_success ='W'";
		
		//인증이 처리되지 않은 개수
		int res=-1;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, chNo);
			rs = ps.executeQuery();
			if(rs.next()) {
				res = rs.getInt("cnt");
			}
			
		} catch (SQLException e) {
			e.printStackTrace(); 
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return res;
	}
	
	@Override
	public Date selectByEndDate(Connection conn, int chNo) {
		String sql ="";
		sql += "select ch_end_date from";
		sql += " challenge";
		sql +=" where ch_no =?";
		
		Date endDate = null;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, chNo);
			rs = ps.executeQuery();
			if(rs.next()) {
				endDate = rs.getDate("ch_end_date");
			}
			
		} catch (SQLException e) {
			e.printStackTrace(); 
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
				
		return endDate;
	}
	@Override
	public Date selectByStartDate(Connection conn, int chNo) {
		String sql ="";
		sql += "select ch_start_date from";
		sql += " challenge";
		sql +=" where ch_no =?";
		
		Date startDate = null;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, chNo);
			rs = ps.executeQuery();
			if(rs.next()) {
				startDate = rs.getDate("ch_start_date");
			}
			
		} catch (SQLException e) {
			e.printStackTrace(); 
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
				
		return startDate;
	}
	
	@Override
	public int chStateUpdate(Connection conn, int chNo) {
		//챌린지 상태 업데이트 (종료)
		String sql ="";
		sql +="UPDATE challenge SET";
		sql +=" ch_state='N'";
		sql +=" where ch_no =?";
		
		int res = -1;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, chNo);
			res = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace(); 
		} finally {
			JDBCTemplate.close(ps);
		}
		
		return res;
	}
	@Override
	public List<Integer> selectAllByPaNo(Connection conn, int chNo) {
		//모든 참가자 번호 반환
		String sql="";
		sql+="SELECT pa_no";
		sql+=" FROM participation";
		sql+=" WHERE ch_no=?";
		
		List<Integer> list = new ArrayList<Integer>();
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, chNo);
			rs= ps.executeQuery();
			while(rs.next()) {
				list.add(rs.getInt("pa_no"));
			}
			
		}catch (SQLException e) {
			e.printStackTrace(); 
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		
		return list;
	}
	
	@Override
	public int selectByCecNo(Connection conn, int chNo) {
		//인증 주기 번호 불러오기
		String sql="";
		sql+="SELECT cec_no";
		sql+=" FROM challenge";
		sql+=" WHERE ch_no=?";
		int cecNo = -1;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, chNo);
			rs = ps.executeQuery();
			if(rs.next()) {
				cecNo = rs.getInt("cec_no");
			}
			
		} catch (SQLException e) {
			e.printStackTrace(); 
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return cecNo;
	}
	
	@Override
	public int selectByCycle(Connection conn, int cecNo) {
		//인증 주기 불러오기
		String sql="";
		sql+="SELECT cec_cycle";
		sql+=" FROM CERTIFICATION_CYCLE";
		sql+=" WHERE cec_no=?";
		int cycle = -1;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, cecNo);
			rs = ps.executeQuery();
			if(rs.next()) {
				cycle = rs.getInt("cec_cycle");
			}
			
		} catch (SQLException e) {
			e.printStackTrace(); 
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return cycle;
	}
	
	@Override
	public int selectByCount(Connection conn, int cecNo) {
		//인증 횟수 불러오기
		String sql="";
		sql+="SELECT cec_count";
		sql+=" FROM CERTIFICATION_CYCLE";
		sql+=" WHERE cec_no=?";
		int count = -1;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, cecNo);
			rs = ps.executeQuery();
			if(rs.next()) {
				count = rs.getInt("cec_count");
			}
			
		} catch (SQLException e) {
			e.printStackTrace(); 
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return count;
	}
	
	@Override
	public Map<Integer, Integer> selectPaCount(Connection conn, List<Integer> paNoList, int chNo) {
		//인증 성공 횟수 불러오기
		String sql="";
		sql+="select p.pa_no, count(ce_is_success) as cnt from certification CE";
		sql+=" inner join participation P";
		sql+=" on CE.pa_no = P.pa_no";
		sql+=" inner join challenge C";
		sql+=" on c.ch_no = p.ch_no";
		sql+=" where P.ch_no = ?";
		sql+=" AND ce_is_success='Y'";
		sql+=" group by rollup(p.pa_no)";
		sql+=" order by p.pa_no desc";
		
		Map<Integer, Integer> result = new HashMap<>();
		//모든 값을 0으로 초기화
		for(int i=0; i<paNoList.size(); i++) {
			result.put(paNoList.get(i), 0);
		}
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, chNo);
			rs = ps.executeQuery();
			
			
			while(rs.next()) {
				for(int i=0; i<paNoList.size(); i++) {
					//참가자 번호가 같을시
					if(paNoList.get(i)==rs.getInt("pa_no")){
						//참가자 번호가 같을시 cnt을 value값으로 넣어준다
						result.put(rs.getInt("pa_no"), rs.getInt("cnt"));
					}
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace(); 
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
				
		return result;
	}
	@Override
	public int selectByPoNo(Connection conn) {
		String sql="";
		sql += "select point_seq.nextval";
		sql += " from dual";
		
		int poNo = 0;
		try {
			ps = conn.prepareStatement(sql); //SQL수행 객체
			rs = ps.executeQuery(); //SQL 수행 및 결과집합 저장
			//조회 결과 처리
			if(rs.next()) {
				poNo=rs.getInt(1);			
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DB객체 닫기
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		
		return poNo;
	}
	@Override
	public int insertPoint(Connection conn, Point point) {
		String sql="";
		sql += "INSERT INTO point(";
		sql += "po_no,";
		sql	+= "pa_no,";
		sql	+= "po_point,";
		sql	+= "po_rate )";
		sql += " VALUES(?, ?, ?, ?)";
		
		int res= -1;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, point.getPoNo());
			ps.setInt(2, point.getPaNo());
			ps.setInt(3, point.getPoPoint());
			ps.setDouble(4, point.getPoRate());
			
			res = ps.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DB객체 닫기
			JDBCTemplate.close(ps);
		}
		
		
		return res;
	}
	@Override
	public int getuNo(Connection conn, Integer key) {
		String sql="";
		sql += "select u_no";
		sql += " from participation";
		sql +=" where pa_no=?";
		
		int uNo = 0;
		try {
			ps = conn.prepareStatement(sql); //SQL수행 객체
			ps.setInt(1, key);
			rs = ps.executeQuery(); //SQL 수행 및 결과집합 저장
			
			//조회 결과 처리
			if(rs.next()) {
				uNo = rs.getInt("u_no");
			}

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
	public int updateMypage(Connection conn, Mypage mypage) {
		//마이페이지 수정
		String sql="";
		sql += "UPDATE mypage";
		sql += " SET";
		sql += " m_c_point = m_c_point+?,";
		sql += " m_a_point = m_a_point+?,";
		sql += " m_fail = m_fail+?,";
		sql += " m_acchall = m_acchall+?";
		sql +=" where m_no=?";
		
		int res= -1;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, mypage.getmCpoint());
			ps.setInt(2, mypage.getmApoint());
			ps.setInt(3, mypage.getmFail());
			ps.setInt(4, mypage.getmAcchall());
			ps.setInt(5, mypage.getmNo());
			
			res = ps.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DB객체 닫기
			JDBCTemplate.close(ps);
		}
		return res;
	}
	@Override
	public int updatePaIsSuccess(Connection conn, Integer key, String isSuccess) {
		//참가자 챌린지 성공
		String sql="";
		sql +="UPDATE participation";
		sql +=" SET";
		sql +=" pa_is_success=?";
		sql +=" where pa_no=?";
		
		int res= -1;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, isSuccess);
			ps.setInt(2, key);
			res = ps.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DB객체 닫기
			JDBCTemplate.close(ps);
		}
		return res;
		
	}
	@Override
	public Participation selectPaIsSuccess(Connection conn,Integer key) {
		String sql="";
		sql += "select pa_no,u_no, ch_no";
		sql += " from participation";
		sql +=" where pa_no=?";
		
		Participation participation=null;
		try {
			ps = conn.prepareStatement(sql); //SQL수행 객체
			ps.setInt(1, key);
			rs = ps.executeQuery(); //SQL 수행 및 결과집합 저장
			
			//조회 결과 처리
			if(rs.next()) {
				participation = new Participation();
				participation.setChNo(rs.getInt("ch_no")); //챌린지 번호
				participation.setuNo(rs.getInt("u_no")); //유저 챌린지
				participation.setPaNo(rs.getInt("pa_no"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DB객체 닫기
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		return participation;
	}
	@Override
	public Refunds selectRefunds(Connection conn, Participation participation) {
		String sql="";
		sql += "select";
		sql += " u_name,";
		sql += " u_bank,";
		sql += " paym_amount,";
		sql += " imp_uid,";
		sql += " merchant_uid,";
		sql += " u_account";
		sql += " from users U";
		sql += " inner join payment P";
		sql +=" on U.u_no = P.u_no";
		sql +=" where P.u_no = ? AND P.ch_no=?";
		
		Refunds refunds=null;
		try {
			ps = conn.prepareStatement(sql); //SQL수행 객체
			ps.setInt(1, participation.getuNo());
			ps.setInt(2, participation.getChNo());
			rs = ps.executeQuery(); //SQL 수행 및 결과집합 저장
			
			//조회 결과 처리
			if(rs.next()) {
				refunds = new Refunds();
				refunds.setPaNo(participation.getPaNo()); //paNo
				refunds.setPaybTaxFree(0); //면세금액
				refunds.setPaybRefundBank(rs.getString("u_bank")); //은행 이름(service에서 kg이니시스  코드로 변환)
				refunds.setPaybRefundAccount(rs.getString("u_account"));//계좌
				refunds.setImpUid(rs.getString("imp_uid")); //아임포트 고유번호
				refunds.setMerchantUid(rs.getString("merchant_uid")); //가맹접에서 전달 받은 고유번호
				refunds.setPaybRefundHolder(rs.getString("u_name")); //예금주
				refunds.setReAmount(rs.getInt("paym_amount")); //환불금액
				refunds.setPaybChecksum(rs.getInt("paym_amount")); //환불금액 체크
				refunds.setRePoint(0); //사용 포인트
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DB객체 닫기
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		return refunds;
	}
	@Override
	public int selectByReno(Connection conn) {
		String sql="";
		sql += "select refunds_seq.nextval";
		sql += " from dual";
		
		int reNo = 0;
		try {
			ps = conn.prepareStatement(sql); //SQL수행 객체
			rs = ps.executeQuery(); //SQL 수행 및 결과집합 저장
			//조회 결과 처리
			if(rs.next()) {
				reNo=rs.getInt(1);			
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DB객체 닫기
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		
		return reNo;
	}
	
	@Override
	public int insertRefunds(Connection conn, Refunds refunds) {
		String sql="";
		sql += "INSERT INTO refunds(";
		sql += "re_no,";
		sql	+= "pa_no,";
		sql	+= "re_point,";
		sql	+= "re_amount,";
		sql	+= "payb_tax_free,";
		sql	+= "payb_checksum,";
		sql	+= "payb_reason,";
		sql	+= "payb_refund_holder,";
		sql	+= "payb_refund_bank,";
		sql	+= "payb_refund_account,";
		sql	+= "imp_uid,";
		sql	+= "merchant_uid,";
		sql += "refund_availability,";
		sql += "refundable_amount)";
		sql += " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		int res= -1;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, refunds.getReNo());
			ps.setInt(2, refunds.getPaNo());
			ps.setInt(3, refunds.getRePoint());
			ps.setInt(4, refunds.getReAmount());
			ps.setInt(5, refunds.getPaybTaxFree());
			ps.setInt(6, refunds.getPaybChecksum());
			ps.setString(7, refunds.getPaybReason());
			ps.setString(8, refunds.getPaybRefundHolder());
			ps.setString(9, refunds.getPaybRefundBank());
			ps.setString(10, refunds.getPaybRefundAccount());
			ps.setString(11, refunds.getImpUid());
			ps.setString(12, refunds.getMerchantUid());
			ps.setString(13, refunds.getRefundAvailability());
			ps.setInt(14, refunds.getRefundableAmount());
			res = ps.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DB객체 닫기
			JDBCTemplate.close(ps);
		}
		
		return res;
	}
	@Override
	public String selectChallengeCheck(Connection conn, int chNo) {
		String sql= "";
		sql += "select ch_state";
		sql += " from challenge";
		sql +=" where ch_no=?";
		
		String chState=null;
		try {
			ps = conn.prepareStatement(sql); //SQL수행 객체
			ps.setInt(1, chNo);
			rs = ps.executeQuery(); //SQL 수행 및 결과집합 저장
			
			//조회 결과 처리
			if(rs.next()) {
				chState = rs.getString("ch_state");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DB객체 닫기
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		return chState;
	}
	
}
