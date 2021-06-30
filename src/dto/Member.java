package dto;

import java.util.Date;

public class Member {
   private int uno; //번호
   private String uid; //아이디
   private String upassword; //비밀번호
   private String username; //이름
   private String email;//이메일
   private String nick; //별명
   private String phone;//휴대전화
   private String gender; //성별
   private Date birth;//생일
   private Date singup; //가입일
   private String account; //계좌
   private String bank; //은행
   private String ugrade; //등급
   private String post; //우편번호
   private int caution; //경고
   private String challenge; //도전하고 싶은 챌린져
   private String address; //주소

   @Override
public String toString() {
	return "Member [uno=" + uno + ", uid=" + uid + ", upassword=" + upassword + ", username=" + username + ", email="
			+ email + ", nick=" + nick + ", phone=" + phone + ", gender=" + gender + ", birth=" + birth + ", singup="
			+ singup + ", account=" + account + ", bank=" + bank + ", ugrade=" + ugrade + ", post=" + post
			+ ", caution=" + caution + ", challenge=" + challenge + ", address=" + address + "]";
}

public int getUno() {
	return uno;
}

public void setUno(int uno) {
	this.uno = uno;
}

public String getUid() {
	return uid;
}

public void setUid(String uid) {
	this.uid = uid;
}

public String getUpassword() {
	return upassword;
}

public void setUpassword(String upassword) {
	this.upassword = upassword;
}

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public String getNick() {
	return nick;
}

public void setNick(String nick) {
	this.nick = nick;
}

public String getPhone() {
	return phone;
}

public void setPhone(String phone) {
	this.phone = phone;
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

public Date getSingup() {
	return singup;
}

public void setSingup(Date singup) {
	this.singup = singup;
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

public String getUgrade() {
	return ugrade;
}

public void setUgrade(String ugrade) {
	this.ugrade = ugrade;
}

public String getPost() {
	return post;
}

public void setPost(String post) {
	this.post = post;
}

public int getCaution() {
	return caution;
}

public void setCaution(int caution) {
	this.caution = caution;
}

public String getChallenge() {
	return challenge;
}

public void setChallenge(String challenge) {
	this.challenge = challenge;
}

public String getAddress() {
	return address;
}

public void setAddress(String address) {
	this.address = address;
}
   
   
   

   
   
 
   
   
   }