package dto;

import java.sql.Date;

public class Comment {
	private int Cno;
	private int Pno;
	private int Uno;
	private int fk_Cno;
	private String U_nick;
	private String U_id;
	private String C_content;
	private Date C_Create_date;
	private int depth;
	
	@Override
	public String toString() {
		return "Comment [Cno=" + Cno + ", Pno=" + Pno + ", Uno=" + Uno + ", fk_Cno=" + fk_Cno + ", U_nick=" + U_nick
				+ ", U_id=" + U_id + ", C_content=" + C_content + ", C_Create_date=" + C_Create_date + "]";
	}
	
	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int getFk_Cno() {
		return fk_Cno;
	}
	public void setFk_Cno(int fk_Cno) {
		this.fk_Cno = fk_Cno;
	}
	public String getU_id() {
		return U_id;
	}
	public void setU_id(String u_id) {
		U_id = u_id;
	}
	public int getCno() {
		return Cno;
	}
	public void setCno(int cno) {
		Cno = cno;
	}
	public int getPno() {
		return Pno;
	}
	public void setPno(int pno) {
		Pno = pno;
	}
	public int getUno() {
		return Uno;
	}
	public void setUno(int uno) {
		Uno = uno;
	}
	public String getU_nick() {
		return U_nick;
	}
	public void setU_nick(String u_nick) {
		U_nick = u_nick;
	}
	public String getC_content() {
		return C_content;
	}
	public void setC_content(String c_content) {
		C_content = c_content;
	}
	public Date getC_Create_date() {
		return C_Create_date;
	}
	public void setC_Create_date(Date c_Create_date) {
		C_Create_date = c_Create_date;
	}
	
	
}
