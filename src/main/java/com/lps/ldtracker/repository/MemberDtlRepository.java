package com.lps.ldtracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lps.ldtracker.model.MemberDetail;

public interface MemberDtlRepository extends JpaRepository<MemberDetail, Integer> {
	
}