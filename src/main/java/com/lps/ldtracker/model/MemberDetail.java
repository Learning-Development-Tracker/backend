package com.lps.ldtracker.model;

import java.util.Date;

import org.hibernate.annotations.NaturalId;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDetail {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "memberdtl")
	@SequenceGenerator(sequenceName = "memberdtl_seq", allocationSize = 1, name = "memberdtl")
	private String memberDetailId;
	@NaturalId(mutable=true)
	private String firstName;
	private String lastName;
	private Integer employeeNum;
	private String emailAddress;
	private boolean isDeleted=false;
	private Date employmentDt;
	private String regionId;
	private String careerLevelId;
	private String teamId;
	private String statusId;
	private String createdBy;
	private String createdDate;
	private String updatedBy;
	private String updatedDate;
}
