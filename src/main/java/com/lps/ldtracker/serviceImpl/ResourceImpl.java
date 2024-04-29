package com.lps.ldtracker.serviceImpl;

import java.security.SecureRandom;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lps.ldtracker.constants.LdTrackerConstants;
import com.lps.ldtracker.dto.ResourceDto;
import com.lps.ldtracker.entity.Resource;
import com.lps.ldtracker.mapper.ResourceMapper;
import com.lps.ldtracker.model.Result;
import com.lps.ldtracker.repository.ResourceRepository;
import com.lps.ldtracker.service.ResourceService;

@Service
public class ResourceImpl implements ResourceService{
	private static final Logger logger =   LoggerFactory.getLogger(ResourceImpl.class);
	
	 private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	 private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
	 private static final String DIGITS = "0123456789";
	 private static final String SPECIAL_CHARS = "!@#$%^&*()-_=+<>?";
	 private static final String PASSWORD_CHARS = UPPERCASE + LOWERCASE + DIGITS + SPECIAL_CHARS;
	 private static final SecureRandom RANDOM = new SecureRandom();
	
	private ResourceRepository resourceRepository;
	private final PasswordEncoder passwordEncoder;
	
	public ResourceImpl(ResourceRepository resourceRepository, PasswordEncoder passwordEncoder) {
		this.resourceRepository = resourceRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	
	public static String generatePassword() {
        return IntStream.range(0, 8)
                        .map(i -> PASSWORD_CHARS.charAt(RANDOM.nextInt(PASSWORD_CHARS.length())))
                        .mapToObj(c -> String.valueOf((char) c))
                        .collect(Collectors.joining());
    }
	
	@Override
	public Optional<Resource> findByEmailAddress(String email) { 
		return resourceRepository.findByEmailAddress(email);
	}
	
	@Override
	public Result addResource(ResourceDto resourceDto) {
		try {
			Result result = new Result();
			Optional <Resource>_resource = this.findByEmailAddress(resourceDto.getEmailAddress());
			
			if (_resource.isPresent()) {
				result.setMessage(LdTrackerConstants.USER_ALREADY_EXISTS);
				result.setStatus(LdTrackerConstants.ERROR);
				return result;
			}
			
			String generatedPassword = passwordEncoder.encode(ResourceImpl.generatePassword());
			Resource resource = ResourceMapper.resourceMapper(resourceDto, generatedPassword);
			Resource saveResource = resourceRepository.save(resource);
			
			result.setData(ResourceMapper.resourceMapperDto(saveResource));
			result.setMessage(LdTrackerConstants.SUCCESS);
			result.setStatus(LdTrackerConstants.SUCCESS);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ERROR add resource: " + e.getMessage());
			throw e;
		}
	}
	
	
}
