package com.lps.ldtracker.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lps.ldtracker.constants.LdTrackerConstants;
import com.lps.ldtracker.exception.AuthenticationFailedException;
import com.lps.ldtracker.model.AuthenticationResponse;
import com.lps.ldtracker.model.LoginRequest;
import com.lps.ldtracker.model.LoginResponse;
import com.lps.ldtracker.model.RegistrationRequest;
import com.lps.ldtracker.model.Result;
import com.lps.ldtracker.model.UserDtl;
import com.lps.ldtracker.service.AuthenticationService;
import com.lps.ldtracker.service.UserDtlService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/authentication")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600)
public class AuthenticationController {
	
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
	public ResponseEntity<Result> login(
		@RequestBody LoginRequest loginRequest
	) throws JsonProcessingException {
		Result result = new Result();
		String userName = loginRequest.getUsername();
		Optional<UserDtl> userDtl = this.userDtlService.findByUserName(userName);
		try {
			AuthenticationResponse authResponse = authenticationService.login(loginRequest);
			if(userDtl.isPresent()) {
				UserDtl userDtl2 = userDtl.get(); 
				var loginData = LoginResponse.builder()
						.userId(userDtl2.getUserId())
						.isActive(userDtl2.getIsActive())
						.userName(userDtl2.getUsername())
						.userPass(userDtl2.getUserPass())
						.isDeleted(userDtl2.getIsDeleted())
						.token(authResponse.getToken())
						.createdBy(userDtl2.getCreatedBy())
						.createdDate(userDtl2.getCreatedDate())
						.updatedBy(userDtl2.getUpdatedBy())
						.updatedDate(userDtl2.getUpdatedDate())
						.accessName(userDtl2.getAccessLevel().getAlName())
						.build();
				result.setData(loginData);
				result.setMessage(LdTrackerConstants.AUTH_SUCCESS);
				result.setStatus(LdTrackerConstants.SUCCESS);
			}
			return ResponseEntity
					.ok(result);
		} catch (AuthenticationFailedException authenticationFailedException) {
			result.setData(null);
			result.setMessage(authenticationFailedException.getMessage());
			result.setStatus(LdTrackerConstants.ERROR);
			return ResponseEntity
				.status(HttpStatus.FORBIDDEN)
				.body(result);
		}
	}
	
	@PostMapping(value="/reset") 
	public ResponseEntity<Result> resetPassword(@RequestBody RegistrationRequest request, final HttpServletRequest httpRequest){
		Result result = this.userDtlService.resetPassword(request);
		if(null != result.getErrors() && !result.getErrors().isEmpty()) {
			 return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}
		return  new ResponseEntity<>(result, HttpStatus.OK);
		
	}
	
	@PostMapping(value="/exist-username")
	public ResponseEntity<Result> existingUsername(@RequestBody LoginRequest request) {
		Result result = this.userDtlService.isExistUsername(request);
		if(null != result.getErrors() && !result.getErrors().isEmpty()) {
			 return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}
		return  new ResponseEntity<>(result, HttpStatus.OK);
	}
	
}