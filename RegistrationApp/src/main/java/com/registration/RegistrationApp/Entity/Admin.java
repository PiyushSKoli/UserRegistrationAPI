package com.registration.RegistrationApp.Entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Admin")
public class Admin {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="UserId")
	private String userId;
	
	@NotEmpty(message = "password may not be null")
	@Column(name="password",nullable = false)
	private String password;
	
	@NotEmpty(message = "Name may not be null")
	@Column(name="Name",nullable = false)
	private String name;
	
	@NotEmpty(message = "Surname may not be null")
	@Column(name="Surname",nullable = false)
	private String surname ;

}
