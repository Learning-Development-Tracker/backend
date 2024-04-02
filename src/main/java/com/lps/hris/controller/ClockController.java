package com.lps.hris.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lps.hris.constants.HrisConstants;
import com.lps.hris.dto.TimeInOutDto;
import com.lps.hris.dto.TimeInOutRequestDto;
import com.lps.hris.model.HrisError;
import com.lps.hris.model.Result;
import com.lps.hris.service.ClockService;
import com.lps.hris.service.ResultService;
import com.lps.hris.serviceImpl.ErrorHandlingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/clock")
public class ClockController {
	final private ClockService clockSevice;
	private final ResultService resultService;
	private final ErrorHandlingService errorHandlingService;
	
	@RequestMapping(value="/timeInOut", method = RequestMethod.POST)
	public ResponseEntity<Result> timeInOut(@RequestBody TimeInOutDto timeInOutDto) {
		try {
			 List<HrisError> errors = new ArrayList<>();
		     errorHandlingService.validateInsertInputParametersEmpId(timeInOutDto, errors);
		     errorHandlingService.validateInsertCharactersInInput(timeInOutDto, errors);
		     
		     if (!errors.isEmpty()) {
		            Result result = resultService.setResult("200", HrisConstants.SUCCESS, errors, null);
		            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		     }
		     
		     String message = clockSevice.timeInOut(timeInOutDto);
		     Result result = resultService.setResult("200", HrisConstants.SUCCESS, null, message);
		     return new ResponseEntity<>(result, HttpStatus.OK);
		     
		}  catch (Exception e) {
			 e.printStackTrace();
		     // Handle other exceptions
		     return errorHandlingService.createErrorResponse("INTERNAL_SERVER_ERROR", "An error occurred while processing the request", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		
		
	}
	
	@RequestMapping(value="/getActiveTimeIn", method = RequestMethod.POST)
	public ResponseEntity<Result> getEmployeeTimeInOut(@RequestBody TimeInOutRequestDto timeInOutRequestDto) {
		try {
			 List<HrisError> errors = new ArrayList<>();
		     errorHandlingService.validateInputParametersEmpId(timeInOutRequestDto.getEmployeeId(), errors);
		     errorHandlingService.validateCharactersInInput(timeInOutRequestDto.getEmployeeId(), errors);
		     
		     if (!errors.isEmpty()) {
		            Result result = resultService.setResult("200", HrisConstants.SUCCESS, errors, null);
		            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		     }
		     
		     TimeInOutDto clockIn = this.clockSevice.getEmployeeTimeInOut(timeInOutRequestDto.getEmployeeId());
		     Result result = resultService.setResult("200", HrisConstants.SUCCESS, null, clockIn);
		     return new ResponseEntity<>(result, HttpStatus.OK);
		     
		}  catch (Exception e) {
			 e.printStackTrace();
		     // Handle other exceptions
		     return errorHandlingService.createErrorResponse("INTERNAL_SERVER_ERROR", "An error occurred while processing the request", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@RequestMapping(value="/getLastClockIn", method = RequestMethod.POST)
	public ResponseEntity<Result> getLastClockIn(@RequestBody TimeInOutRequestDto timeInOutRequestDto) {
		try {
			 List<HrisError> errors = new ArrayList<>();
		     errorHandlingService.validateInputParametersEmpId(timeInOutRequestDto.getEmployeeId(), errors);
		     errorHandlingService.validateCharactersInInput(timeInOutRequestDto.getEmployeeId(), errors);
		     
		     if (!errors.isEmpty()) {
		            Result result = resultService.setResult("200", HrisConstants.SUCCESS, errors, null);
		            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		     }
		     
		     TimeInOutDto clockIn = this.clockSevice.getLastClockIn(timeInOutRequestDto.getEmployeeId());
		     Result result = resultService.setResult("200", HrisConstants.SUCCESS, null, clockIn);
		     return new ResponseEntity<>(result, HttpStatus.OK);
		     
		}  catch (Exception e) {
			 e.printStackTrace();
		     // Handle other exceptions
		     return errorHandlingService.createErrorResponse("INTERNAL_SERVER_ERROR", "An error occurred while processing the request", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@RequestMapping(value="/filterClockInByDate", method = RequestMethod.POST)
	public ResponseEntity<Result> filterClockInByDate(@RequestBody TimeInOutRequestDto timeInOutRequestDto) {
		try {
	        // Validate input parameters
	        List<HrisError> errors = new ArrayList<>();
	        errorHandlingService.validateInputParameters(timeInOutRequestDto, errors);
	        errorHandlingService.validateCharactersInInput(timeInOutRequestDto.getEmployeeId(), errors);

	        if (!errors.isEmpty()) {
	            Result result = resultService.setResult("200", HrisConstants.SUCCESS, errors, null);
	            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
	        }
	       
	        List<TimeInOutDto> ClockIns = clockSevice.getClockInByDate(timeInOutRequestDto);
	        Result result = resultService.setResult("200", HrisConstants.SUCCESS, null, ClockIns);
	        return new ResponseEntity<>(result, HttpStatus.OK);
	    } catch (Exception e) {
	        e.printStackTrace();
	        // Handle other exceptions
	        return errorHandlingService.createErrorResponse("INTERNAL_SERVER_ERROR", "An error occurred while processing the request", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
		
	}
}
