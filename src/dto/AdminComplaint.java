package dto;

import java.util.Date;

public class AdminComplaint {

	private int comNo;				//신고 번호
	private int comUno;				//회원 번호(신고자)
	private int chNo;				//챌린지 번호
	private Date comDate;			//신고날짜
	private String comContent;		//신고내용
	private String comAdminContent;	//관리자 의견
	private String comManage;		//신고 조치
	
	private String comUid;			//신고자 아이디
	private String comUname;		//신고자 이름	
	
	private String chTitle;			//챌린지제목
	private String caTitle;			//카테고리이름
	private int chCaution;			//신고누적횟수
	private String chContent;		//챌린지내용
	private Date chCreateDate;		//챌린지생성일
	private Date chStartDate;		//챌린지시작일
	private Date chEndDate;			//챌린지종료일
	private int chMoney;			//챌린지참가비
	private String cecTitle;		//챌린지인증주기
	private Date chStartTime;		//챌린지시작시간
	private Date chEndTime;			//챌린지마감시간
	private String chWay;			//챌린지인증방법
	private String chUid;			//챌린지개설자아이디
	private int chUcaution;			//챌린지개설자경고횟수	
	
	
	@Override
	public String toString() {
		return "Complaint [comNo=" + comNo + ", comUno=" + comUno + ", chNo=" + chNo + ", comDate=" + comDate
				+ ", comContent=" + comContent + ", comAdminContent=" + comAdminContent + ", comManage=" + comManage
				+ ", comUid=" + comUid + ", comUname=" + comUname + ", chTitle=" + chTitle + ", caTitle=" + caTitle
				+ ", chCaution=" + chCaution + ", chContent=" + chContent + ", chCreateDate=" + chCreateDate
				+ ", chStartDate=" + chStartDate + ", chEndDate=" + chEndDate + ", chMoney=" + chMoney + ", cecTitle="
				+ cecTitle + ", chStartTime=" + chStartTime + ", chEndTime=" + chEndTime + ", chWay=" + chWay
				+ ", chUid=" + chUid + ", chUcaution=" + chUcaution + "]";
	}


	public int getComNo() {
		return comNo;
	}


	public void setComNo(int comNo) {
		this.comNo = comNo;
	}


	public int getComUno() {
		return comUno;
	}


	public void setComUno(int comUno) {
		this.comUno = comUno;
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


	public String getComUid() {
		return comUid;
	}


	public void setComUid(String comUid) {
		this.comUid = comUid;
	}


	public String getComUname() {
		return comUname;
	}


	public void setComUname(String comUname) {
		this.comUname = comUname;
	}


	public String getChTitle() {
		return chTitle;
	}


	public void setChTitle(String chTitle) {
		this.chTitle = chTitle;
	}


	public String getCaTitle() {
		return caTitle;
	}


	public void setCaTitle(String caTitle) {
		this.caTitle = caTitle;
	}


	public int getChCaution() {
		return chCaution;
	}


	public void setChCaution(int chCaution) {
		this.chCaution = chCaution;
	}


	public String getChContent() {
		return chContent;
	}


	public void setChContent(String chContent) {
		this.chContent = chContent;
	}


	public Date getChCreateDate() {
		return chCreateDate;
	}


	public void setChCreateDate(Date chCreateDate) {
		this.chCreateDate = chCreateDate;
	}


	public Date getChStartDate() {
		return chStartDate;
	}


	public void setChStartDate(Date chStartDate) {
		this.chStartDate = chStartDate;
	}


	public Date getChEndDate() {
		return chEndDate;
	}


	public void setChEndDate(Date chEndDate) {
		this.chEndDate = chEndDate;
	}


	public int getChMoney() {
		return chMoney;
	}


	public void setChMoney(int chMoney) {
		this.chMoney = chMoney;
	}


	public String getCecTitle() {
		return cecTitle;
	}


	public void setCecTitle(String cecTitle) {
		this.cecTitle = cecTitle;
	}


	public Date getChStartTime() {
		return chStartTime;
	}


	public void setChStartTime(Date chStartTime) {
		this.chStartTime = chStartTime;
	}


	public Date getChEndTime() {
		return chEndTime;
	}


	public void setChEndTime(Date chEndTime) {
		this.chEndTime = chEndTime;
	}


	public String getChWay() {
		return chWay;
	}


	public void setChWay(String chWay) {
		this.chWay = chWay;
	}


	public String getChUid() {
		return chUid;
	}


	public void setChUid(String chUid) {
		this.chUid = chUid;
	}


	public int getChUcaution() {
		return chUcaution;
	}


	public void setChUcaution(int chUcaution) {
		this.chUcaution = chUcaution;
	}


	
}