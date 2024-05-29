package com.lps.ldtracker.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class ManageTrainingDto {	

	private String Id;	
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
