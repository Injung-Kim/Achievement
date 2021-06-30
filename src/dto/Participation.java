package dto;

import java.util.Date;

public class Participation {
	
	private int paNo; //참여자 번호
	private int chNo; //챌린지 번호
	private int uNo; //사용자 번호
	private Date paCreateDate; //생성 날짜
	private String paIsSuccess; //성공 여부
	private String paReview; //후기
	private String paLike; //좋아요 등록 여부
	
	
	@Override
	public String toString() {
		return "Participation [paNo=" + paNo + ", chNo=" + chNo + ", uNo=" + uNo + ", paCreateDate=" + paCreateDate
				+ ", paIsSuccess=" + paIsSuccess + ", paReview=" + paReview + ", paLike=" + paLike + "]";
	}
	public int getPaNo() {
		return paNo;
	}
	public void setPaNo(int paNo) {
		this.paNo = paNo;
	}
	public int getChNo() {
		return chNo;
	}
	public void setChNo(int chNo) {
		this.chNo = chNo;
	}
	public int getuNo() {
		return uNo;
	}
	public void setuNo(int uNo) {
		this.uNo = uNo;
	}
	public Date getPaCreateDate() {
		return paCreateDate;
	}
	public void setPaCreateDate(Date paCreateDate) {
		this.paCreateDate = paCreateDate;
	}
	public String getPaIsSuccess() {
		return paIsSuccess;
	}
	public void setPaIsSuccess(String paIsSuccess) {
		this.paIsSuccess = paIsSuccess;
	}
	public String getPaReview() {
		return paReview;
	}
	public void setPaReview(String paReview) {
		this.paReview = paReview;
	}
	public String getPaLike() {
		return paLike;
	}
	public void setPaLike(String paLike) {
		this.paLike = paLike;
	}

}
