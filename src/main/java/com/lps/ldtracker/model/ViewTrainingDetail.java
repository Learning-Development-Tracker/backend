package com.lps.ldtracker.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class ViewTrainingDetail {
	
	private String id;
	private String name;
	private String status;
	private String duration;
	private BigDecimal progressNumber;
	private Date date_started;
	private Timestamp date_completed;
	private BigDecimal estimated_hours;
	private Date due_date;
	private Timestamp target_date;
	private String link;
	private String remarks;
	private String description;
	
}
