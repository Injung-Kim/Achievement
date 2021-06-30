package controller.user.challengeList;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.Challenge;
import dto.ChallengeCategory;
import dto.ChallengeList;
import service.user.challenge.face.ChallengeService;
import service.user.challenge.impl.ChallengeServiceImpl;

@WebServlet("/user/challenge/popular")
public class popularChallengeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ChallengeService challengeService = new ChallengeServiceImpl();
@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//System.out.println("bestchallenge - [GET]");
		ChallengeCategory subject = challengeService.getCategory(req);
		
		
    	//파라미터로 카테고리 정보 불러오기
    	ChallengeCategory info = challengeService.getCategoryInfo(subject);
    
    	// view에 카테고리 정보 전송하기
    	req.setAttribute("ca_title", info);
    	
    	
    	//인기챌린지 카테고리별 리스트 가져오기
    	List<ChallengeList> list = challengeService.getPopularChallenges(req, subject);
    	
    	//챌린지 참여 수 조회하기
    	List<Integer> popParticipant = challengeService.getCntParticipant(list);
    	//챌린지 좋아요 수 조회하기
    	List<Integer> popLikes = challengeService.getCntLikes(list);
    	
    	//리스트 속성 전달
    	req.setAttribute("popularList", list); //인기챌린지
    	req.setAttribute("popParticipant",popParticipant); //참여자수
    	req.setAttribute("popLikes", popLikes); //좋아요 수
    	
    	req.getRequestDispatcher("/WEB-INF/views/ChallengeList/popularChallenge.jsp").forward(req, resp);
    	
    }       

}
