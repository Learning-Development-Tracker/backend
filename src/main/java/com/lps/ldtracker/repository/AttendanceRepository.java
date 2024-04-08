package com.lps.ldtracker.repository;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import com.lps.ldtracker.model.Clock;



public interface AttendanceRepository extends JpaRepository<Clock, Long> {

	@Procedure(procedureName  = "get_all_attendance")
	List<Object[]> getAllAttendance(String employeeId);
	
	@Procedure(procedureName  = "GET_LAST_LOG_ATTENDANCE")
	List<Object[]> getLastLogTimeAttendance(String employeeId);
	
	@Procedure(procedureName  = "GET_CLOCK_RECORD_DATE_FILTER")
	List<Object[]> getAttendanceByDate(String employeeId, Date startDate, Date endDate, String filterType);

}
