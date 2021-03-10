package com.registration.RegistrationApp.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.registration.RegistrationApp.Entity.Users;
import com.registration.RegistrationApp.Repository.UserRepository;

@Service
public class CustomeUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user=userRepository.findByUserId(username);
		CustomeUserDetails userDetails=null;
		if(user!=null) {
			userDetails=new CustomeUserDetails();
			userDetails.setUser(user);
		}else {
			throw new UsernameNotFoundException("User Not Exists With UserId "+username);
		}
		return userDetails;
	}

}
