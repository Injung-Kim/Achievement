package dto;

import java.util.Date;

public class Point {
	private int poNo; //포인트 번호
	private int paNo; //참가자 번호
	private int poPoint; // 포인트
	private double poRate; // 인증 성공률
	private Date poCreateDate; //지급날짜
	@Override
	public String toString() {
		return "Point [poNo=" + poNo + ", paNo=" + paNo + ", poPoint=" + poPoint + ", poRate=" + poRate
				+ ", poCreateDate=" + poCreateDate + "]";
	}
	public int getPoNo() {
		return poNo;
	}
	public void setPoNo(int poNo) {
		this.poNo = poNo;
	}
	public int getPaNo() {
		return paNo;
	}
	public void setPaNo(int paNo) {
		this.paNo = paNo;
	}
	public int getPoPoint() {
		return poPoint;
	}
	public void setPoPoint(int poPoint) {
		this.poPoint = poPoint;
	}
	public double getPoRate() {
		return poRate;
	}
	public void setPoRate(double poRate) {
		this.poRate = poRate;
	}
	public Date getPoCreateDate() {
		return poCreateDate;
	}
	public void setPoCreateDate(Date poCreateDate) {
		this.poCreateDate = poCreateDate;
	}
	
	
}
