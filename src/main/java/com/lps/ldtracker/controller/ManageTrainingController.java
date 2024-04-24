package com.lps.ldtracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lps.ldtracker.model.ManageTrainingRequest;
import com.lps.ldtracker.service.ManageTrainingService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/dashboard/")


public class ManageTrainingController {
	
	@Autowired
	ManageTrainingService manageTrainingService;

	@PostMapping(value="/getTrainings") 
    public List<ManageTrainingRequest> searchManageTraining(@RequestBody ManageTrainingRequest manageTrainingRequestdto){
		List<ManageTrainingRequest> trainings =  null;
        try { 
        	 trainings= this.manageTrainingService.getTraining(manageTrainingRequestdto);
          
        }
        catch (Exception e) {
		
        }
        return trainings;

	}
	
}
