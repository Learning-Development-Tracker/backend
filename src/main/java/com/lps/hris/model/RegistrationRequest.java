package com.lps.hris.model;

import com.lps.hris.security.RoleSecurity;

public record RegistrationRequest(
			 Integer id, String email, String username, String password, RoleSecurity role,
			 String phoneNo, String status, String address, String position, String positionCode,
			 String firstName, String lastName
			) {

}
