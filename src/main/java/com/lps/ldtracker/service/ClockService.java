package com.lps.ldtracker.service;

import java.util.List;

import com.lps.ldtracker.dto.TimeInOutDto;
import com.lps.ldtracker.dto.TimeInOutRequestDto;

public interface ClockService {
	String timeInOut(TimeInOutDto timeInOutDto);
	public TimeInOutDto getEmployeeTimeInOut(String employeeId);
	public TimeInOutDto getLastClockIn(String employeeId);
	public List<TimeInOutDto> getClockInByDate(TimeInOutRequestDto timeInOutRequestDto);
}
