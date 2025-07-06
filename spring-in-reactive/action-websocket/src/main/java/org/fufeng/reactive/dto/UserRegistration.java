package org.fufeng.reactive.dto;

import lombok.Data;

@Data
public class UserRegistration {
	private String firstName;
	private String lastName;
	private String email;
	private String password;
}
