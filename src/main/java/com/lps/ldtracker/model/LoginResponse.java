/**
 * 
 */
package com.lps.ldtracker.model;

import java.time.LocalDateTime;

import lombok.Builder;

/**
 * Login response record
 * @author 81258493
 * @version 1.0
 */
@Builder
public record LoginResponse(
		String userId, Integer isActive, String userName, String userPass, Integer isDeleted, String token,
		String createdBy, LocalDateTime createdDate, String updatedBy, LocalDateTime updatedDate, String accessName
		) {
	
}
