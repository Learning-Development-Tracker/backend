package com.lps.ldtracker.model;

import com.lps.ldtracker.security.RoleSecurity;

public record RegistrationRequest(
			 Integer id, String email, String username, String password, RoleSecurity role,
			 String phoneNo, String status, String address, String position, String positionCode,
			 String firstName, String lastName
			) {

}
