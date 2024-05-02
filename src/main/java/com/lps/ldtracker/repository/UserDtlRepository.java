package com.lps.ldtracker.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lps.ldtracker.model.UserDtl;


public interface UserDtlRepository extends JpaRepository<UserDtl, UUID> {
	Optional<UserDtl> findByUserName(String username);
}
