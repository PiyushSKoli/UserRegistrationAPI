package com.registration.RegistrationApp.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
	private String userRole;
	private String password;
	private String userName;
	private String message;
	private String token;
}
