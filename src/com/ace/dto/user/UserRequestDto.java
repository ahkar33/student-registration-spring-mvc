package com.ace.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRequestDto {
	private String id;
	private String email;
	private String name;
	private String password;
	private String userRole;
}
