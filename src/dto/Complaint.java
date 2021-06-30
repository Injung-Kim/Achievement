package dto;

import java.util.Date;

public class Complaint {
	private int comNo; //신고 번호
	private int uNo; //유저 번호
	private int chNo; // 챌린지 번호
	private Date comDate; // 신고 날짜
	private String comContent; // 신고 내용
	private String comAdminContent; // 관리자 의견
	private String comManage; // 신고 상태
	@Override
	public String toString() {
		return "Complaint [comNo=" + comNo + ", uNo=" + uNo + ", chNo=" + chNo + ", comDate=" + comDate
				+ ", comContent=" + comContent + ", comAdminContent=" + comAdminContent + ", comManage=" + comManage
				+ "]";
	}
	public int getComNo() {
		return comNo;
	}
	public void setComNo(int comNo) {
		this.comNo = comNo;
	}
	public int getuNo() {
		return uNo;
	}
	public void setuNo(int uNo) {
		this.uNo = uNo;
	}
	public int getChNo() {
		return chNo;
	}
	public void setChNo(int chNo) {
		this.chNo = chNo;
	}
	public Date getComDate() {
		return comDate;
	}
	public void setComDate(Date comDate) {
		this.comDate = comDate;
	}
	public String getComContent() {
		return comContent;
	}
	public void setComContent(String comContent) {
		this.comContent = comContent;
	}
	public String getComAdminContent() {
		return comAdminContent;
	}
	public void setComAdminContent(String comAdminContent) {
		this.comAdminContent = comAdminContent;
	}
	public String getComManage() {
		return comManage;
	}
	public void setComManage(String comManage) {
		this.comManage = comManage;
	}
	
	
	
}
