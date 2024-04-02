package com.lps.hris.controller;



import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lps.hris.constants.HrisConstants;
import com.lps.hris.dto.AttendanceDto;
import com.lps.hris.dto.TimeInOutRequestDto;
import com.lps.hris.model.HrisError;
import com.lps.hris.model.Result;
import com.lps.hris.service.AttendanceService;
import com.lps.hris.service.ResultService;
import com.lps.hris.serviceImpl.AttendanceServiceImpl;
import com.lps.hris.serviceImpl.ErrorHandlingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/attendance")
public class AttendanceController {

	private final AttendanceService attendanceService;
	private final ResultService resultService;
	private final ErrorHandlingService errorHandlingService;
	
	@RequestMapping(value="/getAllAttendance", method = RequestMethod.POST)
	public ResponseEntity<Result> getAllAttendance(@RequestBody TimeInOutRequestDto timeInOutRequestDto) {
		try {
			 List<HrisError> errors = new ArrayList<>();
		     errorHandlingService.validateInputParametersEmpId(timeInOutRequestDto.getEmployeeId(), errors);
		     errorHandlingService.validateCharactersInInput(timeInOutRequestDto.getEmployeeId(), errors);
		     
		     if (!errors.isEmpty()) {
		            Result result = resultService.setResult("200", HrisConstants.SUCCESS, errors, null);
		            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		     }
		     
		     List<AttendanceDto> attendances = this.attendanceService.getAllAttendance(timeInOutRequestDto.getEmployeeId());
		     Result result = resultService.setResult("200", HrisConstants.SUCCESS, null, attendances);
		     return new ResponseEntity<>(result, HttpStatus.OK);
		     
		}  catch (Exception e) {
			 e.printStackTrace();
		     // Handle other exceptions
		     return errorHandlingService.createErrorResponse("INTERNAL_SERVER_ERROR", "An error occurred while processing the request", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		
	}
	
	@RequestMapping(value="/getLastLogTimeAttendance", method = RequestMethod.POST)
	public ResponseEntity<Result> getLastLogTimeAttendance(@RequestBody TimeInOutRequestDto timeInOutRequestDto) {
		try {
			 List<HrisError> errors = new ArrayList<>();
		     errorHandlingService.validateInputParametersEmpId(timeInOutRequestDto.getEmployeeId(), errors);
		     errorHandlingService.validateCharactersInInput(timeInOutRequestDto.getEmployeeId(), errors);
		     
		     if (!errors.isEmpty()) {
		            Result result = resultService.setResult("200", HrisConstants.SUCCESS, errors, null);
		            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		     }
		     
		     List<AttendanceDto> attendances = this.attendanceService.getLastLogTimeAttendance(timeInOutRequestDto.getEmployeeId());
		     Result result = resultService.setResult("200", HrisConstants.SUCCESS, null, attendances);
		     return new ResponseEntity<>(result, HttpStatus.OK);
		     
		}  catch (Exception e) {
			 e.printStackTrace();
		     // Handle other exceptions
		     return errorHandlingService.createErrorResponse("INTERNAL_SERVER_ERROR", "An error occurred while processing the request", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/filterByAttendance", method = RequestMethod.POST)
	public ResponseEntity<Result> filterByAttendance(@RequestBody TimeInOutRequestDto timeInOutRequestDto) {
	    try {
	        // Validate input parameters
	        List<HrisError> errors = new ArrayList<>();
	        errorHandlingService.validateInputParameters(timeInOutRequestDto, errors);
	        errorHandlingService.validateCharactersInInput(timeInOutRequestDto.getEmployeeId(), errors);

	        if (!errors.isEmpty()) {
	            Result result = resultService.setResult("200", HrisConstants.SUCCESS, errors, null);
	            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
	        }
	       
	        List<AttendanceDto> attendances = attendanceService.getClockRecordDateFilter(timeInOutRequestDto);
	        Result result = resultService.setResult("200", HrisConstants.SUCCESS, null, attendances);
	        return new ResponseEntity<>(result, HttpStatus.OK);
	    } catch (Exception e) {
	        e.printStackTrace();
	        // Handle other exceptions
	        return errorHandlingService.createErrorResponse("INTERNAL_SERVER_ERROR", "An error occurred while processing the request", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	

}
