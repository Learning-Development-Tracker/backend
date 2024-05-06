package com.lps.ldtracker.model;

import java.time.LocalDateTime;

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

@Data
@Entity
@Table(name = "role_dtl")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleDtl {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roledtl")
	@SequenceGenerator(sequenceName = "seq_role_dtl", allocationSize = 1, name = "roledtl")
	private String roleId;
	private Integer active;
	private String roleName;
	private String roleDesc;
	private Integer isDeleted;
	private String createdBy;
	private LocalDateTime createdDate;
	private String updatedBy;
	private LocalDateTime updatedDate;
	
}
