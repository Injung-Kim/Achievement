package service.board.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import dao.board.face.BoardDao;
import dao.board.impl.BoardDaoImpl;
import dto.Board;
import service.board.face.BoardService;
import util.Paging;

public class BoardServiceImpl implements BoardService {
	
	private BoardDao boardDao = new BoardDaoImpl();
	
	@Override
	public List<Board> Select(Paging paging, int bno) {
		// TODO Auto-generated method stub
		Board board = new Board();
		board.setBno(bno);
		
		return boardDao.Select(board, paging);
	}

	@Override
	public List<Board> MostViewList(int bno) {
		Board board = new Board();
		board.setBno(bno);
		return boardDao.MostViewList(board);
	}

	@Override
	public List<Board> cSearch(Paging paging, int bno, String word) {
		Board board = new Board();
		board.setBno(bno);

		return boardDao.cSearch(board, paging, word);
	}
	
	@Override
	public List<Board> pSearch(Paging paging, int bno, String word) {
		Board board = new Board();
		board.setBno(bno);

		return boardDao.pSearch(board, paging, word);
	}
	
	@Override
	public Paging getPaging(HttpServletRequest req, int bno) {
		String param = req.getParameter("curPage");
		int curPage = 0;
		if(param != null && !"".equals(param)) {
			curPage = Integer.parseInt(param);
		}
		
		int totalCount = boardDao.selectCnt(bno);

		Paging paging = new Paging(totalCount, curPage, 20);
		
		return paging;
	}
	
	@Override
	public Paging cSearchGetPaging(HttpServletRequest req, int bno, String keyword) {
		String param = req.getParameter("curPage");
		int curPage = 0;
		if(param != null && !"".equals(param)) {
			curPage = Integer.parseInt(param);
		}
		
		int totalCount = boardDao.CSearchAndCnt(bno, keyword);

		Paging paging = new Paging(totalCount, curPage, 20);
		
		return paging;
	}

	@Override
	public Paging pSearchGetPaging(HttpServletRequest req, int bno, String keyword) {
		String param = req.getParameter("curPage");
		int curPage = 0;
		if(param != null && !"".equals(param)) {
			curPage = Integer.parseInt(param);
		}
		
		int totalCount = boardDao.TSearchAndCnt(bno, keyword);

		Paging paging = new Paging(totalCount, curPage, 20);
		
		return paging;
	}

	@Override
	public String[] BoardData(int bno) {
		System.out.println("");
		return boardDao.boardData(bno);
	}


}
