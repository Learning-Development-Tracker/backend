package com.lps.hris.service;

import java.util.List;

import com.lps.hris.dto.TimeInOutDto;
import com.lps.hris.dto.TimeInOutRequestDto;

public interface ClockService {
	String timeInOut(TimeInOutDto timeInOutDto);
	public TimeInOutDto getEmployeeTimeInOut(String employeeId);
	public TimeInOutDto getLastClockIn(String employeeId);
	public List<TimeInOutDto> getClockInByDate(TimeInOutRequestDto timeInOutRequestDto);
}
