package service.user.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import common.JDBCTemplate;
import dao.user.face.UserDao;
import dao.user.impl.UserDaoImpl;
import dto.RefundPoint;
import dto.Refunds;
import service.user.face.UserService;
import util.BankCode;
import util.Paging;

public class UserServiceImpl implements UserService {
	
	private UserDao userDao = new UserDaoImpl();
	@Override
	public Paging getPaging(HttpServletRequest req) { //페이징 구하기
		//전달파라미터 curPage 파싱
		String param = req.getParameter("curPage");
		int curPage = 0;
		int uNo = 0;
		if(param != null && !"".equals(param)) {
			curPage = Integer.parseInt(param);
		}
		if(req.getSession().getAttribute("u_no")!=null) {
			uNo = (Integer)req.getSession().getAttribute("u_no");
		}
		//challenge 테이블의 총 챌린지 수를 조회한다
		int totalCount = userDao.selectCntAll(JDBCTemplate.getConnection(), uNo);

		//Paging객체 생성
		Paging paging = new Paging(totalCount, curPage);

		return paging;
	}
	@Override
	public List<RefundPoint> getList(Paging paging, int uNo) {
		List<RefundPoint> list = userDao.selectAll(JDBCTemplate.getConnection(), paging, uNo);
		BankCode bankCode = new BankCode();
		for(int i=0; i<list.size(); i++) {
			String bank=bankCode.getBankTable().get(list.get(i).getPaybRefundBank());
			list.get(i).setPaybRefundBank(bank); //code to name
		}
		
		return list;
	}
	@Override
	public int getuNo(HttpServletRequest req) {
		//유저 번호 추출
		String param = req.getParameter("uNo");
		int uNo = 0;
		if(param != null && !"".equals(param)) {
			uNo = Integer.parseInt(param);
		}
		if(req.getSession().getAttribute("u_no")!=null) {
			uNo= (Integer)req.getSession().getAttribute("u_no");
		}
		
		return uNo;
	}
	
	@Override
	public int getChNo(HttpServletRequest req) {
		//챌린지 번호
		String param = req.getParameter("chNo");
		int chNo = 0;
		if(param != null && !"".equals(param)) {
			chNo = Integer.parseInt(param);
		}
		
		return chNo;
	}
	@Override
	public RefundPoint view(int uNo, int chNo) {
		RefundPoint refundPoint = new RefundPoint();
		refundPoint.setChNo(chNo); //챌린지 번호
		refundPoint.setuNo(uNo); //유저 번호
		BankCode bankCode = new BankCode();
		refundPoint = userDao.selectRefundPoint(JDBCTemplate.getConnection(), refundPoint);
		String bank=bankCode.getBankTable().get(refundPoint.getPaybRefundBank());
		refundPoint.setPaybRefundBank(bank); //code to name
		
		
		return refundPoint;
	}
	@Override
	public Refunds getRefunds(HttpServletRequest req) {
		//환급자 반환
		Refunds refunds = new Refunds();
		refunds.setReNo(Integer.parseInt(req.getParameter("reNo")));
		int point = Integer.parseInt(req.getParameter("refundPoint"));
		refunds=userDao.selectRefunds(JDBCTemplate.getConnection(), refunds);
		int reAmount=refunds.getReAmount()+(point*100); //환불 받을 금액
		int refundableAmount = refunds.getRefundableAmount()-(point*100); //남은 환불액
		
		
		
		refunds.setRePoint(point); //사용 포인트
		refunds.setReAmount(reAmount); //환불 받을 금액 
		refunds.setRefundableAmount(refundableAmount); //남은 환불액
		return refunds;
	}
	@Override
	public int getRefundablePoint(HttpServletRequest req) {
		//환불 가능 포인트
		String param = req.getParameter("refundablePoint");
		int refundablePoint = 0;
		if(param != null && !"".equals(param)) {
			refundablePoint = Integer.parseInt(param);
		}
		
		return refundablePoint;
	}
	
	@Override
	public int getMypagePoint(int uNo) {
		//현재 포인트 반환
		return userDao.selectMypagePoint(JDBCTemplate.getConnection(), uNo);
	}
	@Override
	public int getCurPoint(HttpServletRequest req) {
		// 사용 포인트 반환
		int point = Integer.parseInt(req.getParameter("curPoint"));
		return point;
	}
	@Override
	public void refundsUpdate(Refunds refunds) {
		//사용내역 업데이트
		Connection conn = JDBCTemplate.getConnection();
		if(userDao.refundsUpdate(conn, refunds)>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
	}
	@Override
	public void mypageUpdate(int rePoint, int uNo) {
		//마이페이지 포인트 업데이트
		Connection conn = JDBCTemplate.getConnection();
		if(userDao.mypageUpdate(conn, rePoint, uNo)>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
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
	public void refunds(Refunds refunds, String token) throws IOException {
		
		int responseCode;
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
	
		
//		System.out.println("token(response): " + access_token);
//	    System.out.println("now(response): " + now);
//	    System.out.println("expired_at(response): " + expired_at);
		
		
		obj = new JSONObject();
		
		
		obj.put("imp_uid", refunds.getImpUid());
		obj.put("amount", refunds.getReAmount());
		obj.put("reason",refunds.getPaybReason());
		obj.put("refund_holder",refunds.getPaybRefundHolder());
		obj.put("refund_bank",refunds.getPaybRefundBank());
		obj.put("refund_account",refunds.getPaybRefundAccount());
		
//		System.out.println("impUid:"+refunds.getImpUid());
//		System.out.println("amount:"+refunds.getReAmount());
//		System.out.println("reason:"+refunds.getPaybReason());
//		System.out.println("refund_holder:"+refunds.getPaybRefundHolder());
//		System.out.println("refund_bank:"+refunds.getPaybRefundBank());
//		System.out.println("refund_account:"+refunds.getPaybRefundAccount());
		
		
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
