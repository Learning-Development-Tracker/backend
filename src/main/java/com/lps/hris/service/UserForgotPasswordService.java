package com.lps.hris.service;

import java.util.Optional;

import com.lps.hris.model.ForgotPasswordRequest;
import com.lps.hris.model.OTP;
import com.lps.hris.model.OTPVerificationRequest;
import com.lps.hris.model.Result;
import com.lps.hris.model.UpdatePasswordRequest;
import com.lps.hris.model.UserT;

public interface UserForgotPasswordService {

	Optional<UserT> findByemail(String email);
	Result sendOTP(ForgotPasswordRequest request);
	Result verifyOtp(OTPVerificationRequest request);
	Optional<OTP> findOTPByemail(String email);
	Result updatePassword(UpdatePasswordRequest request);
}
