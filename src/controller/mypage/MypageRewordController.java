package controller.mypage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dto.Challenge;
import dto.Member;
import dto.Mypage;
import dto.Participation;
import dto.Payback;
import dto.Payment;
import dto.Refunds;
import service.member.face.MypageService;
import service.member.impl.MypageServiceImpl;


@WebServlet("/mypage/reword")
public class MypageRewordController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// memberService 공용 객체
	private MypageService mypageService = new MypageServiceImpl();


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("------ [MypageRewordController] Do Get ------");
		HttpSession session = req.getSession(); // 세션 객체 생성
		int uNo = (Integer)session.getAttribute("u_no"); // 현재 로그인된 유저아이디를 가져온다.	
		
		// 유저 정보를 가져오는 메소드
		Member member = mypageService.getUserInfo(uNo); 
		req.setAttribute("memberInfo", member);
		
		// 유저 정보(마이페이지)를 가져오는 메소드
		Mypage mypage = mypageService.getMypageInfo(member.getUno());
		req.setAttribute("mypageInfo", mypage);

		// 유저의 결제 정보를 가져오는 메소드
		List<Payment> paymentList = new ArrayList<>();
		paymentList = mypageService.getPaymentInfo(member.getUno());
		req.setAttribute("paymentList", paymentList); //세션의 정보 저장

		// 유저의 환급 정보를 가져오는 메소드(사용자취소, 첼린지 삭제시)
		List<Payback> paybackList = new ArrayList<>();
		paybackList = mypageService.getPaybackInfo(member.getUno());
		req.setAttribute("paybackList", paybackList); //세션의 정보 저장

		// 유저의 참여챌린지중 성공한 챌린지 정보를 가져오는 메소드
		List<Participation> parList = new ArrayList<>();
		parList = mypageService.getSuccessChallInfo(member.getUno());
		req.setAttribute("parList", parList); //세션의 정보 저장

		//유저가 환불받을 금액(상금)을 가져오는 메소드
		List<Refunds> refundsList = new ArrayList<>();
		refundsList = mypageService.getRefundAmount(parList);
		req.setAttribute("refundsList", refundsList); //세션의 정보 저장

		//누적 총상금을 구하는 메소드
		int refundsTotal = mypageService.refundsSum(refundsList);
		req.setAttribute("refundsTotal", refundsTotal); //세션의 정보 저장

		//누적 결재금액을 구하는 메소드		
		int paymentTotal = mypageService.paymentSum(paymentList);
		req.setAttribute("paymentTotal", paymentTotal); //세션의 정보 저장

		//당월의 상금목록을 가져온다. default
		List<Refunds> reqRefundsList = mypageService.getReqFunds(refundsList);
		req.setAttribute("reqRefundsList", reqRefundsList); //세션의 정보 저장

		//당월의 결재목록을 가져온다. default
		List<Payment> reqPaymentList = mypageService.getReqPayment(paymentList);
		req.setAttribute("reqPaymentList", reqPaymentList); //세션의 정보 저장

		//당월의 환급목록을 가져온다. default
		List<Payback> reqPaybackList = mypageService.getReqPayback(paybackList);
		req.setAttribute("reqPaybackList", reqPaybackList); //세션의 정보 저장

		// 이번달 총상금을 구한다.
		int refundsMonthTotal = mypageService.refundsMonthSum(reqRefundsList);
		req.setAttribute("refundsMonthTotal", refundsMonthTotal); //세션의 정보 저장

		// 이번달 총결재를 구한다.
		int paymentMonthTotal = mypageService.paymentMonthSum(reqPaymentList);
		req.setAttribute("paymentMonthTotal", paymentMonthTotal); //세션의 정보 저장

		// 이번달 총환급를 구한다.
		int paybackMonthTotal = mypageService.paybackMonthSum(reqPaybackList);
		req.setAttribute("paybackMonthTotal", paybackMonthTotal); //세션의 정보 저장

		//		// 당월 결재항목 리스트를 받아 챌린지 이름을 리스트로 가져온다.
		//		List<Challenge> challTitleList = mypageService.getChallTitles(paymentList); 
		//		session.setAttribute("challTitleList", challTitleList); //세션의 정보 저장

		req.getRequestDispatcher("/WEB-INF/views/mypage/myReword.jsp")
		.forward(req, resp);
	}

}
