package service.member.impl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import common.JDBCTemplate;
import dao.member.face.MypageDao;
import dao.member.impl.MypageDaoImpl;
import dto.Challenge;
import dto.Member;
import dto.Mypage;
import dto.Participation;
import dto.Payback;
import dto.Payment;
import dto.Refunds;
import service.member.face.MypageService;


public class MypageServiceImpl implements MypageService {


	//마이페이지 dao  객체 생성
	MypageDao mypageDao = new MypageDaoImpl();

	@Override // [마이페이지] 유저정보를 가져오는 메소드
	public Member getUserInfo(int uNo) {
		Connection conn = JDBCTemplate.getConnection(); 
		Member member = null; // 유저 객체 생성
		member = mypageDao.selectInfo(conn, uNo);

		return member;
	}

	@Override // [마이페이지] 마이페이지정보를 가져오는 메소드
	public Mypage getMypageInfo(int getuNo) {
		Connection conn = JDBCTemplate.getConnection(); 
		Mypage mypage = null; // 유저 객체 생성
		mypage = mypageDao.selectMypageInfo(conn, getuNo);

		return mypage;
	}

	@Override // 업데이트 수정에 따른 
	public int updateInfo(HttpServletRequest req, Member memberParam, Mypage mypageParam) {

		boolean isMultipart = ServletFileUpload.isMultipartContent(req);
		Member member = null;
		Mypage mypage = null;

		if(!isMultipart) {
			//파일 첨부가 없을 경우
			member = new Member();

			member.setUpassword(req.getParameter("mPw"));
			member.setNick(req.getParameter("mNick"));
			member.setChallenge(req.getParameter("mChall"));
			member.setEmail(req.getParameter("mEmail"));
			member.setPhone(req.getParameter("mPhone"));

		} else {
			//파일업로드를 사용하고 있을 경우
			member = new Member();

			//디스크팩토리
			DiskFileItemFactory factory = new DiskFileItemFactory();

			//메모리처리 사이즈
			factory.setSizeThreshold(1 * 1024 * 1024); //1MB

			//임시 저장소
			File repository=new File(req.getServletContext().getRealPath("tmp"));
			factory.setRepository(repository);

			//업로드 객체 생성
			ServletFileUpload upload = new ServletFileUpload(factory);

			//용량 제한 설정 : 10MB
			upload.setFileSizeMax(10 * 1024 * 1024);

			//form-data 추출 
			List<FileItem> items = null;
			try {
				items = upload.parseRequest(req);

			} catch (FileUploadException e) {
				e.printStackTrace();
			}

			//파싱된 데이터 처리 반복자
			Iterator<FileItem> iter = items.iterator();

			//요청정보 처리
			while( iter.hasNext() ) {
				FileItem item = iter.next();

				// 빈 파일 처리
				if( item.getSize() <= 0 )	continue;

				// 빈 파일이 아닐 경우
				if( item.isFormField() ) {
					try {
						if( "mPw".equals( item.getFieldName() ) ) {
							member.setUpassword(item.getString("utf-8") );
						}
						if( "mNick".equals( item.getFieldName() ) ) {
							member.setNick( item.getString("utf-8") ); 
						}
						if( "mChall".equals( item.getFieldName() ) ) {
							member.setChallenge( item.getString("utf-8") );
						}
						if( "mEmail".equals( item.getFieldName() ) ) {
							member.setEmail( item.getString("utf-8") );
						}
						if( "mPhone".equals( item.getFieldName() ) ) {
							member.setPhone( item.getString("utf-8") );
						}

						member.setUid(memberParam.getUid()); //아이디
						member.setUno(memberParam.getUno()); // 유저번호

					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}

				} else {

					//기존 파일 삭제 코든
					File file = new File(req.getServletContext().getRealPath("upload") + "\\" + mypageParam.getmStoredname());
					System.out.println("현재 삭제할 파일 경로 -> " + file.getPath());

					if(file.exists()) { // 파일이 존재하면
						file.delete(); // 파일 삭제
						System.out.println("디렉토리에서의 파일 삭제 성공");
					}

					UUID uuid = UUID.randomUUID();
					String u = uuid.toString().split("-")[4];

					String origin = item.getName(); // 원본파일 이름
					int dotIndx = origin.lastIndexOf("."); // 파일 확장자 위치저장
					String ext = origin.substring(dotIndx + 1); // 파일 확장자 추출

					//로컬 저장소 파일
					String stored = item.getName() + "_" + u + "." + ext;
					File up = new File(
							req.getServletContext().getRealPath("upload")
							, stored);

					mypage = new Mypage();
					mypage.setmOriginname(item.getName());
					mypage.setmStoredname(stored);

					try {
						// 실제 업로드
						item.write(up);

						// 임시 파일 삭제
						item.delete();

					} catch (Exception e) {
						e.printStackTrace();
					} // try end
				} //if end
			} //while end
		} //if(!isMultipart) end


		//		System.out.println(board);
		//		System.out.println(boardFile);

		Connection conn = JDBCTemplate.getConnection();

		if(member != null) {
			if( mypageDao.update(conn, member) > 0 ) {
				JDBCTemplate.commit(conn);
				System.out.println("update ->   결과 성공");
			} else {
				JDBCTemplate.rollback(conn);
				System.out.println("update ->   결과 실패");
			}
		}

		if(mypage != null) {
			mypage.setmNo(member.getUno());
			if( mypageDao.updateFile(conn, mypage) > 0 ) {
				JDBCTemplate.commit(conn);
				System.out.println("updateFile ->   결과 성공");
			} else {
				JDBCTemplate.rollback(conn);
				System.out.println("updateFile ->   결과 실패");
			}
		}
		return 0;
	}

	@Override // 유저정보를 삭제하는 메소드
	public int deleteInfo(HttpServletRequest req) {
		Connection conn = JDBCTemplate.getConnection(); 
		String mId = req.getParameter("mId");
		String mPw = req.getParameter("mPw");		
		int check = 0;

		check = mypageDao.deleteUser(conn, mId, mPw);
		if(check == 1) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		return check;
	}


	@Override // 유저 마이페이지 정보를 삭제하는 메소드
	public int deleteMypage(int userNo) {
		Connection conn = JDBCTemplate.getConnection(); 
		int check = 0;
		check = mypageDao.deleteMypage(conn, userNo);

		return check;
	}

	@Override //마이 페이지 중복 체크(이메일)
	public int checkOverlapEmail(HttpServletRequest req, Member member) {
		Connection conn = JDBCTemplate.getConnection(); 
		String email = req.getParameter("email");
		int check = 0;

		check = mypageDao.selectEmail(conn, email, member.getUno());

		if(check == 1) { // 현재 유저이메일과 입력값 같음
			check = 2; // 2를 리턴
		}else {// 아닐 경우 해당 이메일 중복여부 체크
			check = mypageDao.selectEmailCheck(conn, email);
		}

		if(check == 1) {
			System.out.println("중북체크 결과 해당 이메일 존재");
		}else if(check == 2) {
			System.out.println("현재 유저의 이메일 같음 ");
		}else {
			System.out.println("중북체크 결과 해당 이메일 없음 사용가능");
		}
		return check;
	}


	@Override //마이 페이지 중복 체크(닉네임)
	public int checkOverlapNick(HttpServletRequest req, Member member) {
		Connection conn = JDBCTemplate.getConnection(); 
		String nick = req.getParameter("nick");
		int check = 0;

		check = mypageDao.selectNick(conn, nick, member.getUno());
		if(check == 1) { // 현재 유저닉네임과 입력값 같음
			check = 2; // 2를 리턴
		}else {// 아닐 경우 해당 닉네임 중복여부 체크
			check = mypageDao.selectNickCheck(conn, nick);
		}

		if(check == 1) {
			System.out.println("중북체크 결과 해당 닉네임 존재");
		}else if(check == 2) {
			System.out.println("현재 유저의 닉네임 같음 ");
		}else {
			System.out.println("중북체크 결과 해당 닉네임 없음 사용가능");
		}
		return check;
	}


	@Override //마이 페이지 중복 체크(휴대폰)
	public int checkOverlapPhone(HttpServletRequest req, Member member) {
		Connection conn = JDBCTemplate.getConnection(); 
		String phone = req.getParameter("phone");
		int check = 0;

		//현재 유저의 번호와 입력값이 같은지 체크
		check = mypageDao.selectPhone(conn, phone, member.getUno());
		if(check == 1) { // 현재 유저번호와 입력값 같음
			check = 2; // 2를 리턴
		}else {// 아닐 경우 해당 번호 중복여부 체크
			check = mypageDao.selectPhoneCheck(conn, phone);
		}

		if(check == 1) {
			System.out.println("중북체크 결과 해당 전화번호 존재");
		}else if(check == 2) {
			System.out.println("현재 유저의 번호와 같음 ");
		}else {
			System.out.println("중북체크 결과 해당 전화번호 없음 사용가능");
		}
		return check;
	}


	@Override // 유저의 챌린지 정보를 가져오는 메소드
	public List<Challenge> getUserChallInfo(int uNo) {
		Connection conn = JDBCTemplate.getConnection(); 
		List<Challenge> list = new ArrayList<>();
		Challenge chall = new Challenge();	
		
		list = mypageDao.selectAllUserChall(conn, uNo);
		
		if(list.size() == 0) { //현재 유저가 진행중인 챌린지가 없을 경우
			list.add(chall); //기본값이 들어있는 객체를 넣어준다.
		}
		
		System.out.println(list.size());
		
		
		return list;
	}

	@Override //유저의 결제 정보를 가져오는 메소드
	public List<Payment> getPaymentInfo(int uNo) {
		Connection conn = JDBCTemplate.getConnection(); 
		List<Payment> list = new ArrayList<>();
		Payment payment = new Payment();
		
		list = mypageDao.selectUserPayment(conn, uNo);

		if(list.size() == 0) { //현재 유저가 결제가 하나도 없을 경우
			list.add(payment); //기본값이 들어있는 객체를 넣어준다.
		}
		
		
		return list;
	}


	@Override //유저의 환급 정보를 가져오는 메소드
	public List<Payback> getPaybackInfo(int uNo) {
		Connection conn = JDBCTemplate.getConnection(); 
		List<Payback> list = new ArrayList<>();
		Payback payback = new Payback();
		
		list = mypageDao.selectUserPayback(conn, uNo);

		if(list.size() == 0) { //현재 유저가 환급이 하나도 없을 경우
			list.add(payback); //기본값이 들어있는 객체를 넣어준다.
		}
		
		return list;
	}

	@Override // 유저가 성공한 챌린지 정보를 가져오는 메소드
	public List<Participation> getSuccessChallInfo(int uNo) {
		Connection conn = JDBCTemplate.getConnection(); 
		List<Participation> list = new ArrayList<>();
		Participation par = new Participation();
		
		list = mypageDao.selectSuccessChall(conn, uNo);

		if(list.size() == 0) { //현재 유저가 성공한 챌린지가 하나도 없을 경우
			list.add(par); //기본값이 들어있는 객체를 넣어준다.
		}
		
		return list;
	}

	@Override // 유저가 성공한 챌린지의 상금들을 가져오는 메소드
	public List<Refunds> getRefundAmount(List<Participation> parList) {
		Connection conn = JDBCTemplate.getConnection(); 
		List<Refunds> list = new ArrayList<>();
		Refunds refunds = new Refunds();
		for(int i = 0; i < parList.size(); i++) {
			refunds = mypageDao.selectSuccesChallRefunds(conn, parList.get(i).getPaNo());
			list.add(refunds);
		}
	
		if(list.size() == 0) { //현재 유저의 상금이 하나도 없을 경우
			list.add(refunds); //기본값이 들어있는 객체를 넣어준다.
		}
		
		return list;
	}

	@Override // 누적 총상금을 구하는 메소드
	public int refundsSum(List<Refunds> refundsList) {
		int sum = 0;

		for(int i =0; i < refundsList.size(); i++) {
			sum += refundsList.get(i).getReAmount();
		}
		return sum;
	}


	@Override // 누적 총 결재금액을 구하는 메소드
	public int paymentSum(List<Payment> paymentList) {
		int sum = 0;

		for(int i =0; i < paymentList.size(); i++) {
			sum += paymentList.get(i).getPaymAmount();
		}
		return sum;
	}

	@Override // 이번 달 누적 상금을 구하는 메소드
	public int refundsMonthSum(List<Refunds> reqRefundsList) {
		Connection conn = JDBCTemplate.getConnection(); 
		int sum = 0;

		for(int i =0; i < reqRefundsList.size(); i++) {
			sum += reqRefundsList.get(i).getReAmount();
		}

		return sum;
	}

	@Override// 이번 달 누적 결재액을 구하는 메소드
	public int paymentMonthSum(List<Payment> reqPaymentList) {
		Connection conn = JDBCTemplate.getConnection(); 
		int sum = 0;

		for(int i =0; i < reqPaymentList.size(); i++) {
			sum +=reqPaymentList.get(i).getPaymAmount();
		}

		return sum;
	}
	
	@Override// 이번 달 누적 환급액을 구하는 메소드
	public int paybackMonthSum(List<Payback> reqPaybackList) {
		Connection conn = JDBCTemplate.getConnection(); 
		int sum = 0;

		for(int i =0; i < reqPaybackList.size(); i++) {
			sum +=reqPaybackList.get(i).getPaybAmount();
		}

		return sum;
	}
	

	@Override // 전달받은 년월에 따른 상금내역을 가져오는 메소드
	public List<Refunds> getReqFunds(HttpServletRequest req, List<Refunds> refundsList) {
		Connection conn = JDBCTemplate.getConnection(); 
		List<Refunds> getReqFundsList = new ArrayList<Refunds>();
		Refunds refunds = new Refunds();

		String period = req.getParameter("yearSelected") + "-" + req.getParameter("monthSelected") + "-" + "01";

		for(int i = 0; i < refundsList.size(); i++) {
			refunds = mypageDao.selectPeriodFunds(conn, refundsList.get(i).getPaNo(), period);
			getReqFundsList.add(refunds);

			if(i != 0) { // 중복값이 들어가지 않기 위한 체크
				if(getReqFundsList.get(i).getReNo() == getReqFundsList.get(i - 1).getReNo()) {
					getReqFundsList.remove(i); // 현재 i 인덱스 값은 중복됨 제거			
					break;
				}
			}
		}
		
		if(getReqFundsList.isEmpty()) { // 만약에 비었으면 빈값 넣어줌
			getReqFundsList.add(refunds);
		}
		return getReqFundsList;
	}


	@Override // 전달받은 년월에 따른 결재내역을 가져오는 메소드
	public List<Payment> getReqPayment(HttpServletRequest req, List<Payment> paymentList) {
		Connection conn = JDBCTemplate.getConnection(); 
		List<Payment> getReqPaymentList = new ArrayList<Payment>();

		Payment payment = new Payment();
		String period = req.getParameter("yearSelected") + "-" + req.getParameter("monthSelected") + "-" + "01";
		getReqPaymentList = mypageDao.selectPeriodPayment(conn, paymentList.get(0).getuNo(), period);

		if(getReqPaymentList.isEmpty()) { // 만약에 비었으면 빈값 넣어줌
			getReqPaymentList.add(payment);
		}
		
		return getReqPaymentList;
	}

	@Override // 전달받은 년월에 따른 환급내역을 가져오는 메소드
	public List<Payback> getReqPayback(HttpServletRequest req, List<Payback> paybackList) {
		Connection conn = JDBCTemplate.getConnection(); 
		List<Payback> getReqPaybackList = new ArrayList<Payback>();
		Payback payback = new Payback();
		String period = req.getParameter("yearSelected") + "-" + req.getParameter("monthSelected") + "-" + "01";
		getReqPaybackList = mypageDao.selectPeriodPayback(conn, paybackList.get(0).getuNo(), period);
		
		if(getReqPaybackList.isEmpty()) { // 만약에 비었으면 빈값 넣어줌
			getReqPaybackList.add(payback);
		}
		
		return getReqPaybackList;
	}

	@Override // 당월의 상금 데이터를 가져온다.
	public List<Refunds> getReqFunds(List<Refunds> refundsList) {
		Connection conn = JDBCTemplate.getConnection(); 
		List<Refunds> getReqFundsList = new ArrayList<Refunds>();
		Refunds refunds = new Refunds();
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		String period = sdf.format(today) + "-01";

		for(int i = 0; i < refundsList.size(); i++) {
			refunds = mypageDao.selectPeriodFunds(conn, refundsList.get(i).getPaNo(), period);
			getReqFundsList.add(refunds);

			if(i != 0) { // 중복값이 들어가지 않기 위한 체크
				if(getReqFundsList.get(i).getReNo() == getReqFundsList.get(i - 1).getReNo()) {
					getReqFundsList.remove(i); // 현재 i 인덱스 값은 중복됨 제거
					break;
				}
			}
		}

		if(getReqFundsList.isEmpty()) { // 만약에 비었으면 빈값 넣어줌
			getReqFundsList.add(refunds);
		}
		return getReqFundsList;
	}

	@Override // 당월의 결재 데이터를 가져온다.
	public List<Payment> getReqPayment(List<Payment> paymentList) {
		Connection conn = JDBCTemplate.getConnection(); 
		List<Payment> getReqPaymentList = new ArrayList<Payment>();
		Payment payment = new Payment();
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		String period = sdf.format(today) + "-01";

		getReqPaymentList = mypageDao.selectPeriodPayment(conn, paymentList.get(0).getuNo(), period);

		if(getReqPaymentList.isEmpty()) { // 만약에 비었으면 빈값 넣어줌
			getReqPaymentList.add(payment);
		}
		
		return getReqPaymentList;
	}

	@Override // 당월의 환급 데이터를 가져온다.
	public List<Payback> getReqPayback(List<Payback> paybackList) {
		Connection conn = JDBCTemplate.getConnection(); 
		List<Payback> getReqPaybackList = new ArrayList<Payback>();
		Payback payback = new Payback();
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		String period = sdf.format(today) + "-01";

		getReqPaybackList = mypageDao.selectPeriodPayback(conn, paybackList.get(0).getuNo(), period);

		if(getReqPaybackList.isEmpty()) { // 만약에 비었으면 빈값 넣어줌
			getReqPaybackList.add(payback);
		}
		
		return getReqPaybackList;
	}


//	@Override //결제항목에 따른 챌린지의 제목을 가져온다
//	public List<Challenge> getChallTitles(List<Payment> paymentList) {
//		Connection conn = JDBCTemplate.getConnection(); 
//		List<Challenge> challList = new ArrayList<Challenge>();
//		Challenge chall = new Challenge();
//		
//		for(int i = 0; i < paymentList.size(); i++) {
//			chall = mypageDao.selectChallTitle(conn, paymentList.get(i).getChNo());
//			challList.add(chall);
//		}
//				
//		return challList;
//	}
	
	
	@Override // 요청한 기간의 상금목록을 받아 해당기간의 총상금을 구한다.
	public int sumReqFunds(List<Refunds> refundsList) {
		int sum = 0;
		for(int i = 0; i < refundsList.size(); i++) {
			sum += refundsList.get(i).getReAmount();
		}
		return sum;
	}


	@Override // 요청한 기간의 결제목록을 받아 해당기간의 총결제금을 구한다.
	public int sumReqPayment(List<Payment> reqPaymentPeriodList) {
		int sum = 0;
		for(int i = 0; i < reqPaymentPeriodList.size(); i++) {
			sum += reqPaymentPeriodList.get(i).getPaymAmount();
		}
		return sum;
	}
	
	@Override // 요청한 기간의 결제목록을 받아 해당기간의 총환급금을 구한다.
	public int sumReqPayback(List<Payback> reqPaybackPeriodList) {
		int sum = 0;
		for(int i = 0; i < reqPaybackPeriodList.size(); i++) {
			sum += reqPaybackPeriodList.get(i).getPaybAmount();
		}
		return sum;
	}
}
