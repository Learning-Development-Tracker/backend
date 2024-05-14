package com.lps.ldtracker.service;

import java.util.List;
import java.util.Optional;

import com.lps.ldtracker.model.RegistrationRequest;
import com.lps.ldtracker.model.Result;
import com.lps.ldtracker.model.UserDtl;
import com.lps.ldtracker.model.UserDetail;

public interface UserDtlService {
	public List<UserDtl> getUserList();
	public Result registerUser(RegistrationRequest request);
	public void saveUserVerificationToken(UserDtl user, String token);
	Optional<UserDtl> findByUserName(String username);
	public Result resetPassword(RegistrationRequest request);
	public List<UserDetail> getUserById(String id);
}
