package com.lps.ldtracker.service;

import java.util.List;

import com.lps.ldtracker.dto.ManageTrainingDto;
import com.lps.ldtracker.entity.Training_Dtl;

public interface ManageTrainingService {

	List<ManageTrainingDto> getTrainingList();
	public void deleteTrainingById(Integer Id);
	Training_Dtl addTraining(Training_Dtl training);
	Training_Dtl editTraining(Integer id, Training_Dtl training);
	

}
