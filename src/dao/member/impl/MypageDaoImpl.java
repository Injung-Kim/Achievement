
package dao.member.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.JDBCTemplate;
import dao.member.face.MypageDao;
import dto.Challenge;
import dto.Member;
import dto.Mypage;
import dto.Participation;
import dto.Payback;
import dto.Payment;
import dto.Refunds;

public class MypageDaoImpl implements MypageDao {

	// db 연결 위한 객체
	PreparedStatement ps = null;
	ResultSet rs = null;

	@Override // user 정보를 가져오는 메소드
	public Member selectInfo(Connection conn, int uNo) {
		String sql = "SELECT * FROM USERS WHERE U_NO =?";
		Member member = new Member();

		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, uNo);
			rs = ps.executeQuery();
			while(rs.next()) {
				member.setUno(rs.getInt("u_no"));
				member.setUid(rs.getString("u_id"));
				member.setUpassword(rs.getString("u_password"));
				member.setUsername(rs.getString("u_name"));
				member.setEmail(rs.getString("u_email"));
				member.setChallenge(rs.getString("u_challenge"));
				member.setNick(rs.getString("u_nick"));
				member.setGender(rs.getString("u_gender"));
				member.setBirth(rs.getDate("u_birth"));
				member.setSingup(rs.getDate("u_signup"));
				member.setAccount(rs.getString("u_account"));
				member.setBank(rs.getString("u_bank"));
				member.setUgrade(rs.getString("u_grade"));
				member.setPost(rs.getString("u_post"));
				member.setAddress(rs.getString("u_address"));
				member.setPhone(rs.getString("u_phone"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		return member;
	}

	@Override // mypage 정보를 가져오는 메소드
	public Mypage selectMypageInfo(Connection conn, int getuNo) {
		String sql = "SELECT M_NO, M_ORIGIN_NAME, M_STORED_NAME, M_LIKES, M_COMMENT, M_POST, M_ACCHALL, M_FAIL, M_C_POINT, M_A_POINT FROM MYPAGE WHERE M_NO = ?";
		Mypage mypage = new Mypage();	
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, getuNo);
			rs = ps.executeQuery();
			while(rs.next()) {
				mypage.setmNo(rs.getInt("m_no"));
				if(rs.getString("m_origin_name") != null && rs.getString("m_stored_name") !=null) { 
					mypage.setmOriginname(rs.getString("m_origin_name"));
					mypage.setmStoredname(rs.getString("m_stored_name")); 
				}else {
					mypage.setmOriginname("null");
					mypage.setmStoredname("null");
				}
				mypage.setmLikes(rs.getInt("m_likes"));
				mypage.setmComment(rs.getInt("m_comment"));
				mypage.setmPost(rs.getInt("m_post"));
				mypage.setmAcchall(rs.getInt("m_acchall"));
				mypage.setmFail(rs.getInt("m_fail"));
				mypage.setmCpoint(rs.getInt("m_c_point"));
				mypage.setmApoint(rs.getInt("m_a_point"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}

		return mypage;
	}


	@Override // 유저정보를 삭제하는 메소드
	public int deleteUser(Connection conn, String mId, String mPw) {
		String sql = "DELETE USERS WHERE U_ID = ? AND U_PASSWORD = ?";
		int check = 0;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, mId);
			ps.setString(2, mPw);
			check = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(ps);
		}
		return check;
	}


	@Override // 유저마이페이지정보를 삭제하는 메소드
	public int deleteMypage(Connection conn, int userNo) {
		String sql = "DELETE MYPAGE WHERE M_NO = ? ";
		int check = 0;

		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userNo);
			check = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(ps);
		}
		return check;
	}

	@Override // 유저 정보를 업데이트 하는 메소드
	public int update(Connection conn, Member member) {
		String sql = "UPDATE USERS SET U_PASSWORD = ?, U_NICK = ?, U_CHALLENGE = ?, U_EMAIL = ?, U_PHONE = ? WHERE U_NO = ?";
		int check = 0;

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, member.getUpassword());
			ps.setString(2, member.getNick());
			ps.setString(3, member.getChallenge());
			ps.setString(4, member.getEmail());
			ps.setString(5, member.getPhone());		
			ps.setInt(6, member.getUno());
			check = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(ps);
		}
		return check;
	}

	@Override // 프로필 사진 정보를 업데이트 하는 메소드
	public int updateFile(Connection conn, Mypage mypage) {
		String sql = "UPDATE MYPAGE SET M_ORIGIN_NAME = ?,  M_STORED_NAME = ? WHERE M_NO = ?";
		int check = 0;

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, mypage.getmOriginname());
			ps.setString(2, mypage.getmStoredname());
			ps.setInt(3, mypage.getmNo());
			check = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(ps);
		}
		return check;
	}


	@Override // 이메일 존재 여부 체크 메소드
	public int selectEmailCheck(Connection conn, String email) {
		String sql = "SELECT COUNT(*) FROM USERS WHERE U_EMAIL= ?";
		int check = 0;

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			rs = ps.executeQuery();
			while(rs.next()) {
				check = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(ps);
		}
		return check;
	}

	@Override // 닉네임 존재 여부 체크 메소드
	public int selectNickCheck(Connection conn, String nick) {
		String sql = "SELECT COUNT(*) FROM USERS WHERE U_NICK =?";
		int check = 0;

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, nick);
			rs = ps.executeQuery();
			while(rs.next()) {
				check = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(ps);
		}
		return check;
	}


	@Override // 전화번호 존재 여부 체크 메소드
	public int selectPhoneCheck(Connection conn, String phone) {
		String sql = "SELECT COUNT(*) FROM USERS WHERE U_PHONE =?";
		int check = 0;

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, phone);
			rs = ps.executeQuery();
			while(rs.next()) {
				check = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(ps);
		}
		return check;
	}

	@Override
	public List<Challenge> selectAllUserChall(Connection conn, int uNo) {
		String sql = "SELECT * FROM CHALLENGE WHERE U_NO = ? AND TO_CHAR(SYSDATE, 'yyyy/MM/dd') <= TO_CHAR(CH_END_DATE, 'yyyy/MM/dd')";
		List<Challenge> list = new ArrayList<>(); //챌린지 전부를 담을 리스트
		Challenge chall = null; // 챌린지를 담을 객체

		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, uNo);
			rs = ps.executeQuery();
			while(rs.next()) {
				chall = new Challenge();
				chall.setChNo(rs.getInt("ch_no"));
				chall.setCaNo(rs.getInt("ca_no"));
				chall.setCecNo(rs.getInt("cec_no"));
				chall.setuNo(rs.getInt("u_no"));
				chall.setChTitle(rs.getString("ch_title"));
				chall.setChContent(rs.getString("ch_content"));
				chall.setChCreateDate(rs.getDate("ch_create_date"));
				chall.setChStartDate(rs.getDate("ch_start_date"));
				chall.setChEndDate(rs.getDate("ch_end_date"));
				chall.setChMoney(rs.getInt("ch_money"));
				chall.setChStartTime(rs.getString("ch_start_time"));
				chall.setChEndTime(rs.getString("ch_end_time"));
				chall.setChWay(rs.getString("ch_way"));
				chall.setChCaution(rs.getInt("ch_caution"));
				chall.setChLikes(rs.getInt("ch_likes"));
				chall.setChOriginName(rs.getString("ch_origin_name"));				
				chall.setChStoredName(rs.getString("ch_stored_name"));		
				chall.setChState(rs.getString("ch_state"));

				list.add(chall);
			}		
		} catch (SQLException e) {

			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);			
		}	
		return list;
	}


	@Override // 유저의 결제 정보를 가져오는 쿼리 메소드
	public List<Payment> selectUserPayment(Connection conn, int uNo) {
		String sql = "SELECT * FROM PAYMENT WHERE U_NO = ?";
		List<Payment> list = new ArrayList<>(); //결제 정보 전부를 담을 리스트
		Payment payment = null;

		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, uNo);
			rs = ps.executeQuery();
			while(rs.next()) {
				payment = new Payment();
				payment.setPaymNo(rs.getInt("paym_no"));
				payment.setuNo(rs.getInt("u_no"));
				payment.setChNo(rs.getInt("ch_no"));
				payment.setPaymName(rs.getString("paym_name"));
				payment.setPaymDate(rs.getDate("paym_Date"));
				payment.setPaymAmount(rs.getInt("paym_amount"));
				payment.setPaymApplyNum(rs.getString("paym_apply_num"));
				payment.setImpUid(rs.getString("imp_uid"));
				payment.setMerchantUid(rs.getString("merchant_uid"));					
				list.add(payment);
			}		
		} catch (SQLException e) {

			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);			
		}	
		return list;
	}


	@Override // 유저의 환급 정보를 가져오는 쿼리 메소드
	public List<Payback> selectUserPayback(Connection conn, int uNo) {
		String sql = "SELECT * FROM PAYBACK WHERE U_NO = ?";
		List<Payback> list = new ArrayList<>(); //환급 정보 전부를 담을 리스트
		Payback payback = null;

		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, uNo);
			rs = ps.executeQuery();
			while(rs.next()) {
				payback = new Payback();
				payback.setPaybNo(rs.getInt("payb_no"));
				payback.setuNo(rs.getInt("u_no"));
				payback.setChNo(rs.getInt("ch_no"));
				payback.setPaybAmount(rs.getInt("payb_amount"));
				payback.setPaybTaxFree(rs.getInt("payb_tax_free"));
				payback.setPaybChecksum(rs.getInt("payb_checksum"));
				payback.setPaybDate(rs.getDate("payb_Date"));
				payback.setPaybReason(rs.getString("payb_reason"));
				payback.setPaybRefundHolder(rs.getString("payb_refund_holder"));
				payback.setPaybRefundBank(rs.getString("payb_refund_bank"));
				payback.setPaybReFundAccount(rs.getString("payb_refund_account"));
				payback.setImpUid(rs.getString("imp_uid"));
				payback.setMerchantUid(rs.getString("merchant_uid"));					

				list.add(payback);
			}		
		} catch (SQLException e) {

			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);			
		}	
		return list;
	}


	@Override // 유저의 성공한 챌린지 정보를 가져오는 메소드
	public List<Participation> selectSuccessChall(Connection conn, int uNo) {
		String sql = "SELECT * FROM PARTICIPATION WHERE U_NO = ? AND PA_IS_SUCCESS ='Y' ";
		List<Participation> list = new ArrayList<>(); 
		Participation par = null;

		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, uNo);
			rs = ps.executeQuery();
			while(rs.next()) {
				par = new Participation();
				par.setPaNo(rs.getInt("pa_no"));
				par.setChNo(rs.getInt("ch_no"));
				par.setuNo(rs.getInt("u_no"));
				par.setPaCreateDate(rs.getDate("pa_create_date"));
				par.setPaIsSuccess(rs.getString("pa_is_success"));
				par.setPaReview(rs.getString("pa_review"));
				par.setPaLike(rs.getString("pa_like"));

				list.add(par);
			}		
		} catch (SQLException e) {

			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);			
		}	
		return list;
	}


	@Override // 성공한 챌린지의 번호를 받아 해당 상금을 추출하는 메소드
	public Refunds selectSuccesChallRefunds(Connection conn, int paNo) {
		String sql = "SELECT * FROM REFUNDS WHERE PA_NO = ? ";
		Refunds refunds = new Refunds();
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, paNo);
			rs = ps.executeQuery();
			while(rs.next()) {

				refunds.setReNo(rs.getInt("re_no"));
				refunds.setPaNo(rs.getInt("pa_no"));
				refunds.setRePoint(rs.getInt("re_point"));
				refunds.setReAmount(rs.getInt("re_amount"));
				refunds.setPaybTaxFree(rs.getInt("payb_tax_free"));
				refunds.setPaybChecksum(rs.getInt("payb_checksum"));
				refunds.setPaybDate(rs.getDate("payb_date"));
				refunds.setPaybReason(rs.getString("payb_reason"));
				refunds.setPaybRefundHolder(rs.getString("payb_refund_holder"));
				refunds.setPaybRefundBank(rs.getString("payb_refund_bank"));
				refunds.setPaybRefundAccount(rs.getString("payb_refund_account"));
				refunds.setImpUid(rs.getString("imp_uid"));
				refunds.setMerchantUid(rs.getString("merchant_uid"));
				refunds.setRefundAvailability(rs.getString("refund_availability"));
				refunds.setRefundableAmount(rs.getInt("refundable_amount"));
			}		
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);			
		}	
		return refunds;
	}


//	@Override //이번달 누적상금을 구하는 쿼리 메소드
//	public int selectMonthTotalRe(Connection conn, Refunds refunds) {
//		String sql = "SELECT SUM(RE_AMOUNT) FROM REFUNDS WHERE PA_NO = ? AND EXTRACT( MONTH FROM PAYB_DATE) = EXTRACT( MONTH FROM SYSDATE)";
//		int sum = 0;
//		try {
//			ps = conn.prepareStatement(sql);
//			ps.setInt(1, refunds.getPaNo());
//			rs = ps.executeQuery();
//			while(rs.next()) {
//				sum += rs.getInt(1);
//			}		
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}finally {
//			JDBCTemplate.close(rs);
//			JDBCTemplate.close(ps);			
//		}	
//		return sum;
//	}
//
//
//	@Override //이번달 누적결재를 구하는 쿼리 메소드
//	public int selectMonthTotalPa(Connection conn, Payment payment) {
//		String sql = "SELECT SUM(PAYM_AMOUNT) FROM PAYMENT WHERE U_NO = ? AND EXTRACT( MONTH FROM PAYM_DATE) = EXTRACT( MONTH FROM SYSDATE)";
//		int sum = 0;
//		try {
//			ps = conn.prepareStatement(sql);
//			ps.setInt(1, payment.getuNo());
//			rs = ps.executeQuery();
//			while(rs.next()) {
//				sum += rs.getInt(1);
//			}		
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}finally {
//			JDBCTemplate.close(rs);
//			JDBCTemplate.close(ps);			
//		}	
//		return sum;
//	}


	@Override //전달받은 기간의 상금정보를 구하는 코드
	public Refunds selectPeriodFunds(Connection conn, int paNo, String period) {
		String sql = "SELECT * FROM REFUNDS WHERE PA_NO = ? AND PAYB_DATE BETWEEN TO_DATE( ?, 'YYYY-MM-DD') AND LAST_DAY(TO_DATE( ?, 'YYYY-MM-DD')) ORDER BY PAYB_DATE DESC";
		Refunds refunds = new Refunds();
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, paNo);
			ps.setString(2, period);
			ps.setString(3, period);
			rs = ps.executeQuery();
			while(rs.next()) {
				refunds.setReNo(rs.getInt("re_no"));
				refunds.setPaNo(rs.getInt("pa_no"));
				refunds.setRePoint(rs.getInt("re_point"));
				refunds.setReAmount(rs.getInt("re_amount"));
				refunds.setPaybTaxFree(rs.getInt("payb_tax_free"));
				refunds.setPaybChecksum(rs.getInt("payb_checksum"));
				refunds.setPaybDate(rs.getDate("payb_date"));
				refunds.setPaybReason(rs.getString("payb_reason"));
				refunds.setPaybRefundHolder(rs.getString("payb_refund_holder"));
				refunds.setPaybRefundBank(rs.getString("payb_refund_bank"));
				refunds.setPaybRefundAccount(rs.getString("payb_refund_account"));
				refunds.setImpUid(rs.getString("imp_uid"));
				refunds.setMerchantUid(rs.getString("merchant_uid"));
				refunds.setRefundAvailability(rs.getString("refund_availability"));
				refunds.setRefundableAmount(rs.getInt("refundable_amount"));
			}		
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);			
		}	
		return refunds;
	}


	@Override //전달받은 기간의 결재정보를 구하는 코드
	public List<Payment> selectPeriodPayment(Connection conn, int getuNo, String period) {
		String sql = "SELECT * FROM PAYMENT WHERE U_NO = ? AND PAYM_DATE BETWEEN TO_DATE( ?, 'YYYY-MM-DD') AND LAST_DAY(TO_DATE( ?, 'YYYY-MM-DD')) ORDER BY PAYM_DATE DESC";
		List<Payment> list = new ArrayList<>();
		Payment payment = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, getuNo);
			ps.setString(2, period);
			ps.setString(3, period);
			rs = ps.executeQuery();
			while(rs.next()) {
				payment = new Payment();
				payment.setPaymNo(rs.getInt("paym_no"));
				payment.setuNo(rs.getInt("u_no"));
				payment.setChNo(rs.getInt("ch_no"));
				payment.setPaymName(rs.getString("paym_name"));
				payment.setPaymDate(rs.getDate("paym_Date"));
				payment.setPaymAmount(rs.getInt("paym_amount"));
				payment.setPaymApplyNum(rs.getString("paym_apply_num"));
				payment.setImpUid(rs.getString("imp_uid"));
				payment.setMerchantUid(rs.getString("merchant_uid"));					
				list.add(payment);
			}		
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);			
		}	
		return list;
	}
	
	
	@Override //전달받은 기간의 환급정보를 구하는 코드
	public List<Payback> selectPeriodPayback(Connection conn, int getuNo, String period) {
		String sql = "SELECT * FROM PAYBACK WHERE U_NO = ? AND PAYB_DATE BETWEEN TO_DATE( ? , 'YYYY-MM-DD') AND LAST_DAY(TO_DATE( ?, 'YYYY-MM-DD')) ORDER BY PAYB_DATE DESC";
		Payback payback = null;
		List<Payback> list = new ArrayList<>();
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, getuNo);
			ps.setString(2, period);
			ps.setString(3, period);
			rs = ps.executeQuery();
			while(rs.next()) {
				payback = new Payback();
				payback.setPaybNo(rs.getInt("payb_no"));
				payback.setuNo(rs.getInt("u_no"));
				payback.setChNo(rs.getInt("ch_no"));
				payback.setPaybAmount(rs.getInt("payb_amount"));
				payback.setPaybTaxFree(rs.getInt("payb_tax_free"));
				payback.setPaybChecksum(rs.getInt("payb_checksum"));
				payback.setPaybDate(rs.getDate("payb_Date"));
				payback.setPaybReason(rs.getString("payb_reason"));
				payback.setPaybRefundHolder(rs.getString("payb_refund_holder"));
				payback.setPaybRefundBank(rs.getString("payb_refund_bank"));
				payback.setPaybReFundAccount(rs.getString("payb_refund_account"));
				payback.setImpUid(rs.getString("imp_uid"));
				payback.setMerchantUid(rs.getString("merchant_uid"));						
				
				list.add(payback);
			}		
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);			
		}	
		return list;
	}
	
	
	@Override // 입력받은 번호가 현재 번호와 일치하는지 체크
	public int selectPhone(Connection conn, String phone, int getuNo) {
		String sql = "SELECT COUNT(*) FROM USERS WHERE U_NO = ? AND U_PHONE= ?";
		int check = 0;

		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, getuNo);
			ps.setString(2, phone);
			rs = ps.executeQuery();
			while(rs.next()) {
				check = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(ps);
		}
		return check;
	}
	
	
	@Override // 입력받은 닉네임이 현재 닉네임과 일치하는지 체크
	public int selectNick(Connection conn, String nick, int getuNo) {
		String sql = "SELECT COUNT(*) FROM USERS WHERE U_NO = ? AND U_NICK= ?";
		int check = 0;

		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, getuNo);
			ps.setString(2, nick);
			rs = ps.executeQuery();
			while(rs.next()) {
				check = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(ps);
		}
		return check;
	}
	
	
	@Override
	public int selectEmail(Connection conn, String email, int getuNo) {
		String sql = "SELECT COUNT(*) FROM USERS WHERE U_NO = ? AND U_EMAIL= ?";
		int check = 0;

		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, getuNo);
			ps.setString(2, email);
			rs = ps.executeQuery();
			while(rs.next()) {
				check = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(ps);
		}
		return check;
	}
	
	
//	@Override // 결재 항목에 따라 챌린지 제목을 가져온다.
//	public Challenge selectChallTitle(Connection conn, int chNo) {
//		String sql = "SELECT * FROM CHALLENGE WHERE CH_NO = ?";
//		Challenge chall = null; // 챌린지를 담을 객체
//
//		try {
//			ps = conn.prepareStatement(sql);
//			ps.setInt(1, chNo);
//			rs = ps.executeQuery();
//			while(rs.next()) {
//				chall = new Challenge();
//				chall.setChNo(rs.getInt("ch_no"));
//				chall.setCaNo(rs.getInt("ca_no"));
//				chall.setCecNo(rs.getInt("cec_no"));
//				chall.setuNo(rs.getInt("u_no"));
//				chall.setChTitle(rs.getString("ch_title"));
//				chall.setChContent(rs.getString("ch_content"));
//				chall.setChcReatedate(rs.getDate("ch_create_date"));
//				chall.setChStartdate(rs.getDate("ch_start_date"));
//				chall.setChEnddate(rs.getDate("ch_end_date"));
//				chall.setChMoney(rs.getInt("ch_money"));
//				chall.setChStarttime(rs.getDate("ch_start_time"));
//				chall.setChEndtime(rs.getDate("ch_end_time"));
//				chall.setChWay(rs.getString("ch_way"));
//				chall.setChCaution(rs.getInt("ch_caution"));
//				chall.setChLikes(rs.getInt("ch_likes"));
//				chall.setChOriginname(rs.getString("ch_origin_name"));				
//				chall.setChStoredname(rs.getString("ch_stored_name"));				
//			}		
//		} catch (SQLException e) {
//
//			e.printStackTrace();
//		}finally {
//			JDBCTemplate.close(rs);
//			JDBCTemplate.close(ps);			
//		}	
//		return chall;
//	}
}



