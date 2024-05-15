package com.lps.ldtracker.service;

import java.util.List;
import java.util.Optional;

import com.lps.ldtracker.dto.ResourceDto;
import com.lps.ldtracker.entity.Resource;
import com.lps.ldtracker.model.Result;

public interface ResourceService {
	Result addResource(ResourceDto resourceDto);

	Optional<Resource> findByEmailAddress(String emailAddress);
	
	Result editResource(Long id, ResourceDto resourceDto);
	Result viewResource(Long id);
	List<ResourceDto> getAllResources();

	Optional<Resource> findById(Long id);
}
