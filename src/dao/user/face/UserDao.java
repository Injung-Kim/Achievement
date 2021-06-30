package dao.user.face;

import java.sql.Connection;
import java.util.List;

import dto.RefundPoint;
import dto.Refunds;
import util.Paging;

public interface UserDao {

	
	/**
	 * 조인 결과 전체 개수 구하기
	 * @param conn - 디비 연결 객체
	 * @return 전체개수
	 */
	public int selectCntAll(Connection conn, int uNo);
	
	/**
	 * 페이징 처리
	 * @param conn - DB연결 객체
	 * @param paging - 페이징
	 * @param uNo - 유저번호
	 * @return 페이징 처리된 리스트
	 */
	
	public List<RefundPoint> selectAll(Connection conn, Paging paging, int uNo);
	
	/**
	 * 챌린지 번호
	 * @param conn - DB연결 객체
	 * @param refundPoint - chNo와 uNo
	 * @return refundPoint 반환
	 */
	public RefundPoint selectRefundPoint(Connection connection, RefundPoint refundPoint);
	
	/**
	 * Refunds 객체 생성
	 * @param conn - DB연결 객체
	 * @param refunds - reNo로 해당 객체를 조회한다
	 * @return - 조회된 객체
	 */
	public Refunds selectRefunds(Connection conn, Refunds refunds);

	/**
	 * 현재 포인트 반환
	 * @param conn - DB연결 객체
	 * @param uNo - 사용자 번호
	 * @return 현재 포인트 반환
	 */
	public int selectMypagePoint(Connection conn, int uNo);
	
	/**
	 * 포인트 사용에 따른 update
	 * @param conn - DB연결 객체
	 * @param refunds - 저장 내역
	 * @return 1이면 성공 0이면 실패
	 */
	public int refundsUpdate(Connection conn, Refunds refunds);
	
	/**
	 * 포인트 사용에 따른 update
	 * @param conn - DB연결 객체
	 * @param rePoint 포인트
	 * @return 1이면 성공 0이면 실패
	 */
	public int mypageUpdate(Connection conn, int rePoint, int uNo);

}
