package com.registration.RegistrationApp.Dto;

import lombok.Data;

@Data
public class SearchByNameSurnamePincodeDto {

	private String name;
	
	private String surname;
	
	private Integer pinCode;
}
