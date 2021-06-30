package controller.user.challengeList;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;

import org.apache.commons.io.IOUtils;

import dto.Challenge;
import dto.ChallengeCategory;
import dto.ChallengeList;
import service.user.challenge.face.ChallengeService;
import service.user.challenge.impl.ChallengeServiceImpl;

@WebServlet("/user/challenge/new")
public class newChallengeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ChallengeService challengeService = new ChallengeServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//    	System.out.println("chllenge/new - [GET]");
    	//카테고리 번호 요청받아 저장하기
    	ChallengeCategory number = challengeService.getCategory(req);
    	//파라미터로 카테고리 정보 불러오기
    	ChallengeCategory info = challengeService.getCategoryInfo(number);
    	// view에 카테고리 정보 전송하기
    	req.setAttribute("ca_title", info);
    	
    	
    	//신규챌린지 카테고리별 리스트 가져오기
    	List<ChallengeList> list = challengeService.getNewChallenges(req,number);
    	
    	//챌린지 참여 수 조회하기
    	List<Integer> cntParticipant = challengeService.getCntParticipant(list);
    	//챌린지 좋아요 수 조회하기
    	List<Integer> cntLikes = challengeService.getCntLikes(list);
    	//리스트 속성 전달
    	req.setAttribute("newChallenges", list);
    	req.setAttribute("cntParticipant", cntParticipant);
    	req.setAttribute("cntLikes", cntLikes);
    	
    	req.getRequestDispatcher("/WEB-INF/views/ChallengeList/newChallenge.jsp").forward(req, resp);
    	
    }
}
