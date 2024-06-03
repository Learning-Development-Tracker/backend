package com.lps.ldtracker.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lps.ldtracker.entity.UserDtl;
import com.lps.ldtracker.model.Result;
import com.lps.ldtracker.service.UserDtlService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {
 
	private final UserDtlService userDtlService; 
	
	@GetMapping(value="/users/get-profile-information")
	public ResponseEntity<String> getProfileInformation() {
		return new ResponseEntity<>("Employee Test", HttpStatus.OK);
	}
	
	@GetMapping(value="/admin/users/get-users")
	public ResponseEntity<Result> getusers() {
		List<UserDtl> usr = this.userDtlService.getUserList();
		Result result = new Result();
		result.setData(usr);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
}