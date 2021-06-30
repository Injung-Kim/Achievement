package dao.board.face;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import dto.Board;
import util.Paging;

public interface BoardDao {
	public List<Board> Select(Board board, Paging paging);
	public List<Board> MostViewList(Board board);
	public List<Board> cSearch(Board board, Paging paging, String word);
	public List<Board> pSearch(Board board, Paging paging, String word);
	public int selectCnt(int bno);
	public int TSearchAndCnt(int bno, String keyword);
	public int CSearchAndCnt(int bno, String keyword);
	public String[] boardData(int bno);
}
