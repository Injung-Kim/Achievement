package service.board.face;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import dto.Comment;
import dto.Post;

public interface PostService {
	public Post SelectPost(HttpServletRequest req);
	public void DeletePost(HttpServletRequest req);
	public void UpdatePost(HttpServletRequest req);
	public void UpdateViews(HttpServletRequest req);
	public List<Comment> SelectComment(HttpServletRequest req);
	public void DeleteComment(HttpServletRequest req);
	public void InsertComment(Comment commnet);
	public void UpdateComment(HttpServletRequest req);
	public void InsertCIC(Comment comment);
	public String getNick(HttpSession session);
	public int write(HttpServletRequest req);
}
