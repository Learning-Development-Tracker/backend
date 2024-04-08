package com.lps.ldtracker.service;

import java.util.Optional;

import com.lps.ldtracker.model.ForgotPasswordRequest;
import com.lps.ldtracker.model.OTP;
import com.lps.ldtracker.model.OTPVerificationRequest;
import com.lps.ldtracker.model.Result;
import com.lps.ldtracker.model.UpdatePasswordRequest;
import com.lps.ldtracker.model.UserT;

public interface UserForgotPasswordService {

	Optional<UserT> findByemail(String email);
	Result sendOTP(ForgotPasswordRequest request);
	Result verifyOtp(OTPVerificationRequest request);
	Optional<OTP> findOTPByemail(String email);
	Result updatePassword(UpdatePasswordRequest request);
}
