package com.ace.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.ace.dao.CourseDao;
import com.ace.dao.StudentDao;
import com.ace.dto.course.CourseResponseDto;
import com.ace.dto.student.StudentRequestDto;
import com.ace.dto.student.StudentResponseDto;
import com.ace.model.StudentBean;

@Controller
public class StudentController {
	@Autowired
	private StudentDao studentDao;

	@Autowired
	private CourseDao courseDao;

	@GetMapping("/studentManagement")
	public String studentManagement(ModelMap model) {
		ArrayList<StudentResponseDto> studentList = studentDao.selectAllStudents();
		ArrayList<ArrayList<CourseResponseDto>> coursesLists = new ArrayList<>();
		for (StudentResponseDto student : studentList) {
			ArrayList<CourseResponseDto> courseList = courseDao.selectCoursesByStudentId(student.getId());
			coursesLists.add(courseList);
		}
		model.addAttribute("studentList", studentList);
		model.addAttribute("coursesLists", coursesLists);
		return "STU003";
	}

	@GetMapping("/addStudent")
	public String setupAddStudent(ModelMap model) {
		ArrayList<CourseResponseDto> courseList = courseDao.selectAllCourses();
		model.addAttribute("courseList", courseList);
		model.addAttribute("data", new StudentBean());
		return "STU001";
	}

	@PostMapping("/addStudent")
	public String addStudent(@ModelAttribute("data") StudentBean studentBean, ModelMap model) {
		ArrayList<CourseResponseDto> courseList = courseDao.selectAllCourses();
		model.addAttribute("courseList", courseList);
		if (studentBean.getAttendCourses().size() == 0) {
			model.addAttribute("error", "Fill the blank !!");
			model.addAttribute("data", studentBean);
			return "STU001";
		}
		if (studentBean.getName().isBlank() || studentBean.getDob().isBlank() || studentBean.getGender().isBlank()
				|| studentBean.getPhone().length() < 4 || studentBean.getEducation().isBlank()) {
			model.addAttribute("error", "Fill the blank !!");
			model.addAttribute("data", studentBean);
			return "STU001";
		}
		ArrayList<StudentResponseDto> studentList = studentDao.selectAllStudents();
		if (studentList == null) {
			studentList = new ArrayList<>();
		}
		if (studentList.size() == 0) {
			studentBean.setId("STU001");
		} else {
			int tempId = Integer.parseInt(studentList.get(studentList.size() - 1).getId().substring(3)) + 1;
			String userId = String.format("STU%03d", tempId);
			studentBean.setId(userId);
		}
		StudentRequestDto reqDto = new StudentRequestDto(studentBean.getName(), studentBean.getDob(), studentBean.getGender(), studentBean.getPhone(), studentBean.getEducation());
		reqDto.setId(studentBean.getId());
		studentDao.insertStudent(reqDto);
		String[] attendCourses = new String[studentBean.getAttendCourses().size()];
		attendCourses = studentBean.getAttendCourses().toArray(attendCourses);
		for (int i = 0; i < attendCourses.length; i++) {
			studentDao.insertStudentCourse(attendCourses[i], studentBean.getId());
		}
		model.addAttribute("message", "Registered Succesfully !!");
		// clear the bean
		model.addAttribute("data", new StudentBean());
		return "STU001";
	}
}
