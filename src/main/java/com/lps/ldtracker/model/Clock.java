package com.lps.ldtracker.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Clock {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clock")
	@SequenceGenerator(sequenceName = "clock_seq", allocationSize = 1, name = "clock")
	private Integer id;
	
	private String employeeId;

	private Date timeIn;
	
	private Date timeOut;

	private String description;
}
