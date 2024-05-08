package com.lps.ldtracker.dto;

import java.util.List;

import com.lps.ldtracker.entity.CertificationFileUpload;
import com.lps.ldtracker.security.RoleSecurity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResourceDto {
	private Long id;
	private String lastname;
	private String firstname;
	private String middlename;
	private String suffix;
	private String gender;
	private String emailAddress;
	private String password;
	private String careerStep;
	private String empId;
	private String region;
	private String team;
	private String status;
	private String skills;
	private Boolean isEnabled = false;
	@Enumerated(EnumType.STRING)
	private RoleSecurity role;
	private List<CertificationFileUpload> certifications;
}
