package com.lps.ldtracker.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.procedure.ProcedureCall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.lps.ldtracker.configuration.RealSessionAware;

import com.lps.ldtracker.constants.LdTrackerConstants;
import com.lps.ldtracker.model.AuthenticationResponse;
import com.lps.ldtracker.model.LdTrackerError;
import com.lps.ldtracker.model.LoginRequest;
import com.lps.ldtracker.model.MemberDetail;
import com.lps.ldtracker.model.RegistrationRequest;
import com.lps.ldtracker.model.Result;
import com.lps.ldtracker.model.StatusDetail;
import com.lps.ldtracker.model.UserDetail;
import com.lps.ldtracker.model.UserDtl;
import com.lps.ldtracker.model.ValidationParamCollection;
import com.lps.ldtracker.repository.MemberDtlRepository;
import com.lps.ldtracker.repository.StatusDtlRepository;
import com.lps.ldtracker.repository.UserDtlRepository;
import com.lps.ldtracker.repository.VerificationTokenRepository;
import com.lps.ldtracker.security.RoleSecurity;
import com.lps.ldtracker.security.UserRegistrationDetails;
import com.lps.ldtracker.security.VerificationToken;
import com.lps.ldtracker.service.JwtService;
import com.lps.ldtracker.service.ResultService;
import com.lps.ldtracker.service.UserDtlService;


import jakarta.persistence.ParameterMode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDtlServiceImpl implements UserDtlService, UserDetailsService, RealSessionAware{
	private static final Logger logger =   LoggerFactory.getLogger(UserDtlServiceImpl.class);

	
	private final UserDtlRepository userDtlRepository;
	
	private final MemberDtlRepository memberDtlRepository;
	
	private final StatusDtlRepository statusDtlRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	private final VerificationTokenRepository tokenRepo;
	
	private final JwtService jwtService;
	
	private final ErrorHandlingService errorService;
	
	private final ResultService resultService;
	private static final String SP_GETUSERINFO = "sp_getUserInfo";
	@Autowired
	SessionFactory sessionFactory;
	
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
				result = resultService.setResult("400", LdTrackerConstants.ERROR_OCCURED, errors, null);
				return result;
			}

			if (user.isPresent()) {
				result.setMessage(LdTrackerConstants.USER_ALREADY_EXISTS);
				result.setStatus(LdTrackerConstants.ERROR);
				return result;
			} else {
				StatusDetail statDtl = statusDtlRepository
						.findAll().stream().findFirst()
						.orElseThrow(() -> new NotFoundException());
				var memberBuilder = MemberDetail.builder()
						.firstName(request.firstName())
						.lastName(request.lastName())
						.employeeNum(new Random().nextInt(Integer.MAX_VALUE))
						.emailAddress(request.email())
						.careerLevelId("").teamId("")
						.statusId("").build();
				MemberDetail mbrDtl = memberDtlRepository.save(memberBuilder);
				var userBuilder = UserDtl.builder()
						.userName(request.username())
						.userPass(passwordEncoder.encode(request.password()))
						.statusDtl(statDtl).memberDtl(mbrDtl).role(RoleSecurity.ADMIN)
						.isActive(1).isDeleted(0)
						.createdDate(LocalDateTime.now()).build();
				userDtlRepository.save(userBuilder);						
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
	public Result isExistUsername(LoginRequest request) {
		Result result = new Result();
		List<LdTrackerError> errors = new ArrayList<>();
		Boolean uDtl = userDtlRepository.existsByUserName(request.getUsername());
		this.validateUsernameInput(request, errors);
		if (!errors.isEmpty()) {
			result = resultService.setResult("400", LdTrackerConstants.ERROR, errors, null);
			return result;
		}
		if(uDtl) {
			result.setData(uDtl);
			result.setMessage(LdTrackerConstants.USER_DOES_EXISTS);
			result.setStatus(LdTrackerConstants.SUCCESS);
		} else {
			result.setData(uDtl);
			result.setMessage(LdTrackerConstants.USER_DOES_NOT_EXISTS);
			result.setStatus(LdTrackerConstants.ERROR);						
		}		
		return result ;
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException { 
		return userDtlRepository.findByUserName(email)
			.map(UserRegistrationDetails::new)
			.orElseThrow(()-> new UsernameNotFoundException(LdTrackerConstants.USER_DOES_NOT_EXISTS));
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
	
	private void validateUsernameInput(LoginRequest param, List<LdTrackerError> errors) {
		if(!param.getUsername().isEmpty()) {
			errorService.validateUserName(param.getUsername(), LdTrackerConstants.INVALID_USERNAME, errors);
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
	

	@Override
	public Result resetPassword(RegistrationRequest request) {
		Result result = new Result();
		List<LdTrackerError> errors = new ArrayList<LdTrackerError>();
		Optional<UserDtl> user = this.findByUserName(request.username());
			if (!user.isPresent()) {
				errors.add(new LdTrackerError(LdTrackerConstants.INVALID_USERNAME, LdTrackerConstants.USER_DOES_NOT_EXISTS));
				result.setErrors(errors);
				result.setStatus(LdTrackerConstants.ERROR);
				return result;
			} else {
				try {
					UserDtl userDtl = user.get();
					userDtl.setUserPass(passwordEncoder.encode(request.password()));
					userDtl.setUpdatedDate(LocalDateTime.now());
					result.setData(userDtl);
					result.setMessage(LdTrackerConstants.SUCCESS_PASSWORD_UPDATE);
					result.setStatus(LdTrackerConstants.SUCCESS);
					userDtlRepository.save(userDtl);
					
					var jwtToken = jwtService.generateToken(userDtl);
					
					AuthenticationResponse
					.builder()
					.token(jwtToken)
					.build();
					
					return result;
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("ERROR resetPassword: " + e.getMessage());
				}
			}

		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<UserDetail> getUserById(String id) {

	    List<UserDetail> resList = new ArrayList<UserDetail>();
	    try  {
	    		Session session = getRealSession(sessionFactory);
	            ProcedureCall storedProcedureCall = session.createStoredProcedureCall(SP_GETUSERINFO);
	            storedProcedureCall.registerStoredProcedureParameter("MemberID", String.class, ParameterMode.IN);
	            storedProcedureCall.setParameter("MemberID", id);
	            List<Object[]> recordList = storedProcedureCall.getResultList();
	                recordList.forEach(result -> {
	                    UserDetail res = new UserDetail();
	                    // Map the retrieved attributes to UserDetail object
	                    res.setLastName((String) result[0]);
	                    res.setFirstName((String) result[1]);
	                    res.setMiddleName((String) result[2]);
	                    res.setSuffix((String) result[3]);
	                    res.setGender((String) result[8]);
	                    res.setEmailAddress((String) result[9]);
	                    res.setCareerStep((String) result[10]);
	                    res.setEmployeeID((int) result[11]);
	                    res.setRegion((String) result[4]);
	                    // Assuming role and team information are available
	                    res.setRoles((String) result[5]);
	                    res.setTeams((String) result[6]);
	                    // Assuming employment status is available as a boolean
	                    res.setEmploymentStatus((String) result[7]);
	                    resList.add(res);
	                });
	        
	    } catch (Exception e) {
	        logger.error("Error occurred while fetching user details: " + e.getMessage(), e);
	    }
	    return resList;
	}
	
}
