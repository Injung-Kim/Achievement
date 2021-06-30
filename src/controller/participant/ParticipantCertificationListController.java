package controller.participant;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dto.Certification;
import dto.CertificationCycle;
import service.participant.face.ParticipantService;
import service.participant.impl.ParticipantServiceImpl;
import util.Paging;

/**
 * Servlet implementation class ParticipantCertificationListController
 */
@WebServlet("/participant/certification/list")
public class ParticipantCertificationListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ParticipantService participantService = new ParticipantServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Paging paging =null;
		paging = participantService.getCertificaitonPaging(req);
		List<Certification> certificationList = null;
		//챌린지 번호 얻어오기
		int chNo = participantService.getChallengeno(req);
		//참여 챌린지 번호 얻어오기
		int paNo = participantService.getParticipationno(req);
		//참여중인 챌린지 제목 얻어오기
		String title = participantService.getTitle(req);
		//보여질 인증 리스트
		certificationList = participantService.getList(paging,paNo);
		//챌린지 인증 번호 가져오기
		int cecNo = participantService.getCecNo(chNo);
	
		//인증 주기 얻기
		CertificationCycle certificationCycle =  participantService.getCertificationCycle(cecNo);
		//자신이 챌린지 시작 날짜부터 이번주간 인증한 횟수 구하기
		
		//1) 챌린지를 몇 구간으로 진행하는지
		//1-1 챌린지 시작날짜 및 끝나는 날짜 얻어오기
		Map<String, Date> challengeDate = participantService.getChallengeDate(chNo);
		
		//1-2 챌린지를 몇 구간 인증을 진행해야 하는지
		int section = participantService.getWeeks(challengeDate, certificationCycle.getCecCycle());
		//System.out.println("section:"+section);
		//2) 현재 몇 번째 구간인지
		// 2-1 날짜별로 전체 구간 구하기
		List<Map<String, Date>> sectionAll = participantService.getSectionAll(challengeDate, certificationCycle.getCecCycle(), section);
		
		//2-2 현재 구간 구하기
		Map<String, Date> curSection = participantService.getCurSection(challengeDate, certificationCycle.getCecCycle(), section);
		
		//3) 현재 구간에 인증 횟수
		int cerCount = participantService.getCerCount(curSection, paNo);
		
		
		
		//세션 객체 사용
		HttpSession session = req.getSession();		
		session.setAttribute("chNo", chNo); //chNo저장
		session.setAttribute("paNo", paNo); //paNo저장
		
		//페이징 객체를 MODEL값으로 전달
		req.setAttribute("paging", paging);
		req.setAttribute("title", title);
		req.setAttribute("chNo", chNo);
		req.setAttribute("cerCount", cerCount); //현재 구간 인증 횟수
		req.setAttribute("cecCount", certificationCycle.getCecCount()); //구간 총 인증 횟수
		
		
		//좋아요 등록 여부와 신고 등록 여부를 가져온다
		Map<String, Boolean> whethers = participantService.getWhethers(req); 
		
//		System.out.println(whethers.get("paLike"));
//		System.out.println(whethers.get("paComplaint"));

		//조회결과 MODEL값 전달
		req.setAttribute("certificationList", certificationList);
		req.setAttribute("whethers", whethers);
		
		req.getRequestDispatcher("/WEB-INF/views/participantCertification/list.jsp")
		.forward(req, resp);
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
}
