package com.lps.hris.service;
import java.util.List;

import com.lps.hris.dto.AttendanceDto;
import com.lps.hris.dto.TimeInOutRequestDto;

public interface AttendanceService {
	public List<AttendanceDto> getAllAttendance(String employeeId);
	public List<AttendanceDto> getLastLogTimeAttendance(String employeeId);
	public List<AttendanceDto> getClockRecordDateFilter(TimeInOutRequestDto timeInOutRequestDto);
}
