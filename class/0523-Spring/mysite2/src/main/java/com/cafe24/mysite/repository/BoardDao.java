package com.cafe24.mysite.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.mysite.vo.BoardVo;

@Repository
public class BoardDao {

	@Autowired
	private SqlSession sqlSession;
	
	public Boolean hit(long no) {
		int result = sqlSession.update("board.hit", no);
		return result==1;
	}
	
	public long count(String searchKeyword) {
		long result = sqlSession.selectOne("board.count", searchKeyword);
		return result;
	}
	
	public Boolean writeReply(BoardVo vo) {
		sqlSession.update("board.push", vo);
		int result = sqlSession.insert("board.insertReply", vo);
		return result==1;
	}
	
	public Boolean delete(long no) {
		int result = sqlSession.delete("board.delete", no);
		return result==1;
	}
	
	public Boolean write(BoardVo vo) {
		int result = sqlSession.insert("board.insert", vo);
		return result==1;
	}	
	
	public List<BoardVo> getList(Map<String, Object> map){
		List<BoardVo> result = sqlSession.selectList("board.getList", map);
		return result;
	}	
	
	public BoardVo get(long no) {
		BoardVo result = sqlSession.selectOne("board.get", no);
		return result;
	}
	
	public Boolean update(BoardVo vo) {
		int result = sqlSession.update("board.update", vo);
		return result==1;
	}
}
