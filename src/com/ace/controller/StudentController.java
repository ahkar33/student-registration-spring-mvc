package com.ace.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
		StudentRequestDto reqDto = new StudentRequestDto(studentBean.getName(), studentBean.getDob(),
				studentBean.getGender(), studentBean.getPhone(), studentBean.getEducation());
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

	@GetMapping("/seeMore")
	public String seeMore(@RequestParam("id") String id, ModelMap model) {
		StudentResponseDto studentDto = studentDao.selectStudentById(id);
		StudentBean student = new StudentBean();
		student.setId(studentDto.getId());
		student.setName(studentDto.getName());
		student.setDob(studentDto.getDob());
		student.setGender(studentDto.getGender());
		student.setPhone(studentDto.getPhone());
		student.setEducation(studentDto.getEducation());
		ArrayList<CourseResponseDto> attendCourses = courseDao.selectCoursesByStudentId(id);
		for (CourseResponseDto course : attendCourses) {
			student.addAttendCourse(course);
		}
		ArrayList<CourseResponseDto> courses = courseDao.selectAllCourses();
		model.addAttribute("courseList", courses);
		model.addAttribute("data", student);
		return "STU002";
	}

	@PostMapping("/updateStudent")
	public String updateStudent(@ModelAttribute("") StudentBean studentBean, ModelMap model) {
		ArrayList<CourseResponseDto> courseList = courseDao.selectAllCourses();
		model.addAttribute("courseList", courseList);
		if (studentBean.getAttendCourses().size() == 0) {
			model.addAttribute("error", "Fill the blank !!");
			model.addAttribute("data", studentBean);
			return "STU002";
		}
		if (studentBean.getName().isBlank() || studentBean.getDob().isBlank() || studentBean.getGender().isBlank()
				|| studentBean.getPhone().length() < 4 || studentBean.getEducation().isBlank()) {
			model.addAttribute("error", "Fill the blank !!");
			model.addAttribute("data", studentBean);
			return "STU002";
		}
		StudentRequestDto reqDto = new StudentRequestDto(studentBean.getId(), studentBean.getName(),
				studentBean.getDob(), studentBean.getGender(), studentBean.getPhone(), studentBean.getEducation());
		studentDao.updateStudent(reqDto);
		studentDao.deleteAttendCoursesByStudentId(studentBean.getId());
		String[] attendCourses = new String[studentBean.getAttendCourses().size()];
		attendCourses = studentBean.getAttendCourses().toArray(attendCourses);
		for (int i = 0; i < attendCourses.length; i++) {
			studentDao.insertStudentCourse(attendCourses[i], studentBean.getId());
		}
		return "redirect:/studentManagement";
	}

	@GetMapping("/deleteStudent/{id}")
	public String deleteStudent(@PathVariable("id") String id) {
		// you have to delete from transition table first and then from student table
		studentDao.deleteAttendCoursesByStudentId(id);
		studentDao.deleteStudentById(id);
		return "redirect:/studentManagement";
	}

	@GetMapping("/searchStudent")
	public String searchStudent(@RequestParam("id") String searchId, @RequestParam("name") String searchName,
			@RequestParam("course") String searchCourse, ModelMap model) {
		// ")#<>(}" <- this is just random bullshit to avoid sql wildcard, not REGEX
		String id = searchId.isBlank() ? ")#<>(}" : searchId;
		String name = searchName.isBlank() ? ")#<>(}" : searchName;
		String course = searchCourse.isBlank() ? ")#<>(}" : searchCourse;

		ArrayList<StudentResponseDto> studentList = studentDao.selectStudentListByIdOrNameOrCourse(id, name, course);
		ArrayList<ArrayList<CourseResponseDto>> coursesLists = new ArrayList<>();
		for (StudentResponseDto student : studentList) {
			ArrayList<CourseResponseDto> courseList = courseDao.selectCoursesByStudentId(student.getId());
			coursesLists.add(courseList);
		}
		if (studentList.size() == 0) {
			studentList = studentDao.selectAllStudents();
			ArrayList<ArrayList<CourseResponseDto>> coursesList = new ArrayList<>();
			for (StudentResponseDto student : studentList) {
				ArrayList<CourseResponseDto> courseList = courseDao.selectCoursesByStudentId(student.getId());
				coursesList.add(courseList);
			}
			model.addAttribute("studentList", studentList);
			model.addAttribute("coursesLists", coursesList);
			return "STU003";
		}
		model.addAttribute("studentList", studentList);
		model.addAttribute("coursesLists", coursesLists);
		return "STU003";
	}
}
