package com.lps.ldtracker.service;

import java.util.List;
import java.util.Optional;

import com.lps.ldtracker.model.RegistrationRequest;
import com.lps.ldtracker.model.Result;
import com.lps.ldtracker.model.UserT;

public interface UserService {
	public List<UserT> getUserList();
	public Result registerUser(RegistrationRequest request);
	public Optional<UserT> findByemail(String email);
	public void saveUserVerificationToken(UserT user, String token);
	public Result isVerified(String otp, String email);

}
