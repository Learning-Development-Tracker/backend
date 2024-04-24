package com.lps.ldtracker.serviceImpl;


import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;


import com.lps.ldtracker.model.ManageTrainingRequest;
import com.lps.ldtracker.service.ManageTrainingService;


@Service
public class ManageTrainingServiceImpl  implements ManageTrainingService{
	
	
	@Override
	public List<ManageTrainingRequest> getTraining(ManageTrainingRequest manageTrainingRequestdto) {
		 // Creating an ArrayList of Training objects
        ArrayList<ManageTrainingRequest> trainingList = new ArrayList<>();

        // Sample data
        BigDecimal processId1 = new BigDecimal(1);
        BigDecimal trainingId1 = new BigDecimal(101);
        BigDecimal duration1 = new BigDecimal(30);
        Date startDt1 = new Date(0); // Assuming current date/time
        int fee1 = 100;
        String trainingLink1 = "https://example.com";
        String trainingName1 = "Java Programming";
        Boolean type1 = true;
        Date dueDt1 = new Date(fee1); // Assuming current date/time
        String roleName1 = "Developer";
        String typeCert1 = "Certification";
        String certLink1 = "https://example.com/cert";

        BigDecimal processId2 = new BigDecimal(2);
        BigDecimal trainingId2 = new BigDecimal(102);
        BigDecimal duration2 = new BigDecimal(45);
        Date startDt2 = new Date(fee1); // Assuming current date/time
        int fee2 = 150;
        String trainingLink2 = "https://example.com";
        String trainingName2 = "Python Programming";
        Boolean type2 = false;
        Date dueDt2 = new Date(fee2); // Assuming current date/time
        String roleName2 = "Developer";
        String typeCert2 = "Certification";
        String certLink2 = "https://example.com/cert";
        
        
    	trainingList.add(new ManageTrainingRequest(processId1, trainingId1, duration1, startDt1, fee1, trainingLink1,
                trainingName1, type1, dueDt1, roleName1, typeCert1, certLink1));

    	trainingList.add(new ManageTrainingRequest(processId2, trainingId2, duration2, startDt2, fee2, trainingLink2,
                trainingName2, type2, dueDt2, roleName2, typeCert2, certLink2));
  
    	return trainingList;
	}
	
	


}


