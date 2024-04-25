package com.lps.ldtracker.serviceImpl;


import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

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
}


