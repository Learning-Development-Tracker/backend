package com.lps.ldtracker.entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.lps.ldtracker.model.UserT;
import com.lps.ldtracker.security.RoleSecurity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor 
@Data
public class Resource implements UserDetails{

//	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "resource")
	@SequenceGenerator(sequenceName = "resource_seq", allocationSize = 1, name = "resource")
	private Integer id;
	private String lastname;
	private String firstname;
	private String middlename;
	private String suffix;
	private String gender;
	
	@Column(name = "email_address")
	private String emailAddress;
	private String password;
	@Column(name = "career_step")
	private String careerStep;
	@Column(name = "emp_id")
	private String empId;
	private String region;
	private String team;
	private String status;
	private String skills;
	private Boolean isEnabled = false;
	
	@Enumerated(EnumType.STRING)
	private RoleSecurity role;
	
	@Override
	public String getPassword() {
	    return password;
	}
	
	@Override 
	public String getUsername() {
		return emailAddress;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return role.getAuthorities();
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	
}
