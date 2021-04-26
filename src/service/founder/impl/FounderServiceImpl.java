package service.founder.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import common.JDBCTemplate;
import dao.founder.face.FounderDao;
import dao.founder.impl.FounderDaoImpl;
import dto.Certification;
import dto.Challenge;
import dto.Mypage;
import dto.Participation;
import dto.Point;
import dto.Refunds;
import service.founder.face.FounderService;
import util.BankCode;
import util.Paging;

public class FounderServiceImpl implements FounderService {
	private FounderDao founderDao = new FounderDaoImpl();
	
	@Override
	public Paging getPaging(HttpServletRequest req) {
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
		int totalCount = founderDao.selectCntAll(JDBCTemplate.getConnection(),challenge);
		//Paging객체 생성
		Paging paging = new Paging(totalCount, curPage);

		return paging;
	}
	@Override
	public int getChallengeno(HttpServletRequest req) {
		int chNo =0; 
		//chNo을 세션에서 구한다
		if(req.getSession().getAttribute("chNo")!=null) {
				chNo = (Integer)req.getSession().getAttribute("chNo");
		}
			
		return chNo;
	}
	@Override
	public Map<String, Integer> getuId(int chNo) {
		
		return founderDao.selectByUno(JDBCTemplate.getConnection(), chNo);
	}
	
	@Override
	public Map<String, Object> getMap(Paging paging, int chNo) {
		
		return founderDao.selectAll(JDBCTemplate.getConnection(), paging, chNo);
	}
	@Override
	public Certification getCertificationno(HttpServletRequest req) {
		Certification certification = null;
		
		//객체가 있으면 코드 삽입
		if(req.getParameter("ceNo")!=null && !"".equals(req.getParameter("ceNo"))) {
			certification = new Certification();
			certification.setCeNo(Integer.parseInt(req.getParameter("ceNo")));
		}
		return certification;
	}
	
	@Override
	public Certification certificationView(Certification certification) {
		
		return founderDao.selectCertification(JDBCTemplate.getConnection(), certification);
	}
	

	@Override
	public void whatherUpdate(HttpServletRequest req) {
		int ceNo = Integer.parseInt(req.getParameter("ceNo"));
		String whather = req.getParameter("whather");
		Connection conn = JDBCTemplate.getConnection();
		
		Certification certification = new Certification();
		certification.setCeNo(ceNo);
		certification.setCeIsSuccess(whather);
		
		if(founderDao.whatherUpdate(conn, certification)>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
	}
	
	//인증이 모두 처리되었는지
	@Override
	public boolean checkCertification(HttpServletRequest req) {
		int chNo =0; 
		boolean check = false;
		//chNo을 세션에서 구한다
		if(req.getSession().getAttribute("chNo")!=null) {
				chNo = (Integer)req.getSession().getAttribute("chNo");
		}
		if(founderDao.selectCeIsSuccess(JDBCTemplate.getConnection(), chNo)>0) {
			check=false; //인증이 처리되지 않음
		}else {
			check=true; //인증이 처리됨
		}
		
		return check;
	}
	//날짜가 모두 처리되었는지
	@Override
	public boolean checkEndDate(HttpServletRequest req) {
		int chNo =0; 
		Date currentDate = new Date(); //현재 날짜
		Date endDate; //끝나는 날짜
		boolean check = false;
		
		//chNo을 세션에서 구한다
		if(req.getSession().getAttribute("chNo")!=null) {
				chNo = (Integer)req.getSession().getAttribute("chNo");
		}
		
		endDate=founderDao.selectByEndDate(JDBCTemplate.getConnection(), chNo);
		
		if(currentDate.before(endDate)) {
			check=false; //아직 챌린지 진행중
		}else if(currentDate.after(endDate)) {
			check=true; //챌린지 끝났을 경우
		}
		
		return check;
	}
	@Override
	public void chStateUpdate(HttpServletRequest req) {
		int chNo =0;
		Connection conn = JDBCTemplate.getConnection();
		
		if(req.getSession().getAttribute("chNo")!=null) {
			chNo = (Integer)req.getSession().getAttribute("chNo");
		}
		if(founderDao.chStateUpdate(conn, chNo)>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
	}
	@Override
	public List<Integer> getPaNoList(int chNo) {
		
		return founderDao.selectAllByPaNo(JDBCTemplate.getConnection(),chNo);
	}
	
	@Override
	public int totalCertification(int chNo) {
		Date startDate=founderDao.selectByStartDate(JDBCTemplate.getConnection(), chNo); //챌린지 시작 날짜
		Date endDate=founderDao.selectByEndDate(JDBCTemplate.getConnection(), chNo);; //챌린지 종료 날짜
		int cecNo=founderDao.selectByCecNo(JDBCTemplate.getConnection(), chNo); //인증 주기 번호
		
		int cycle=founderDao.selectByCycle(JDBCTemplate.getConnection(), cecNo); //인증 주기
		int count=founderDao.selectByCount(JDBCTemplate.getConnection(), cecNo); //인증 횟수
//		System.out.println("-----------------------");
//		System.out.println("startdate:"+startDate);
//		System.out.println("enddate:"+endDate);
//		System.out.println("cecNo:"+cecNo);
//		System.out.println("cycle:"+cycle);
//		System.out.println("count:"+count);
//		System.out.println("------------------------");
		Long lday = (endDate.getTime() - startDate.getTime())/(24*60*60*1000);
		double day = lday.doubleValue();
		
//		System.out.println("일자 :"+day);
//		System.out.println("사이클:"+cycle);
//		System.out.println("인증 횟수:"+count);
		
		double total = count*(day/cycle); //총 인증을 해야할 횟수
		
		System.out.println("total:"+total);
		
		return (int)Math.ceil(total); //총 인증을 해야할 횟수 int형으로 반환(올림)
	}
	@Override
	public Map<Integer, Double> getPaRate(List<Integer> paNoList, int total, int chNo) {
		//참가자 번호, 인증 횟수 불러오기
		Map<Integer, Integer> paCount = founderDao.selectPaCount(JDBCTemplate.getConnection(), paNoList, chNo);
		
		Map<Integer, Double> paRate = new HashMap<>();
		for(Integer key: paCount.keySet()) {
			double value = (double)paCount.get(key);
			//인증율 
			paRate.put(key, (value/total)*100);
		}

		return paRate;
	}
	@Override
	public void pointInsert(Map<Integer, Double> paRate) {
		Connection conn = JDBCTemplate.getConnection();
		Point point = null; //포인트 객체 생성
		for(Integer key: paRate.keySet()) {
			point = new Point();
			int poPoint=0;
			//지급 포인트 기준
			if(paRate.get(key)>95) { //95% 초과 
				poPoint = 50;
			}else if(paRate.get(key)>=90 && paRate.get(key)<=95){ //90~95
				poPoint = 40;
			}else if(paRate.get(key)>=80 && paRate.get(key)<=89){ //80~89
				poPoint = 30;
			}else if(paRate.get(key)>=70 && paRate.get(key)<=79){ //70~79
				poPoint = 20;
			}else if(paRate.get(key)>=60 && paRate.get(key)<=69){ //60~69
				poPoint = 10;
			}
			//포인트 번호
			int poNo= founderDao.selectByPoNo(conn);
			point.setPoNo(poNo); //포인트 번호
			point.setPaNo(key);  //참가자 번호
			point.setPoRate(paRate.get(key)); //성공률
			point.setPoPoint(poPoint); // 포인트
			
			//포인트 테이블 저장
			if(founderDao.insertPoint(conn, point) > 0) {
				JDBCTemplate.commit(conn);
			}else {
				JDBCTemplate.rollback(conn);
			}
			
		}
		
	}
	@Override
	public void mypageUpdate(Map<Integer, Double> paRate) {
		Connection conn = JDBCTemplate.getConnection();
		int poPoint=0;
		//지급 포인트 기준
		for(Integer key: paRate.keySet()) {
			int fail=0;
			int acchall =0;
			Mypage mypage = new Mypage();
			if(paRate.get(key)>95) { //95% 초과 
				poPoint = 50;
				acchall = 1;
			}else if(paRate.get(key)>=90 && paRate.get(key)<=95){ //90~95
				poPoint = 40;
				acchall = 1;
			}else if(paRate.get(key)>=80 && paRate.get(key)<=89){ //80~89
				poPoint = 30;
				acchall = 1;
			}else if(paRate.get(key)>=70 && paRate.get(key)<=79){ //70~79
				poPoint = 20;
				acchall = 1;
			}else if(paRate.get(key)>=60 && paRate.get(key)<=69){ //60~69
				poPoint = 10;
				acchall = 1;
			}else if(paRate.get(key)>=50 && paRate.get(key)<=59){
				acchall = 1;
			}else if(paRate.get(key)<50) { //실패
				fail = 1;
				acchall = 0;
			}
			//포인트 적립
			mypage.setmCpoint(poPoint);
			mypage.setmApoint(poPoint);
			//도전 횟수 +1
			mypage.setmAcchall(acchall);
			//실패 횟수 
			mypage.setmFail(fail);
			//유저 번호
			int uNo = founderDao.getuNo(conn, key);
			mypage.setmNo(uNo);
			
			
			if(founderDao.updateMypage(conn, mypage) > 0) {
				JDBCTemplate.commit(conn);
			}else {
				JDBCTemplate.rollback(conn);
			}
		}
	}
	@Override
	public void participationIsSuccess(Map<Integer, Double> paRate) {
		Connection conn = JDBCTemplate.getConnection();
		//지급 포인트 기준
		for(Integer key: paRate.keySet()) {
				String isSuccess = null;
				if(paRate.get(key)>=50) { //50% 이상 성공 
					isSuccess = "Y"; //성공
				}
				else{ //실패
					isSuccess = "N"; //실패
				}
			if(founderDao.updatePaIsSuccess(conn, key, isSuccess) > 0) {
				JDBCTemplate.commit(conn);
			}else {
				JDBCTemplate.rollback(conn);
			}
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
	public Map<Integer, Participation> refundsPaSuccess(Map<Integer, Double> paRate) {
		Connection conn = JDBCTemplate.getConnection();
		Map<Integer, Participation> paSuMap = new HashMap<>();
		//성공자 불러오기 
		for(Integer key: paRate.keySet()) { //인증 성공률을 반복 조회
			if(paRate.get(key)>=50) { //참여자 성공 key = paNo value participation 유저번호와 챌린지 번호를 저장한다 
				Participation participation=founderDao.selectPaIsSuccess(conn,key);
				paSuMap.put(key, participation); //성공자 객체 저장
			}
		}
		//성공자 반환
		return paSuMap;
	}
	@Override
	public Map<Integer, Participation> refundsPaFail(Map<Integer, Double> paRate) {
		Connection conn = JDBCTemplate.getConnection();
		Map<Integer, Participation> paFailMap = new HashMap<>();
		//실패자 불러오기 
		for(Integer key: paRate.keySet()) {
			if(paRate.get(key)<50) { //실패자 성공 key = paNo value participation 유저번호와 챌린지 번호를 저장한다 
				Participation participation=founderDao.selectPaIsSuccess(conn,key);
				paFailMap.put(key, participation); //실패자 객체 저장
			}
		}
		//실패자 반환
		return paFailMap;
	}
	
	@Override
	public Map<Integer, Refunds> refunder(Map<Integer, Participation> paMap, boolean isSuccess) {
		Connection conn = JDBCTemplate.getConnection();
		Map<Integer, Refunds> reMap = new HashMap<>();
		Refunds refunds = null;
		BankCode bankCode = null;
		//환급자 불러오기 
		for(Integer key: paMap.keySet()) {
			refunds = new Refunds();
			//환급자 기본 정보들 가져오기
			refunds=founderDao.selectRefunds(conn,paMap.get(key));
			//환급자 은행이름을 코드로 변경
			bankCode = new BankCode(refunds.getPaybRefundBank());
			refunds.setPaybRefundBank(bankCode.getCode());
			//환급자 다음 번호 가져오기
			int reNo = founderDao.selectByReno(conn);
			refunds.setReNo(reNo);
			
			if(isSuccess) {//성공자들
				refunds.setPaybReason("챌린지 성공");  // 환불 사유
				refunds.setRefundableAmount(0); //환불 가능액
				refunds.setRefundAvailability("N"); //환불 불가
			}else { //실패자들
				refunds.setPaybReason("챌린지 실패");  // 환불 사유
				refunds.setRefundableAmount(refunds.getReAmount()); //환불 가능액
				refunds.setReAmount(0); // 금액을 안 돌려줌
				refunds.setRefundAvailability("Y"); //환불 가능
			}
			
			//환급자 저장
			reMap.put(key, refunds); //환급자 객체 저장
			
		}
		return reMap;
	}
	@Override
	public void refunds(Map<Integer, Refunds> reMap, String token) throws IOException {
		//최종 환불
		int responseCode; //응답 코드
		for(Integer key: reMap.keySet()) {
			URL url = new URL("https://api.iamport.kr/payments/cancel"); //환불 주소
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			JSONObject jobj = new JSONObject(token);
			
			//응답 객체
			JSONObject post1Object = jobj.getJSONObject("response");
			
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
			
			
			obj.put("imp_uid", reMap.get(key).getImpUid());
			obj.put("amount", reMap.get(key).getReAmount());
			obj.put("checksum",reMap.get(key).getPaybChecksum());
			obj.put("reason",reMap.get(key).getPaybReason());
			obj.put("refund_holder",reMap.get(key).getPaybRefundHolder());
			obj.put("refund_bank",reMap.get(key).getPaybRefundBank());
			obj.put("refund_account",reMap.get(key).getPaybRefundAccount());
			
//			System.out.println("impUid:"+reMap.get(key).getImpUid());
//			System.out.println("amount:"+reMap.get(key).getReAmount());
//			System.out.println("checksum:"+reMap.get(key).getPaybChecksum());
//			System.out.println("reason:"+reMap.get(key).getPaybReason());
//			System.out.println("refund_holder:"+reMap.get(key).getPaybRefundHolder());
//			System.out.println("refund_bank:"+reMap.get(key).getPaybRefundBank());
//			System.out.println("refund_account:"+reMap.get(key).getPaybRefundAccount());
			
			
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
	}
	@Override
	public void refundsInsert(Map<Integer, Refunds> reMap) {
		Connection conn = JDBCTemplate.getConnection();
		
		for(Integer key: reMap.keySet()) {
			if(founderDao.insertRefunds(conn, reMap.get(key))>0) {
				JDBCTemplate.commit(conn);
			}else {
				JDBCTemplate.rollback(conn);
			}
		}
		
	}
	@Override
	public boolean checkStateChallenge(HttpServletRequest req) {
		int chNo =0; 
		boolean check = false;
		
		//chNo을 세션에서 구한다
		if(req.getSession().getAttribute("chNo")!=null) {
				chNo = (Integer)req.getSession().getAttribute("chNo");
		}
		
		String chState = founderDao.selectChallengeCheck(JDBCTemplate.getConnection(), chNo);
		if("W".equals(chState) || "Y".equals(chState)) {
			check=false; //챌린지가 완료 되지 않음
		}else {
			check=true; //챌린지가 완료됨
		}
		
		return check;
	}
	
}

