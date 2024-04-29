package com.lps.ldtracker.model;

import java.sql.Date;
import java.util.Collection;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;

import com.lps.ldtracker.security.RoleSecurity;

@Entity 
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Training_Dtl {

	/**
	 * 
	 */
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "training_dtl")
	@SequenceGenerator(sequenceName = "training_seq", allocationSize = 1, name = "training_dtl")
	private Integer trainingId;
	@NaturalId(mutable=true)
	private String trainingName;
	private String trTypeID;
	private String productName;
	private Date startDate;
	private Date dueDate;
	private String preReq;
	private String description;
	private String link;
    private Boolean required;
    private Boolean certification;
    private String duration;
    private String fee;
    private Boolean approval;
    private String certId;
    private Boolean isActive;
    private Boolean isDeleted;        
    private String userRole;    
    private Date expiryDate;
    private String certLink;
    private String reqTraining;
    private String typeCert;
    private String type;
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;
	
}
