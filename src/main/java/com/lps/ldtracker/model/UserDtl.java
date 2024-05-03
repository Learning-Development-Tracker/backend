package com.lps.ldtracker.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "user_dtl")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDtl implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
	private String userId;
	@Column(name = "is_active")
	private Integer isActive;
	@Column(name = "user_name")
	private String userName;
	@Column(name = "user_pass")
	private String userPass;
	@Column(name = "role_id")
	private String roleId;
	@Column(name = "status_id")
	private String statusId;
	@Column(name = "member_id")
	private String memberId;
	@Column(name = "is_deleted")
	private Integer isDeleted;
	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "created_date")
	private LocalDateTime createdDate;
	@Column(name = "updated_by")
	private String updatedBy;
	@Column(name = "updated_date")
	private LocalDateTime updatedDate;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		return userPass;
	}

	@Override
	public String getUsername() {
		return userName;
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
	public boolean isEnabled() {
		return true;
	}

}
