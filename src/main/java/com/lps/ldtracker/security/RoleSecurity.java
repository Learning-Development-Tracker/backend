package com.lps.ldtracker.security;

import static com.lps.ldtracker.permission.Permission.EMPLOYEE_CREATE;
import static com.lps.ldtracker.permission.Permission.EMPLOYEE_DELETE;
import static com.lps.ldtracker.permission.Permission.EMPLOYEE_READ;
import static com.lps.ldtracker.permission.Permission.EMPLOYEE_UPDATE;
import static com.lps.ldtracker.permission.Permission.HR_CREATE;
import static com.lps.ldtracker.permission.Permission.HR_DELETE;
import static com.lps.ldtracker.permission.Permission.HR_READ;
import static com.lps.ldtracker.permission.Permission.HR_UPDATE;
import static com.lps.ldtracker.permission.Permission.MANAGER_CREATE;
import static com.lps.ldtracker.permission.Permission.MANAGER_DELETE;
import static com.lps.ldtracker.permission.Permission.MANAGER_READ;
import static com.lps.ldtracker.permission.Permission.MANAGER_UPDATE;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.lps.ldtracker.permission.Permission;

@RequiredArgsConstructor
public enum RoleSecurity {
	
	USER(Collections.emptySet()),
	EMPLOYEE(
		Set.of(
			EMPLOYEE_READ,
			EMPLOYEE_CREATE,
			EMPLOYEE_UPDATE,
			EMPLOYEE_DELETE
		)
	),
	HR(
		Set.of(
			HR_READ,
			HR_CREATE,
			HR_UPDATE,
			HR_DELETE,
			MANAGER_READ,
			MANAGER_CREATE,
			MANAGER_UPDATE,
			MANAGER_DELETE
		)
	),
	MANAGER(
		Set.of(
			MANAGER_READ,
			MANAGER_CREATE,
			MANAGER_UPDATE,
			MANAGER_DELETE
		)
	);
	
	@Getter
	private final Set<Permission> permissions;
	
	public List<SimpleGrantedAuthority> getAuthorities() {
		var authorities = getPermissions()
			.stream()
			.map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
			.collect(Collectors.toList());
		authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
		
		return authorities;
	}
}