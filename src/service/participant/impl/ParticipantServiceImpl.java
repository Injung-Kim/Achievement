package service.participant.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;

import common.JDBCTemplate;
import dao.participant.face.ParticipantDao;
import dao.participant.impl.ParticipantDaoImpl;
import dto.Certification;
import dto.CertificationCycle;
import dto.Challenge;
import dto.Complaint;
import dto.Member;
import dto.Participation;
import dto.Payback;
import dto.Payment;
import service.participant.face.ParticipantService;
import util.BankCode;
import util.FileRemove;
import util.Paging;

public class ParticipantServiceImpl implements ParticipantService {
	
	private ParticipantDao participantDao = new ParticipantDaoImpl();
	private FileRemove fileRemove = new FileRemove();
	//저장할 객체
	@Override
	public Participation getParticipation(HttpServletRequest req) {
		int paNo = participantDao.selectPaNo(JDBCTemplate.getConnection());
		int chNo = Integer.parseInt(req.getParameter("chNo"));
		int uNo = (Integer)req.getSession().getAttribute("u_no");
		Participation participation = new Participation();
		participation.setChNo(chNo);
		participation.setPaNo(paNo);
		participation.setuNo(uNo);
		participation.setPaIsSuccess("W");
		participation.setPaReview("후기없음");
		
		return participation;
	}
	@Override
	public Payment getPayment(HttpServletRequest req) {
		Payment payment = new Payment();
		int paymNo = participantDao.selectPaymNo(JDBCTemplate.getConnection());
		int uNo = (Integer)req.getSession().getAttribute("u_no");
		
		payment.setPaymNo(paymNo);
		payment.setuNo(uNo);
		payment.setImpUid(req.getParameter("imp_uid"));
		payment.setChNo(Integer.parseInt(req.getParameter("chNo")));
		payment.setMerchantUid(req.getParameter("merchant_uid"));
		payment.setPaymApplyNum(req.getParameter("apply_num"));
		payment.setPaymAmount(Integer.parseInt(req.getParameter("paid_amount")));
		payment.setPaymName(req.getParameter("pay_name"));
		
		
		return payment;
	}
	
	//추출해낼 paNo
	@Override
	public int getParticipationno(HttpServletRequest req) {
		//session에서 값을 가져온다
		int chNo = (Integer)req.getSession().getAttribute("chNo");
		int uNo = (Integer)req.getSession().getAttribute("u_no");
		int paNo = participantDao.selectByPaNo(JDBCTemplate.getConnection(), chNo, uNo);
		return paNo;
	}
	@Override
	public Complaint getComplaint(HttpServletRequest req) {
		Complaint complaint = null;
		if(req.getParameter("complaintReason")!=null &&!"".equals(req.getParameter("complaintReason"))) {
			//다음 신고 번호
			int comNo = participantDao.selectComNo(JDBCTemplate.getConnection());
			complaint = new Complaint();
			complaint.setComNo(comNo);
			int chNo = (Integer)req.getSession().getAttribute("chNo");
			int uNo = (Integer)req.getSession().getAttribute("u_no");
			complaint.setChNo(chNo);
			complaint.setuNo(uNo);
			complaint.setComContent(req.getParameter("complaintReason"));
		}
		
		return complaint;
	}
	
	//챌린지 타이틀 반환
	@Override
	public String getTitle(HttpServletRequest req) {
		//챌린지 번호
		int chNo = Integer.parseInt(req.getParameter("chNo"));
		String title = participantDao.selectByTitle(JDBCTemplate.getConnection(), chNo);
		
		return title;
	}
	//챌린지 타이틀 반환
	@Override
	public String getTitle(int chNo) {
		String title = participantDao.selectByTitle(JDBCTemplate.getConnection(), chNo);
		return title;
	}
	
	@Override
	public String getChway(int chNo) {
		String chWay = participantDao.selectByChWay(JDBCTemplate.getConnection(), chNo);
		return chWay;
	}
	@Override
	public String getReview(int paNo) {
		String review = participantDao.selectByReview(JDBCTemplate.getConnection(), paNo);
		return review;
	}
	
	
	@Override
	public int getChallengeno(HttpServletRequest req) {
		
		int chNo = 0;
		if(req.getParameter("chNo")!=null) {
			chNo = Integer.parseInt(req.getParameter("chNo"));
		}else if(req.getSession().getAttribute("chNo")!=null) {
			chNo = (Integer)req.getSession().getAttribute("chNo");
		}
		
		return chNo;
	}
	@Override
	public Certification getCertificationno(HttpServletRequest req) {
		//인증 번호 반환
		String param = req.getParameter("ceNo");
		Certification certification = null;
		if(param!=null && !"".equals(param)) {
			certification= new Certification();
			certification.setCeNo(Integer.parseInt(param));
			
		}
		
		return certification;
	}
	
	@Override
	public Paging getCertificaitonPaging(HttpServletRequest req) {
		//전달파라미터 curPage 파싱
		String param = req.getParameter("curPage");
		
		//챌린지 번호와 유저 번호 구하기
		int chNo = Integer.parseInt(req.getParameter("chNo"));
		int uNo = (Integer)req.getSession().getAttribute("u_no");
		int paNo = participantDao.selectByPaNo(JDBCTemplate.getConnection(), chNo, uNo);
		
		int curPage = 0;
		if(param != null && !"".equals(param)) {
			curPage = Integer.parseInt(param);
		}
		//참가 챌린지 번호 생성
		Participation participation = new Participation();
		participation.setPaNo(paNo);
		
		//challenge 테이블의 자신의 인증 수를 조회한다
		int totalCount = participantDao.selectCntAll(JDBCTemplate.getConnection(),participation);

		//Paging객체 생성
		Paging paging = new Paging(totalCount, curPage, 3);

		return paging;
	}
	@Override
	public Paging getParticipantPaging(HttpServletRequest req) {
		//전달파라미터 curPage 파싱
		String param = req.getParameter("curPage");
		Challenge challenge = null;
		
		int curPage = 0;
		if(param != null && !"".equals(param)) {
			curPage = Integer.parseInt(param);
		}
		
		if(req.getSession().getAttribute("chNo") !=null) {
			challenge = new Challenge();
			int chNo=(Integer)req.getSession().getAttribute("chNo");
			challenge.setChNo(chNo);
		}
		

		//participation 테이블의 참가자 수를 조회한다
		int totalCount = participantDao.selectCntAll(JDBCTemplate.getConnection(),challenge);
		//Paging객체 생성
		Paging paging = new Paging(totalCount, curPage);

		return paging;
	}
	
	@Override
	public Certification certificationView(Certification certification) {
		
		return participantDao.selectCertification(JDBCTemplate.getConnection(), certification);
	}
	@Override
	public List<Certification> getList(Paging paging,int paNo) {
		return participantDao.selectAllCertification(JDBCTemplate.getConnection(), paging,paNo);
	}
	
	@Override
	public List<Member> getParticipantList(Paging paging, int chNo) {
		return  participantDao.selectAllPartification(JDBCTemplate.getConnection(), paging, chNo);
	}
	@Override
	public Participation getLike(HttpServletRequest req) {
		Boolean param = Boolean.parseBoolean(req.getParameter("paLike"));
		//true일때 좋아요 활성화 -> 비활성화
		Participation participation = new Participation();
		if(param) {
			participation.setPaLike("N");
			//false일때 좋아요 비활성화 -> 활성화
		}else {
			participation.setPaLike("Y");
		}
		//좋아요 수를 증감할 챌린지 번호 입력
		if(req.getSession().getAttribute("chNo") !=null) {
			int chNo=(Integer)req.getSession().getAttribute("chNo");
			participation.setChNo(chNo);
		}
		
		return participation;
	}
	@Override
	public void attendWrite(Participation participation) {
		Connection conn = JDBCTemplate.getConnection();
		if( participantDao.attendInsert(conn, participation) > 0 ) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
		
	}
	//좋아요 증감
	@Override
	public void increaseLike(Participation participation) {
		Connection conn = JDBCTemplate.getConnection();
		if(participantDao.increaseChLike(conn, participation)>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		
	}
	@Override
	public void increaseLikeMypage(Participation participation) {
		Connection conn = JDBCTemplate.getConnection();
		if(participantDao.increaseMypageLike(conn, participation)>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
	}
	
	//좋아요 여부
	@Override
	public void updatePaLike(Participation participation) {
		Connection conn = JDBCTemplate.getConnection();
		
		if(participantDao.updatePaLike(conn, participation)>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		
	}
	
	@Override
	public void paymentWrite(Payment payment) {
		Connection conn = JDBCTemplate.getConnection();
		if( participantDao.paymentInsert(conn, payment) > 0 ) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
	}
	@Override
	public Map<String, Boolean> getWhethers(HttpServletRequest req) {
		//신고 여부와 좋아요 여부
		Map<String, Boolean> result = new HashMap<>();
		int chNo = (Integer)req.getSession().getAttribute("chNo");
		int uNo = (Integer)req.getSession().getAttribute("u_no");
		
		
		//해당 챌린지의 좋아요 여부를 DB에 조회해서 가져온다(단순 조회)
		boolean paLike =  participantDao.selectByPaLike(JDBCTemplate.getConnection(), chNo, uNo);
		
		//해당 챌린지의 신고여부를 DB에 조회해서 가져온다(신고 테이블에 uNo와 chNo의 값이 존재하면 신고를 한 것)
		boolean paComplaint = participantDao.selectByComplaint(JDBCTemplate.getConnection(), chNo, uNo);
		result.put("paLike", paLike);
		result.put("paComplaint", paComplaint);
		
		return result;
	}
	
	@Override
	public void certificationWrite(HttpServletRequest req) {

		int paNo = (Integer)req.getSession().getAttribute("paNo"); //paNo 가져오기
		//챌린지 정보 저장할 객체
		Certification certification = null;

		//파일업로드 형태의 데이터가 맞는지 검사
		boolean isMultipart = false;
		isMultipart = ServletFileUpload.isMultipartContent(req);



		//multipart/form-data 인코딩으로 전송되지 않았을 경우
		if( !isMultipart ) {
			System.out.println("[ERROR] multipart/form-data 형식이 아님");

			return; //fileupload() 메소드 실행 중지
		}

		//챌린지 정보 저장할 객체 생성
		certification = new Certification();

		//디스트기반 아이템 팩토리
		DiskFileItemFactory factory = new DiskFileItemFactory();

		//메모리 처리 사이즈 지정
		factory.setSizeThreshold( 1  * 1024 ); //1KB

		//임시 저장소 설정
		File repository = new File(req.getServletContext().getRealPath("tmp"));
		repository.mkdir();

		factory.setRepository(repository);

		//파일업로드 객체 생성
		ServletFileUpload upload = new ServletFileUpload(factory);

		//업로드 용량 제한
		upload.setFileSizeMax( 10*1024*1024 ); //10MB

		//전달 데이터 파싱
		List<FileItem> items = null;
		try {

			items = upload.parseRequest(req);
		} catch (FileUploadException e) {
			e.printStackTrace();
		}

		//추출된 전달파라미터 처리 반복자
		Iterator<FileItem> iter = items.iterator();

		//모든 요청 정보 처리하기
		while( iter.hasNext() ) {
			FileItem item = iter.next();

			// 1) 빈 파일 처리
			if( item.getSize() <= 0 )	continue;

			// 3) 파일 처리
			if( !item.isFormField() ) {
				// java.util.Date -> String 변환 - SimpleDateFormat 클래스
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssS");
				String rename = sdf.format(new Date());

				//확장자는 원본파일 확장자 얻어오기
				String origin = item.getName(); // 원본파일 이름
				int dotIdx = origin.lastIndexOf("."); //가장 마지막 "."의 인덱스

				//확장자
				String ext = origin.substring(dotIdx + 1);

				//서버에 저장될 파일의 이름
				String stored = rename +"."+ext;

				//						System.out.println("[TEST] 원본 파일명 : "+origin);
				//						System.out.println("[TEST] 저장될 파일명 : "+stored);

				// --- 첨부파일 정보 객체 ---
				certification.setCeOriginName(origin);
				certification.setCeStoredName(stored);
				// --------------------------

				//업로드된 임시파일을 실제로 저장하기
				// -> 1MB이하 파일 : 메모리에 임시파일로 업로드
				// -> 1MB이상 파일 : HDD에 임시파일로 업로드
				File uploadFolder = new File(req.getServletContext().getRealPath("upload")); // 업로드될 폴더 경로

				uploadFolder.mkdir();

				//실제로 저장될 파일
				File up = new File(uploadFolder, stored);

				try {
					item.write(up); //실제 업로드(최종 결과 파일이 생성됨)
					item.delete(); //임시 파일 삭제
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} // 파일 처리 end

		} // while( iter.hasNext() ) end - FileItem 반복 처리

		//파일이 선택되지 않았을경우
		if(certification.getCeOriginName()==null || "".equals(certification.getCeOriginName())) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssS");
			String rename = sdf.format(new Date());

			File uploadFolder = new File(req.getServletContext().getRealPath("upload")); // 업로드될 폴더 경로
			File defaultImg = new File(req.getServletContext().getRealPath("resources/img/challenge.png")); //이미지 파일 경로

			String origin = defaultImg.getName(); //파일의 본래 이름 challenge.png
			int dotIdx = origin.lastIndexOf("."); //가장 마지막 "."의 인덱스
			String ext = origin.substring(dotIdx + 1); //확장자
			String stored = rename +"."+ext; //저장 파일

			certification.setCeOriginName(origin);
			certification.setCeStoredName(stored);

			uploadFolder.mkdir();

			//실제로 저장될 파일
			File up = new File(uploadFolder.getPath()+"/"+stored);

			try {
				FileInputStream fis = new FileInputStream(defaultImg); //읽을파일
				FileOutputStream fos = new FileOutputStream(up); //복사할파일

				int fileByte = 0; 
				// fis.read()가 -1 이면 파일을 다 읽은것
				while((fileByte = fis.read()) != -1) {
					fos.write(fileByte);
				}

				//자원 사용 종료
				fis.close(); 
				fos.close();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}



		//DB데이터 입력
		Connection conn = JDBCTemplate.getConnection();
		
		//인증 번호
		int ceNo = participantDao.selectCeNo(conn);
		certification.setCeNo(ceNo);
		
		//참가 챌린지 번호 얻어오기
		certification.setPaNo(paNo);

		//인증 정보가 있을 경우
		if(certification != null) {
			//인증글 삽입
			if( participantDao.certificationInsert(conn, certification) > 0 ) {
				JDBCTemplate.commit(conn);
			} else {
				JDBCTemplate.rollback(conn);
			}
		}
		
	} //certificationWrtie End
	
	//수정
	@Override
	public void certificationUpdate(HttpServletRequest req) {
		int paNo = (Integer)req.getSession().getAttribute("paNo"); //paNo 가져오기
		
		//챌린지 정보 저장할 객체
		Certification certification = null;
		
		//파일업로드 형태의 데이터가 맞는지 검사
		boolean isMultipart = false;
		isMultipart = ServletFileUpload.isMultipartContent(req);

		//multipart/form-data 인코딩으로 전송되지 않았을 경우
		if( !isMultipart ) {
			System.out.println("[ERROR] multipart/form-data 형식이 아님");
			return; //fileupload() 메소드 실행 중지
		}
		//인증 정보 저장할 객체 생성
		certification = new Certification();

		//디스트기반 아이템 팩토리
		DiskFileItemFactory factory = new DiskFileItemFactory();

		//메모리 처리 사이즈 지정
		factory.setSizeThreshold( 1  * 1024 ); //1KB

		//임시 저장소 설정
		File repository = new File(req.getServletContext().getRealPath("tmp"));
		repository.mkdir();

		factory.setRepository(repository);

		//파일업로드 객체 생성
		ServletFileUpload upload = new ServletFileUpload(factory);

		//업로드 용량 제한
		upload.setFileSizeMax( 10*1024*1024 ); //10MB

		//전달 데이터 파싱
		List<FileItem> items = null;
		try {

			items = upload.parseRequest(req);
		} catch (FileUploadException e) {
			e.printStackTrace();
		}

		//추출된 전달파라미터 처리 반복자
		Iterator<FileItem> iter = items.iterator();

		//모든 요청 정보 처리하기
		while( iter.hasNext() ) {
			FileItem item = iter.next();

			// 1) 빈 파일 처리
			if( item.getSize() <= 0 )	continue;
			
			if( item.isFormField() ) {
				String key = item.getFieldName(); //키 추출
				
				try {
					if( "ceNo".equals(key) ) { //전달파라미터 name이 "ceNo"
						certification.setCeNo(Integer.parseInt(item.getString("UTF-8")));
					} 
				}catch(UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				
			}
			// 3) 파일 처리
			if( !item.isFormField() ) {
				// java.util.Date -> String 변환 - SimpleDateFormat 클래스
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssS");
				String rename = sdf.format(new Date());

				//확장자는 원본파일 확장자 얻어오기
				String origin = item.getName(); // 원본파일 이름
				int dotIdx = origin.lastIndexOf("."); //가장 마지막 "."의 인덱스
				
				//확장자
				String ext = origin.substring(dotIdx + 1);
				
				//서버에 저장될 파일의 이름
				String stored = rename +"."+ext;

				//						System.out.println("[TEST] 원본 파일명 : "+origin);
				//						System.out.println("[TEST] 저장될 파일명 : "+stored);

				// --- 첨부파일 정보 객체 ---
				certification.setCeOriginName(origin);
				certification.setCeStoredName(stored);
				// --------------------------

				//업로드된 임시파일을 실제로 저장하기
				// -> 1MB이하 파일 : 메모리에 임시파일로 업로드
				// -> 1MB이상 파일 : HDD에 임시파일로 업로드
				File uploadFolder = new File(req.getServletContext().getRealPath("upload")); // 업로드될 폴더 경로

				uploadFolder.mkdir();

				//실제로 저장될 파일
				File up = new File(uploadFolder, stored);

				try {
					item.write(up); //실제 업로드(최종 결과 파일이 생성됨)
					item.delete(); //임시 파일 삭제
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} // 파일 처리 end

		} // while( iter.hasNext() ) end - FileItem 반복 처리

		//파일이 선택되지 않았을경우
		if(certification.getCeOriginName()==null || "".equals(certification.getCeOriginName())) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssS");
			String rename = sdf.format(new Date());

			File uploadFolder = new File(req.getServletContext().getRealPath("upload")); // 업로드될 폴더 경로
			File defaultImg = new File(req.getServletContext().getRealPath("resources/img/AchievementWhite.png")); //이미지 파일 경로

			String origin = defaultImg.getName(); //파일의 본래 이름AchievementWhite.png
			int dotIdx = origin.lastIndexOf("."); //가장 마지막 "."의 인덱스
			String ext = origin.substring(dotIdx + 1); //확장자
			String stored = rename +"."+ext; //저장 파일

			certification.setCeOriginName(origin);
			certification.setCeStoredName(stored);

			uploadFolder.mkdir();

			//실제로 저장될 파일
			File up = new File(uploadFolder.getPath()+"/"+stored);
			try {
				FileInputStream fis = new FileInputStream(defaultImg); //읽을파일
				FileOutputStream fos = new FileOutputStream(up); //복사할파일

				int fileByte = 0; 
				// fis.read()가 -1 이면 파일을 다 읽은것
				while((fileByte = fis.read()) != -1) {
					fos.write(fileByte);
				}
				//자원 사용 종료
				fis.close(); 
				fos.close();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		//storardName을 가져온다
		Certification removeCertification=participantDao.selectByStoredName(JDBCTemplate.getConnection(), certification);

		String path=req.getServletContext().getRealPath("upload");
		//기존 파일 삭제 로직
		File file = new File(path+"/"+removeCertification.getCeStoredName());
		fileRemove.setFile(file);

		boolean remove = fileRemove.fileRemove(); //파일 삭제
		if(remove) {
			System.out.println("파일 삭제 성공");
		}else {
			System.out.println("파일 삭제 실패");
		}
		
		//DB데이터 입력
		Connection conn = JDBCTemplate.getConnection();
		
		//참가 챌린지 번호 얻어오기
		certification.setPaNo(paNo);

		//인증 정보가 있을 경우
		if(certification != null) {
			//인증글 수정
			if( participantDao.certificationUpdate(conn, certification) > 0 ) {
				JDBCTemplate.commit(conn);
			} else {
				JDBCTemplate.rollback(conn);
			}
		}
	}
	@Override
	public void certificationDelete(HttpServletRequest req) {
		String param = req.getParameter("ceNo");
		Certification certification = null;
		if(param!=null && !"".equals(param)) {
			certification= new Certification();
			certification.setCeNo(Integer.parseInt(param));
			
		}
		//storardName을 가져온다
		Certification removeCertification=participantDao.selectByStoredName(JDBCTemplate.getConnection(), certification);

		String path=req.getServletContext().getRealPath("upload");
		//기존 파일 삭제 로직
		File file = new File(path+"/"+removeCertification.getCeStoredName());
		fileRemove.setFile(file);

		boolean remove = fileRemove.fileRemove(); //파일 삭제
		if(remove) {
			System.out.println("파일 삭제 성공");
		}else {
			System.out.println("파일 삭제 실패");
		}

		
		//인증 정보가 있을 경우
		if(certification != null) {
			//인증글 삭제
			if( participantDao.certificationDelete(JDBCTemplate.getConnection(), certification) > 0 ) {
				JDBCTemplate.commit(JDBCTemplate.getConnection());
			} else {
				JDBCTemplate.rollback(JDBCTemplate.getConnection());
			}
		}
		
	}
	
	//신고 내역 저장
	@Override
	public void insertComplaint(Complaint complaint) {
		if( participantDao.complaintInsert(JDBCTemplate.getConnection(), complaint) > 0 ) {
			JDBCTemplate.commit(JDBCTemplate.getConnection());
		} else {
			JDBCTemplate.rollback(JDBCTemplate.getConnection());
		}
		
	}
	
	//리뷰 추출
	@Override
	public Participation getReview(HttpServletRequest req) {
		//session에서 값을 가져온다
		Participation participation =null;
		String review = req.getParameter("paReview");
		if(review !=null && !"".equals(review)) {
			participation = new Participation();
			int chNo = (Integer)req.getSession().getAttribute("chNo");
			int uNo = (Integer)req.getSession().getAttribute("u_no");
			int paNo = participantDao.selectByPaNo(JDBCTemplate.getConnection(), chNo, uNo);
			participation.setPaNo(paNo);
			participation.setPaReview(review);
			
		}
		
		return participation;
	}
	@Override
	public void insertReview(Participation participation) {
		if( participantDao.reviewInsert(JDBCTemplate.getConnection(), participation) > 0 ) {
			JDBCTemplate.commit(JDBCTemplate.getConnection());
		} else {
			JDBCTemplate.rollback(JDBCTemplate.getConnection());
		}
		
	}
	@Override
	public String refundsToken() throws IOException {
		HttpURLConnection conn=null;
		URL url = new URL("https://api.iamport.kr/users/getToken"); //엑세스할 토큰을 받아올 주소 입력
		conn = (HttpURLConnection)url.openConnection();
		
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Accept", "application/json");
		
		//Data설정
		conn.setDoOutput(true); //OutputStream으로 POST 데이터를 넘겨주겠다는 옵션
		JSONObject obj = new JSONObject();
		obj.put("imp_key", "9081765440266501"); 
		obj.put("imp_secret","DdLtVjTUlJ47lCyLGOsnmwycGAlfngk0u6uqpnkK7oSs0qeHfG5BdDNTd99BqtBVA6m0tGLB5D364hOj"); 
		
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
		bw.write(obj.toString());
		bw.flush();
		bw.close();
		
		int responseCode = conn.getResponseCode(); //응답코드 받기
		System.out.println("응답코드 : "+responseCode);
		StringBuilder sb=null;
		
		if(responseCode==200) {//성공
			//System.out.println("토큰 발급 성공!!");
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			sb = new StringBuilder();
			String line = null;
			while((line=br.readLine())!=null) {
				sb.append(line+"\n");
			}
			br.close();
			System.out.println(""+sb.toString());
			
		}else {
			System.out.println(conn.getResponseMessage());
		}
		String  token= sb.toString(); //발급된 통근 반환
		return token;
	}
	@Override
	public Map<String, Integer> getChNoUno(HttpServletRequest req) {
		Map<String, Integer> paMap = new HashMap<>();
		int chNo = 0;
		int uNo = 0;
		if(req.getParameter("chNo")!=null) {
			chNo = Integer.parseInt(req.getParameter("chNo"));
		}else if(req.getSession().getAttribute("chNo")!=null) {
			chNo = (Integer)req.getSession().getAttribute("chNo");
		}
		
		if(req.getSession().getAttribute("u_no")!=null) {
			uNo = (Integer)req.getSession().getAttribute("u_no");
		}

		paMap.put("chNo", chNo);
		paMap.put("uNo", uNo);
		
		return paMap;
	}
	//환급 정보 가져오기
	@Override
	public Payback getPayback(Map<String, Integer> paMap) {
		//payment를 조회하여 payBack를 가져온다
		Payback payback = participantDao.selectPayback(JDBCTemplate.getConnection(),paMap);
		
		int paybNo = participantDao.selectByPaybNo(JDBCTemplate.getConnection());
		
		payback.setPaybNo(paybNo);
		//kg이니시스 은행코드로 변환
		BankCode bankCode = new BankCode(payback.getPaybRefundBank());
		payback.setPaybRefundBank(bankCode.getCode());
		
		return payback;
	}
	
	
	@Override
	public void payback(Payback payback, String token) throws IOException {
		int responseCode; //응답 코드
		URL url = new URL("https://api.iamport.kr/payments/cancel"); //환불 주소
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		JSONObject jobj = new JSONObject(token);
		
		//응답 객체
		JSONObject post1Object = jobj.getJSONObject("response");
		System.out.println(post1Object.toString()); 
		String access_token = post1Object.getString("access_token");
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json"); //header 설정
		conn.setRequestProperty("Authorization", access_token); //header 설정
		conn.setRequestProperty("Accept", "application/json");
		//Data설정
		conn.setDoOutput(true); //OutputStream으로 POST 데이터를 넘겨주겠다는 옵션
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
		JSONObject obj = new JSONObject(); //JSON 객체
		StringBuilder sb = new StringBuilder();
		
		int now = post1Object.getInt("now");
		int expired_at  = post1Object.getInt("expired_at");
	
		
		System.out.println("token(response): " + access_token);
	    System.out.println("now(response): " + now);
	    System.out.println("expired_at(response): " + expired_at);
		
		
		obj = new JSONObject();
		
		
		obj.put("imp_uid", payback.getImpUid());
		obj.put("amount", payback.getPaybAmount());
		obj.put("checksum",payback.getPaybChecksum());
		obj.put("reason",payback.getPaybReason());
		obj.put("refund_holder",payback.getPaybRefundHolder());
		obj.put("refund_bank", payback.getPaybRefundBank());
		obj.put("refund_account",payback.getPaybReFundAccount());
		
		bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
		bw.write(obj.toString());
		bw.flush();
		bw.close();
	    
		
		responseCode = conn.getResponseCode(); //응답코드 받기
		
		System.out.println("응답코드 : "+responseCode);
		sb=null;
		
		if(responseCode==200) {//성공
			System.out.println("환불성공!!");
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			sb = new StringBuilder();
			String line = null;
			while((line=br.readLine())!=null) {
				sb.append(line+"\n");
			}
			br.close();
			System.out.println(""+sb.toString());
			JSONObject messageObj = new JSONObject(sb.toString());
			//메시지가 있을때 출력
			if(!messageObj.isNull("message")) { //메시지가 null이 아닐때
				String message = messageObj.getString("message");
				System.out.println("message:"+message);
			}
		}else {
			System.out.println(conn.getResponseMessage());
		}
		
	}
	@Override
	public void paybackInsert(Payback payback) { //환급 정보 저장
		if( participantDao.paybackInsert(JDBCTemplate.getConnection(), payback) > 0 ) {
			JDBCTemplate.commit(JDBCTemplate.getConnection());
		} else {
			JDBCTemplate.rollback(JDBCTemplate.getConnection());
		}
		
	}
	
	@Override
	public void participationDelete(int paNo) {
		//참가자 삭제
		if( participantDao.participationDelete(JDBCTemplate.getConnection(), paNo) > 0 ) {
			JDBCTemplate.commit(JDBCTemplate.getConnection());
		} else {
			JDBCTemplate.rollback(JDBCTemplate.getConnection());
		}
	}
	@Override
	public int getCecNo(int chNo) {
		//참가 챌린지의 인증 주기 번호 가져오기
		return participantDao.getCecNo(JDBCTemplate.getConnection(),chNo);
	}
	
	@Override
	public CertificationCycle getCertificationCycle(int chNo) {
		//인증 주기 가져오기
		return participantDao.selectCertificationCycle(JDBCTemplate.getConnection(), chNo);
	}
	@Override
	public Map<String, Date> getChallengeDate(int chNo) {
		
		return participantDao.selectChallengeDate(JDBCTemplate.getConnection(), chNo);
	}
	@Override
	public int getWeeks(Map<String, Date> challengeDate, int cycle) {
		Long lday = (challengeDate.get("chEndDate").getTime() - challengeDate.get("chStartDate").getTime())/(24*60*60*1000);
		double day = lday.doubleValue();
		double total = day/cycle;
		int section = (int)Math.ceil(total); 
		
		
		//double total = count*(day/cycle); //총 인증을 해야할 횟수
		
		//System.out.println("total:"+total);
		return section;
	}
	
	@Override
	public List<Map<String, Date>> getSectionAll(Map<String, Date> challengeDate, int cecCycle, int section) {
		List<Map<String, Date>> result = new ArrayList<>();
		Date startDate = challengeDate.get("chStartDate");
		Date endDate = challengeDate.get("chEndDate");
		Map<String, Date> element = null;
		
		for(int i=0; i<section; i++) {
			Date curDate=addDate(startDate, cecCycle); // 합산한 날짜
			if(endDate.getTime() - curDate.getTime()<=0) { //합산한 날짜가 최종 챌린지보다 이상일떄(종료)
				element = new HashMap<String, Date>();
				element.put("chStartDate", startDate); //시작 날짜
				element.put("chEndDate", endDate); //끝나는 날짜
				result.add(element); //저장
			}else { //뒤에 날짜가 더 있다
				element = new HashMap<String, Date>();
				element.put("chStartDate", startDate); //시작 날짜
				element.put("chEndDate", curDate); //합산한 날짜 끝나는 날짜
				result.add(element); //저장
			}
			startDate = curDate; //시작날짜를 현재날짜로
		}
		
		return result;
	}
	@Override
	public Map<String, Date> getCurSection(Map<String, Date> challengeDate, int cecCycle, int section) {
		Date startDate = challengeDate.get("chStartDate");
		Date endDate = challengeDate.get("chEndDate");
		Date nowDate = new Date();
		Map<String, Date> element = null;
		
		for(int i=0; i<section; i++) {
			Date curDate=addDate(startDate, cecCycle); // 합산한 날짜
			if(endDate.getTime() - curDate.getTime()<=0) { //합산한 날짜가 최종 챌린지보다 이상일떄(종료)
				//시작 날짜보다 크고 끝나는 날짜보다는 작아야 한다
				if(nowDate.getTime() - startDate.getTime()>=0 && nowDate.getTime() - endDate.getTime()<=0){
					element = new HashMap<String, Date>();
					element.put("chStartDate", startDate); //시작 날짜
					element.put("chEndDate", endDate); //끝나는 날짜
				}
			}else { //뒤에 날짜가 더 있다
				if(nowDate.getTime() - startDate.getTime()>=0 && nowDate.getTime() - endDate.getTime()<=0){
					element = new HashMap<String, Date>();
					element.put("chStartDate", startDate); //시작 날짜
					element.put("chEndDate", curDate); //끝나는 날짜
				}
			}
			startDate = curDate; //시작날짜를 현재날짜로
		}
		
		return element;
	}
	
	@Override
	public int getCerCount(Map<String, Date> curSection, int paNo) {
		return participantDao.getCerCount(JDBCTemplate.getConnection(), curSection, paNo);
	}
	
	//날짜 더하기
	private Date addDate(Date start, int day) {
		Calendar cal = Calendar.getInstance();

         cal.setTime(start);
         cal.add(Calendar.DATE, day); //날짜 더하기
         
         Date sum =new Date(cal.getTimeInMillis()); //합산한 날짜 반환
         
 
		 return sum;
	}
	
}
