package com.lps.hris.serviceImpl;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lps.hris.constants.HrisConstants;
import com.lps.hris.model.ForgotPasswordRequest;
import com.lps.hris.model.OTP;
import com.lps.hris.model.OTPVerificationRequest;
import com.lps.hris.model.Result;
import com.lps.hris.model.UpdatePasswordRequest;
import com.lps.hris.model.UserT;
import com.lps.hris.repository.OTPRepository;
import com.lps.hris.repository.UserRepository;
import com.lps.hris.service.UserForgotPasswordService;
import com.lps.hris.model.HrisError;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserForgotPasswordImpl implements UserForgotPasswordService{
	private static final Logger logger =   LoggerFactory.getLogger(UserForgotPasswordImpl.class);
	private static final String statusCode = "200";

	 
	private final UserRepository userRepository;
	 
	private final PasswordEncoder passwordEncoder;
	
    private final JavaMailSender emailSender;
    
    private final OTPRepository otpRepository;
    
    private final ErrorHandlingService errorHandlingService;

	@Override
	public Optional<UserT> findByemail(String email) { 
		return userRepository.findByEmail(email);
	}
	
	@Override
	public Optional<OTP> findOTPByemail(String email) { 
		return otpRepository.findByEmail(email);
	}
	
	public String generateOTP() {
		SecureRandom random = new SecureRandom();
		int otp = 100000 + random.nextInt(900000);
		
		return String.valueOf(otp);
	}
	
	private Result setErrorResult(Result result, String message, Boolean data, String errCode, String errMsg) {
		HrisError error = new HrisError(HrisConstants.ERROR_OCCURED, errCode);
		List<HrisError> errorList = new ArrayList<>();
		errorList.add(error);
	    result.setMessage(HrisConstants.ERROR);
	    result.setStatus(message);
	    result.setData(data);
	    result.setErrors(errorList);
	    
	    return result;
	}

	private Result setSuccessResult(Result result, String message, Boolean data) {
	    result.setMessage(HrisConstants.SUCCESS);
	    result.setStatus(message);
	    result.setData(data);
	    return result;
	}
	
	@Override
	public Result sendOTP(ForgotPasswordRequest request) {
	    Result result = new Result();
	    Optional<UserT> user = this.findByemail(request.email());

	    try {
	        if (!user.isPresent()) {
	            return setErrorResult(result, statusCode, false, HrisConstants.USER_DOES_NOT_EXISTS, HrisConstants.ERROR_OCCURED);
	        }

	        OTP newOtp = generateAndSaveOTP(request.email());
	        sendOTPEmail(request.email(), newOtp.getOtp());

	        return setSuccessResult(result, HrisConstants.SUCCESS, true);
	    } catch (MailException mailException) {
	    	mailException.printStackTrace();
	        return setErrorResult(result, statusCode, false,  HrisConstants.AUTH_FAILED, HrisConstants.ERROR_OCCURED);
	    } catch (Exception e) {
	    	e.printStackTrace();
	        return setErrorResult(result, statusCode, false, e.getMessage(), HrisConstants.ERROR_OCCURED);
	    }
	}

	private OTP generateAndSaveOTP(String email) {
	    OTP newOtp = new OTP();
	    newOtp.setOtp(this.generateOTP());
	    newOtp.setEmail(email);
	    newOtp.setCreatedAt(LocalDateTime.now());
	    newOtp.setExpiresAt(LocalDateTime.now().plusMinutes(3));
	    newOtp.setIsValid(0);
	    this.deleteOTPByEmail(email);
	    return otpRepository.save(newOtp);
	}

	private void sendOTPEmail(String email, String otp) {
	    SimpleMailMessage mailMessage = new SimpleMailMessage();
	    mailMessage.setTo(email);
	    mailMessage.setSubject("Your OTP for password reset");
	    mailMessage.setText("Your OTP is: " + otp);
	    emailSender.send(mailMessage);
	    logger.info("OTP sent to: {}", email);
	}
	
	private void deleteOTPByEmail(String email) {
		Optional<OTP> exisitingOTP = otpRepository.findByEmail(email);
	    exisitingOTP.ifPresent(otpRepository::delete);
	}
	
	@Override
	public Result verifyOtp(OTPVerificationRequest request) {
	    Result result = new Result();
	    Optional<OTP> otp = this.findOTPByemail(request.email());

	    try {
	        if (this.isEmailOrOtpMissing(request)) {
	            return setErrorResult(result, statusCode, false, HrisConstants.EMPTY_FIELD, HrisConstants.MISSING_PARAMETERS);
	        }

	        if (this.isEmailOrOtpEmpty(request)) {
	            return setErrorResult(result, statusCode, false, HrisConstants.EMPTY_FIELD, HrisConstants.MISSING_PARAMETERS);
	        }

	        if (!otp.isPresent()) {
	            return setErrorResult(result, statusCode, false, HrisConstants.YOU_HAVE_NOT_SENT_OTP, HrisConstants.ERROR_OCCURED);
	        }

	        if (this.isExpiredOtp(otp.get(), request.otp())) {
	            return setErrorResult(result, statusCode, false, HrisConstants.EXPIRED_OTP, HrisConstants.ERROR_OCCURED);
	        }

	        if (!this.isValidOtp(otp.get(), request.otp())) {
	            return setErrorResult(result, statusCode, false, HrisConstants.INVALID_OTP, HrisConstants.ERROR_OCCURED);
	        }

	        OTP newOtp = otp.get();
	        newOtp.setIsValid(1);
	        otpRepository.save(newOtp);
	        return this.setSuccessResult(result, HrisConstants.SUCCESS, true);

	    } catch (RuntimeException e) {
	        e.printStackTrace();
	        logger.debug("ERROR verifyOtp: {} ", e.getMessage());
	        return this.setErrorResult(result, statusCode, false, e.getMessage(), HrisConstants.ERROR_OCCURED);
	    }
	}

	private boolean isEmailOrOtpMissing(OTPVerificationRequest request) {
	    return request.otp() == null || request.email() == null;
	}

	private boolean isEmailOrOtpEmpty(OTPVerificationRequest request) {
	    return request.email().isEmpty() || request.otp().isEmpty();
	}

	private boolean isExpiredOtp(OTP otp,String providedOtp) {
	    return otp.getExpiresAt().isBefore(LocalDateTime.now()) && otp.getOtp().equals(providedOtp);
	}

	private boolean isValidOtp(OTP otp, String providedOtp) {
	    return otp.getOtp().equals(providedOtp);
	}
	
	@Override
	public Result updatePassword(UpdatePasswordRequest request) {
	    Result result = new Result();

	    try {
	        UserT user = this.findByemail(request.email())
	                            .orElseThrow(() -> new UsernameNotFoundException("UserT not found"));

	        Optional<OTP> otp = this.findOTPByemail(request.email());
	        
	        if (!otp.isPresent()) {
	            return setErrorResult(result, statusCode, false, HrisConstants.YOU_HAVE_NOT_SENT_OTP, HrisConstants.ERROR_OCCURED);
	        }
	        
	        if (this.isEmailOrOtpOrPwMissing(request)) {
	        	return setErrorResult(result, statusCode, false, HrisConstants.REQ_FIELD_MISSING, HrisConstants.MISSING_PARAMETERS);
	        }
	        
	        if (this.isPasswordInvalid(request.password())) {
	        	return setErrorResult(result, statusCode, false, HrisConstants.REQ_PASSWORD_LENGTH, HrisConstants.ERROR_OCCURED);
	        }

	        if (this.isInvalidOtp(otp.get(), request)) {
	            return setErrorResult(result, statusCode, false, HrisConstants.INVALID_OTP, HrisConstants.ERROR_OCCURED);
	        }

	        user.setPassword(passwordEncoder.encode(request.password()));
	        userRepository.save(user);
	        this.deleteOTPByEmail(request.email());

	        return this.setSuccessResult(result, HrisConstants.SUCCESS_PASSWORD_UPDATE, true);
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        logger.error("ERROR : {}", e.getMessage());
	        return this.setErrorResult(result, statusCode, false, e.getMessage(), HrisConstants.ERROR_OCCURED);
	    }
	}
	
	private boolean isPasswordInvalid(String password) {
	    return password != null && 
	    		(password.length() < HrisConstants.MIN_PASSWORD_LENGTH || 
	    				password.length() > HrisConstants.MAX_PASSWORD_LENGTH);
	}

	private boolean isInvalidOtp(OTP otp, UpdatePasswordRequest request) {
	    return !otp.getOtp().equals(request.otp()) ||
	            request.otp() == null || request.otp().isEmpty() ||
	            otp.getIsValid() == 0;
	}
	
	private boolean isEmailOrOtpOrPwMissing(UpdatePasswordRequest request) {
	    return request.otp() == null || request.email() == null || request.password() == null;
	}

}
