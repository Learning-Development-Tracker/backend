package com.lps.ldtracker.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.NaturalId;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Table(name = "access_level")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccessLevel {

	@Id
	@Column(name = "al_id")
	private String alId;
	@NaturalId(mutable=true)
	private String alName;
	private String alDesc;
	private Boolean isDeleted;
	private String createdBy;
	private LocalDateTime createdDate;
	private String updatedBy;
	private LocalDateTime updatedDate;
}
