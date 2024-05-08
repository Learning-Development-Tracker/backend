package com.lps.ldtracker.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.apache.logging.log4j.message.StringFormattedMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lps.ldtracker.constants.LdTrackerConstants;
import com.lps.ldtracker.model.AuthenticationResponse;
import com.lps.ldtracker.model.LdTrackerError;
import com.lps.ldtracker.model.MemberDetail;
import com.lps.ldtracker.model.RegistrationRequest;
import com.lps.ldtracker.model.Result;
import com.lps.ldtracker.model.RoleDtl;
import com.lps.ldtracker.model.StatusDetail;
import com.lps.ldtracker.model.UserDtl;
import com.lps.ldtracker.model.ValidationParamCollection;
import com.lps.ldtracker.repository.MemberDtlRepository;
import com.lps.ldtracker.repository.RoleDtlRepository;
import com.lps.ldtracker.repository.StatusDtlRepository;
import com.lps.ldtracker.repository.UserDtlRepository;
import com.lps.ldtracker.repository.VerificationTokenRepository;
import com.lps.ldtracker.security.RoleSecurity;
import com.lps.ldtracker.security.UserRegistrationDetails;
import com.lps.ldtracker.security.VerificationToken;
import com.lps.ldtracker.service.JwtService;
import com.lps.ldtracker.service.ResultService;
import com.lps.ldtracker.service.UserDtlService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDtlServiceImpl implements UserDtlService, UserDetailsService{
	private static final Logger logger =   LoggerFactory.getLogger(UserDtlServiceImpl.class);

	
	private final UserDtlRepository userDtlRepository;
	
	private final MemberDtlRepository memmberDtlRepository;
	
	private final StatusDtlRepository statusDtlRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	private final VerificationTokenRepository tokenRepo;
	
	private final JwtService jwtService;
	
	private final ErrorHandlingService errorService;
	
	private final ResultService resultService;

	@Override
	public List<UserDtl> getUserList() { 
		return userDtlRepository.findAll();
	}

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
				StatusDetail statusDtl = statusDtlRepository.findById("1")
						.orElseThrow(() -> new NotFoundException());
				var userBuilder = UserDtl.builder()
						.userName(request.username())
						.userPass(passwordEncoder.encode(request.password()))
						.statusDtl(statusDtl).role(RoleSecurity.ADMIN)
						.isActive(1).isDeleted(0)
						.createdDate(LocalDateTime.now()).build();
				userDtlRepository.save(userBuilder);

				var memberBuilder = MemberDetail.builder()
						.firstName(request.firstName())
						.lastName(request.lastName())
						.employeeNum(new Random().nextInt(Integer.MAX_VALUE))
						.emailAddress(request.email())
						.careerLevelId("").teamId("")
						.statusId("").build();
				memmberDtlRepository.save(memberBuilder);
						
				var jwtToken = jwtService.generateToken(userBuilder);
				
				AuthenticationResponse
				.builder()
				.token(jwtToken)
				.build();
				
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
	public void saveUserVerificationToken(UserDtl user, String token) {
		var vToken = new VerificationToken(user, token);  
		this.tokenRepo.save(vToken);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException { 
		return userDtlRepository.findByUserName(email)
			.map(UserRegistrationDetails::new)
			.orElseThrow(()-> new UsernameNotFoundException("UserDtl not found"));
	}

	@Override
	public Optional<UserDtl> findByUserName(String username) {
		return userDtlRepository.findByUserName(username);
	}

	private void validateRegisterInputs(RegistrationRequest param, List<LdTrackerError> errors) {
	    for (ValidationParamCollection<String, String, String> tuple : this.getValidationParams(param)) {
	        errorService.validateEmptyInputs(tuple.getFirst(), tuple.getSecond(), tuple.getThird(), errors);
	    }
	    // Additional specific validations
	    errorService.validateEmail(param.email(), "Email", LdTrackerConstants.INVALID_EMAIL, errors);
	    if(null != param.phoneNo() && !param.phoneNo().isEmpty()) {
	    	errorService.validateCharLength(param.phoneNo(), "Phone number", LdTrackerConstants.INVALID_PHONE_NO, 11, errors);
		    errorService.validatePhoneNumber(param.phoneNo(), "Phone number", LdTrackerConstants.INVALID_PHONE_NO, errors);
	    }
	    
	}
	
	private List<ValidationParamCollection<String, String, String>> getValidationParams(RegistrationRequest param) {
		List<ValidationParamCollection<String, String, String>> validationTuples = new ArrayList<>();
		validationTuples.add(new ValidationParamCollection<>(param.address(), "Address", LdTrackerConstants.INVALID_ADDRESS));
	    validationTuples.add(new ValidationParamCollection<>(param.email(), "Email", LdTrackerConstants.INVALID_EMAIL));
	    validationTuples.add(new ValidationParamCollection<>(param.username(), "User Name", LdTrackerConstants.INVALID_USERNAME));
	    validationTuples.add(new ValidationParamCollection<>(param.password(), "Password", LdTrackerConstants.INVALID_PASSWORD));
	    validationTuples.add(new ValidationParamCollection<>(param.firstName(), "First Name", LdTrackerConstants.INVALID_FIRSTNAME));
	    validationTuples.add(new ValidationParamCollection<>(param.lastName(), "Last Name", LdTrackerConstants.INVALID_LASTNAME));
	    validationTuples.add(new ValidationParamCollection<>(param.phoneNo(), "Phone number", LdTrackerConstants.INVALID_PHONE_NO));
	    validationTuples.add(new ValidationParamCollection<>(param.position(), "Position", LdTrackerConstants.INVALID_POSITION));
	    validationTuples.add(new ValidationParamCollection<>(param.positionCode(), "Position Code", LdTrackerConstants.INVALID_POSITION_CODE));
	    validationTuples.add(new ValidationParamCollection<>(String.valueOf(param.role()), "Role", LdTrackerConstants.INVALID_ROLE));

		return validationTuples;
	}
	
	
}
