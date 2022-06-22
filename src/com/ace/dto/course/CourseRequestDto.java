package com.ace.dto.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CourseRequestDto {
	private String id;
	private String name;

	public CourseRequestDto(String name) {
		super();
		this.name = name;
	}
}
