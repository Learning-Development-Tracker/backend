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

import com.lps.ldtracker.dto.ManageResourceDto;
//import com.lps.ldtracker.model.Resource_Dtl;
import com.lps.ldtracker.model.Result;
import com.lps.ldtracker.service.ManageResourcesService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@RestController
@RequestMapping("/api/v1/resources")


@CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600)
public class ManageResourcesController {
	
	@Autowired
	ManageResourcesService manageResourcesService;

	@GetMapping(value="/getResources")
	public ResponseEntity<Result> getResources() {
		Result result = new Result();
		try {
			List<ManageResourceDto> resourceDtl =  this.manageResourcesService.getResourceList();
			result.setData(resourceDtl);
			result.setStatus("SUCCESS");
		} catch (Exception e) {
			result.setStatus("FAILED");
			e.printStackTrace();
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
//	@GetMapping(value="/getResourceList")
//	public ResponseEntity<Result> getResourceList() {
//		List<Training_Dtl> usr = this.manageResourcesService.getResourceList();
//		Result result = new Result();
//		result.setData(usr);
//		
//		return new ResponseEntity<>(result, HttpStatus.OK);
//	}
	
}
