package com.lps.ldtracker.permission;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
	
	EMPLOYEE_READ("employee:read"),
	EMPLOYEE_CREATE("employee:create"),
	EMPLOYEE_UPDATE("employee:update"),
	EMPLOYEE_DELETE("employee:delete"),
	HR_READ("hr:read"),
	HR_CREATE("hr:create"),
	HR_UPDATE("hr:update"),
	HR_DELETE("hr:delete"),
	MANAGER_READ("manager:read"),
	MANAGER_CREATE("manager:create"),
	MANAGER_UPDATE("manager:update"),
	MANAGER_DELETE("manager:delete");
	
	@Getter
	private final String permission;
}