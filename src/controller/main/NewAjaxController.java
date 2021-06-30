package controller.main;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.Challenge;
import service.mainajax.face.AjaxMainService;
import service.mainajax.impl.AjaxMainServiceImpl;

/**
 * Servlet implementation class NewAjaxController
 */
@WebServlet("/new/ajax")
public class NewAjaxController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AjaxMainService service = new AjaxMainServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//신규 챌린지를 저장할 리스트
		List<Challenge> newList = service.getNewChallenge();
		
		//생성날짜와 현재날짜간 차이를 저장할 리스트
		List<Long> datediff = new ArrayList<>();
		
		SimpleDateFormat time = new SimpleDateFormat("yyyy-mm-dd");
		String today = time.format(System.currentTimeMillis()); //현재 날짜
		
		long calDateDays = 0;//날짜 차이를 담을 변수
		
		for(int i=0; i< newList.size(); i++){
			 String createDate = time.format(newList.get(i).getChCreateDate()); 
			try {
				Date todays = time.parse(today);
				Date challengeDate = time.parse(createDate);
				
				long Days = todays.getTime() - challengeDate.getTime();//밀리초 단위로 계산
				calDateDays = Days / ( 24*60*60*1000); // 일 단위로 변환
				calDateDays = Math.abs(calDateDays); // 실수값 제거		
				
				datediff.add(calDateDays);
				//System.out.println("경과시간: " + calDateDays );
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		//조회값 전달하기
		req.setAttribute("newList", newList);
		req.setAttribute("datediff", datediff);
		req.getRequestDispatcher("/WEB-INF/views/mainAjax/new.jsp")
			.forward(req, resp);
	}
}
