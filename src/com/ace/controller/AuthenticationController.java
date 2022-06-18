package com.ace.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ace.model.UserBean;

@Controller
public class AuthenticationController {
	@GetMapping("/login")
	public ModelAndView setUpLogin(ModelMap model) {
		return new ModelAndView("LGN001", "data", new UserBean());
	}

	@PostMapping("/login")
	public void login(@ModelAttribute("data") UserBean userBean) {
		System.out.println(userBean.toString());
	}
}
