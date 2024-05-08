package com.lps.ldtracker.model;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "status_dtl")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatusDetail {

	@Id
	@Column(name  = "status_id")
	private String statusId;
	private Integer isActive;
	private String statusName;
	private String description;
	private String type;
	private Integer isDeleted;
	private String createdBy;
	private LocalDateTime createdDate;
	private String updatedBy;
	private LocalDateTime updatedDate;
	@OneToOne(mappedBy = "statusDtl", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
	private UserDtl user;
}
