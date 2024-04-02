package com.lps.hris.model;

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
public class Code {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "attendance")
	@SequenceGenerator(sequenceName = "attendance_seq", allocationSize = 1, name = "attendance")
	private Integer id;
	private String codeType;
	private String code;
	private String codeDescription;
}
