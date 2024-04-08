package com.lps.ldtracker.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.lps.ldtracker.model.UserT;
import com.lps.ldtracker.security.RoleSecurity;

import lombok.Data;

@Data
public class UserRegistrationDetails implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 
	private String username;
	private String email;
	private String password;
	private boolean isEnabled;
	private RoleSecurity authorities;

	public UserRegistrationDetails(UserT user) {
		super(); 
		this.username = user.getUsername();
		this.email = user.getEmail();
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
