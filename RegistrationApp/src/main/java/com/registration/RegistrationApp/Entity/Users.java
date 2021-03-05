package com.registration.RegistrationApp.Entity;

import java.io.Serializable;
import java.sql.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Users")
public class Users implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="UserId")
	private String userId;
	
	@NotEmpty(message = "Name may not be null")
	@Column(name="Name",nullable = false)
	private String name;
	
	@NotEmpty(message = "Surname may not be null")
	@Column(name="Surname",nullable = false)
	private String surname ;

	@NotEmpty(message = "Email may not be null")
	@Email(message = "Email is Invalid")
	@Column(name="Email")
	private String email ;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "DOB may not be null")
	@Column(name="DOB")
	private Date dob;
	
	@NotEmpty(message = "City may not be null")
	@Column(name="City")
	private String city;
	
	@NotEmpty(message = "Contact_Number may not be null")
	@Length(message = "Invalid Contact Numebr", min = 10,max=12)
	@Column(name="Contact_Number")
	private String contactNumber;
	
	@NotNull(message = "Pincode may not be null")
	@Column(name="Pincode")
	private Integer pinCode;
	
	@NotEmpty(message = "Designation may not be null")
	@Column(name="Designation")
	private String designation;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "Joining_Date may not be null")
	@Column(name="Joining_Date")
	private Date joiningDate;
}
