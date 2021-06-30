package service.board.impl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import dao.board.face.PostDao;
import dao.board.impl.PostDaoImpl;
import dto.Comment;
import dto.Post;
import service.board.face.PostService;

public class PostServiceImpl implements PostService {
	private PostDao postDao = new PostDaoImpl();
	
	@Override
	public Post SelectPost(HttpServletRequest req) {
		Post post = new Post();
		post.setP_no(Integer.parseInt(req.getParameter("pno")));
		return postDao.Select(post);
	}

	@Override
	public void DeletePost(HttpServletRequest req) {
		Post post = new Post();
		post.setP_no(Integer.parseInt(req.getParameter("pno")));
		post.setU_no((int)req.getSession().getAttribute("u_no"));
		
		postDao.Delete(post);
		postDao.MinusMyPost(post);
		
	}

	@Override
	public void UpdatePost(HttpServletRequest req) {
		// TODO Auto-generated method stub
		Post post = new Post();
		
		//파일 업로드 형태 검사
		boolean isMultipart = false;
		isMultipart = ServletFileUpload.isMultipartContent(req);
		
		if(!isMultipart) {
			return;
		}
		
		//아이템 팩토리 객체 생성
		DiskFileItemFactory factory = new DiskFileItemFactory();
		
		//메모리 처리 사이즈 1mb 지정
		factory.setSizeThreshold(1 * 1024 * 1024);
		
		//임시 저장소 설정
		File repository = new File(req.getServletContext().getRealPath("tmp"));
		repository.mkdir();
		
		factory.setRepository(repository);
		
		//파일 업로드 객체 생성
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		//업로드 최대 크기 설정
		upload.setFileSizeMax(10 * 1024 * 1024);
		
		//받아온게시글 정보를 items에 집어넣음
		List<FileItem> items = null;
		try {
			items = upload.parseRequest(req);
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Iterator<FileItem> iter = items.iterator();
		
		//request에서 받아온 게시글 정보 처리
		while(iter.hasNext()) {
			FileItem item = iter.next();
			
			//게시글 내용 객체에 저장
			if(item.getSize() <= 0) continue;
			
			if(item.isFormField()) {
				String key = item.getFieldName();
				if("p_title".equals(key)) {
					try {
						//제목 post객체에 저장
						post.setP_title(item.getString("UTF-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}else if("p_content".equals(key)) {
					try {
						//내용을 post객체에 저장
						post.setP_content(item.getString("UTF-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}else if("pno".equals(key)) {
					try {
						//게시글 번호를 post객체에 저장
						post.setP_no(Integer.parseInt(item.getString("UTF-8")));
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}

			}//게시글 객체 생성 끝
			
			
			//게시글에 올린 첨부파일를 업로드진행
			if(!item.isFormField()) {
				//파일 뒤에 들어갈 UUID 생성
				UUID uuid = UUID.randomUUID();
				String id = uuid.toString().split("-")[0];// 8자리 UUID
				
				//파일을 저장할 경로 설정
				File uploadFolder = new File(req.getServletContext().getRealPath("upload"));
				uploadFolder.mkdir();
				
				//원본파일명에 UUID를 추가해서 저장할 파일명설정
				File up = new File(uploadFolder, item.getName() + "_" + id);
				
				//원본파일 이름 post객체에 저장
				post.setP_origin_name(item.getName());
				//저장될 파일 이름 post객체에 저장
				post.setP_stored_name(item.getName() + "_" + id);
				
				//파일 업로드
				try {
					item.write(up);
					item.delete();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}//파일 업로드 끝
			
		}//while(iter.next())끝

		//Dao에서 게시글 수정 메서드 호출
		postDao.Update(post);
	}

	@Override
	public void UpdateViews(HttpServletRequest req) {
		Post post = new Post();
		post.setP_no(Integer.parseInt(req.getParameter("pno")));
		postDao.UpdateViews(post);
	}

	@Override
	public List<Comment> SelectComment(HttpServletRequest req) {
		Comment comment = new Comment();
		comment.setPno(Integer.parseInt(req.getParameter("pno")));
		List<Comment> commentList = postDao.SelectComment(comment);
		return commentList;
	}

	@Override
	public void DeleteComment(HttpServletRequest req) {
		Comment c = new Comment();
		c.setCno(Integer.parseInt(req.getParameter("cno")));
		c.setUno((int)req.getSession().getAttribute("u_no"));
		postDao.DeleteComment(c);
		
		postDao.MinusMyComment(c);
	}

	@Override
	public void InsertComment(Comment comment) {
		postDao.InsertComment(comment);
		
		postDao.PlusMyComment(comment);
		
	}

	@Override
	public void UpdateComment(HttpServletRequest req) {
		// TODO Auto-generated method stub
		Comment c = new Comment();
		c.setCno(Integer.parseInt(req.getParameter("cno")));
		c.setC_content(req.getParameter("c_content"));
		postDao.UpdateComment(c);
		
	}

	@Override
	public void InsertCIC(Comment comment) {
		postDao.InsertCIC(comment);
		
		postDao.PlusMyComment(comment);
		
	}

	@Override
	public String getNick(HttpSession session) {
		
		return postDao.getNick(session.getAttribute("u_no").toString());
	}

	@Override
	public int write(HttpServletRequest req) {
		//게시글 객체 생성
		Post post = new Post();
		//사용자번호 객체에 저장
		post.setU_no((int)req.getSession().getAttribute("u_no"));
		
		//enctype 검사
		boolean isMultipart = false;
		isMultipart = ServletFileUpload.isMultipartContent(req);
		
		if(!isMultipart) {
			return 0;
		}
		
		//아이템 팩토리 객체 생성
		DiskFileItemFactory factory = new DiskFileItemFactory();
		
		//메모리 처리 사이즈 1mb 지정
		factory.setSizeThreshold(1 * 1024 * 1024);
		
		//임시 저장소 설정
		File repository = new File(req.getServletContext().getRealPath("tmp"));
		repository.mkdir();
		
		factory.setRepository(repository);
		
		//파일 업로드 객체 생성
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		//업로드 최대 크기 10mb로 설정
		upload.setFileSizeMax(10 * 1024 * 1024);
		
		//작성한 게시글 내용을 items에 저장
		List<FileItem> items = null;
		try {
			items = upload.parseRequest(req);
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Iterator<FileItem> iter = items.iterator();
		
		//게시글 정보 처리
		while(iter.hasNext()) {
			FileItem item = iter.next();
			
			//빈파일 처리
			if(item.getSize() <= 0) continue;
			
			
			if(item.isFormField()) {
				String key = item.getFieldName();
				if("p_title".equals(key)) {
					try {
						//작성한 제목 게시글 객체에 저장
						post.setP_title(item.getString("UTF-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}else if("p_content".equals(key)) {
					try {
						//작성한 내용 게시글 객체에 저장
						post.setP_content(item.getString("UTF-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}else if("bno".equals(key)) {
					//게시글 객체에 게시판 번호 저장
					post.setB_no(Integer.parseInt(item.getString()));
				}

			}//게시글 객체 생성 끝
			
			//게시글에 첨부한 파일 업로드 처리
			if(!item.isFormField()) {
				//저장될 파일명뒤에 들어갈 UUID 생성
				UUID uuid = UUID.randomUUID();
				String id = uuid.toString().split("-")[0];// 8자리 UUID
				
				//파일객체생성, 저장될 위치 설정
				File uploadFolder = new File(req.getServletContext().getRealPath("upload"));
				uploadFolder.mkdir();
				
				File up = new File(uploadFolder, item.getName() + "_" + id);
				
				
				//원본 파일명 게시글 객체에 저장
				post.setP_origin_name(item.getName());
				//저장될 파일명 게시글 객체에 저장
				post.setP_stored_name(item.getName() + "_" + id);
				
				//첨부파일 업로드 처리
				try {
					item.write(up);
					item.delete();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}//파일 업로드 끝
			
		}//while(iter.next())끝
		
		//게시글 객체에 사용자 id 저장
		post.setU_id(req.getSession().getAttribute("u_id").toString());
		//게시글 DB에 저장하는 Dao메서드 실행
		postDao.Insert(post);
		//마이페이지에 작성한 게시글수 증가시키는 Dao실행
		postDao.PlusMyPost(post);
		
		return post.getB_no();
	}
	
}
