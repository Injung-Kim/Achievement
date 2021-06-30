
package dto;

import java.util.Date;

public class AdminComment {
	
	private int c_no;
	private int p_no;
	private int u_no;
	private int fk_Cno;
	private String u_nick;
	private String u_id;
	private String c_content;
	private Date c_create_date;
	@Override
	public String toString() {
		return "AdminComment [c_no=" + c_no + ", p_no=" + p_no + ", u_no=" + u_no + ", fk_Cno=" + fk_Cno + ", u_nick="
				+ u_nick + ", u_id=" + u_id + ", c_content=" + c_content + ", c_create_date=" + c_create_date + "]";
	}
	public int getC_no() {
		return c_no;
	}
	public void setC_no(int c_no) {
		this.c_no = c_no;
	}
	public int getP_no() {
		return p_no;
	}
	public void setP_no(int p_no) {
		this.p_no = p_no;
	}
	public int getU_no() {
		return u_no;
	}
	public void setU_no(int u_no) {
		this.u_no = u_no;
	}
	public int getFk_Cno() {
		return fk_Cno;
	}
	public void setFk_Cno(int fk_Cno) {
		this.fk_Cno = fk_Cno;
	}
	public String getU_nick() {
		return u_nick;
	}
	public void setU_nick(String u_nick) {
		this.u_nick = u_nick;
	}
	public String getU_id() {
		return u_id;
	}
	public void setU_id(String u_id) {
		this.u_id = u_id;
	}
	public String getC_content() {
		return c_content;
	}
	public void setC_content(String c_content) {
		this.c_content = c_content;
	}
	public Date getC_create_date() {
		return c_create_date;
	}
	public void setC_create_date(Date c_create_date) {
		this.c_create_date = c_create_date;
	}
	
	
	

}

