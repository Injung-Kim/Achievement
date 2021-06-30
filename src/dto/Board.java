package dto;

import java.sql.Date;

public class Board {
	private int Bno;
	private int Pno;
	private String U_nick;
	private String P_Title;
	private Date P_Create_Date;
	private int P_views;
	private String B_name;
	
	@Override
	public String toString() {
		return "Board [Bno=" + Bno + ", Pno=" + Pno + ", U_nick=" + U_nick + ", P_Title=" + P_Title + ", P_Create_Date="
				+ P_Create_Date + ", P_views=" + P_views + ", B_name=" + B_name + "]";
	}
	
	public String getB_name() {
		return B_name;
	}
	public void setB_name(String b_name) {
		B_name = b_name;
	}
	public int getBno() {
		return Bno;
	}
	public void setBno(int bno) {
		Bno = bno;
	}
	public int getPno() {
		return Pno;
	}
	public void setPno(int pno) {
		Pno = pno;
	}
	public String getU_nick() {
		return U_nick;
	}
	public void setU_nick(String u_nick) {
		U_nick = u_nick;
	}
	public String getP_Title() {
		return P_Title;
	}
	public void setP_Title(String p_Title) {
		P_Title = p_Title;
	}
	public Date getP_Create_Date() {
		return P_Create_Date;
	}
	public void setP_Create_Date(Date p_Create_Date) {
		P_Create_Date = p_Create_Date;
	}
	public int getP_views() {
		return P_views;
	}
	public void setP_views(int p_views) {
		P_views = p_views;
	}
	
}
