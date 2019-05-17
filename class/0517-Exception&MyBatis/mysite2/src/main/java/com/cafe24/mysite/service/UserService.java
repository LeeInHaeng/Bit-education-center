package com.cafe24.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.mysite.repository.UserDao;
import com.cafe24.mysite.vo.UserVo;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	public Boolean join(UserVo userVo) {
		return userDao.insert(userVo);
	}
	
	public UserVo getUser(UserVo userVo) {
		return userDao.get(userVo.getEmail(), userVo.getPassword());
	}
	
	// modify용
	public UserVo getUser(Long no) {
		return userDao.get(no);
	}
	
	public Boolean update(UserVo userVo) {
		return userDao.update(userVo);
	}
}
