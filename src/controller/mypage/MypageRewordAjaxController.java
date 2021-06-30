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

import dto.Member;
import dto.Mypage;
import dto.Participation;
import dto.Payback;
import dto.Payment;
import dto.Refunds;
import service.member.face.MypageService;
import service.member.impl.MypageServiceImpl;


@WebServlet("/mypage/reword/ajax")
public class MypageRewordAjaxController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// memberService 공용 객체
	private MypageService mypageService = new MypageServiceImpl();


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("MypageRewordAjaxController do get");

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

		//년도와 월을 입력받아 해당기간의 상금목록을 가져온다.
		List<Refunds> reqRefundsPeriodList = mypageService.getReqFunds(req, refundsList);
		req.setAttribute("reqRefundsPeriodList", reqRefundsPeriodList); //세션의 정보 저장

		//해당기간의 상금목록을 받아 상금의 합을 구한다.
		int reqRefundsTotal = mypageService.sumReqFunds(reqRefundsPeriodList);
		req.setAttribute("reqRefundsTotal", reqRefundsTotal); //세션의 정보 저장

		//년도와 월을 입력받아 해당기간의 결재목록을 가져온다.
		List<Payment> reqPaymentPeriodList = mypageService.getReqPayment(req, paymentList);
		req.setAttribute("reqPaymentPeriodList", reqPaymentPeriodList); //세션의 정보 저장

		//해당기간의 결제목록을 받아 결제의 합을 구한다.
		int reqPaymentTotal = mypageService.sumReqPayment(reqPaymentPeriodList);
		req.setAttribute("reqPaymentTotal", reqPaymentTotal); //세션의 정보 저장
		
		//년도와 월을 입력받아 해당기간의 환급목록을 가져온다.
		List<Payback> reqPaybackPeriodList = mypageService.getReqPayback(req, paybackList);
		req.setAttribute("reqPaybackPeriodList", reqPaybackPeriodList); //세션의 정보 저장

		//해당기간의 환급목록을 받아 환급의 합을 구한다.
		int reqPaybackTotal = mypageService.sumReqPayback(reqPaybackPeriodList);
		req.setAttribute("reqPaybackTotal", reqPaybackTotal); //세션의 정보 저장

		req.getRequestDispatcher("/WEB-INF/views/mypage/myRewordAjax.jsp")
		.forward(req, resp);	
	}
}
