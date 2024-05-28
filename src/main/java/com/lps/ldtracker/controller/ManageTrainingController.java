package com.lps.ldtracker.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lps.ldtracker.constants.LdTrackerConstants;
import com.lps.ldtracker.dto.ManageTrainingDto;
import com.lps.ldtracker.dto.TrainingLinksDto;
import com.lps.ldtracker.dto.TrainingLinksDto;
import com.lps.ldtracker.entity.Training_Dtl;
import com.lps.ldtracker.model.LdTrackerError;
import com.lps.ldtracker.model.MemberInfo;
import com.lps.ldtracker.model.MemberInfo;
import com.lps.ldtracker.model.Result;
import com.lps.ldtracker.service.ManageTrainingService;
import com.lps.ldtracker.service.ResultService;
import com.lps.ldtracker.service.ViewCalendarScheduleService;
import com.lps.ldtracker.serviceImpl.ErrorHandlingService;
import com.lps.ldtracker.repository.TrainingRepository;
import com.lps.ldtracker.dto.ViewCalenderScheduleDto;

import java.util.ArrayList;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/trainings")
public class ManageTrainingController {
	
	private final ResultService resultService;
	private final ErrorHandlingService errorHandlingService;
	
	@Autowired
	ManageTrainingService manageTrainingService;
	
	@Autowired
	ViewCalendarScheduleService viewCalendarScheduleService;
	
	
	@GetMapping(value="/getTrainingList")
	public ResponseEntity<Result> getTrainingList() {
		Result result = new Result();
		try {
			List<ManageTrainingDto> trainingDtl =  this.manageTrainingService.getTrainingList();
			result.setData(trainingDtl);
			result.setStatus("SUCCESS");
		} catch (Exception e) {
			result.setStatus("FAILED");
			e.printStackTrace();
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteTraining/{id}")
    public ResponseEntity<?> deleteTraining(@PathVariable String id) {
		try {
			List<LdTrackerError> errors = new ArrayList<>();
			errorHandlingService.validateInputParametersId(id, errors);
			
			if (!errors.isEmpty()) {
				Result result = resultService.setResult("200", LdTrackerConstants.SUCCESS, errors, null);
	            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
			}
			manageTrainingService.deleteTraining(id);
			Result result = resultService.setResult("200", LdTrackerConstants.SUCCESS, null, null);
		    return new ResponseEntity<>(result, HttpStatus.OK);
		     
		}  catch (Exception e) {
			 e.printStackTrace();
		     // Handle other exceptions
		     return errorHandlingService.createErrorResponse("INTERNAL_SERVER_ERROR", "An error occurred while processing the request", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@DeleteMapping("/deleteTrainingv2/{id}")
    public ResponseEntity<?> deleteTrainingV2(@PathVariable Integer id) {
		try {
			manageTrainingService.deleteTrainingById(id);
	        return ResponseEntity.ok().build();
		}catch (Exception e ){
			throw e;
		}
		
    }
	
    @PostMapping("/addTraining")
    public ResponseEntity<ManageTrainingDto> addTraining(@RequestBody ManageTrainingDto trainingDetails) {
    	ManageTrainingDto addedTraining = manageTrainingService.saveTraining(trainingDetails);
        try {
        	if(addedTraining.getTrainingLinksLists().size() != 0) {
        		addedTraining.setSubTrId(trainingDetails.getSubTrId());
        		manageTrainingService.saveTrainingLinks(addedTraining.getTrainingLinksLists());
        	}
            return ResponseEntity.status(HttpStatus.CREATED).body(addedTraining);
        } catch (Exception e) {
        	e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            
        }
    }   
    
    @GetMapping(value="/getViewCalendarSchedule")
	public ResponseEntity<Result> getViewCalendarSchedule() {
		Result result = new Result();
		try {
			List<ViewCalenderScheduleDto> calendarSched =  this.viewCalendarScheduleService.getViewCalendarSchedule();
			result.setData(calendarSched);
			result.setStatus("SUCCESS");
		} catch (Exception e) {
			result.setStatus("FAILED");
			e.printStackTrace();
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
    
    @GetMapping(value = "/getTrainingLinks/{id}")
    public ResponseEntity<Result> getTrainingLinks(@PathVariable String id) {
        Result result = new Result();
        try {
            System.out.println("TrainingID: " + id);
            List<TrainingLinksDto> trainingLinksList = manageTrainingService.getTrainingLinks(id);
            result.setData(trainingLinksList);
            result.setStatus("SUCCESS");
        } catch (Exception e) {
            result.setStatus("FAILED");
            e.printStackTrace();
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
}
