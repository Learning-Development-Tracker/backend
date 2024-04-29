package com.lps.ldtracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lps.ldtracker.model.Result;
import com.lps.ldtracker.model.SkillDetail;
import com.lps.ldtracker.model.UserT;
import com.lps.ldtracker.service.AdminService;
import com.lps.ldtracker.service.AuthenticationService;
import com.lps.ldtracker.service.UserForgotPasswordService;
import com.lps.ldtracker.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
	
	@Autowired
	private AdminService adm;
	
	@GetMapping(value="/getMemberSkillSet")
	public ResponseEntity<Result> getMemberSkillSet(@RequestBody SkillDetail request) {
		Result result = new Result();
		List<SkillDetail> skillDtl = adm.getMemberSkillSet(request.getMemberId());
		result.setData(skillDtl);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}
