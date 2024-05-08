package com.lps.ldtracker.dto;

import java.sql.Date;

import org.hibernate.annotations.NaturalId;
import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class ManageTrainingDto {	

	private Integer Id;	
	private String trainingname;
	private String trainingtype;
	private String productname;
	private Date startdate;
	private Date duedate;
	private String prereq;
	private String description;
	private String traininglink;
	private String trainingtags;  
    private Boolean isrequired;
    private Boolean certification;
    private String certificationname;
    private String duration;
    private String fee;
    private String certlink;
    private boolean trcondition;
    private String trconditionValue;
    private boolean active;
    private String expirydate;
    

}
