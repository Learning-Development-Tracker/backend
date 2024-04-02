package com.lps.hris.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lps.hris.model.UserT;


public interface UserRepository extends JpaRepository<UserT, Integer> {

	Optional<UserT> findByEmail(String email);
	Optional<UserT> findByUsername(String username);
}
