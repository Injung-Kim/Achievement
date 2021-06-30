package dto;

public class RefundPoint {
	private int reNo; // 환불 번호
	private int uNo; //유저번호
	private int chNo; // 챌린지 번호
	private String chTitle; // 챌린지 제목
	private String refundAvailability; //환불 가능 여부
	private int refundableAmount; // 환불 가능 금액
	private String paybRefundHolder; // 예금주
	private String paybRefundBank; // 환불 가능 은행
	private String paybRefundAccount; // 환불 계좌
	private String chStoredName; //챌린지 사진 저장 이름
	private String chOriginName; //챌린지 사진 원본 이름
	
	@Override
	public String toString() {
		return "RefundPoint [reNo=" + reNo + ", uNo=" + uNo + ", chNo=" + chNo + ", chTitle=" + chTitle
				+ ", refundAvailability=" + refundAvailability + ", refundableAmount=" + refundableAmount
				+ ", paybRefundHolder=" + paybRefundHolder + ", paybRefundBank=" + paybRefundBank
				+ ", paybRefundAccount=" + paybRefundAccount + ", chStoredName=" + chStoredName + ", chOriginName="
				+ chOriginName + "]";
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
	public String getChTitle() {
		return chTitle;
	}
	public void setChTitle(String chTitle) {
		this.chTitle = chTitle;
	}
	public String getRefundAvailability() {
		return refundAvailability;
	}
	public void setRefundAvailability(String refundAvailability) {
		this.refundAvailability = refundAvailability;
	}
	public int getRefundableAmount() {
		return refundableAmount;
	}
	public void setRefundableAmount(int refundableAmount) {
		this.refundableAmount = refundableAmount;
	}
	public String getPaybRefundHolder() {
		return paybRefundHolder;
	}
	public void setPaybRefundHolder(String paybRefundHolder) {
		this.paybRefundHolder = paybRefundHolder;
	}
	public String getPaybRefundBank() {
		return paybRefundBank;
	}
	public void setPaybRefundBank(String paybRefundBank) {
		this.paybRefundBank = paybRefundBank;
	}
	public String getPaybRefundAccount() {
		return paybRefundAccount;
	}
	public void setPaybRefundAccount(String paybRefundAccount) {
		this.paybRefundAccount = paybRefundAccount;
	}
	public String getChStoredName() {
		return chStoredName;
	}
	public void setChStoredName(String chStoredName) {
		this.chStoredName = chStoredName;
	}
	public String getChOriginName() {
		return chOriginName;
	}
	public void setChOriginName(String chOriginName) {
		this.chOriginName = chOriginName;
	}
	public int getReNo() {
		return reNo;
	}
	public void setReNo(int reNo) {
		this.reNo = reNo;
	}
	
	
	
}
