package com.lps.ldtracker.serviceImpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lps.ldtracker.dto.TimeInOutDto;
import com.lps.ldtracker.dto.TimeInOutRequestDto;
import com.lps.ldtracker.repository.ClockRepository;
import com.lps.ldtracker.service.ClockService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ClockServiceImpl implements ClockService{
	private static final Logger logger =   LoggerFactory.getLogger(ClockServiceImpl.class);
	
	private final ClockRepository clockRepository;
	
	@Override
	public String timeInOut(TimeInOutDto timeInOutDto){
		String message = "";
		
		try {
			message = clockRepository.timeInOut(
					timeInOutDto.getEmployeeId(), 
					timeInOutDto.getTimeIn(), 
					timeInOutDto.getTimeOut(), 
					timeInOutDto.getDescription()
					);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ERROR timeInOut: " + e.getMessage());

		}
		
		return message;
	}
	
	@Override
	@Transactional(readOnly = true)
	public TimeInOutDto getEmployeeTimeInOut(String employeeId) {
		TimeInOutDto timeInOutDto = new TimeInOutDto();
		
		try {
			List<Object[]> results = clockRepository.getEmployeeTimeInOut(employeeId);
			
			if (!results.isEmpty()) {
				var empoyeeTimeInOut = results.get(0);
				timeInOutDto.setEmployeeId((String)empoyeeTimeInOut[0]);
				timeInOutDto.setTimeIn((Timestamp)empoyeeTimeInOut[1]);
				timeInOutDto.setTimeOut((Timestamp)empoyeeTimeInOut[2]);
				timeInOutDto.setDescription((String)empoyeeTimeInOut[3]);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ERROR getEmployeeTimeInOut: " + e.getMessage());

		}
		
		return timeInOutDto;
	}
	
	@Override
	@Transactional(readOnly = true)
	public TimeInOutDto getLastClockIn(String employeeId) {
		TimeInOutDto timeInOutDto = new TimeInOutDto();
		
		try {
			List<Object[]> results = clockRepository.getLastClockIn(employeeId);
			
			if (!results.isEmpty()) {
				var empoyeeTimeInOut = results.get(0);
				timeInOutDto.setEmployeeId((String)empoyeeTimeInOut[1]);
				timeInOutDto.setTimeIn((Timestamp)empoyeeTimeInOut[2]);
				timeInOutDto.setTimeOut((Timestamp)empoyeeTimeInOut[3]);
				timeInOutDto.setDescription((String)empoyeeTimeInOut[4]);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ERROR getLastClockIn: " + e.getMessage());

		}
		
		return timeInOutDto;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<TimeInOutDto> getClockInByDate(TimeInOutRequestDto timeInOutRequestDto) {
		List<TimeInOutDto> ClockIn = new ArrayList<>();
		
		try {
			List<Object[]> results = clockRepository.getClockInByDate(timeInOutRequestDto.getEmployeeId(), timeInOutRequestDto.getStartDate(), timeInOutRequestDto.getEndDate(), timeInOutRequestDto.getFilterType());
			
			for (Object[] result : results) {
				TimeInOutDto TimeInOutByDateDto = new TimeInOutDto();
				TimeInOutByDateDto.setEmployeeId((String)result[1]);
				TimeInOutByDateDto.setTimeIn((Timestamp)result[2]);
				TimeInOutByDateDto.setTimeOut((Timestamp)result[3]);
				TimeInOutByDateDto.setDescription((String)result[4]);
				ClockIn.add(TimeInOutByDateDto);
			};
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ERROR getClockInByDate: " + e.getMessage());

		}
		
		return ClockIn;
	}
}
