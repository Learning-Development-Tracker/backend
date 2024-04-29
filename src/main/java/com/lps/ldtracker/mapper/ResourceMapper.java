package com.lps.ldtracker.mapper;

import com.lps.ldtracker.dto.ResourceDto;
import com.lps.ldtracker.entity.Resource;

public class ResourceMapper {
	public static Resource resourceMapper(ResourceDto resourceDto) {
		return new Resource(
					resourceDto.getId(),
					resourceDto.getLastname(),
					resourceDto.getFirstname(),
					resourceDto.getMiddlename(),
					resourceDto.getSuffix(),
					resourceDto.getGender(),
					resourceDto.getEmailAddress(),
					resourceDto.getPassword(),
					resourceDto.getCareerStep(),
					resourceDto.getEmpId(),
					resourceDto.getRegion(),
					resourceDto.getTeam(),
					resourceDto.getStatus(),
					resourceDto.getSkills(),
					resourceDto.isEnabled(),
					resourceDto.getRole()
				);
	}
	
	public static ResourceDto resourceMapperDto(Resource resource) {
		return new ResourceDto(
					resource.getId(),
					resource.getLastname(),
					resource.getFirstname(),
					resource.getMiddlename(),
					resource.getSuffix(),
					resource.getGender(),
					resource.getEmailAddress(),
					resource.getPassword(),
					resource.getCareerStep(),
					resource.getEmpId(),
					resource.getRegion(),
					resource.getTeam(),
					resource.getStatus(),
					resource.getSkills(),
					resource.isEnabled(),
					resource.getRole()
				);
	}
}
