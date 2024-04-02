package com.lps.hris.service;

import java.util.List;
import java.util.Optional;

import com.lps.hris.model.RegistrationRequest;
import com.lps.hris.model.Result;
import com.lps.hris.model.UserT;

public interface UserService {
	public List<UserT> getUserList();
	public Result registerUser(RegistrationRequest request);
	public Optional<UserT> findByemail(String email);
	public void saveUserVerificationToken(UserT user, String token);
	public Result isVerified(String otp, String email);

}
