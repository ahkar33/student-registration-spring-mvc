package com.ace.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ace.dao.UserDao;
import com.ace.dto.user.UserResponseDto;
import com.ace.model.UserBean;

@Controller
public class AuthenticationController {
	
	@Autowired
	private UserDao userDao;
	
	@GetMapping("/login")
	public ModelAndView setUpLogin(ModelMap model) {
		return new ModelAndView("LGN001", "data", new UserBean());
	}

	@PostMapping("/login")
	public String login(@ModelAttribute("data") UserBean userBean, HttpSession session, ModelMap model) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(System.currentTimeMillis());
		String currentDate = formatter.format(date);
		if (userDao.checkLogin(userBean.getEmail(), userBean.getPassword())) {
			UserResponseDto dto = userDao.selectUserByEmail(userBean.getEmail());
			session.setAttribute("date", currentDate);
			session.setAttribute("userInfo", dto);
			return "MNU001";
		} else {
			model.addAttribute("data", userBean);
			model.addAttribute("error", "Email and Password do not match !!");
			return "LGN001";
		}
	}
	
	@GetMapping("/welcome")
	public String showWelcome() {
		return "MNU001";
	}
}
