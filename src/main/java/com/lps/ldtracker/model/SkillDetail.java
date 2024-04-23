package com.lps.ldtracker.model;

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
public class SkillDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "skilldtl")
	@SequenceGenerator(sequenceName = "skilldtl_seq", allocationSize = 1, name = "skilldtl")
	private String skillDetailId;
	@NaturalId(mutable=true)
	private String skillName;
	private String description;
	private boolean isActive=false;
	private String createdBy;
	private String createdDate;
	private String updatedBy;
	private String updatedDate;
}
