package com.cafe24.mysite.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cafe24.mysite.service.UserService;
import com.cafe24.mysite.vo.UserVo;
import com.cafe24.security.Auth;
import com.cafe24.security.AuthUser;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join(@ModelAttribute UserVo userVo) {
		return "user/join";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(
			@ModelAttribute @Valid UserVo userVo,
			BindingResult result,
			Model model) {
		
		if(result.hasErrors()) {
//			List<ObjectError> list = result.getAllErrors();
//			for(ObjectError error : list) {
//				System.out.println(error);
//			}
			model.addAllAttributes(result.getModel());
			return "user/join";
		}
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
	
	@Auth
	@RequestMapping(value="/update", method=RequestMethod.GET)
	public String update(HttpSession session, Model model, @AuthUser UserVo authUser) {
		//if(session != null && session.getAttribute("authUser")!=null) {
			//UserVo authUser = (UserVo) session.getAttribute("authUser");
			model.addAttribute("authUser", userService.getUser(authUser.getNo()));
			return "user/update";
		//}
		//return "redirect:/user/login";
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String update(UserVo userVo, HttpSession session) {
		//if(session != null && session.getAttribute("authUser")!=null) {
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
		//}
		//return "redirect:/user/login";
	}

}
