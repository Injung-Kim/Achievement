
package dto;

import java.util.Date;

public class Notice {

	public int n_no;
	public String n_content;
	public String n_title;
	public int n_views;
	public Date n_create_date;
	public Date n_update_date;
	public int u_no;
	@Override
	public String toString() {
		return "notice [n_no=" + n_no + ", n_content=" + n_content + ", n_title=" + n_title + ", n_views=" + n_views
				+ ", n_create_date=" + n_create_date + ", n_update_date=" + n_update_date + ", u_no=" + u_no + "]";
	}
	public int getN_no() {
		return n_no;
	}
	public void setN_no(int n_no) {
		this.n_no = n_no;
	}
	public String getN_content() {
		return n_content;
	}
	public void setN_content(String n_content) {
		this.n_content = n_content;
	}
	public String getN_title() {
		return n_title;
	}
	public void setN_title(String n_title) {
		this.n_title = n_title;
	}
	public int getN_views() {
		return n_views;
	}
	public void setN_views(int n_views) {
		this.n_views = n_views;
	}
	public Date getN_create_date() {
		return n_create_date;
	}
	public void setN_create_date(Date n_create_date) {
		this.n_create_date = n_create_date;
	}
	public Date getN_update_date() {
		return n_update_date;
	}
	public void setN_update_date(Date n_update_date) {
		this.n_update_date = n_update_date;
	}
	public int getU_no() {
		return u_no;
	}
	public void setU_no(int u_no) {
		this.u_no = u_no;
	}
}