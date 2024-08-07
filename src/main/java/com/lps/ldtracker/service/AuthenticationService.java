package com.lps.ldtracker.service;

import com.lps.ldtracker.constants.LdTrackerConstants;
import com.lps.ldtracker.exception.AuthenticationFailedException;
import com.lps.ldtracker.model.AuthenticationResponse;
import com.lps.ldtracker.model.LoginRequest;
import com.lps.ldtracker.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	private final UserRepository userRepository;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
	
	public AuthenticationResponse login(LoginRequest loginRequest) throws AuthenticationFailedException {
		String username = loginRequest.getUsername();
	    String password = loginRequest.getPassword();
	    
	    if (username.length() < LdTrackerConstants.MIN_USERNAME_LENGTH || username.length() > LdTrackerConstants.MAX_USERNAME_LENGTH) {
	        throw new AuthenticationFailedException("Username length must be between " + LdTrackerConstants.MIN_USERNAME_LENGTH + " and " + LdTrackerConstants.MAX_USERNAME_LENGTH + " characters");
	    }

	    if (password.length() < LdTrackerConstants.MIN_PASSWORD_LENGTH || password.length() > LdTrackerConstants.MAX_PASSWORD_LENGTH) {
	        throw new AuthenticationFailedException("Password length must be between " + LdTrackerConstants.MIN_PASSWORD_LENGTH + " and " + LdTrackerConstants.MAX_PASSWORD_LENGTH + " characters");
	    }
		
		var user = userRepository.findByUsername(username)
			.orElseThrow(() -> {
		        throw new AuthenticationFailedException("User not found");
		    });
		
		if (!user.isEnabled()) {
            throw new AuthenticationFailedException("Your account is inactive");
		}
		
		try {
			authenticationManager.authenticate(
		        new UsernamePasswordAuthenticationToken(
		        	username,
		        	password
		        )
		    );
		} catch (AuthenticationException authenticationException) {
			throw new AuthenticationFailedException("Incorrect username or password");
		}
		
		var jwtToken = jwtService.generateToken(user);
		
		return AuthenticationResponse
			.builder()
			.token(jwtToken)
			.build();
	}
}