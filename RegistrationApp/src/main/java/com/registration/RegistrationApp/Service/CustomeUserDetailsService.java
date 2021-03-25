package com.registration.RegistrationApp.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.registration.RegistrationApp.Entity.Admin;
import com.registration.RegistrationApp.Entity.Users;
import com.registration.RegistrationApp.Repository.AdminRepository;
import com.registration.RegistrationApp.Repository.UserRepository;

@Service
public class CustomeUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AdminRepository adminRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user=userRepository.findByUserId(username);
		//Admin admin=adminRepository.findByUserId(username);
		
		CustomeUserDetails userDetails=null;
		if(user!=null) {
			System.out.println("Inside User find");
			userDetails=new CustomeUserDetails();
			userDetails.setUser(user);
		}
//		else if(admin!=null) {
//			System.out.println("Inside Admin find");
//			userDetails=new CustomeUserDetails();
//			userDetails.setAdmin(admin);
//		}	
		else {
			throw new UsernameNotFoundException("User Not Exists With UserId "+username);
		}
		return userDetails;
	}

}
