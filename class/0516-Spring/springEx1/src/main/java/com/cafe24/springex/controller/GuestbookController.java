package com.cafe24.springex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/guestbook/*")
public class GuestbookController {

	@ResponseBody
	@RequestMapping
	public String list() {
		return "GuestbookController:list";
	}
}
