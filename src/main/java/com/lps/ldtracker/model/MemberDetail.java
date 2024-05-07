package com.lps.ldtracker.model;

import java.util.Date;

import org.hibernate.annotations.NaturalId;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Table(name = "member_dtl")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "memberdtl")
	@SequenceGenerator(sequenceName = "SEQ_MEMBER_DTL", allocationSize = 1, name = "memberdtl")
	@Column(name = "member_id")
	private Integer memberDetailId;
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
	@OneToOne(mappedBy = "memberDtl", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
	private UserDtl user;
}
