package com.lps.ldtracker.model;

import java.util.Collection;

import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;

import com.lps.ldtracker.security.RoleSecurity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class SkillSet {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "skillset")
	@SequenceGenerator(sequenceName = "skillset_seq", allocationSize = 1, name = "skillset")
	private String skillSetId;
	@NaturalId(mutable=true)
	private String type;
	private String memberId;
	private String trainingId;
	private String skillDtlId;
	private boolean isDeleted=false;
	private String createdBy;
	private String createdDate;
	private String updatedBy;
	private String updatedDate;

}
