package com.lps.ldtracker.dto;

import java.sql.Date;

import org.hibernate.annotations.NaturalId;
import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class ManageTrainingDto {	

		private String Id;	
		private String trainingName;
		private String trainingType;
		private String productName;
		private Date startDate;
		private Date dueDate;
		private String preReq;
		private String description;
		private String trainingLink;
		private String trainingTags;  
	    private Boolean isRequired;
	    private Boolean certification;
	    private Integer trCondition;
	    private String trConditionValue;
	    private boolean active;	    
	    private String certID;
	    private String certName;
	    private String duration;
	    private Integer fee;
	    private String currency;
	    private String certLink;
	    private Date expiryDate;
	    private Boolean renewable;
	    private String skillID;
	    private String createdBy;
    

}
