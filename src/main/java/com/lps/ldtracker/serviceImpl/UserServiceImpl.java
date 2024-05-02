package com.lps.ldtracker.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lps.ldtracker.constants.LdTrackerConstants;
import com.lps.ldtracker.model.AuthenticationResponse;
import com.lps.ldtracker.model.LdTrackerError;
import com.lps.ldtracker.model.RegistrationRequest;
import com.lps.ldtracker.model.Result;
import com.lps.ldtracker.model.UserDtl;
import com.lps.ldtracker.model.UserT;
import com.lps.ldtracker.model.ValidationParamCollection;
import com.lps.ldtracker.repository.UserDtlRepository;
import com.lps.ldtracker.repository.UserRepository;
import com.lps.ldtracker.repository.VerificationTokenRepository;
import com.lps.ldtracker.security.RoleSecurity;
import com.lps.ldtracker.security.UserRegistrationDetails;
import com.lps.ldtracker.security.VerificationToken;
import com.lps.ldtracker.service.EmailService;
import com.lps.ldtracker.service.JwtService;
import com.lps.ldtracker.service.ResultService;
import com.lps.ldtracker.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	private final UserRepository userRepository;

	private final UserDtlRepository userDtlRepository;

	private final PasswordEncoder passwordEncoder;

	private final VerificationTokenRepository tokenRepo;

	private final JwtService jwtService;

	private final EmailService emailService;

	private final ErrorHandlingService errorService;

	private final ResultService resultService;

	@Override
	public List<UserT> getUserList() {
		return userRepository.findAll();
	}

//	@Override
//	public Result registerUser(RegistrationRequest request) { 
//		Result result = new Result();
//		Optional<UserT> user = this.findByemail(request.email());
//		List<LdTrackerError> errors = new ArrayList<>();
//		try {
//			
//			this.validateRegisterInputs(request, errors);
//			
//			if(!errors.isEmpty()) {
//				    result = resultService.setResult("200", LdTrackerConstants.SUCCESS, errors, null);
//		            return result;
//			}
//			
//			if(user.isPresent()) {
//				result.setMessage(LdTrackerConstants.USER_ALREADY_EXISTS);
//				result.setStatus(LdTrackerConstants.ERROR);
//				return result;
//			} else {
//				String otp = generateOtp();
//				var userBuilder = UserT
//					.builder()
//					.email(request.email())
//					.otp(otp)
//					.username(request.username())
//					.password(passwordEncoder.encode(request.password()))
//					.status(request.status())
//					.phoneNo(request.phoneNo())
//					.address(request.address())
//					.position(request.position())
//					.positionCode(request.positionCode())
//					.firstName(request.firstName())
//					.lastName(request.lastName())
//					.role(request.role())
//					.isEnabled(false)
//					.build();
//				
//				sendVerificationEmail(request.email(), otp);
//				userRepository.save(userBuilder);
//			
//				var jwtToken = jwtService.generateToken(userBuilder);
//				
//				AuthenticationResponse
//					.builder()
//					.token(jwtToken)
//					.build();
//				
//				var newUser = new UserT();
//				newUser.setEmail(request.email());
//				newUser.setUsername(request.username());
//				newUser.setPassword(passwordEncoder.encode(request.password()));
//				newUser.setRole(RoleSecurity.EMPLOYEE);
//
//				result.setData(userBuilder);
//				result.setMessage(LdTrackerConstants.SUCCESS);
//				result.setStatus(LdTrackerConstants.SUCCESS);
//				
//				return result;
//			} 
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error("ERROR registerUser: " + e.getMessage());
//
//		}
//		return result;
//	}

	@Override
	public Result registerUser(RegistrationRequest request) {
		Result result = new Result();
		Optional<UserDtl> user = this.findByUserName(request.username());
		List<LdTrackerError> errors = new ArrayList<>();
		try {

			this.validateRegisterInputs(request, errors);
			if (!errors.isEmpty()) {
				result = resultService.setResult("200", LdTrackerConstants.SUCCESS, errors, null);
				return result;
			}

			if (user.isPresent()) {
				result.setMessage(LdTrackerConstants.USER_ALREADY_EXISTS);
				result.setStatus(LdTrackerConstants.ERROR);
				return result;
			} else {
				var userBuilder = UserDtl.builder().userName(request.username()).userPass(request.password())
						.isActive(0).isDeleted(0).createdBy(RoleSecurity.EMPLOYEE.name())
						.createdDate(LocalDateTime.now()).build();
				userDtlRepository.save(userBuilder);

				result.setData(userBuilder);
				result.setMessage(LdTrackerConstants.SUCCESS);
				result.setStatus(LdTrackerConstants.SUCCESS);

				return result;
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ERROR registerUser: " + e.getMessage());

		}
		return result;
	}

	@Override
	public Optional<UserT> findByemail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public Optional<UserDtl> findByUserName(String username) {
		return userDtlRepository.findByUserName(username);
	}

	@Override
	public void saveUserVerificationToken(UserT user, String token) {
		var vToken = new VerificationToken(user, token);
		this.tokenRepo.save(vToken);

	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return userRepository.findByEmail(email).map(UserRegistrationDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("UserT not found"));
	}

	private String generateOtp() {
		Random random = new Random();
		int otpValue = 100000 + random.nextInt(900000);
		return String.valueOf(otpValue);
	}

	private void sendVerificationEmail(String email, String otp) {
		String subject = LdTrackerConstants.SUBJECT;
		String body = LdTrackerConstants.YOUR_OTP + otp;
		emailService.sendEmail(email, subject, body);
	}

	@Override
	public Result isVerified(String otp, String email) {
		Optional<UserT> user = this.findByemail(email);
		Result result = new Result();
		if (otp.equals(user.get().getOtp())) {
			var userBuilder = UserT.builder().email(user.get().getEmail()).otp(otp).username(user.get().getUsername())
					.password(user.get().getPassword()).role(user.get().getRole()).status(user.get().getStatus())
					.address(user.get().getAddress()).phoneNo(user.get().getPhoneNo())
					.position(user.get().getPosition()).positionCode(user.get().getPositionCode())
					.firstName(user.get().getFirstName()).lastName(user.get().getLastName()).id(user.get().getId())
					.isEnabled(true).build();
			userRepository.save(userBuilder);
			result.setData(true);
			result.setMessage(LdTrackerConstants.SUCCESS);
			result.setStatus(LdTrackerConstants.SUCCESS);
			return result;
		} else {
			result.setData(false);
			result.setMessage(LdTrackerConstants.ERROR);
			result.setStatus(LdTrackerConstants.ERROR);
		}
		return result;
	}

	private void validateRegisterInputs(RegistrationRequest param, List<LdTrackerError> errors) {
		for (ValidationParamCollection<String, String, String> tuple : this.getValidationParams(param)) {
			errorService.validateEmptyInputs(tuple.getFirst(), tuple.getSecond(), tuple.getThird(), errors);
		}
		// Additional specific validations
		errorService.validateEmail(param.email(), "Email", LdTrackerConstants.INVALID_EMAIL, errors);
		if (null != param.phoneNo() && !param.phoneNo().isEmpty()) {
			errorService.validateCharLength(param.phoneNo(), "Phone number", LdTrackerConstants.INVALID_PHONE_NO, 11,
					errors);
			errorService.validatePhoneNumber(param.phoneNo(), "Phone number", LdTrackerConstants.INVALID_PHONE_NO,
					errors);
		}

	}

	private List<ValidationParamCollection<String, String, String>> getValidationParams(RegistrationRequest param) {
		List<ValidationParamCollection<String, String, String>> validationTuples = new ArrayList<>();
		validationTuples
				.add(new ValidationParamCollection<>(param.address(), "Address", LdTrackerConstants.INVALID_ADDRESS));
		validationTuples.add(new ValidationParamCollection<>(param.email(), "Email", LdTrackerConstants.INVALID_EMAIL));
		validationTuples.add(
				new ValidationParamCollection<>(param.username(), "User Name", LdTrackerConstants.INVALID_USERNAME));
		validationTuples.add(
				new ValidationParamCollection<>(param.password(), "Password", LdTrackerConstants.INVALID_PASSWORD));
		validationTuples.add(
				new ValidationParamCollection<>(param.firstName(), "First Name", LdTrackerConstants.INVALID_FIRSTNAME));
		validationTuples.add(
				new ValidationParamCollection<>(param.lastName(), "Last Name", LdTrackerConstants.INVALID_LASTNAME));
		validationTuples.add(
				new ValidationParamCollection<>(param.phoneNo(), "Phone number", LdTrackerConstants.INVALID_PHONE_NO));
		validationTuples.add(
				new ValidationParamCollection<>(param.position(), "Position", LdTrackerConstants.INVALID_POSITION));
		validationTuples.add(new ValidationParamCollection<>(param.positionCode(), "Position Code",
				LdTrackerConstants.INVALID_POSITION_CODE));
		validationTuples.add(
				new ValidationParamCollection<>(String.valueOf(param.role()), "Role", LdTrackerConstants.INVALID_ROLE));

		return validationTuples;
	}

}
