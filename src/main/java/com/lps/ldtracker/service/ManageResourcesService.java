package com.lps.ldtracker.service;

import java.util.List;

//import com.lps.ldtracker.model.ManagResourceRequest;
import com.lps.ldtracker.dto.ManageResourceDto;
//import com.lps.ldtracker.model.Training_Dtl;
import com.lps.ldtracker.model.ViewTrainingDetail;

public interface ManageResourcesService {

	List<ManageResourceDto> getResourceList();
	List<ViewTrainingDetail> getAllViewTrainingDetails();
}
