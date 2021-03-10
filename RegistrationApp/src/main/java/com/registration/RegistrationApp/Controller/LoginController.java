package com.registration.RegistrationApp.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/rest/auth")
public class LoginController {

	@GetMapping("/process")
	public String process() {
		return " Spring Security Called";
	}
}
