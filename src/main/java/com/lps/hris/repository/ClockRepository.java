package com.lps.hris.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import com.lps.hris.model.Clock;

public interface ClockRepository extends JpaRepository<Clock, Long>{
	
	@Procedure(procedureName  = "TIME_IN_OUT")
	String timeInOut(String employeeId, Date timeIn, Date timeOut, String description);
	
	@Procedure(procedureName  = "GET_EMPLOYEE_TIME_IN_OUT")
	List<Object[]> getEmployeeTimeInOut(String employeeId);
	
	@Procedure(procedureName  = "GET_CLOCK_IN_LAST_LOG")
	List<Object[]> getLastClockIn(String employeeId);
	
	@Procedure(procedureName  = "GET_CLOCK_IN_DATE_FILTER")
	List<Object[]> getClockInByDate(String employeeId, Date timeIn, Date timeOut, String filterType);
}
