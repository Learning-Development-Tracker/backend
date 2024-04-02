package com.lps.hris.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lps.hris.constants.HrisConstants;
import com.lps.hris.dto.TimeInOutDto;
import com.lps.hris.dto.TimeInOutRequestDto;
import com.lps.hris.model.HrisError;
import com.lps.hris.model.Result;
import com.lps.hris.service.ResultService;

@Service
public class ErrorHandlingService {
	
	@Autowired
    private MessageSource messageSource;
	
	private static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
		    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	
	private static final Pattern VALID_PHONE_NO_REGEX = Pattern.compile("\\d+");

    public ResponseEntity<Result> createErrorResponse(String errorCode, String errorMessage, HttpStatus httpStatus) {
        List<HrisError> errors = new ArrayList<>();
        HrisError hrisError = new HrisError(errorCode, errorMessage);
        errors.add(hrisError);

        ResultService resultService = new ResultServiceImpl();
        Result result = resultService.setResult(String.valueOf(httpStatus.value()), "Failure", errors, null);

        return new ResponseEntity<>(result, httpStatus);
    }
    
    public void validateInputParameters(TimeInOutRequestDto timeInOutRequestDto, List<HrisError> errors) {
        if (timeInOutRequestDto == null) {
            errors.add(new HrisError(HrisConstants.MISSING_PARAMETERS, "Request body cannot be null"));
        }
        if (timeInOutRequestDto.getEmployeeId() == null || timeInOutRequestDto.getEmployeeId().isEmpty()) {
            errors.add(new HrisError(HrisConstants.MISSING_PARAMETERS, "employeeId is required"));
        }
        if (timeInOutRequestDto.getStartDate() == null) {
            errors.add(new HrisError(HrisConstants.MISSING_PARAMETERS, "startDate is required"));
        }
        if (timeInOutRequestDto.getEndDate() == null) {
            errors.add(new HrisError(HrisConstants.MISSING_PARAMETERS, "endDate is required"));
        }else if (timeInOutRequestDto.getEndDate() != null && timeInOutRequestDto.getStartDate() != null && timeInOutRequestDto.getEndDate().before(timeInOutRequestDto.getStartDate())) {
            errors.add(new HrisError("INVALID_DATE_RANGE", "endDate cannot be earlier than startDate"));
        }
    }
    
    public void validateInputParametersEmpId(String employeeId, List<HrisError> errors) {
        if (employeeId == null || employeeId.isEmpty()) {
            errors.add(new HrisError(HrisConstants.INVALID_EMAIL, "employeeId is required"));
        }
    }
    
    public void validateCharactersInInput(String employeeId, List<HrisError> errors) {
        // Validate employeeId
        if (!isValidEmployeeId(employeeId)) {
            errors.add(new HrisError(HrisConstants.INVALID_EMAIL, "employeeId contains special characters"));
        }
    }
    
    public void validateInsertCharactersInInput(TimeInOutDto timeInOutDto, List<HrisError> errors) {
        // Validate employeeId
        if (!isValidEmployeeId(timeInOutDto.getEmployeeId())) {
            errors.add(new HrisError(HrisConstants.INVALID_CHARACTERS, "employeeId contains special characters"));
        }
    }
    
    
    public void validateInsertInputParametersEmpId(TimeInOutDto timeInOutDto, List<HrisError> errors) {
        if (timeInOutDto == null) {
            errors.add(new HrisError(HrisConstants.MISSING_PARAMETERS, "Request body cannot be null"));
        }
        if (timeInOutDto.getEmployeeId() == null || timeInOutDto.getEmployeeId().isEmpty()) {
            errors.add(new HrisError(HrisConstants.MISSING_PARAMETERS, "employeeId is required"));
        }
    }
    
    private boolean isValidEmployeeId(String employeeId) {
        // Validate if employeeId does not contain special characters
        String employeeIdRegex = "^[a-zA-Z0-9-]*$";
        return Pattern.matches(employeeIdRegex, employeeId);
    }
    
    public void validateEmail(String email, String label, String errorCode, List<HrisError> errors) {  
    	Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email); 
    	if(!matcher.matches()) {
    		String[] msgParam = new String[1];
    		msgParam[0] = email;
    		errors.add(new HrisError(errorCode, messageSource.getMessage("validation.format.message", msgParam, LocaleContextHolder.getLocale())));
    	}
    }
    
    public void validateEmptyInputs(String input, String label , String errorCode, List<HrisError> errors) {
    	if(null == input || input.isEmpty()) {
    		String[] msgParam = new String[1];
    		msgParam[0] = label;
    		errors.add(new HrisError(errorCode, messageSource.getMessage("validation.empty.message", msgParam, LocaleContextHolder.getLocale())));
    	}
    }
    
    public void validateCharLength(String str, String label, String errorCode, int maxLength, List<HrisError> errors) {
    	if(str.length() > maxLength) {
    		String[] msgParam = new String[2];
    		msgParam[0] = label;
    		msgParam[1] = String.valueOf(maxLength);
    		errors.add(new HrisError(errorCode, messageSource.getMessage("validation.charlength.message", msgParam, LocaleContextHolder.getLocale())));
    	}
    }
    
    public void validatePhoneNumber(String phoneNo, String label, String errorCode, List<HrisError> errors) {  
    	Matcher matcher = VALID_PHONE_NO_REGEX.matcher(phoneNo); 
    	if(!matcher.matches()) {
    		String[] msgParam = new String[1];
    		msgParam[0] = phoneNo;
    		errors.add(new HrisError(errorCode, messageSource.getMessage("validation.number.message", msgParam, LocaleContextHolder.getLocale())));
    	}
    }
    
}