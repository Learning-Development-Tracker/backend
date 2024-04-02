package com.lps.hris.dto;

import java.util.Date;

import lombok.Data;

@Data
public class AttendanceDto {
	

	private Integer id;
    
	private String employeeId;

	private String leaveType;

	private String logType;

	private String workSetUpType;

	private String shiftType;
    
	private Date timeIn;
	
	private Date timeOut;
    
	private String logTimeDisplay;
	
	private String description;
	
	//for Date filtering
	private Date startDate;
	private Date endDate;
	private String filterType;
}
