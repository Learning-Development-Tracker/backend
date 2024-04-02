package com.lps.hris.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lps.hris.model.OTP;

public interface OTPRepository extends JpaRepository<OTP, Long>{
	Optional<OTP> findByEmail(String email);
}
