
package dto;

import java.util.Date;

public class AdminPost {
	
	private int p_no;
	private int u_no;
	private int b_no;
	private String p_title;
	private String p_content;
	private Date p_create_date;
	private Date p_update_date;
	private int p_views;
	private String U_nick;
	private String u_id;
	private String p_origin_name;
	private String p_stored_name;
	@Override
	public String toString() {
		return "AdminPost [p_no=" + p_no + ", u_no=" + u_no + ", b_no=" + b_no + ", p_title=" + p_title + ", p_content="
				+ p_content + ", p_create_date=" + p_create_date + ", p_update_date=" + p_update_date + ", p_views="
				+ p_views + ", U_nick=" + U_nick + ", u_id=" + u_id + ", p_origin_name=" + p_origin_name
				+ ", p_stored_name=" + p_stored_name + "]";
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
	public int getB_no() {
		return b_no;
	}
	public void setB_no(int b_no) {
		this.b_no = b_no;
	}
	public String getP_title() {
		return p_title;
	}
	public void setP_title(String p_title) {
		this.p_title = p_title;
	}
	public String getP_content() {
		return p_content;
	}
	public void setP_content(String p_content) {
		this.p_content = p_content;
	}
	public Date getP_create_date() {
		return p_create_date;
	}
	public void setP_create_date(Date p_create_date) {
		this.p_create_date = p_create_date;
	}
	public Date getP_update_date() {
		return p_update_date;
	}
	public void setP_update_date(Date p_update_date) {
		this.p_update_date = p_update_date;
	}
	public int getP_views() {
		return p_views;
	}
	public void setP_views(int p_views) {
		this.p_views = p_views;
	}
	public String getU_nick() {
		return U_nick;
	}
	public void setU_nick(String u_nick) {
		U_nick = u_nick;
	}
	public String getU_id() {
		return u_id;
	}
	public void setU_id(String u_id) {
		this.u_id = u_id;
	}
	public String getP_origin_name() {
		return p_origin_name;
	}
	public void setP_origin_name(String p_origin_name) {
		this.p_origin_name = p_origin_name;
	}
	public String getP_stored_name() {
		return p_stored_name;
	}
	public void setP_stored_name(String p_stored_name) {
		this.p_stored_name = p_stored_name;
	}


}

