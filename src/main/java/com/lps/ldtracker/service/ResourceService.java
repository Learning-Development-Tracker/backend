package com.lps.ldtracker.service;

import java.util.Optional;

import com.lps.ldtracker.dto.ResourceDto;
import com.lps.ldtracker.entity.Resource;
import com.lps.ldtracker.model.Result;

public interface ResourceService {
	Result addResource(ResourceDto resourceDto);

	Optional<Resource> findByEmailAddress(String emailAddress);
}
