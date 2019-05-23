package com.cafe24.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.mysite.dto.PageInfo;
import com.cafe24.mysite.repository.BoardDao;
import com.cafe24.mysite.vo.BoardVo;

@Service
public class BoardService {

	@Autowired
	private BoardDao boardDao;
	
	public long getTotalBoardCount(String searchKeyword) {
		return boardDao.count(searchKeyword);
	}
	
	public boolean insertReplyBoard(BoardVo boardVo) {
		return boardDao.writeReply(boardVo);
	}
	
	public boolean deleteBoard(long boardNo) {
		return boardDao.delete(boardNo);
	}
	
	public boolean writeBoard(BoardVo boardVo) {
		return boardDao.write(boardVo);
	}
	
	public List<BoardVo> getBoardList(PageInfo pageInfo){
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", (pageInfo.getCurrentPage()-1)*pageInfo.getPageSize());
		map.put("end", pageInfo.getPageSize());
		map.put("searchKeyword", pageInfo.getSearchKeyword());
		
		return boardDao.getList(map);
	}
	
	public BoardVo getBoardByNo(long no) {
		boardDao.hit(no);
		return boardDao.get(no);
	}
	
	public boolean updateBoard(BoardVo boardVo) {
		return boardDao.update(boardVo);
	}
}
