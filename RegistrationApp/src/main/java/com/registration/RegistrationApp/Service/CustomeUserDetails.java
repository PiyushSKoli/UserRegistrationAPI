package com.registration.RegistrationApp.Service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.registration.RegistrationApp.Entity.Admin;
import com.registration.RegistrationApp.Entity.Users;

import lombok.Data;

@Data
public class CustomeUserDetails implements UserDetails{

	private static final long serialVersionUID = 1L;
	
	private Users user;
	
	private Admin admin;
	
	
	public Users getUser() {
		return user;
	}
	
	public Admin getUsers() {
		return admin;
	}

	public void setUser(Users user) {
		this.user = user;
	}
	
	public void setUser(Admin admin) {
		this.admin = admin;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}
	
	public String getPasswords() {
		// TODO Auto-generated method stub
		return admin.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUserId();
	}
	
	public String getUsernames() {
		// TODO Auto-generated method stub
		return admin.getUserId();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
