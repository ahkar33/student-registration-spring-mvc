package com.ace.dto.student;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StudentRequestDto {
	private String id;
	private String name;
	private String dob;
	private String gender;
	private String phone;
	private String education;
	private List<String> attendCourses;

	public StudentRequestDto(String name, String dob, String gender, String phone, String education,
			List<String> attendCourses) {
		super();
		this.name = name;
		this.dob = dob;
		this.gender = gender;
		this.phone = phone;
		this.education = education;
		this.attendCourses = attendCourses;
	}

	public StudentRequestDto(String name, String dob, String gender, String phone, String education) {
		super();
		this.name = name;
		this.dob = dob;
		this.gender = gender;
		this.phone = phone;
		this.education = education;
	}

	public StudentRequestDto(String id, String name, String dob, String gender, String phone, String education) {
		super();
		this.id = id;
		this.name = name;
		this.dob = dob;
		this.gender = gender;
		this.phone = phone;
		this.education = education;
	}
}
