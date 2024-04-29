package com.lps.ldtracker.serviceImpl;

import org.springframework.stereotype.Service;

import com.lps.ldtracker.dto.ResourceDto;
import com.lps.ldtracker.entity.Resource;
import com.lps.ldtracker.mapper.ResourceMapper;
import com.lps.ldtracker.repository.ResourceRepository;
import com.lps.ldtracker.service.ResourceService;

@Service
public class ResourceImpl implements ResourceService{
	private ResourceRepository resourceRepository;
	
	public ResourceImpl(ResourceRepository resourceRepository) {
		this.resourceRepository = resourceRepository;
	}
	
	@Override
	public ResourceDto addResource(ResourceDto resourceDto) {
		Resource resource = ResourceMapper.resourceMapper(resourceDto);
		Resource saveResource = resourceRepository.save(resource);
		return ResourceMapper.resourceMapperDto(saveResource);
	}
	
}
