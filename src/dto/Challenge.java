package dto;

import java.util.Date;


public class Challenge {
	private int chNo; //챌린지 번호
	private int caNo; //카테고리 번호
	private int cecNo; //인증주기 번호
	private int uNo; //유저 번호
	private String chTitle; //챌린지 제목
	private String chContent; //챌린지내용
	private Date chCreateDate;// 작성날짜
	private Date chStartDate; // 시작 날짜
	private Date chEndDate; //끝나는 날짜
	private int chMoney; //참가비
	private String chStartTime; //인증 시작 시간
	private String chEndTime; // 인증 끝나는 시간
	private String chWay; //인증 방법
	private int chCaution; // 신고 횟수
	private int chLikes; //좋아요 개수
	private String chOriginName; // 원본 파일 이름(사진) 
	private String chStoredName; //저장 파일 이름(사진)
	private String chState; //챌린지 운영 상태
	
	@Override
	public String toString() {
		return "Challenge [chNo=" + chNo + ", caNo=" + caNo + ", cecNo=" + cecNo + ", uNo=" + uNo + ", chTitle="
				+ chTitle + ", chContent=" + chContent + ", chCreateDate=" + chCreateDate + ", chStartDate="
				+ chStartDate + ", chEndDate=" + chEndDate + ", chMoney=" + chMoney + ", chStartTime=" + chStartTime
				+ ", chEndTime=" + chEndTime + ", chWay=" + chWay + ", chCaution=" + chCaution + ", chLikes=" + chLikes
				+ ", chOriginName=" + chOriginName + ", chStoredName=" + chStoredName + ", chState=" + chState + "]";
	}
	public int getChNo() {
		return chNo;
	}
	public void setChNo(int chNo) {
		this.chNo = chNo;
	}
	public int getCaNo() {
		return caNo;
	}
	public void setCaNo(int caNo) {
		this.caNo = caNo;
	}
	public int getCecNo() {
		return cecNo;
	}
	public void setCecNo(int cecNo) {
		this.cecNo = cecNo;
	}
	
	public int getuNo() {
		return uNo;
	}
	public void setuNo(int uNo) {
		this.uNo = uNo;
	}
	
	public String getChTitle() {
		return chTitle;
	}
	public void setChTitle(String chTitle) {
		this.chTitle = chTitle;
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
	
	public String getChStartTime() {
		return chStartTime;
	}
	public void setChStartTime(String chStartTime) {
		this.chStartTime = chStartTime;
	}
	public String getChEndTime() {
		return chEndTime;
	}
	public void setChEndTime(String chEndTime) {
		this.chEndTime = chEndTime;
	}
	public String getChWay() {
		return chWay;
	}
	public void setChWay(String chWay) {
		this.chWay = chWay;
	}
	public int getChCaution() {
		return chCaution;
	}
	public void setChCaution(int chCaution) {
		this.chCaution = chCaution;
	}
	public int getChLikes() {
		return chLikes;
	}
	public void setChLikes(int chLikes) {
		this.chLikes = chLikes;
	}
	public String getChOriginName() {
		return chOriginName;
	}
	public void setChOriginName(String chOriginName) {
		this.chOriginName = chOriginName;
	}
	public String getChStoredName() {
		return chStoredName;
	}
	public void setChStoredName(String chStoredName) {
		this.chStoredName = chStoredName;
	}
	public String getChState() {
		return chState;
	}
	public void setChState(String chState) {
		this.chState = chState;
	}
	
	
}
