package com.lps.ldtracker.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lps.ldtracker.exception.AuthenticationFailedException;
import com.lps.ldtracker.model.LoginRequest;
import com.lps.ldtracker.model.RegistrationRequest;
import com.lps.ldtracker.model.Result;
import com.lps.ldtracker.service.AuthenticationService;
import com.lps.ldtracker.service.UserDtlService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/authentication")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600)
@Slf4j
public class AuthenticationController {
	private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class); 
	private final AuthenticationService authenticationService;
	
	private final UserDtlService userDtlService; 
 
	@PostMapping(value="/register") 
	public ResponseEntity<Result> registerUser(@RequestBody RegistrationRequest request, final HttpServletRequest httpRequest){
		Result result = this.userDtlService.registerUser(request);
		if(null != result.getErrors() && !result.getErrors().isEmpty()) {
			 return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}
		return  new ResponseEntity<>(result, HttpStatus.OK);
		
	}
	
	@PostMapping(value="/login")
	public ResponseEntity<?> login(
		@RequestBody LoginRequest loginRequest
	) {
		try {
			return ResponseEntity
				.ok(authenticationService.login(loginRequest));
		} catch (AuthenticationFailedException authenticationFailedException) {
			return ResponseEntity
				.status(HttpStatus.FORBIDDEN)
				.body(authenticationFailedException.getMessage());
		}
	}
	
}