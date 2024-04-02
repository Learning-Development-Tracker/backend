package com.lps.hris.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lps.hris.model.HrisError;
import com.lps.hris.model.Result;
import com.lps.hris.service.ResultService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService{
	public Result setResult (String status, String message, List<HrisError> errors, Object data) {
		Result result = new Result();
		
		result.setStatus(status);
		result.setMessage(message);
		result.setErrors(errors);
		result.setData(data);
		
		return result;
	}
}
