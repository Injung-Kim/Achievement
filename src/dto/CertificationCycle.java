package dto;

import java.util.Date;

public class CertificationCycle {
	private int cecNo; //인증 번호
	private String cecTitle; //인증 제목
	private Date cecCreateDate; // 인증 날짜
	private int cecCycle; // 인증 주기
	private int cecCount; //인증 횟수
	@Override
	public String toString() {
		return "CertificationCycle [cecNo=" + cecNo + ", cecTitle=" + cecTitle + ", cecCreateDate=" + cecCreateDate
				+ ", cecCycle=" + cecCycle + ", cecCount=" + cecCount + "]";
	}
	public int getCecNo() {
		return cecNo;
	}
	public void setCecNo(int cecNo) {
		this.cecNo = cecNo;
	}
	public String getCecTitle() {
		return cecTitle;
	}
	public void setCecTitle(String cecTitle) {
		this.cecTitle = cecTitle;
	}
	public Date getCecCreateDate() {
		return cecCreateDate;
	}
	public void setCecCreateDate(Date cecCreateDate) {
		this.cecCreateDate = cecCreateDate;
	}
	public int getCecCycle() {
		return cecCycle;
	}
	public void setCecCycle(int cecCycle) {
		this.cecCycle = cecCycle;
	}
	public int getCecCount() {
		return cecCount;
	}
	public void setCecCount(int cecCount) {
		this.cecCount = cecCount;
	}
	
	
}
