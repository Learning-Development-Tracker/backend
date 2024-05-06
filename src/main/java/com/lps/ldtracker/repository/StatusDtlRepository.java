package com.lps.ldtracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lps.ldtracker.model.StatusDetail;

public interface StatusDtlRepository extends  JpaRepository<StatusDetail, String>{

}
