package com.lps.ldtracker.model;

import java.util.Date;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Parameter;

import com.lps.ldtracker.service.StringPrefixedSequenceIdGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_member_dtl")
    @GenericGenerator(
        name = "seq_member_dtl", 
        strategy = "com.lps.ldtracker.service.StringPrefixedSequenceIdGenerator", 
        parameters = {
            @Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
            @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "LPS2024"),
            @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%09d") })
	@Column(name = "member_id")
	private String memberDetailId;
	@NaturalId(mutable=true)
	private String firstName;
	private String lastName;
	private Integer employeeNum;
	private String emailAddress;
	private Boolean isDeleted;
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
