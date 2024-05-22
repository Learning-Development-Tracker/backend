package com.lps.ldtracker.serviceImpl;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.procedure.ProcedureOutputs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lps.ldtracker.configuration.RealSessionAware;
import com.lps.ldtracker.dto.ManageTrainingDto;
import com.lps.ldtracker.entity.Training_Dtl;
import com.lps.ldtracker.repository.TrainingRepository;
import com.lps.ldtracker.service.ManageTrainingService;

import jakarta.persistence.ParameterMode;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ManageTrainingServiceImpl  implements ManageTrainingService, RealSessionAware{
	
	private final TrainingRepository trainingRepository;
	
	private static final String SP_GETTRAININGLIST = "sp_getTrainingList";
	private static final String SP_DELETETRAINING = "sp_deleteTraining";
	
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
			    res.setTrainingName((String) result[1]);    
			    res.setTrainingType((String) result[2]);    
			    res.setProductName((String) result[3]);
			    res.setStartDate((Date) result[4]);
			    res.setDueDate((Date) result[5]);
			    res.setPreReq((String) result[6]);
			    res.setDescription((String) result[7]);
			    res.setTrainingLink((String) result[8]);
			    res.setTrainingTags((String) result[9]);
			    res.setIsRequired((Boolean) result[10]);
			    res.setCertification((Boolean) result [11]);
			    res.setCertID((String) result[12]);
			    res.setCertName((String) result[13]);
			    res.setDuration((String) result[14]);
			    res.setFee((Integer) result[15]);
			    res.setCurrency((String) result[16]);
			    res.setCertLink((String) result[17]);
			    res.setTrCondition((Integer) result[18]);
			    res.setTrConditionValue((String) result[19]);
			    res.setActive((Boolean) result[20]); 
			    res.setExpiryDate((Date) result[21]);
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
	public String deleteTraining(String trainingId) {
	    try {
	        Session session = getRealSession(sessionFactory);
	        ProcedureCall storedProcedureCall = session.createStoredProcedureCall(SP_DELETETRAINING);
	        
	        // Register input parameter
	        storedProcedureCall.registerStoredProcedureParameter("TrainingID", String.class, ParameterMode.IN);
	        storedProcedureCall.registerStoredProcedureParameter("Message", String.class, ParameterMode.OUT);
	        
	        // Set input parameter
	        storedProcedureCall.setParameter("TrainingID", trainingId);
	        
	        // Execute the stored procedure
	        ProcedureOutputs procedureOutputs = storedProcedureCall.getOutputs();
	        
	        // Get output parameter
	        Object output = procedureOutputs.getOutputParameterValue("Message");
	        
	        // Check if output is null
	        if (output != null) {
	            return output.toString();
	        } else {
	            return "No message returned from the stored procedure.";
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "Error occurred while deleting training record.";
	    }
	}


	@Override
    public Training_Dtl addTraining(Training_Dtl training) {
        return trainingRepository.save(training);
    }
	

	@Override
    public Training_Dtl editTraining(Integer Id, Training_Dtl updatedTraining) {
		Optional<Training_Dtl> existingTraining = trainingRepository.findById(Id);
        if (existingTraining != null) {
            updatedTraining.setId(Id.toString());
            return trainingRepository.save(updatedTraining);
        } else {
            return null;
        }
    }
}


