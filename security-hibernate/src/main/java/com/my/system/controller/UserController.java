package com.my.system.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.my.core.model.User;
import com.my.system.service.UserService;

@Controller
@RequestMapping({"/user"})
public class UserController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value="/register",method=RequestMethod.GET)
	public String register() {
		return "register";
	}
	
	@RequestMapping(value="/logon",method=RequestMethod.GET)
	public String logon() {
		return "logon";
	}
	
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public String save(User user) {
		//System.out.println(user.getUsername());
		user.setPassword(DigestUtils.md5Hex(user.getPassword()));
		userService.saveObject(user);
		return "redirect:/";
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(@RequestParam(value="username") String username,
			@RequestParam(value="password") String password,Model model,
			HttpServletRequest request) {
		User user = userService.findUserByNameAndPassword(username,password);
		if (user == null) {
			return "access_denied";
		}
		HttpSession session = request.getSession();
		session.setAttribute("user", user);
		model.addAttribute("user", user);
		return "redirect:userinfo";
	}
	
	@RequestMapping(value="/userinfo",method=RequestMethod.GET)
	public String userinfo() {
		return "userinfo";
	}
	
	@RequestMapping(value="/userinfo/info/info",method=RequestMethod.GET)
	public String info() {
		return "userinfo";
	}
	
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:/";
	}
}
