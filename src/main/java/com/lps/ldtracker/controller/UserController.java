package com.lps.ldtracker.controller;

import java.util.List;

import com.lps.ldtracker.model.Result;
import com.lps.ldtracker.model.UserT;
import com.lps.ldtracker.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {
 
	private final UserService userSErvice; 
	
	@GetMapping(value="/users/get-profile-information")
	public ResponseEntity<String> getProfileInformation() {
		return new ResponseEntity<>("Employee Test", HttpStatus.OK);
	}
	
	@GetMapping(value="/admin/users/get-users")
	public ResponseEntity<Result> getusers() {
		List<UserT> usr = this.userSErvice.getUserList();
		Result result = new Result();
		result.setData(usr);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
