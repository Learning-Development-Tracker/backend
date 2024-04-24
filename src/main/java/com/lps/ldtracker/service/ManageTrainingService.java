package com.lps.ldtracker.service;

import java.util.List;

import com.lps.ldtracker.model.ManageTrainingRequest;

public interface ManageTrainingService {
	
	public List<ManageTrainingRequest> getTraining(ManageTrainingRequest manageTrainingRequestdto);

}
