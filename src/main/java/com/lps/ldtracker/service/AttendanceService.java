package com.lps.ldtracker.service;
import java.util.List;

import com.lps.ldtracker.dto.AttendanceDto;
import com.lps.ldtracker.dto.TimeInOutRequestDto;

public interface AttendanceService {
	public List<AttendanceDto> getAllAttendance(String employeeId);
	public List<AttendanceDto> getLastLogTimeAttendance(String employeeId);
	public List<AttendanceDto> getClockRecordDateFilter(TimeInOutRequestDto timeInOutRequestDto);
}
