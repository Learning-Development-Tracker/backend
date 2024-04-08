package com.lps.ldtracker.dto;

import java.util.Date;

import lombok.Data;

@Data
public class TimeInOutRequestDto {
    
	private String employeeId;

	private Date startDate;
	
	private Date endDate;
	
	private String filterType;
}
