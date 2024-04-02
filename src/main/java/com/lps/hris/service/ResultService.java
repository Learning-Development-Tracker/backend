package com.lps.hris.service;

import java.util.List;

import com.lps.hris.model.HrisError;
import com.lps.hris.model.Result;

public interface ResultService {
	public Result setResult (String status, String message, List<HrisError> errors, Object data);
}
