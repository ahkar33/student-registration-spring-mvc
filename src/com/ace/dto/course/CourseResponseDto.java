package com.ace.dto.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CourseResponseDto {
	private String id;
	private String name;

	public CourseResponseDto(String name) {
		super();
		this.name = name;
	}

}
