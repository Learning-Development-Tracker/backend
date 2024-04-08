package com.lps.ldtracker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AppController {

	@GetMapping(value="/health")
	public ResponseEntity<String> getProfileInformation() {
		return new ResponseEntity<>("Health check", HttpStatus.OK);
	}
}