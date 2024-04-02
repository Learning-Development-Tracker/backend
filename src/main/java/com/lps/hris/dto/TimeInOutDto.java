package com.lps.hris.dto;

import java.util.Date;

import lombok.Data;

@Data
public class TimeInOutDto {
    
	private String employeeId;

	private Date timeIn;
	
	private Date timeOut;

	private String description;
}
