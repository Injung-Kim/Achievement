package dto;

import java.util.Date;

public class Refunds {
	private int reNo;
	private int paNo;
	private int rePoint;
	private int reAmount;
	private int paybTaxFree;
	private int paybChecksum;
	private Date paybDate;
	private String paybReason;
	private String paybRefundHolder;
	private String paybRefundBank;
	private String paybRefundAccount;
	private String impUid;
	private String merchantUid;
	private String refundAvailability;
	private int refundableAmount; 
	
	
	// to String
	@Override
	public String toString() {
		return "Refunds [reNo=" + reNo + ", paNo=" + paNo + ", rePoint=" + rePoint + ", reAmount=" + reAmount
				+ ", paybTaxFree=" + paybTaxFree + ", paybChecksum=" + paybChecksum + ", paybDate=" + paybDate
				+ ", paybReason=" + paybReason + ", paybRefundHolder=" + paybRefundHolder + ", paybRefundBank="
				+ paybRefundBank + ", paybRefundAccount=" + paybRefundAccount + ", impUid=" + impUid + ", merchantUid="
				+ merchantUid + ", refundAvailability=" + refundAvailability + ", refundableAmount=" + refundableAmount
				+ "]";
	}

	//getter setter
	public final int getReNo() {
		return reNo;
	}

	public final void setReNo(int reNo) {
		this.reNo = reNo;
	}

	public final int getPaNo() {
		return paNo;
	}

	public final void setPaNo(int paNo) {
		this.paNo = paNo;
	}

	public final int getRePoint() {
		return rePoint;
	}

	public final void setRePoint(int rePoint) {
		this.rePoint = rePoint;
	}

	public final int getReAmount() {
		return reAmount;
	}

	public final void setReAmount(int reAmount) {
		this.reAmount = reAmount;
	}

	public final int getPaybTaxFree() {
		return paybTaxFree;
	}

	public final void setPaybTaxFree(int paybTaxFree) {
		this.paybTaxFree = paybTaxFree;
	}

	public final int getPaybChecksum() {
		return paybChecksum;
	}

	public final void setPaybChecksum(int paybChecksum) {
		this.paybChecksum = paybChecksum;
	}

	public final Date getPaybDate() {
		return paybDate;
	}

	public final void setPaybDate(Date paybDate) {
		this.paybDate = paybDate;
	}

	public final String getPaybReason() {
		return paybReason;
	}

	public final void setPaybReason(String paybReason) {
		this.paybReason = paybReason;
	}

	public final String getPaybRefundHolder() {
		return paybRefundHolder;
	}

	public final void setPaybRefundHolder(String paybRefundHolder) {
		this.paybRefundHolder = paybRefundHolder;
	}

	public final String getPaybRefundBank() {
		return paybRefundBank;
	}

	public final void setPaybRefundBank(String paybRefundBank) {
		this.paybRefundBank = paybRefundBank;
	}

	public final String getPaybRefundAccount() {
		return paybRefundAccount;
	}

	public final void setPaybRefundAccount(String paybRefundAccount) {
		this.paybRefundAccount = paybRefundAccount;
	}

	public final String getImpUid() {
		return impUid;
	}

	public final void setImpUid(String impUid) {
		this.impUid = impUid;
	}

	public final String getMerchantUid() {
		return merchantUid;
	}

	public final void setMerchantUid(String merchantUid) {
		this.merchantUid = merchantUid;
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
	


}
