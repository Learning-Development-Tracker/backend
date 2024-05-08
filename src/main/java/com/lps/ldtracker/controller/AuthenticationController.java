package com.lps.ldtracker.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lps.ldtracker.dto.ResourceDto;
import com.lps.ldtracker.entity.CertificationFileUpload;
import com.lps.ldtracker.exception.AuthenticationFailedException;
import com.lps.ldtracker.model.ForgotPasswordRequest;
import com.lps.ldtracker.model.LoginRequest;
import com.lps.ldtracker.model.OTPVerificationRequest;
import com.lps.ldtracker.model.RegistrationRequest;
import com.lps.ldtracker.model.Result;
import com.lps.ldtracker.model.UpdatePasswordRequest;
import com.lps.ldtracker.model.VerifyOtpRecord;
import com.lps.ldtracker.service.AuthenticationService;
import com.lps.ldtracker.service.CertificationFileUploadService;
import com.lps.ldtracker.service.ResourceService;
import com.lps.ldtracker.service.UserForgotPasswordService;
import com.lps.ldtracker.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/v1/authentication")
@RequiredArgsConstructor
public class AuthenticationController {
	
	private final AuthenticationService authenticationService;
	private final UserForgotPasswordService userForgotPasswordService; 
	private final ResourceService resourceService;
	private final CertificationFileUploadService certificationFileUploadService;
	
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
	
	@PostMapping(value="/addResource")
	public ResponseEntity<Result> addUser(@RequestBody ResourceDto resourceDto, final HttpServletRequest httpRequest) {

		return new ResponseEntity<Result>(resourceService.addResource(resourceDto), HttpStatus.OK); 
	}
	
	@PostMapping(value="/resourceCertificationUpload")
	public ResponseEntity<Object> certificationUpload(@RequestParam("files") MultipartFile[] files,
            												@RequestParam Map<String, String> headers) {
		return ResponseEntity.ok().body(this.certificationFileUploadService.uploadFiles(files, headers));
	}
	
	@GetMapping(value="/viewResource/{id}")
	public ResponseEntity<Object> viewResource(@PathVariable Long id) {
		Result result = this.resourceService.viewResource(id);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping(value="/viewResourceCertification/{ownerId}")
	public ResponseEntity<Object> viewResourceCertification(@PathVariable String ownerId) {
		Result result = this.certificationFileUploadService.viewResourceCertification(ownerId);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
}