package com.lps.ldtracker.service;

import java.util.List;

import com.lps.ldtracker.model.ManageTrainingRequest;
import com.lps.ldtracker.model.Training_Dtl;

public interface ManageTrainingService {

	List<Training_Dtl> getTrainingList();
	public void deleteTrainingById(Integer Id);
	

}
