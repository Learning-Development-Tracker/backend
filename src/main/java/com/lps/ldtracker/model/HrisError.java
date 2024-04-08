package com.lps.ldtracker.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class HrisError {
	private String code;
	private String message;
	private LocalDateTime timestamp;
	
	public HrisError (String code, String message) {
		this.code = code;
		this.message = message;
		this.timestamp = LocalDateTime.now();
	}
}
