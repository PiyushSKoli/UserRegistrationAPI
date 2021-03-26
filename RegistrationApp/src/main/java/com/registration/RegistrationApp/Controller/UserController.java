package com.registration.RegistrationApp.Controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.registration.RegistrationApp.Dto.LoginDto;
import com.registration.RegistrationApp.Dto.ResultModel;
import com.registration.RegistrationApp.Dto.SearchByNameSurnamePincodeDto;
import com.registration.RegistrationApp.Entity.Users;
import com.registration.RegistrationApp.Service.UserService;
import com.registration.RegistrationApp.config.JwtUtil;

@RestController
@RequestMapping(value = "/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public final static Logger logger = Logger.getLogger(UserController.class);

	@GetMapping(value ="/")
	public String homepage() {
		return "Welcome to Registration Form";
	}
	
/*	@PostMapping("/authenticate")
	public String generateToken(@RequestBody LoginDto loginDto) throws Exception {
		try {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUserName(), loginDto.getPassword()));
		}catch(Exception e) {
			throw new Exception("Invalid Username/Password");
		}
		return jwtUtil.generateToken(loginDto.getUserName());
	}*/
	
	@PostMapping("/authenticate")
	public ResponseEntity<ResultModel> generateToken(@RequestBody LoginDto loginDto) {
		ResultModel resultModel = new ResultModel();
		logger.info("Authenticating Users.......");
		try {
			//authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUserName(), loginDto.getPassword()));
			//String response=jwtUtil.generateToken(loginDto.getUserName());
			LoginDto response = userService.login(loginDto);
			if (response.getMessage().equals("Login Successfully")) {
				resultModel.setData(response);
				resultModel.setMessage("Success");
				logger.info("Authentication Successfully.......");
			} else {
				resultModel.setMessage("Failed");
				resultModel.setMessageList(null);
				logger.info("" + response);
			}
		} catch (Exception e) {
			logger.info("Error Occure" + e);
			return new ResponseEntity<ResultModel>(resultModel, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		return new ResponseEntity<ResultModel>(resultModel, HttpStatus.CREATED);
	}

	@GetMapping("/getAllUsers")
	public ResponseEntity<ResultModel> getAllUsers(@RequestHeader ("Authorization") String Authorization) {
		ResultModel resultModel = new ResultModel();
		logger.info("Getting All Users From Users");
		try {
			List<Users> response = userService.getAllUsers();
			if (!response.isEmpty()) {
				resultModel.setData(response);
				resultModel.setMessage("Success");
				logger.info("Getting All Users Successfully....!");
				return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
			} else {
				resultModel.setMessage("No Records Found In Users....!");
				logger.info("No Records Found In Users....!");
				return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
			}
		} catch (Exception e) {
			logger.info("Getting Users Failed.....!");
			return new ResponseEntity<ResultModel>(resultModel, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	@GetMapping("/getByUserId/{userId}")
	public ResponseEntity<ResultModel> getByUserId(@PathVariable("userId") String userId,@RequestHeader ("Authorization") String Authorization) {
		ResultModel resultModel = new ResultModel();
		logger.info("Getting User By UserId From Users");
		try {
			Users response = userService.getByUserId(userId);
			if (response != null) {
				resultModel.setData(response);
				resultModel.setMessage("Success");
				logger.info("Getting User By UserId Successfully....!");
				return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
			} else {
				resultModel.setMessage("No Records Found for Userid :- " + userId + "....!");
				logger.info("No Records Found In Users for userId :- " + userId + "....!");
				return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
			}
		} catch (Exception e) {
			logger.info("Getting User by UserId Failed.....!");
			return new ResponseEntity<ResultModel>(resultModel, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	@PostMapping("/saveUpdateUser")
	public ResponseEntity<ResultModel> cretaeUpdateUser(@RequestBody Users user,@RequestHeader ("Authorization") String Authorization) {
		ResultModel resultModel = new ResultModel();
		logger.info("Creating data in Users.......");
		try {
			List<String> response = userService.saveUpdateUser(user);
			if (response.contains("Success")) {
				resultModel.setData(response);
				resultModel.setMessage("Success");
				logger.info("Creating/Update User Successfully.......");
			} else {
				resultModel.setMessage("Failed");
				resultModel.setMessageList(response);
				logger.info("" + response);
			}
		} catch (Exception e) {
			logger.info("Error Occure" + e);
			return new ResponseEntity<ResultModel>(resultModel, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		return new ResponseEntity<ResultModel>(resultModel, HttpStatus.CREATED);
	}

	@GetMapping("/getByNameAndSurenameAndPincode")
	public ResponseEntity<ResultModel> getByNameAndSurenameAndPincode(@RequestParam("name") String name,
			@RequestParam("surname") String surname, @RequestParam("pinCode") Integer pinCode,@RequestHeader ("Authorization") String Authorization) {
		ResultModel resultModel = new ResultModel();
		logger.info("Getting User By Name,Surname,Pincode From Users");
		try {
			List<Users> response = userService.getByNameAndSurenameAndPincode(name, surname, pinCode);
			if (!response.isEmpty()) {
				resultModel.setData(response);
				resultModel.setMessage("Success");
				logger.info("Getting User By Name,Surname,Pincode Successfully....!");
				return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
			} else {
				resultModel.setData(null);
				resultModel.setMessage("No Records Found for search by Name,Surname,Pincode....!");
				logger.info("No Records Found In Users for search by Name,Surname,Pincode ....!");
				return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
			}
		} catch (Exception e) {
			logger.info("Getting User by Name,Surname,Pincode Failed.....!");
			return new ResponseEntity<ResultModel>(resultModel, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	@GetMapping("/getByDobAndJoiningDate")
	public ResponseEntity<ResultModel> getByDobAndJoiningDate(@RequestHeader ("Authorization") String Authorization) {
		ResultModel resultModel = new ResultModel();
		logger.info("Getting All Users From Users Order By Dob and Joining Date");
		try {
			List<Users> response = userService.getByDobAndJoiningDate();
			if (!response.isEmpty()) {
				resultModel.setData(response);
				resultModel.setMessage("Success");
				logger.info("Getting All Users Order By Dob and Joining Date Successfully....!");
				return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
			} else {
				resultModel.setMessage("No Records Found In Users Order By Dob and Joining Date....!");
				logger.info("No Records Found In Users Order By Dob and Joining Date....!");
				return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
			}
		} catch (Exception e) {
			logger.info("Getting Users Failed.....!");
			return new ResponseEntity<ResultModel>(resultModel, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	@DeleteMapping("/deleteUser")
	public ResponseEntity<ResultModel> deleteUser(@RequestParam("userId") String userId,@RequestHeader ("Authorization") String Authorization) {
		ResultModel resultModel = new ResultModel();
		logger.info("Deleting  User in Users.......");
		try {
			String response = userService.deleteUser(userId);
			if (response.equals("Success")) {
				resultModel.setMessage(response);
				logger.info("Deleting User Successfully.......");
				return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
			} else {
				resultModel.setMessage(response);
				logger.info("UserId Not Present In Users.......");
				return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
			}
		} catch (Exception e) {
			resultModel.setMessage("Failed");
			logger.info("Error Occure" + e);
			return new ResponseEntity<ResultModel>(resultModel, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	@PostMapping("/getByNameOrSurenameOrPincode")
	public ResponseEntity<ResultModel> getByNameOrSurenameOrPincode(
			@RequestBody SearchByNameSurnamePincodeDto searchByNameSurnamePincodeDto,@RequestHeader ("Authorization") String Authorization) {
		ResultModel resultModel = new ResultModel();
		logger.info("Getting User By Name,Surname,Pincode From Users");
		try {
			List<Users> response = userService.getByNameOrSurenameOrPincode(searchByNameSurnamePincodeDto);
			if (!response.isEmpty()) {
				resultModel.setData(response);
				resultModel.setMessage("Success");
				logger.info("Getting User By Name,Surname,Pincode Successfully....!");
				return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
			} else {
				resultModel.setData(null);
				resultModel.setMessage("No Records Found for search by Name,Surname,Pincode....!");
				logger.info("No Records Found In Users for search by Name,Surname,Pincode ....!");
				return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
			}
		} catch (Exception e) {
			logger.info("Getting User by Name,Surname,Pincode Failed.....!");
			return new ResponseEntity<ResultModel>(resultModel, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	/************************************************************
	                      Java 8
	 ************************************************************/

	@GetMapping("/getByDobAndJoining")
	public ResponseEntity<ResultModel> getByDobAndJoining(@RequestHeader ("Authorization") String Authorization) {
		ResultModel resultModel = new ResultModel();
		logger.info("Getting All Users From Users Order By Dob and Joining Date");
		try {
			List<Users> response = userService.getByDobAndJoining();
			if (!response.isEmpty()) {
				resultModel.setData(response);
				resultModel.setMessage("Success");
				logger.info("Getting All Users Order By Dob and Joining Date Successfully....!");
				return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
			} else {
				resultModel.setMessage("No Records Found In Users Order By Dob and Joining Date....!");
				logger.info("No Records Found In Users Order By Dob and Joining Date....!");
				return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
			}
		} catch (Exception e) {
			logger.info("Getting Users Failed.....!");
			return new ResponseEntity<ResultModel>(resultModel, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	@DeleteMapping("/deleteUserById/{id}")
	public ResponseEntity<ResultModel> deleteUserById(@PathVariable("id") Integer id,@RequestHeader ("Authorization") String Authorization) {
		ResultModel resultModel = new ResultModel();
		logger.info("Deleting  User in Users.......");
		try {
			String response = userService.deleteUserById(id);
			if (response.equals("Success")) {
				resultModel.setMessage(response);
				logger.info("Deleting User Successfully.......");
				return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
			} else {
				resultModel.setMessage(response);
				logger.info("Id Not Present In Users.......");
				return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
			}
		} catch (Exception e) {
			resultModel.setMessage("Failed");
			logger.info("Error Occure" + e);
			return new ResponseEntity<ResultModel>(resultModel, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	@GetMapping("/getAllUsersData")
	public ResponseEntity<ResultModel> getAllUsersData(@RequestHeader ("Authorization") String Authorization) {
		ResultModel resultModel = new ResultModel();
		logger.info("Getting All Users From Users");
		try {
			List<Users> response = userService.getAllUsersData();
			if (!response.isEmpty()) {
				resultModel.setData(response);
				resultModel.setMessage("Success");
				logger.info("Getting All Users Successfully....!");
				return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
			} else {
				resultModel.setMessage("No Records Found In Users....!");
				logger.info("No Records Found In Users....!");
				return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
			}
		} catch (Exception e) {
			logger.info("Getting Users Failed.....!");
			return new ResponseEntity<ResultModel>(resultModel, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	@PostMapping("/saveUpdateUser1")
	public ResponseEntity<ResultModel> cretaeUpdateUser1(@RequestBody Users user,@RequestHeader ("Authorization") String Authorization) {
		ResultModel resultModel = new ResultModel();
		logger.info("Creating data in Users.......");
		try {
			List<String> response = userService.saveUpdateUser(user);
			if (response.contains("Success")) {
				resultModel.setData(response);
				resultModel.setMessage("Success");
				logger.info("Creating/Update User Successfully.......");
			} else {
				resultModel.setMessage("Failed");
				resultModel.setMessageList(response);
				logger.info("" + response);
			}
		} catch (Exception e) {
			logger.info("Error Occure" + e);
			return new ResponseEntity<ResultModel>(resultModel, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		return new ResponseEntity<ResultModel>(resultModel, HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<ResultModel> login(@RequestBody LoginDto loginDto) {
		ResultModel resultModel = new ResultModel();
		logger.info("Authenticating Users");
		try {
			LoginDto response = userService.login(loginDto);
			if (response.getMessage().equals("Login Successfully")) {
				resultModel.setData(response);
				resultModel.setMessage("Success");
				logger.info("Authentication Successfully....!");
				return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
			} else {
				resultModel.setMessage("Authentication Failed");
				resultModel.setData(response);
				logger.info("Authentication Failed....!");
				return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
			}
		} catch (Exception e) {
			logger.info("Authentication Failed.....!");
			return new ResponseEntity<ResultModel>(resultModel, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
}
