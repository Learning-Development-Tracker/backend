package com.lps.ldtracker.serviceImpl;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lps.ldtracker.dto.AttendanceDto;
import com.lps.ldtracker.dto.TimeInOutRequestDto;
import com.lps.ldtracker.repository.AttendanceRepository;
import com.lps.ldtracker.service.AttendanceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {
	private static final Logger logger =   LoggerFactory.getLogger(AttendanceServiceImpl.class);
	
	private final AttendanceRepository attendanceRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<AttendanceDto> getAllAttendance(String employeeId) {
		List<AttendanceDto> attendances = new ArrayList<>();
		
		try {
			List<Object[]> results = attendanceRepository.getAllAttendance(employeeId);
			
			for (Object[] result : results) {
				AttendanceDto attendanceDto = new AttendanceDto();
				attendanceDto.setId((Integer)result[0]);
				attendanceDto.setEmployeeId((String)result[1]);
				attendanceDto.setLeaveType((String)result[2]);
				attendanceDto.setLogType((String)result[3]);
				attendanceDto.setWorkSetUpType((String)result[4]);
				attendanceDto.setShiftType((String)result[5]);
				attendanceDto.setTimeIn((Timestamp)result[6]);
				attendanceDto.setTimeOut((Timestamp)result[7]);
				
				attendances.add(attendanceDto);
			};
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ERROR getAllAttendance: " + e.getMessage());

		}
		
		return attendances;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<AttendanceDto> getLastLogTimeAttendance(String employeeId) {
		List<AttendanceDto> attendances = new ArrayList<>();
		
		try {

			List<Object[]> results = attendanceRepository.getLastLogTimeAttendance(employeeId);
			
			for (Object[] result : results) {
				AttendanceDto attendanceDto = new AttendanceDto();
				attendanceDto.setId((Integer)result[0]);
				attendanceDto.setEmployeeId((String)result[1]);
				attendanceDto.setLeaveType((String)result[2]);
				attendanceDto.setLogType((String)result[3]);
				attendanceDto.setWorkSetUpType((String)result[4]);
				attendanceDto.setShiftType((String)result[5]);
				attendanceDto.setTimeIn((Timestamp)result[6]);
				attendanceDto.setTimeOut((Timestamp)result[7]);
				
				attendances.add(attendanceDto);
			};
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ERROR getLastLogTimeAttendance: " + e.getMessage());

		}
		
		
		return attendances;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<AttendanceDto> getClockRecordDateFilter(TimeInOutRequestDto timeInOutRequestDto){
		List<AttendanceDto> attendances = new ArrayList<>();
		
		
		try {

			List<Object[]> results = attendanceRepository.getAttendanceByDate(timeInOutRequestDto.getEmployeeId(), timeInOutRequestDto.getStartDate(), timeInOutRequestDto.getEndDate(), timeInOutRequestDto.getFilterType());
			
			for (Object[] result : results) {
				AttendanceDto clockIn = new AttendanceDto();
				clockIn.setId((Integer)result[0]);
				clockIn.setEmployeeId((String)result[1]);
				clockIn.setLeaveType((String)result[2]);
				clockIn.setLogType((String)result[3]);
				clockIn.setWorkSetUpType((String)result[4]);
				clockIn.setShiftType((String)result[5]);
				clockIn.setTimeIn((Timestamp)result[6]);
				clockIn.setTimeOut((Timestamp)result[7]);
				
				attendances.add(clockIn);
			};
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ERROR getClockRecordDateFilter: " + e.getMessage());

		}
		
		return attendances;
	}
}
