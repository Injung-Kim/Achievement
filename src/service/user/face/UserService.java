package service.user.face;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import dto.RefundPoint;
import dto.Refunds;
import util.Paging;

public interface UserService {
	/**
	 * 페이징 구하기
	 * @param req - 페이징 정보
	 * @return 페이징 객체
	 */
	public Paging getPaging(HttpServletRequest req);
	
	/**
	 * 페이징 처리된 리스트 구하기
	 * @param paging - 페이징
	 * @param uNo - 유저번호
	 * @return 페이징 처리된 리스트
	 */
	
	public List<RefundPoint> getList(Paging paging, int uNo);
	
	/**
	 * 유저 번호 추출
	 * @param req - 요청 객체 
	 * @return 유저 번호
	 */
	public int getuNo(HttpServletRequest req);
	
	/**
	 * 조회할 객체 반환
	 * @param uNo - 유저 번호
	 * @param chNo - 챌린지 번호
	 * @return
	 */
	
	public RefundPoint view(int uNo, int chNo);
	
	/**
	 * 챌린지 번호 추출
	 * @param req - 요청 객체
	 * @return 챌린지 번호
	 */
	public int getChNo(HttpServletRequest req);

	/**
	 * 환불할 refunds객체를 추출해낸다
	 * @param req - 요청 객체
	 * @return refunds
	 */
	
	public Refunds getRefunds(HttpServletRequest req);
	/**
	 * 환불할 refunds객체를 추출해낸다
	 * @param uNo 
	 * @param req - 요청 객체
	 * @return refunds
	 */
	
	/**
	 * 현재 자신이 가지고 있는 포인트 반환
	 * @param uNo - 유저 번호
	 * @return 현재 포인트
	 */
	public int getMypagePoint(int uNo);
	
	/**
	 * 사용자 현재포인트 반환 
	 * @param req - 요청 객체
	 * @return 현재 포인트
	 */
	public int getCurPoint(HttpServletRequest req);
	
	
	/**
	 * refunds 업데이트
	 * @param refunds - 포인트 사용내역 저장
	 */
	public void refundsUpdate(Refunds refunds);
	/**
	 * 포인트 사용 업데이트
	 * @param rePoint - 사용한 포인트
	 */
	public void mypageUpdate(int rePoint, int uNo);
	/**
	 * 환불을 위한 토큰 발급
	 * @return 토큰
	 * @throws IOException
	 */
	public String refundsToken() throws IOException;
	
	
	/**
	 * 최종 환불
	 * @param refunds - 최종 환불 객체
	 * @param token - 토큰
	 */
	public void refunds(Refunds refunds, String token) throws IOException;
	/**
	 * 사용할 수 있는 포인트
	 * @param req - 요청 객체
	 * @return 사용할 수 있는 포인트 반환
	 */
	public int getRefundablePoint(HttpServletRequest req);


	
	

}
