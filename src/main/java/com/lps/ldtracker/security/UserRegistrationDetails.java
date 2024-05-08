package com.lps.ldtracker.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.lps.ldtracker.model.UserDtl;

import lombok.Data;

@Data
public class UserRegistrationDetails implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 
	private String username;
	private String password;
	private boolean isEnabled;
	private RoleSecurity authorities;

	public UserRegistrationDetails(UserDtl user) {
		super(); 
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.isEnabled = user.isEnabled();
		this.authorities = user.getRole();
	}
	
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities.getAuthorities();
	}

	@Override
	public String getPassword() { 
		return password;
	}

	@Override
	public String getUsername() { 
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() { 
		return isEnabled;
	}

}
