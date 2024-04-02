package com.lps.hris.security;

import java.util.Calendar;
import java.util.Date;

import com.lps.hris.model.UserT;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
public class VerificationToken {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "verify_token")
	@SequenceGenerator(sequenceName = "verification_token_seq", allocationSize = 1, name = "verify_token")
	private Integer id;
	private String token;
	private Date expirationTime;
	private static final int EXPIRATION_TIME=15;
	@OneToOne
	@JoinColumn(name="id")
	private UserT user;
	
	public VerificationToken(UserT user,String token) {
		super();
		this.token = token;
		this.user = user;
		this.expirationTime = this.getTokenExpirationTime();
	}

	public VerificationToken(String token) {
		super();
		this.token = token; 
		this.expirationTime = this.getTokenExpirationTime();
	}
	
	private Date getTokenExpirationTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(new Date().getTime());
		calendar.add(Calendar.MINUTE,EXPIRATION_TIME);
		return new Date(calendar.getTime().getTime());
	}
	
	

}
