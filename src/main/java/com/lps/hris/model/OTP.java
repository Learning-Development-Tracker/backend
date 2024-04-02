package com.lps.hris.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OTP {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "otp")
	@SequenceGenerator(sequenceName = "otp_seq", allocationSize = 1, name = "otp")
	private int id;
	@Column(name = "otp")
	private String otp;
	private String email;
	@CreationTimestamp
	private LocalDateTime createdAt;
	private LocalDateTime expiresAt;
	@Column(name = "is_valid")
    private Integer isValid;
}
