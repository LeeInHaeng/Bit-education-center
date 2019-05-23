package com.cafe24.mysite.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cafe24.mysite.dto.PageInfo;
import com.cafe24.mysite.service.BoardService;
import com.cafe24.mysite.vo.BoardVo;
import com.cafe24.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;

	@RequestMapping(value={"","/list"})
	public String list(Model model, @ModelAttribute PageInfo pageInfo) {
		
		pageInfo.setTotalBoardCount(boardService.getTotalBoardCount(""));
		
		model.addAttribute("list", boardService.getBoardList(pageInfo));
		
		return "board/list";
	}
	
	@RequestMapping(value="/list/{no:[0-9]+}", method=RequestMethod.GET)
	public String list(
			Model model,
			@PathVariable("no") int currentPage,
			@RequestParam(value="keyword", required=false, defaultValue="") String searchKeyword) {

		PageInfo pageInfo = new PageInfo();
		pageInfo.setSearchKeyword(searchKeyword);
		
		pageInfo.setTotalBoardCount(boardService.getTotalBoardCount(searchKeyword));
		pageInfo.setCurrentPage(currentPage);
		
		model.addAttribute("list", boardService.getBoardList(pageInfo));
		model.addAttribute("pageInfo", pageInfo);
		return "board/list";
	}
	
	@RequestMapping(value="/writeReply/{boardNo:[0-9]+}", method=RequestMethod.GET)
	public String replyBoard(
			HttpSession session,
			Model model,
			@ModelAttribute @PathVariable("boardNo") long boardNo) {
		if(session != null && session.getAttribute("authUser")!=null) {
			// 정상 동작
			model.addAttribute("boardVo", new BoardVo());
			return "board/writeReply";
		}
		
		return "redirect:/user/login";
	}
	
	@RequestMapping(value="/writeReply/{boardNo:[0-9]+}", method=RequestMethod.POST)
	public String replyBoard(
			HttpSession session,
			Model model,
			@ModelAttribute @PathVariable("boardNo") long boardNo,
			@ModelAttribute @Valid BoardVo boardVo,
			BindingResult result) {
		
		if(session != null && session.getAttribute("authUser")!=null) {
			UserVo authUser = (UserVo) session.getAttribute("authUser");
			
			if(result.hasErrors()) {
				model.addAllAttributes(result.getModel());
				return "board/writeReply";
			}else {
				BoardVo parentBoard = boardService.getBoardByNo(boardNo);
				boardVo.setGroupNo(parentBoard.getGroupNo());
				boardVo.setOrderNo(parentBoard.getOrderNo()+1);
				boardVo.setDepth(parentBoard.getDepth()+1);
				boardVo.setUserNo(authUser.getNo());
				
				boardService.insertReplyBoard(boardVo);
				
				return "redirect:/board/list";
			}
		}else { // 세션이 끊어진 경우
			return "redirect:/user/login";
		}
	}
	
	@RequestMapping("/delete/{no:[0-9]+}")
	public String delete(
			HttpSession session,
			Model model,
			@PathVariable("no") long boardNo) {
		
		if(session != null && session.getAttribute("authUser")!=null) {
			BoardVo boardVo = boardService.getBoardByNo(boardNo);
			long writeUserNo = boardVo.getUserNo();
			UserVo authUser = (UserVo) session.getAttribute("authUser");
			
			if(writeUserNo == authUser.getNo()) {
				// 정상 동작
				boardService.deleteBoard(boardNo);
				return "redirect:/board/list";
			}else {
				// 다른 권한의 사용자
				model.addAttribute("message","권한이 없습니다.");
				model.addAttribute("url","board/list");
				return "main/redirect";
			}
		}else {
			return "redirect:/user/login";
		}
	}
	
	@RequestMapping(value="/modify/{no:[0-9]+}", method=RequestMethod.POST)
	public String modify(
			HttpSession session,
			@PathVariable("no") long boardNo,
			Model model,
			@ModelAttribute @Valid BoardVo boardVo,
			BindingResult result) {
		
		if(session != null && session.getAttribute("authUser")!=null) {
			if(result.hasErrors()) {
				model.addAllAttributes(result.getModel());
				return "board/modify";
			}else {
				boardService.updateBoard(boardVo);
				return "redirect:/board/list";
			}
		}else { // 세션이 끊어진 경우
			return "redirect:/user/login";
		}
	}
	
	@RequestMapping(value="/modify/{no:[0-9]+}", method=RequestMethod.GET)
	public String modify(
			HttpSession session,
			@PathVariable("no") long boardNo,
			Model model) {

		if(session != null && session.getAttribute("authUser")!=null) {
			BoardVo boardVo = boardService.getBoardByNo(boardNo);
			long writeUserNo = boardVo.getUserNo();
			UserVo authUser = (UserVo) session.getAttribute("authUser");
			
			if(writeUserNo == authUser.getNo()) {
				// 정상 동작
				model.addAttribute("authUser", authUser);
				model.addAttribute("boardVo", boardVo);
				return "board/modify";
			}else {
				// 다른 권한의 사용자
				model.addAttribute("message","권한이 없습니다.");
				model.addAttribute("url","board/view/"+boardNo);
				return "main/redirect";
			}
		}else {
			return "redirect:/user/login";
		}
	}
	
	@RequestMapping("/view/{no:[0-9]+}")
	public String view(@PathVariable("no") long no, Model model) {
		model.addAttribute("board", boardService.getBoardByNo(no));
		return "board/view";
	}
	
	//@Auth
	@RequestMapping(value="/write", method=RequestMethod.GET)
	public String write(
			HttpSession session,
			Model model,
			@ModelAttribute BoardVo boardVo) {
		if(session != null && session.getAttribute("authUser")!=null) {
			// 정상 동작
			return "board/write";
		}else {
			return "redirect:/user/login";
		}
	}
	
	//@Auth
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String write(
			HttpSession session,
			Model model,
			@ModelAttribute @Valid BoardVo boardVo,
			BindingResult result) {
		
		if(session != null && session.getAttribute("authUser")!=null) {
			UserVo authUser = (UserVo) session.getAttribute("authUser");
			long userNo = authUser.getNo();
			
			if(result.hasErrors()) {
				model.addAllAttributes(result.getModel());
				return "board/write";
			}else {
				boardVo.setHit(0);
				boardVo.setOrderNo(1);
				boardVo.setDepth(0);
				boardVo.setUserNo(userNo);
				boardService.writeBoard(boardVo);
				return "redirect:/board/list";
			}
		}else { // 세션이 끊어진 경우
			return "redirect:/user/login";
		}
		
	}
}
