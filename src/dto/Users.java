package dto;

import java.util.Date;

public class Users {
	private int userNo;
	private String userId;
	private String userPw;
	private String name;
	private String userEmail;
	private String userChallenge;
	private String userNick;
	private String gender;
	private Date birth;
	private Date signupDate;
	private String account;
	private String bank;
	private String grade;
	private int postNum;
	private String address;
	private String phone;
	private int caution;
	@Override
	public String toString() {
		return "Users [userNo=" + userNo + ", userId=" + userId + ", userPw=" + userPw + ", name=" + name
				+ ", userEmail=" + userEmail + ", userChallenge=" + userChallenge + ", userNick=" + userNick
				+ ", gender=" + gender + ", birth=" + birth + ", signupDate=" + signupDate + ", account=" + account
				+ ", bank=" + bank + ", grade=" + grade + ", postNum=" + postNum + ", address=" + address
				+ ", phone=" + phone + ", caution=" + caution + "]";
		
	}
	public int getUserNo() {
		return userNo;
	}
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPw() {
		return userPw;
	}
	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserChallenge() {
		return userChallenge;
	}
	public void setUserChallenge(String userChallenge) {
		this.userChallenge = userChallenge;
	}
	public String getUserNick() {
		return userNick;
	}
	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	public Date getSignupDate() {
		return signupDate;
	}
	public void setSignupDate(Date signupDate) {
		this.signupDate = signupDate;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public int getPostNum() {
		return postNum;
	}
	public void setPostNum(int postNum) {
		this.postNum = postNum;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getCaution() {
		return caution;
	}
	public void setCaution(int caution) {
		this.caution = caution;
	}
}
