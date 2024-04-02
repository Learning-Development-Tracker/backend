package com.lps.hris.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lps.hris.security.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

}
