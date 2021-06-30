package dto;

public class ChallengeCategory {
	private int categoryNo;
	private String title;
	@Override
	public String toString() {
		return "ChallengeCategory [categoryNo=" + categoryNo + ", title=" + title + "]";
	}
	public int getCategoryNo() {
		return categoryNo;
	}
	public void setCategoryNo(int categoryNo) {
		this.categoryNo = categoryNo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
}
