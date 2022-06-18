package com.ace.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserBean {
	private String id;
	private String email;
	private String name;
	private String password;
	private String confirmPassword;
	private String userRole;

	public UserBean(String email, String name, String password, String confirmPassword, String userRole) {
		super();
		this.email = email;
		this.name = name;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.userRole = userRole;
	}

}