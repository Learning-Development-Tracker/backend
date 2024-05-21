package com.lps.ldtracker.service;

import java.util.List;
import java.util.Optional;

import com.lps.ldtracker.model.LoginRequest;
import com.lps.ldtracker.model.RegistrationRequest;
import com.lps.ldtracker.model.Result;
import com.lps.ldtracker.model.UserDetail;
import com.lps.ldtracker.model.UserDtl;

public interface UserDtlService {
	public List<UserDtl> getUserList();
	public Result registerUser(RegistrationRequest request);
	Optional<UserDtl> findByUserName(String username);
	public Result resetPassword(RegistrationRequest request);
	public List<UserDetail> getUserById(String id);
	public Result isExistUsername(LoginRequest request);
	public String verifyToken(String token);
	public String refreshToken(String username);
}
