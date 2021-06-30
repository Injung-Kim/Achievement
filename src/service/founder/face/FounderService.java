package service.founder.face;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import dto.Certification;
import dto.Participation;
import dto.Refunds;
import util.Paging;

public interface FounderService {
	/**
	 * chNo을 통하여 inner join으로 참여한 챌린지의 인증 페이징을 불러옴 
	 * @param req - 전달 객체 chNo
	 * @return 페이징 객체 
	 */
	public Paging getPaging(HttpServletRequest req);

	
	/**
	 * 세션에서 chNo을 추출한다 
	 * 
	 * @param req - chNo을 구한다
	 * @return chNo을 반환한다
	 */
	public int getChallengeno(HttpServletRequest req);

	/**
	 *chNo을 통하여 개설자 아이디를 반환한다
	 * 
	 * @param int - chNo을 통하여 개설자를 구한다
	 * @return 개설자 ID를 반환한다
	 */
	
	public Map<String, Integer> getuId(int chNo);
	
	
	/**
	 * 페이징과 챌린지 번호로 join된 테이블에 데이터를 map형식으로 받아온다
	 * 
	 * @param paging - 페이징 객체
	 * @param chNo - 챌린지 번호
	 * @return
	 */
	public Map<String, Object> getMap(Paging paging, int chNo);


	/**
	 * 인증 번호를 받아온다
	 * 
	 * @param req - 인증 번호 추출
	 * @return certification - 인증 번호가 담긴 certification을 반환한다
	 */
	
	public Certification getCertificationno(HttpServletRequest req);
	/**
	 * 인증 내역을 받아온다
	 * 
	 * @param certification - 인증 번호
	 * @return certification - 전체 정보가 담기 certification
	 */

	public Certification certificationView(Certification certification);

	/**
	 * 인증 성공 여부를 입력한다
	 * @param req - 인증 성공에 관한 데이터
	 */
	public void whatherUpdate(HttpServletRequest req);

	/**
	 * 모든 인증이 처리 되었는지 확인
	 * @param req - 요청 객체
	 * @return - 모든인증이 처리 될 경우 true 인증중 하나라도 처리가 안 된 경우 false
	 */

	public boolean checkCertification(HttpServletRequest req);

	/**
	 * 챌린지 날짜가 지났는지 확인
	 * @param req - 요청 객체
	 * @return - 챌린지 날짜가 지날경우 true 날짜가 지나지 않을 경우 false
	 */
	public boolean checkEndDate(HttpServletRequest req);

	
	/**
	 * 현재 챌린지 상태 변화
	 * @param req - 요청 객체
	 */

	public void chStateUpdate(HttpServletRequest req);

	/**
	 * 참가자 리스트 반환
	 * @param chNo
	 * @return 참가자 리스트 반환
	 */
	public List<Integer> getPaNoList(int chNo);

	
	/**
	 * 총 인증해야할 횟수
	 * 
	 * @param chNo
	 * @return 총 인증 횟수
	 */
	
	public int totalCertification(int chNo);


	/**
	 * 참가자별 인증 성공률을 map으로 받아온다
	 * @param paNoList - 참가자 번호 리스트
	 * @param total - 총 인증해야할 횟수
	 * @param chNo - 조회에 필요한 챌린지 번호
	 * @return
	 */
	public Map<Integer, Double> getPaRate(List<Integer> paNoList, int total, int chNo);

	/**
	 * 성공률에 따라 포인트를 지급한다
	 * 
	 * @param paRate - 참가자 번호와 인증 성공률의 데이터가 들어있다
	 */
	public void pointInsert(Map<Integer, Double> paRate);
	
	
	/**
	 * 마이페이지에 데이터 삽입
	 * 
	 * @param paRate - 참가자 번호와 인증 성공률의 데이터가 들어있다
	 */

	public void mypageUpdate(Map<Integer, Double> paRate);

	/**
	 * 참여자 챌린지 성공 업데이트
	 * @param paRate - 참가자 번호와 인증 성공률의 데이터가 들어있다
	 */
	public void participationIsSuccess(Map<Integer, Double> paRate);

	
	/**
	 * 참여자 챌린지 최종 환급
	 * 
	 * @param paRate - 참가자 번호와 인증 성공률의 데이터
	 * @return 발급된 토큰
	 * @throws IOException 
	 */
	
	public String refundsToken() throws IOException;

	/**
	 * 참여자 챌린지 성공자 반환
	 * 
	 * @param paRate 인증 성공률
	 * @return 성공자 반환
	 */
	public Map<Integer, Participation> refundsPaSuccess(Map<Integer, Double> paRate);
	/**
	 * 환급자 반환
	 * 
	 * @param paMap - 챌린지 성공자
	 * @return 환급자들을 반환
	 */

	public Map<Integer, Refunds> refunder(Map<Integer, Participation> paMap, boolean isSuccess);

	/**
	 * 최종 환급
	 * @param reMap - 환급자 정보
	 * @param token - 환급에 필요한 토큰
	 * @throws IOException 예외처리
	 */
	public void refunds(Map<Integer, Refunds> reMap, String token) throws IOException;
	
	/**
	 * 최종 환급에 따른 DB에 저장
	 * @param reMap - 환급자 정보
	 */

	public void refundsInsert(Map<Integer, Refunds> reMap);
	
	/**
	 * 참여자 챌린지 실패자 반환
	 * 
	 * @param paRate 인증 성공률
	 * @return 실패자 반환
	 */

	public Map<Integer, Participation> refundsPaFail(Map<Integer, Double> paRate);

	/**
	 * 챌린지 상태를 반환한다
	 * @param req - 요청 객체
	 * @return 챌린지 상태 반환
	 */
	public boolean checkStateChallenge(HttpServletRequest req);


}
