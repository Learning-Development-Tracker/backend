package com.lps.hris.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lps.hris.exception.AuthenticationFailedException;
import com.lps.hris.model.ForgotPasswordRequest;
import com.lps.hris.model.LoginRequest;
import com.lps.hris.model.OTPVerificationRequest;
import com.lps.hris.model.RegistrationRequest;
import com.lps.hris.model.Result;
import com.lps.hris.model.UpdatePasswordRequest;
import com.lps.hris.model.VerifyOtpRecord;
import com.lps.hris.service.AuthenticationService;
import com.lps.hris.service.UserForgotPasswordService;
import com.lps.hris.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/authentication")
@RequiredArgsConstructor
public class AuthenticationController {
	
	private final AuthenticationService authenticationService;
	private final UserForgotPasswordService userForgotPasswordService; 
	
	private final UserService userService; 
 
	@PostMapping(value="/register") 
	public ResponseEntity<Result> registerUser(@RequestBody RegistrationRequest request, final HttpServletRequest httpRequest){
		Result result = this.userService.registerUser(request);
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
	
	@PostMapping(value="/verifyRegOtp") 
	public ResponseEntity<Result> verifyOtp(@RequestBody VerifyOtpRecord verifyOtp){
		
		Result result = new Result();
		result = this.userService.isVerified(verifyOtp.otp(), verifyOtp.email());
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PostMapping(value="/sendResetPwOtp")
	public ResponseEntity<Result> sendOTP(@RequestBody ForgotPasswordRequest request, final HttpServletRequest httpRequest) {
		Result result = this.userForgotPasswordService.sendOTP(request);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PostMapping(value="/verifyResetPwOtp")
	public ResponseEntity<Result> verifyOtp(@RequestBody OTPVerificationRequest request, final HttpServletRequest httpRequest) {
		Result result = this.userForgotPasswordService.verifyOtp(request);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PostMapping(value="/updatePassword")
	public ResponseEntity<Result> updatePassword(@RequestBody UpdatePasswordRequest request, final HttpServletRequest httpRequest) {
		Result result = this.userForgotPasswordService.updatePassword(request);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
}