package com.cafe24.mysite.action.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cafe24.mysite.dao.UserDao;
import com.cafe24.mysite.vo.UserVo;
import com.cafe24.web.WebUtil;
import com.cafe24.web.mvc.Action;

public class JoinAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String gender = request.getParameter("gender");
		
		UserVo vo = new UserVo();
		vo.setName(name);
		vo.setPassword(password);
		vo.setEmail(email);
		vo.setGender(gender);
		
		new UserDao().insert(vo);
		
		WebUtil.redirect(request, response, request.getContextPath() + "/user?a=joinsuccess");
	}

}
