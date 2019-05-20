package com.cafe24.mysite.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cafe24.mysite.service.UserService;
import com.cafe24.mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join() {
		return "user/join";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(@ModelAttribute UserVo userVo) {
		userService.join(userVo);
		return "redirect:/user/joinsuccess";
	}
	
	@RequestMapping(value="/joinsuccess")
	public String joinsuccess() {
		return "user/joinsuccess";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() {
		return "user/login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(
			@RequestParam(value="email", required=true, defaultValue="") String email,
			@RequestParam(value="password", required=true, defaultValue="") String password,
			HttpSession session,
			Model model) {
		
		UserVo userVo = new UserVo(email, password);
		UserVo authUser = userService.getUser(userVo);
		if(authUser == null) {
			model.addAttribute("result", "fail");
			return "user/login";
		}
		
		// session 처리
		session.setAttribute("authUser", authUser);
		
		return "redirect:/";
	}
	
	@RequestMapping(value="/logout")
	public String logout(HttpSession session, Model model) {
		
		if(session != null && session.getAttribute("authUser")!=null) {
			session.removeAttribute("authUser");
			session.invalidate();
		}
		
		return "redirect:/";
	}
	
	@RequestMapping(value="/update", method=RequestMethod.GET)
	public String update(HttpSession session, Model model) {
		if(session != null && session.getAttribute("authUser")!=null) {
			UserVo authUser = (UserVo) session.getAttribute("authUser");
			model.addAttribute("authUser", userService.getUser(authUser.getNo()));
			return "user/update";
		}
		return "redirect:/user/login";
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String update(UserVo userVo, HttpSession session) {
		if(session != null && session.getAttribute("authUser")!=null) {
			UserVo authUser = (UserVo) session.getAttribute("authUser");
			if(userVo.getNo() == authUser.getNo()) {
				userService.update(userVo);
				
				// 업데이트 후 수정된 정보로 세션 재연결
				session.removeAttribute("authUser");
				//session.invalidate();
				
				UserVo refreshUserVo = new UserVo(userVo.getEmail(), userVo.getPassword());
				UserVo refreshAuthUser = userService.getUser(refreshUserVo);
				session.setAttribute("authUser", refreshAuthUser);
				
				return "main/index";
			}
			return "redirect:/user/login";
		}
		return "redirect:/user/login";
	}

}
