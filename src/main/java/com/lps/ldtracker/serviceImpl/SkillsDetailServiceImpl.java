package com.lps.ldtracker.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lps.ldtracker.entity.SkillsDetail;
import com.lps.ldtracker.repository.SkillsDetailRepository;
import com.lps.ldtracker.service.SkillsDetailService;

@Service
public class SkillsDetailServiceImpl implements SkillsDetailService{
	 @Autowired
	    private SkillsDetailRepository skillsDetailRepository;

	    public List<SkillsDetail> getAllSkillDetails() {
	        return skillsDetailRepository.findAll();
	    }
}
