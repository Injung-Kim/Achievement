package dao.participant.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.JDBCTemplate;
import dao.participant.face.ParticipantDao;
import dto.Certification;
import dto.CertificationCycle;
import dto.Challenge;
import dto.Complaint;
import dto.Member;
import dto.Participation;
import dto.Payback;
import dto.Payment;
import util.Paging;

public class ParticipantDaoImpl implements ParticipantDao {
	private PreparedStatement ps= null;
	private ResultSet rs = null;
	
	
	@Override
	public int selectPaNo(Connection conn) {
		String sql="";
		sql += "select participation_seq.nextval";
		sql += " from dual";
		
		int paNo = 0;
		try {
			ps = conn.prepareStatement(sql); //SQL수행 객체
			rs = ps.executeQuery(); //SQL 수행 및 결과집합 저장
			//조회 결과 처리
			if(rs.next()) {
				paNo=rs.getInt(1);			
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DB객체 닫기
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		
		return paNo;
	}
	@Override
	public int selectPaymNo(Connection conn) {
		String sql="";
		sql += "select payment_seq.nextval";
		sql += " from dual";
		
		int paymNo = 0;
		try {
			ps = conn.prepareStatement(sql); //SQL수행 객체
			rs = ps.executeQuery(); //SQL 수행 및 결과집합 저장
			//조회 결과 처리
			if(rs.next()) {
				paymNo=rs.getInt(1);			
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DB객체 닫기
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		
		return paymNo;
	}
	@Override
	public int selectCeNo(Connection conn) {
		String sql="";
		sql += "select certification_seq.nextval";
		sql += " from dual";
		
		int ceNo = 0;
		try {
			ps = conn.prepareStatement(sql); //SQL수행 객체
			rs = ps.executeQuery(); //SQL 수행 및 결과집합 저장
			//조회 결과 처리
			if(rs.next()) {
				ceNo=rs.getInt(1);			
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DB객체 닫기
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		
		return ceNo;
	}
	
	
	@Override
	public int attendInsert(Connection conn, Participation participation) {
		//참가자 삽입 쿼리
		
		String sql = "";
		sql += "insert into Participation(";
		sql += " pa_no,";
		sql += " ch_no,";
		sql += " u_no,";
		sql += " pa_is_success,";
		sql += " pa_review)";
		sql += "  VALUES(?,?,?,?,?)";
				
		int res = 0;

		try {
			
			ps = conn.prepareStatement(sql);
			ps.setInt(1, participation.getPaNo());
			ps.setInt(2, participation.getChNo());
			ps.setInt(3, participation.getuNo());
			ps.setString(4, participation.getPaIsSuccess());
			ps.setString(5, participation.getPaReview());
			
			res = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(ps);
		}

		return res;
	}
	@Override
	public int paymentInsert(Connection conn, Payment payment) {
		//결제내역 삽입 쿼리

		String sql = "";
		sql += "insert into payment(";
		sql += " paym_no,";
		sql += " u_no,";
		sql += " ch_no,";
		sql += " paym_name,";
		sql += " paym_amount,";
		sql += " paym_apply_num,";
		sql += " imp_uid,";
		sql += " merchant_uid)";
		sql += "  VALUES(?,?,?,?,?,?,?,?)";

		int res = 0;

		try {

			ps = conn.prepareStatement(sql);
			ps.setInt(1, payment.getPaymNo());
			ps.setInt(2, payment.getuNo());
			ps.setInt(3, payment.getChNo());
			ps.setString(4, payment.getPaymName());
			ps.setInt(5, payment.getPaymAmount());
			ps.setString(6, payment.getPaymApplyNum());
			ps.setString(7, payment.getImpUid());
			ps.setString(8, payment.getMerchantUid());
			res = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(ps);
		}

		return res;
	}
	@Override
	public int certificationInsert(Connection conn, Certification certification) {
		//인증내역 삽입 쿼리

		String sql = "";
		sql += "insert into certification(";
		sql += " ce_no,";
		sql += " pa_no,";
		sql += " ce_origin_name,";
		sql += " ce_stored_name)";
		sql += "  VALUES(?,?,?,?)";

		int res = 0;

		try {

			ps = conn.prepareStatement(sql);
			ps.setInt(1, certification.getCeNo());
			ps.setInt(2, certification.getPaNo());
			ps.setString(3, certification.getCeOriginName());
			ps.setString(4, certification.getCeStoredName());
			res = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(ps);
		}

		return res;
	}
	@Override
	public Certification selectByStoredName(Connection conn, Certification certification) {
		String sql="";
		sql += "select ce_stored_name";
		sql += " from certification";
		sql += " where ce_no =?";
		
		Certification result = null;
		try {
			ps = conn.prepareStatement(sql); //SQL수행 객체
			ps.setInt(1, certification.getCeNo());
			rs = ps.executeQuery(); //SQL 수행 및 결과집합 저장
			//조회 결과 처리
			if(rs.next()) {
				result = new Certification();
				result.setCeStoredName(rs.getString("ce_stored_name"));			
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
	public int selectByPaNo(Connection conn, int chNo, int uNo) {
		//참여 챌린지 번호 구하기
		String sql = "";
		sql += "SELECT * FROM participation";
		sql += " WHERE ch_no=? AND u_no=?";
		
		int paNo=0;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, chNo);
			ps.setInt(2, uNo);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				paNo = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return paNo;
	}
	
	@Override
	public String selectByTitle(Connection conn, int chNo) {
		//참여 챌린지 제목 구하기
		String sql = "";
		sql += "SELECT ch_title FROM challenge";
		sql += " WHERE ch_no=?";
		
		String chTitle=null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, chNo);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				chTitle = rs.getString("ch_title");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return chTitle;
	}
	
	@Override
	public String selectByChWay(Connection conn, int chNo) {
		//참여 챌린지 제목 구하기
		String sql = "";
		sql += "SELECT ch_way FROM challenge";
		sql += " WHERE ch_no=?";

		String chWay=null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, chNo);
			rs = ps.executeQuery();

			while(rs.next()) {
				chWay = rs.getString("ch_way");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}

		return chWay;
	}
	
	@Override
	public int selectCntAll(Connection conn, Participation participation) {
		String sql = "";
		sql += "SELECT count(*) cnt FROM certification";
		sql += " WHERE pa_no=?";
		
		//총 인증글 수
		int cnt = 0;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, participation.getPaNo());
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
	public int selectCntAll(Connection conn, Challenge challenge) {
		//참가한 챌린지중에 동일한 챌린지를 구한다
		String sql = "";
		sql += "SELECT count(*) cnt FROM participation";
		sql += " WHERE ch_no=?";
		
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
	public List<Certification> selectAllCertification(Connection conn, Paging paging, int paNo) { //수정 요망
		//SQL 작성
		String sql = "";
		sql += "SELECT * FROM (";
		sql += " 	SELECT rownum rnum, C.* FROM (";
		sql += " 		SELECT";
		sql += " 			ce_no, pa_no, ce_origin_name, ce_stored_name";
		sql += " 			,ce_create_date, ce_is_success, ce_update_date";
		sql += " 		FROM certification";
		sql += "		WHERE pa_no=?";
		sql += " 		ORDER BY ce_no DESC";
		sql += "	) C";
		sql += " ) certification";
		sql += " WHERE rnum BETWEEN ? AND ?";

		//결과 저장할 List
		List<Certification> certificationList = new ArrayList<>();

		try {
			ps = conn.prepareStatement(sql); //SQL수행 객체
			
			ps.setInt(1, paNo);
			ps.setInt(2, paging.getStartNo());
			ps.setInt(3, paging.getEndNo());

			rs = ps.executeQuery(); //SQL 수행 및 결과집합 저장

			//조회 결과 처리
			while(rs.next()) {
				Certification ce = new Certification();
				//결과값 한 행 처리
				ce.setCeNo(rs.getInt("ce_no"));
				ce.setPaNo(rs.getInt("pa_no"));
				ce.setCeOriginName(rs.getString("ce_origin_name"));
				ce.setCeStoredName(rs.getString("ce_stored_name"));
				ce.setCeCreateDate(rs.getDate("ce_create_date"));
				ce.setCeIsSuccess(rs.getString("ce_is_success"));
				ce.setCeUpdateDate(rs.getDate("ce_update_date"));
				//리스트에 결과값 저장
				certificationList.add(ce);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DB객체 닫기
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}

		//최종 결과 반환

		return certificationList;
	}
	@Override
	public List<Member> selectAllPartification(Connection conn, Paging paging, int chNo) {
		//SQL 작성
		String sql = "";
		sql += "SELECT * FROM (";
		sql += " 	SELECT rownum rnum, PU.* FROM (";
		sql += " 		SELECT";
		sql += " 			U.u_no, U.u_nick, U.u_name, U.u_id";
		sql += " 		from participation P";
		sql += " 		inner join users U";
		sql += " 		on U.u_no = p.u_no";
		sql += " 		where ch_no = ?";
		sql += " 		ORDER BY pa_no DESC";
		sql += "	) PU";
		sql += " ) users";
		sql += " WHERE rnum BETWEEN ? AND ?";

		//결과 저장할 List
		List<Member> memberList = new ArrayList<>();

		try {
			ps = conn.prepareStatement(sql); //SQL수행 객체
			ps.setInt(1, chNo);
			ps.setInt(2, paging.getStartNo());
			ps.setInt(3, paging.getEndNo());

			rs = ps.executeQuery(); //SQL 수행 및 결과집합 저장

			//조회 결과 처리
			while(rs.next()) {
				Member mem = new Member();
				//결과값 한 행 처리
				mem.setUno(rs.getInt("u_no"));
				mem.setNick(rs.getString("u_nick"));
				mem.setUsername(rs.getString("u_name"));
				mem.setUid(rs.getString("u_id"));
				
				//리스트에 결과값 저장
				memberList.add(mem);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DB객체 닫기
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}

		//최종 결과 반환

		return memberList;
	}
	
	@Override
	public Certification selectCertification(Connection conn, Certification certification) {
		//인증 조회하기
		String sql = "";
		sql += "SELECT * FROM certification";
		sql += " WHERE ce_no=?";

		Certification result = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, certification.getCeNo());
			rs = ps.executeQuery();

			while(rs.next()) {
				result = new Certification();
				result.setCeNo(rs.getInt("ce_no"));
				result.setCeIsSuccess(rs.getString("ce_is_success"));
				result.setPaNo(rs.getInt("pa_no"));
				result.setCeCreateDate(rs.getDate("ce_create_date"));
				result.setCeUpdateDate(rs.getDate("ce_update_date"));
				result.setCeOriginName(rs.getString("ce_origin_name"));
				result.setCeStoredName(rs.getString("ce_stored_name"));
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
	public int certificationUpdate(Connection conn, Certification certification) {
		String sql="";
		sql+="UPDATE certification SET";
		sql+=" ce_origin_name=?,";
		sql+=" ce_stored_name=?,";
		sql+=" ce_update_date=sysdate";
		sql+=" WHERE ce_no=?";
		
		int res = -1;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, certification.getCeOriginName());
			ps.setString(2, certification.getCeStoredName());
			ps.setInt(3, certification.getCeNo());
			res = ps.executeUpdate();
			

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return res;
	}
	@Override
	public int certificationDelete(Connection conn, Certification certification) {
		//인증 삭제
		String sql="";
		sql+="DELETE FROM certification";
		sql+=" WHERE ce_no=?";
		
		int res = -1;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, certification.getCeNo());
			res = ps.executeUpdate();
			

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return res;
		
	}
	@Override
	public boolean selectByComplaint(Connection conn, int chNo, int uNo) {
		//신고 여부 쿼리
		String sql="";
		sql += "SELECT count(*) AS cnt";
		sql += " FROM complaint";
		sql += " WHERE ch_no=? AND u_no=?";
		boolean paComplaint = false;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, chNo);
			ps.setInt(2, uNo);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				//신고 여부
				int cnt = rs.getInt(1);
				if(cnt >0) {
					paComplaint = true;
				}else {
					paComplaint= false;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		//결과값 반환
		return paComplaint;
	}
	
	@Override
	public boolean selectByPaLike(Connection conn, int chNo, int uNo) {
		//좋아요 여부 쿼리
		String sql="";
		sql += "SELECT pa_like";
		sql += " FROM participation";
		sql += " WHERE ch_no=? AND u_no=?";
		
		boolean paLike = false;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, chNo);
			ps.setInt(2, uNo);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				//좋아요 여부
				String temp = rs.getString("pa_like");
				if("Y".equals(temp)) {
					paLike = true;
				}else {
					paLike= false;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		//결과값 반환
		return paLike;
	}
	@Override
	public int updatePaLike(Connection conn, Participation participation) {
		//좋아요 여부
		String sql="";
		sql+="UPDATE participation set pa_like=?";
		sql+=" WHERE pa_no=?";
		
		int res = -1;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, participation.getPaLike());
			ps.setInt(2, participation.getPaNo());
			res = ps.executeUpdate();
			

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(ps);
		}
		
		return res;
	}
	//증감 여부
	@Override
	public int increaseChLike(Connection conn, Participation participation) {
		//좋아요 여부
		String sql="";
		sql+="UPDATE challenge";
		if("Y".equals(participation.getPaLike())) {
			sql+=" set ch_likes = ch_likes+1";
		}else if("N".equals(participation.getPaLike())){
			sql+=" set ch_likes = ch_likes-1";
		}
		sql+=" WHERE ch_no =?";
		
		int res = -1;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, participation.getChNo());
			res = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(ps);
		}
		return res;
	}
	@Override
	public int increaseMypageLike(Connection conn, Participation participation) {
		//좋아요 여부
		String sql="";
		sql+="UPDATE Mypage";
		if("Y".equals(participation.getPaLike())) {
			sql+=" set m_likes = m_likes+1";
		}else if("N".equals(participation.getPaLike())){
			sql+=" set m_likes = m_likes-1";
		}
		sql+=" WHERE m_no =?";

		int res = -1;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, participation.getuNo());
			res = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(ps);
		}
		return res;
	}
	
	
	//다음 신고 번호 가져오기
	@Override
	public int selectComNo(Connection conn) {
		String sql="";
		sql += "select complaint_seq.nextval";
		sql += " from dual";
		
		int comNo = 0;
		try {
			ps = conn.prepareStatement(sql); //SQL수행 객체
			rs = ps.executeQuery(); //SQL 수행 및 결과집합 저장
			//조회 결과 처리
			if(rs.next()) {
				comNo=rs.getInt(1);			
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DB객체 닫기
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		
		return comNo;
	}
	@Override
	public int complaintInsert(Connection conn, Complaint complaint) {
		String sql = "";
		sql += "insert into complaint(";
		sql += " com_no,";
		sql += " u_no,";
		sql += " ch_no,";
		sql += " com_content)";
		sql += "  VALUES(?,?,?,?)";
		int res = 0;

		try {

			ps = conn.prepareStatement(sql);
			ps.setInt(1, complaint.getComNo());
			ps.setInt(2, complaint.getuNo());
			ps.setInt(3, complaint.getChNo());
			ps.setString(4, complaint.getComContent());
			res = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(ps);
		}

		return res;
	}
	@Override
	public String selectByReview(Connection conn, int paNo) {
		String sql="";
		sql += "select pa_review";
		sql += " from participation";
		sql += " where pa_no =?";
		
		String review = null;
		
		try {

			ps = conn.prepareStatement(sql);
			ps.setInt(1, paNo);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				review = rs.getString("pa_review");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(ps);
		}
		
		return review;
	}
	@Override
	public int reviewInsert(Connection conn, Participation participation) {
		//리뷰 저장 및 수정
		String sql = "";
		sql += "UPDATE participation SET";
		sql += " pa_review=?";
		sql += " WHERE pa_no = ?";
		int res = 0;
		try {

			ps = conn.prepareStatement(sql);
			ps.setString(1, participation.getPaReview());
			ps.setInt(2, participation.getPaNo());
			res = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(ps);
		}

		return res;
	}
	@Override
	public Payback selectPayback(Connection conn, Map<String, Integer> paMap) {
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
		
		
		
		Payback payback = null;
		try {
			ps = conn.prepareStatement(sql); //SQL수행 객체
			ps.setInt(1, paMap.get("uNo"));
			ps.setInt(2, paMap.get("chNo"));
			rs = ps.executeQuery(); //SQL 수행 및 결과집합 저장
			
			//조회 결과 처리
			if(rs.next()) {
				payback = new Payback();
				payback.setChNo(paMap.get("chNo")); //챌린지 번호
				payback.setuNo(paMap.get("uNo")); //유저 번호
				payback.setPaybTaxFree(0); //면세금액
				payback.setPaybReason("챌린지취소"); //환불사유
				payback.setPaybAmount(rs.getInt("paym_amount")); //환불금액
				payback.setPaybRefundBank(rs.getString("u_bank")); //환불 은행(service에서 kg이니시스 코드로 변환)
				payback.setImpUid(rs.getString("imp_uid")); //아임포트에서 발급한 고유번호
				payback.setMerchantUid(rs.getString("merchant_uid")); // 가맹점에서 발급한 고유번호
				payback.setPaybChecksum(rs.getInt("paym_amount")); //환불 확인 금액
				payback.setPaybRefundHolder(rs.getString("u_name")); //예금주
				payback.setPaybReFundAccount(rs.getString("u_account")); //예금주 계좌번호
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DB객체 닫기
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		return payback;
	}
	
	@Override
	public int selectByPaybNo(Connection conn) {
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
	public int paybackInsert(Connection conn, Payback payback) {
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
	public int participationDelete(Connection conn, int paNo) {
		//참가자 삭제
		String sql = "";
		sql += "delete from participation";
		sql += " where pa_no=?";
		int res = 0;
		try {

			ps = conn.prepareStatement(sql);
			ps.setInt(1, paNo);
			res = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(ps);
		}
		
		return res;
	}
	@Override
	public int getCecNo(Connection conn, int chNo) {
		//인증 주기 번호 가져오기
		String sql ="";
		sql += "select cec_no";
		sql += " from challenge";
		sql += " where ch_no =?";
		
		int cecNo = 0;
		try {
			ps = conn.prepareStatement(sql); //SQL수행 객체
			ps.setInt(1, chNo);
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
	public CertificationCycle selectCertificationCycle(Connection conn, int cecNo) {
		//인증 주기 가져오기
		String sql ="";
		sql += "select*from certification_cycle";
		sql += " where cec_no =?";
		
		CertificationCycle certificationCycle = null;
		
		try {
			ps = conn.prepareStatement(sql); //SQL수행 객체
			ps.setInt(1, cecNo);
			rs = ps.executeQuery(); //SQL 수행 및 결과집합 저장
			//조회 결과 처리
			if(rs.next()) {
				certificationCycle = new CertificationCycle();
				certificationCycle.setCecNo(rs.getInt("cec_no"));
				certificationCycle.setCecTitle(rs.getString("cec_title"));
				certificationCycle.setCecCreateDate(rs.getDate("cec_create_date"));
				certificationCycle.setCecCycle(rs.getInt("cec_cycle"));
				certificationCycle.setCecCount(rs.getInt("cec_count"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DB객체 닫기
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		
		return certificationCycle;
	}
	@Override
	public Map<String, Date> selectChallengeDate(Connection conn, int chNo) {
		//챌린지 시작 날짜와 끝날짜 구해오기
		String sql ="";
		sql += "select";
		sql += " ch_start_date,";
		sql += " ch_end_date";
		sql += " from challenge";
		sql += " where ch_no =?";
		
		Map<String, Date> result = new HashMap<>();
		
		try {
			ps = conn.prepareStatement(sql); //SQL수행 객체
			ps.setInt(1, chNo);
			rs = ps.executeQuery(); //SQL 수행 및 결과집합 저장
			//조회 결과 처리
			if(rs.next()) {
				result.put("chStartDate", rs.getDate("ch_start_date"));
				result.put("chEndDate", rs.getDate("ch_end_date"));
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
	public int getCerCount(Connection conn, Map<String, Date> curSection, int paNo) {
		String sql ="";
		sql += "select count(*) as cnt from";
		sql += " certification";
		sql += " where ce_create_date";
		sql += " between to_date(?, 'yyyy-mm-dd') and to_date(?, 'yyyy-mm-dd')";
		sql += " and pa_no =?";
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		
		int count=0;
		
		try {
			ps = conn.prepareStatement(sql); //SQL수행 객체
			
			ps.setString(1, transFormat.format(curSection.get("chStartDate")));
			ps.setString(2, transFormat.format(curSection.get("chEndDate")));
			ps.setInt(3, paNo);
			
			rs = ps.executeQuery(); //SQL 수행 및 결과집합 저장
			//조회 결과 처리
			if(rs.next()) {
				count = rs.getInt("cnt");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DB객체 닫기
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		
		return count;
	}
	
}
