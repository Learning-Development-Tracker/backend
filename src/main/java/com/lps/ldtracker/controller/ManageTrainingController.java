package com.lps.ldtracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lps.ldtracker.dto.ManageTrainingDto;
import com.lps.ldtracker.model.Result;
import com.lps.ldtracker.service.ManageTrainingService;

import java.util.List;

import com.lps.ldtracker.model.Training_Dtl;


@RestController
@RequestMapping("/api/v1/trainings")


@CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600)
public class ManageTrainingController {
	
	@Autowired
	ManageTrainingService manageTrainingService;
	
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
    public ResponseEntity<?> deleteTraining(@PathVariable Integer id) {
		try {
			manageTrainingService.deleteTrainingById(id);
	        return ResponseEntity.ok().build();
		}catch (Exception e ){
			throw e;
		}
		
    }
	
    @PostMapping("/addTraining/")
    public ResponseEntity<Training_Dtl> addTraining(@RequestBody Training_Dtl training) {
        Training_Dtl addedTrainingDetails = manageTrainingService.addTraining(training);
        return new ResponseEntity<>(addedTrainingDetails, HttpStatus.CREATED);
    }
    
    @PutMapping("/editTraining/{Id}")
    public ResponseEntity<Training_Dtl> editTraining(@RequestBody Integer Id, Training_Dtl training) {
        Training_Dtl editedTraining = manageTrainingService.editTraining(Id, training);
        if (editedTraining != null) {
            return new ResponseEntity<>(editedTraining, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
	
}
