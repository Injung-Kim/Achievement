package controller.mypage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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


@WebServlet("/mypage/home")
public class MypageHomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// memberService 공용 객체
	private MypageService mypageService = new MypageServiceImpl();

	@Override 
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println(req.getSession().getMaxInactiveInterval());
		
		System.out.println("------ [MypageHomeController] Do Get ------");

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

		// 유저의 환급 정보를 가져오는 메소드(사용자취소, 첼린지 삭제시)
		List<Payback> paybackList = new ArrayList<>();
		paybackList = mypageService.getPaybackInfo(member.getUno());

		// 유저의 참여챌린지중 성공한 챌린지 정보를 가져오는 메소드
		List<Participation> parList = new ArrayList<>();
		parList = mypageService.getSuccessChallInfo(member.getUno());

		//유저가 환불받을 금액(상금)을 가져오는 메소드
		List<Refunds> refundsList = new ArrayList<>();
		refundsList = mypageService.getRefundAmount(parList);

		//누적 총상금을 구하는 메소드
		int refundsTotal = mypageService.refundsSum(refundsList);
		req.setAttribute("refundsTotal", refundsTotal); //세션의 정보 저장

		//누적 결재금액을 구하는 메소드		
		int paymentTotal = mypageService.paymentSum(paymentList);
		req.setAttribute("paymentTotal", paymentTotal); //세션의 정보 저장

		req.getRequestDispatcher("/WEB-INF/views/mypage/myHome.jsp")
		.forward(req, resp);
	}
}
