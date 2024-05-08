package com.lps.ldtracker.serviceImpl;


import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.procedure.ProcedureCall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.lps.ldtracker.configuration.RealSessionAware;
import com.lps.ldtracker.dto.ManageTrainingDto;
import com.lps.ldtracker.model.OTP;
import com.lps.ldtracker.model.Training_Dtl;
import com.lps.ldtracker.model.UserT;
import com.lps.ldtracker.service.EmailService;
import com.lps.ldtracker.service.JwtService;
import com.lps.ldtracker.service.ManageTrainingService;
import com.lps.ldtracker.service.ResultService;

import lombok.RequiredArgsConstructor;

import com.lps.ldtracker.repository.TrainingRepository;
import com.lps.ldtracker.repository.UserRepository;
import com.lps.ldtracker.repository.VerificationTokenRepository;


@Service
@RequiredArgsConstructor
public class ManageTrainingServiceImpl  implements ManageTrainingService, RealSessionAware{
	
	private final TrainingRepository trainingRepository;
	
private static final String SP_GETTRAININGLIST = "sp_getTrainingList";
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ManageTrainingDto> getTrainingList() { 
		List<ManageTrainingDto> resList = new ArrayList<ManageTrainingDto>();
		try {
			Session session = getRealSession(sessionFactory);
			ProcedureCall storedProcedureCall = session.createStoredProcedureCall(SP_GETTRAININGLIST);
			List<Object[]> recordList = storedProcedureCall.getResultList();
			recordList.forEach(result -> {
			    ManageTrainingDto res = new ManageTrainingDto();
			    res.setId((String) result[0]);
			    res.setTrainingname((String) result[1]);    
			    res.setTrainingtype((String) result[2]);    
			    res.setProductname((String) result[3]);
			    res.setStartdate((Date) result[4]);
			    res.setDuedate((Date) result[5]);
			    res.setPrereq((String) result[6]);
			    res.setDescription((String) result[7]);
			    res.setTraininglink((String) result[8]);
			    res.setTrainingtags((String) result[9]);
			    res.setIsrequired((Boolean) result[10]);
			    res.setCertification((Boolean) result [11]);
			    res.setCertificationname((String) result[12]);
			    res.setDuration((String) result[13]);
			    res.setFee((String) result[14]);
			    res.setCertlink((String) result[15]);
			    res.setTrcondition((Boolean) result[16]);
			    res.setTrconditionValue((String) result[17]);
			    res.setActive((Boolean) result[18]); 
			    res.setExpirydate((String) result[19]);
			    resList.add(res);		
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resList;
		
		
	}	
	
	@Override
	public void deleteTrainingById(Integer Id) {
		Optional<Training_Dtl> existingTraining = trainingRepository.findById(Id);
		existingTraining.ifPresent(trainingRepository::delete);
	}

	@Override
    public Training_Dtl addTraining(Training_Dtl training) {
        return trainingRepository.save(training);
    }
	

	@Override
    public Training_Dtl editTraining(Integer Id, Training_Dtl updatedTraining) {
		Optional<Training_Dtl> existingTraining = trainingRepository.findById(Id);
        if (existingTraining != null) {
            updatedTraining.setId(Id);
            return trainingRepository.save(updatedTraining);
        } else {
            return null;
        }
    }
}


