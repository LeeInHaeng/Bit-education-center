package com.cafe24.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cafe24.mysite.service.GuestbookService;
import com.cafe24.mysite.vo.GuestbookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestbookController {

	@Autowired
	private GuestbookService guestbookService;
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public String guestbook(Model model) {
		model.addAttribute("list", guestbookService.getList());
		return "guestbook/list";
	}
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public String guestbook(GuestbookVo guestbookVo) {
		guestbookService.insert(guestbookVo);
		return "redirect:/guestbook";
	}
	
	@RequestMapping(value="/delete/{no}", method=RequestMethod.GET)
	public String delete(@PathVariable(value="no") int no, Model model) {
		model.addAttribute("no", no);
		return "guestbook/delete";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(
			@RequestParam(value="no", required=true, defaultValue="-1") long no,
			String password) {

		GuestbookVo guestbookVo = new GuestbookVo();
		guestbookVo.setNo(no);
		guestbookVo.setPassword(password);
		guestbookService.delete(guestbookVo);
		return "redirect:/guestbook";
	}
}
