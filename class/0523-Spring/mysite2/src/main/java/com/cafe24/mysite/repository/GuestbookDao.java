package com.cafe24.mysite.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.mysite.vo.GuestbookVo;

@Repository
public class GuestbookDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public Boolean delete(GuestbookVo vo) {
		int result = sqlSession.delete("guestbook.delete", vo);
		return result==1;
	}
	
	public Boolean insert(GuestbookVo vo) {
		int result = sqlSession.insert("guestbook.insert", vo);
		return result==1;
	}	
	
	public List<GuestbookVo> getList(){
		List<GuestbookVo> result = sqlSession.selectList("guestbook.getList");
		return result;
	}	
	
}
