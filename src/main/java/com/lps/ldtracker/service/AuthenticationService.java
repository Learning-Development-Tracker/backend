package com.lps.ldtracker.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lps.ldtracker.constants.LdTrackerConstants;
import com.lps.ldtracker.exception.AuthenticationFailedException;
import com.lps.ldtracker.model.AuthenticationResponse;
import com.lps.ldtracker.model.LoginRequest;
import com.lps.ldtracker.repository.UserDtlRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	private final UserDtlRepository userDtlRepository;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
	
	public AuthenticationResponse login(LoginRequest loginRequest) throws AuthenticationFailedException, JsonProcessingException {
		String username = loginRequest.getUsername();
	    String password = loginRequest.getPassword();
	    
	    if (username.length() < LdTrackerConstants.MIN_USERNAME_LENGTH || username.length() > LdTrackerConstants.MAX_USERNAME_LENGTH) {
	        throw new AuthenticationFailedException("Username length must be between " + LdTrackerConstants.MIN_USERNAME_LENGTH + " and " + LdTrackerConstants.MAX_USERNAME_LENGTH + " characters");
	    }

	    if (password.length() < LdTrackerConstants.MIN_PASSWORD_LENGTH || password.length() > LdTrackerConstants.MAX_PASSWORD_LENGTH) {
	        throw new AuthenticationFailedException("Password length must be between " + LdTrackerConstants.MIN_PASSWORD_LENGTH + " and " + LdTrackerConstants.MAX_PASSWORD_LENGTH + " characters");
	    }
		
		var userDtl = userDtlRepository.findByUserName(username)
			.orElseThrow(() -> {
		        throw new AuthenticationFailedException("User not found");
		    });
		
		if (Boolean.FALSE.equals(userDtl.getIsActive())) {
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
			throw new AuthenticationFailedException(LdTrackerConstants.USER_INCORRECT);
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();
		String json = mapper.writeValueAsString(userDtl);
		Map<String, Object> map 
		  = mapper.readValue(json, new TypeReference<Map<String,Object>>(){});
		var jwtToken = jwtService.generateToken(map, userDtl);
		
		return AuthenticationResponse
			.builder()
			.token(jwtToken)
			.build();
	}
}