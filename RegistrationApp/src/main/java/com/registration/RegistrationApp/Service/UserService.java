package com.registration.RegistrationApp.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.registration.RegistrationApp.Dto.LoginDto;
import com.registration.RegistrationApp.Dto.SearchByNameSurnamePincodeDto;
import com.registration.RegistrationApp.Entity.Admin;
import com.registration.RegistrationApp.Entity.Users;
import com.registration.RegistrationApp.Repository.AdminRepository;
import com.registration.RegistrationApp.Repository.UserRepository;
import com.registration.RegistrationApp.config.JwtUtil;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtUtil;

	private static Validator validator;
	
//	@Autowired
//	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public List<Users> getAllUsers() {
		return (List<Users>) userRepository.findAll();
	}

	public Users getByUserId(String userId) {
		return userRepository.findByUserId(userId);
	}

	public List<String> saveUpdateUser(@Valid Users user) {
		Users userData = userRepository.findByUserId(user.getUserId());
		/*String pwd=user.getPassword();
		if(pwd!=null) {
			String encryptedPwd=bCryptPasswordEncoder.encode(pwd);
			user.setPassword(encryptedPwd);			
		}*/
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
		List<String> response = new ArrayList<String>();
		Set<ConstraintViolation<Users>> constraintViolations = validator.validate(user);
		if (constraintViolations.size() > 0) {
			for (ConstraintViolation<Users> violation : constraintViolations) {
				response.add(violation.getMessage());
			}
			return response;
		} else {
			if (userData != null) {
				user.setId(userData.getId());
				userData.setName(user.getName());
				userData.setPassword(user.getPassword());
				userData.setId(userData.getId());
				userData.setSurname(user.getSurname());
				userData.setCity(user.getCity());
				userData.setDob(user.getDob());
				userData.setDesignation(user.getDesignation());
				userData.setPinCode(user.getPinCode());
				userData.setContactNumber(user.getContactNumber());
				userData.setJoiningDate(user.getJoiningDate());
				userData.setEmail(user.getEmail());
				userData.setRole(user.getRole());
				userRepository.save(userData);
			} else {
				System.out.println("Inside Save API");
				userRepository.save(user);
				String userId = "NeoSoft_" + user.getId();
				user.setUserId(userId);
				userRepository.save(user);
			}
		}
		response.add("Success");
		return response;
	}

	public List<Users> getByNameAndSurenameAndPincode(String name, String surname, Integer pinCode) {
		return userRepository.findByNameAndSurnameAndPincode(name, surname, pinCode);
	}

	public List<Users> getByDobAndJoiningDate() {
		return userRepository.findByDobAndJoiningDate();
	}

	public String deleteUser(String userId) {
		Users user = userRepository.findByUserId(userId);
		if (user != null) {
			userRepository.deleteUserByUserId(userId);
			return "Success";
		}
		return "Failed";
	}
	
	
	public List<Users> getByNameOrSurenameOrPincode(SearchByNameSurnamePincodeDto searchByNameSurnamePincodeDto) {
		if (searchByNameSurnamePincodeDto.getSurname() == null && searchByNameSurnamePincodeDto.getPinCode() == null) {
			return userRepository.findByName(searchByNameSurnamePincodeDto.getName());
		} else if (searchByNameSurnamePincodeDto.getName() == null
				&& searchByNameSurnamePincodeDto.getPinCode() == null) {
			return userRepository.findBySurname(searchByNameSurnamePincodeDto.getSurname());
		} else if (searchByNameSurnamePincodeDto.getName() == null
				&& searchByNameSurnamePincodeDto.getSurname() == null) {
			return userRepository.findByPinCode(searchByNameSurnamePincodeDto.getPinCode());
		} else if (searchByNameSurnamePincodeDto.getPinCode() == null) {
			return userRepository.findByNameAndSurname(searchByNameSurnamePincodeDto.getName(),
					searchByNameSurnamePincodeDto.getSurname());
		} else if (searchByNameSurnamePincodeDto.getSurname() == null) {
			return userRepository.findByNameAndPinCode(searchByNameSurnamePincodeDto.getName(),
					searchByNameSurnamePincodeDto.getPinCode());
		} else if (searchByNameSurnamePincodeDto.getName() == null) {
			return userRepository.findBySurnameAndPinCode(searchByNameSurnamePincodeDto.getSurname(),
					searchByNameSurnamePincodeDto.getPinCode());
		} else {
			return userRepository.findByNameAndSurnameAndPincode(searchByNameSurnamePincodeDto.getName(),
					searchByNameSurnamePincodeDto.getSurname(), searchByNameSurnamePincodeDto.getPinCode());
		}
	}

	/**********************************JAVA 8*************************************/
	
	public List<Users> getByDobAndJoining() {
		List<Users> userList = (List<Users>) userRepository.findAll();
		Comparator<Users> compareByDobAndJoining = Comparator.comparing(Users::getDob)
				.thenComparing(Users::getJoiningDate);
		List<Users> sortedList = userList.stream().sorted(compareByDobAndJoining).collect(Collectors.toList());
		return sortedList;

	}

	public String deleteUserById(Integer id) {
		Optional<Users> user = userRepository.findById(id);
		if (user.isPresent()) {
			userRepository.deleteById(id);
			return "Success";
		}
		return "Failed";
	}

	public List<Users> getAllUsersData() {
		List<Users> userList = (List<Users>) userRepository.findAll();
		userList.forEach(System.out::println);
		return userList;
	}

	//-----------------------------------------------------------------------------------------------//
	
/*	public LoginDto login(LoginDto loginDto){
		if(loginDto.getUserRole().equals("User")) {
			Users user=userRepository.findByUserIdAndPassword(loginDto.getUserName(), loginDto.getPassword());
			if(user!=null) {
				loginDto.setMessage("Login Successfully");	
			}else {
				loginDto.setMessage("Incorrect UserName/Password");	
			}
		}else if(loginDto.getUserRole().equals("Admin")){
			Admin user=adminRepository.findByUserIdAndPassword(loginDto.getUserName(), loginDto.getPassword());
			if(user!=null) {
				loginDto.setMessage("Login Successfully");
			}else {
				loginDto.setMessage("Incorrect UserName/Password");
			}
		}		
		return loginDto;	
	}*/
	
	public LoginDto login(LoginDto loginDto){
		//if(loginDto.getUserRole().equals("User")) {
			System.out.println("Inside User");
			Users user=userRepository.findByUserIdAndPasswordAndRole(loginDto.getUserName(), loginDto.getPassword(),loginDto.getUserRole());
			if(user!=null) {
				loginDto.setMessage("Login Successfully");
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUserName(), loginDto.getPassword()));
				String response=jwtUtil.generateToken(loginDto.getUserName());
				loginDto.setToken(response);
			}else {
				loginDto.setMessage("Incorrect UserName/Password");	
			}
	/*	}else if(loginDto.getUserRole().equals("Admin")){
			System.out.println("Inside Admin");
			Admin user=adminRepository.findByUserIdAndPassword(loginDto.getUserName(), loginDto.getPassword());
			if(user!=null) {
				System.out.println("Inside Admin check");
				loginDto.setMessage("Login Successfully");
				System.out.println("Msg Set");
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUserName(), loginDto.getPassword()));
				String response=jwtUtil.generateToken(loginDto.getUserName());
				System.out.println("Token generate");
				loginDto.setToken(response);
			}else {
				loginDto.setMessage("Incorrect UserName/Password");
			}
		}		*/
		return loginDto;	
	}
}
