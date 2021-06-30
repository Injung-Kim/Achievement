package dao.board.face;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import dto.Comment;
import dto.Post;

public interface PostDao {
	public void Update(Post post);
	public Post Select(Post post);
	public void Insert(Post post);
	public void Delete(Post post);
	public void PlusMyPost(Post post);
	public void MinusMyPost(Post post);
	public void UpdateViews(Post post);
	public List<Comment> SelectComment(Comment comment);
	public void DeleteComment(Comment comment);
	public void InsertComment(Comment comment);
	public void UpdateComment(Comment comment);
	public void InsertCIC(Comment comment);
	public void PlusMyComment(Comment comment);
	public void MinusMyComment(Comment comment);
	public String getNick(String uid);
}
