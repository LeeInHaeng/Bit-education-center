package com.cafe24.springex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user")
public class UserController {

	@RequestMapping(value="/join", method=RequestMethod.GET)
	// @GetMapping("/join") 과 완전 동일
	public String join() {
		return "/WEB-INF/views/join.jsp";
	}
	
	@RequestMapping(value= {"/join", "/j"}, method=RequestMethod.POST)
	public String join(@ModelAttribute UserVo userVo) {
		if(!valid(userVo)) {
			return "/WEB-INF/views/join.jsp";
		}
		System.out.println(userVo);
		return "redirect:/hello";
	}
	
	private boolean valid(UserVo userVo) {
		return false;
	}
}
