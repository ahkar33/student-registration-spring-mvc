package com.ace.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ace.dao.CourseDao;
import com.ace.dto.course.CourseRequestDto;
import com.ace.dto.course.CourseResponseDto;
import com.ace.model.CourseBean;

@Controller
public class CourseController {
	@Autowired
	private CourseDao courseDao;
	
	@GetMapping("/addCourse")
	public ModelAndView setupAddCourse() {
		return new ModelAndView("BUD003", "data", new CourseBean());
	}
	
	@PostMapping("/addCourse")
	public String addCourse(@ModelAttribute("data") CourseBean courseBean, ModelMap model) {
		CourseRequestDto resDto = new CourseRequestDto(courseBean.getName());
		if (courseBean.getName().isBlank()) {
			model.addAttribute("error", "Fill the blank !!");
			model.addAttribute("data", courseBean);
			return "BUD003";
		} else if (courseDao.checkCourseName(courseBean.getName())) {
			model.addAttribute("error", "Course name already exists !!");
			model.addAttribute("data", courseBean);
			return "BUD003";
		} else {
			ArrayList<CourseResponseDto> courseList = courseDao.selectAllCourses();
			if (courseList.size() == 0) {
				resDto.setId("COU001");
			} else {
				int tempId = Integer.parseInt(courseList.get(courseList.size() - 1).getId().substring(3)) + 1;
				String userId = String.format("COU%03d", tempId);
				resDto.setId(userId);
			}
			courseDao.insertCourse(resDto);
			model.addAttribute("message", "Registered Succesfully !!");
			return "BUD003";
		}
	}
	
}
