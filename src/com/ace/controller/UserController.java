package com.ace.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ace.dao.UserDao;
import com.ace.dto.user.UserRequestDto;
import com.ace.dto.user.UserResponseDto;
import com.ace.model.UserBean;

@Controller
public class UserController {

	@Autowired
	private UserDao userDao;

	@GetMapping("/userManagement")
	public String showUserManagement(ModelMap model) {
		ArrayList<UserResponseDto> userList = userDao.selectAllUsers();
		model.addAttribute("userList", userList);
		return "USR003";
	}

	@GetMapping("/addUser")
	public ModelAndView setupAdduser() {
		return new ModelAndView("USR001", "data", new UserBean());
	}

	@PostMapping("/addUser")
	public String addUser(@ModelAttribute("data") UserBean userBean, ModelMap model) {
		if (userBean.getEmail().isBlank() || userBean.getName().isBlank() || userBean.getPassword().isBlank()
				|| userBean.getConfirmPassword().isBlank() || userBean.getUserRole().isBlank()) {
			model.addAttribute("error", "Fill the blank !!");
			model.addAttribute("data", userBean);
			return "USR001";
		} else if (!userBean.getPassword().equals(userBean.getConfirmPassword())) {
			model.addAttribute("error", "Passwords do not match !!");
			model.addAttribute("data", userBean);
			return "USR001";
		} else {
			ArrayList<UserResponseDto> userList = userDao.selectAllUsers();
			if (userDao.checkEmailExists(userBean.getEmail())) {
				model.addAttribute("error", "Email already exists !!");
				model.addAttribute("data", userBean);
				return "USR001";
			} else {
				if (userList.size() == 0) {
					userBean.setId("USR001");
				} else {
					int tempId = Integer.parseInt(userList.get(userList.size() - 1).getId().substring(3)) + 1;
					String userId = String.format("USR%03d", tempId);
					userBean.setId(userId);
				}
				UserRequestDto reqDto = new UserRequestDto();
				reqDto.setId(userBean.getId());
				reqDto.setEmail(userBean.getEmail());
				reqDto.setName(userBean.getName());
				reqDto.setPassword(userBean.getPassword());
				reqDto.setUserRole(userBean.getUserRole());
				userDao.insertUser(reqDto);
				model.addAttribute("message", "Registered Succesfully !!");
				model.addAttribute("data", new UserBean());
				return "USR001";
			}
		}
	}

	@GetMapping("/updateUser/{id}")
	public ModelAndView setupUpdateUser(@PathVariable("id") String id) {
		UserResponseDto resDto = userDao.selectUserById(id);
		UserBean userBean = new UserBean();
		userBean.setId(id);
		userBean.setEmail(resDto.getEmail());
		userBean.setName(resDto.getName());
		userBean.setPassword(resDto.getPassword());
		userBean.setConfirmPassword(resDto.getPassword());
		userBean.setUserRole(resDto.getUserRole());
		return new ModelAndView("USR002", "data", userBean);
	}

	@PostMapping("/updateUser")
	public String updateUser(@ModelAttribute("data") UserBean userBean, ModelMap model, HttpSession session,
			HttpServletRequest req) {
		UserResponseDto sessionDto = (UserResponseDto) session.getAttribute("userInfo");
		if (userBean.getEmail().isBlank() || userBean.getName().isBlank() || userBean.getPassword().isBlank()
				|| userBean.getConfirmPassword().isBlank() || userBean.getUserRole().isBlank()) {
			model.addAttribute("error", "Fill the blank !!");
			return "USR002";
		} else if (!userBean.getPassword().equals(userBean.getConfirmPassword())) {
			model.addAttribute("error", "Passwords do not match !!");
			return "USR002";
		} else {
			UserResponseDto tempDto = userDao.selectUserById(userBean.getId());
			UserRequestDto reqDto = new UserRequestDto(userBean.getId(), userBean.getEmail(), userBean.getName(),
					userBean.getPassword(), userBean.getUserRole());
			if (!tempDto.getEmail().equals(userBean.getEmail())) {
				if (userDao.checkEmailExists(userBean.getEmail())) {
					model.addAttribute("error", "Email already exists !!");
					return "USR002";
				} else if (!userBean.getPassword().equals(userBean.getConfirmPassword())) {
					model.addAttribute("error", "Passwords do not match !!");
					return "USR002";
				} else {
					userDao.updateUser(reqDto);
					if (reqDto.getEmail().equals(sessionDto.getEmail())) {
						session.setAttribute("userInfo", reqDto);
					}
					ArrayList<UserResponseDto> userList = userDao.selectAllUsers();
					req.getServletContext().setAttribute("userList", userList);
					return "redirect:/userManagement";

				}
			}
			userDao.updateUser(reqDto);
			if (reqDto.getEmail().equals(sessionDto.getEmail())) {
				UserResponseDto resDto = new UserResponseDto(userBean.getId(), userBean.getEmail(), userBean.getName(),
						userBean.getPassword(), userBean.getUserRole());
				session.setAttribute("userInfo", resDto);
			}
			ArrayList<UserResponseDto> userList = userDao.selectAllUsers();
			req.getServletContext().setAttribute("userList", userList);
			return "redirect:/userManagement";
		}
	}
	
	@GetMapping("/deleteUser/{id}")
	public String deleteUser(@PathVariable("id") String id) {
		userDao.deleteUserById(id);
		return "redirect:/userManagement";
	}
	
	@GetMapping("/searchUser")
	public String searchUser(@RequestParam("id") String searchId, @RequestParam("name") String searchName, ModelMap model) {
		// ")#<>(}" <- this is just random bullshit to avoid sql wildcard, not REGEX
		String id = searchId.isBlank() ? ")#<>(}" : searchId;
		String name = searchName.isBlank() ? ")#<>(}" : searchName;
		ArrayList<UserResponseDto> searchUserList = null;
		searchUserList = userDao.selectUserListByIdOrName(id, name);
		if (searchUserList.size() == 0) {
			searchUserList = userDao.selectAllUsers();
		}
		model.addAttribute("userList", searchUserList);
		return "USR003";
	}
}
