package com.lps.ldtracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lps.ldtracker.model.ManageTrainingRequest;
import com.lps.ldtracker.model.Result;
import com.lps.ldtracker.service.ManageTrainingService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import com.lps.ldtracker.model.Training_Dtl;


@RestController
@RequestMapping("/api/v1/trainings")


@CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600)
public class ManageTrainingController {
	
	@Autowired
	ManageTrainingService manageTrainingService;
	
	@GetMapping(value="/getTrainingList")
	public ResponseEntity<Result> getTrainingList() {
		List<Training_Dtl> usr = this.manageTrainingService.getTrainingList();
		Result result = new Result();
		result.setData(usr);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
}
