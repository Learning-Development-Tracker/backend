package com.lps.ldtracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lps.ldtracker.entity.Resource;

public interface ResourceRepository extends JpaRepository<Resource, Long>{
	
}
