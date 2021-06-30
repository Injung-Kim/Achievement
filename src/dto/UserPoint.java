package dto;

import java.util.Date;

public class UserPoint {
	private int Payments;
	private int getPoint;
	private int UsedPoint;
	private int SumPoint;
	private Date earnedDate;
	private Date UsedDate;
	@Override
	public String toString() {
		return "UserPoint [getPoint=" + getPoint + ", SumPoint=" + SumPoint+ ", UsedPoint=" + UsedPoint + ", earnedDate=" + earnedDate
				+ ", UsedDate=" + UsedDate + ", Payments=" + Payments + "]";
	}
	public int getGetPoint() {
		return getPoint;
	}
	public void setGetPoint(int getPoint) {
		this.getPoint = getPoint;
	}
	public int getUsedPoint() {
		return UsedPoint;
	}
	public void setUsedPoint(int usedPoint) {
		UsedPoint = usedPoint;
	}
	public Date getEarnedDate() {
		return earnedDate;
	}
	public void setEarnedDate(Date earnedDate) {
		this.earnedDate = earnedDate;
	}
	public Date getUsedDate() {
		return UsedDate;
	}
	public void setUsedDate(Date usedDate) {
		UsedDate = usedDate;
	}
	public int getPayments() {
		return Payments;
	}
	public void setPayments(int payments) {
		Payments = payments;
	}
	public int getSumPoint() {
		return SumPoint;
	}
	public void setSumPoint(int sumPoint) {
		SumPoint = sumPoint;
	}
	
}
