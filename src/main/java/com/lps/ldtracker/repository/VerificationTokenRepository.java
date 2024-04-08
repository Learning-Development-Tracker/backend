package com.lps.ldtracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lps.ldtracker.security.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

}
