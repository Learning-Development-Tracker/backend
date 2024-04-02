package com.lps.hris.model;

import java.util.Collection;

import com.lps.hris.security.RoleSecurity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity 
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserT implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usert")
	@SequenceGenerator(sequenceName = "usert_seq", allocationSize = 1, name = "usert")
	private Integer id;
	@NaturalId(mutable=true)
	private String email;
	private String username;
	private String password;
	private boolean isEnabled = false;
	private String phoneNo;
	private String status;
	private String address;
	private String position;
	private String positionCode;
	private String firstName;
	private String lastName;
	private String otp;
	
	@Enumerated(EnumType.STRING)
	private RoleSecurity role;
	
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return role.getAuthorities();
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
}
