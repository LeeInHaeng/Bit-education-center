package com.cafe24.mysite.repository;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.mysite.exception.UserDaoException;
import com.cafe24.mysite.vo.UserVo;

@Repository
public class UserDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public UserVo get(String email) {
		UserVo result = sqlSession.selectOne("user.getByEmail",email);		
		return result;
	}
	
	public UserVo get(Long no) {
		UserVo result = sqlSession.selectOne("user.getByNo",no);		
		return result;
	}
	
	public UserVo get(String email, String password) throws UserDaoException{
		Map<String, String> map = new HashMap<String,String>();
		map.put("email",email);
		map.put("password",password);
		UserVo result = sqlSession.selectOne("user.getByEmailAndPassword", map);
		return result;
	}	

	public Boolean insert(UserVo vo) {
		int count = sqlSession.insert("user.insert", vo);
		return 1 == count;
	}
	
	public Boolean update(UserVo vo) {
		int result = sqlSession.update("user.update", vo);
		return result==1;
	}
	
}
