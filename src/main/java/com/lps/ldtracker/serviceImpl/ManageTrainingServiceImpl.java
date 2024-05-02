package com.lps.ldtracker.serviceImpl;


import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

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
public class ManageTrainingServiceImpl  implements ManageTrainingService{
	
	private final TrainingRepository trainingRepository;
	
	@Override
	public List<Training_Dtl> getTrainingList() { 
		return trainingRepository.findAll();
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


