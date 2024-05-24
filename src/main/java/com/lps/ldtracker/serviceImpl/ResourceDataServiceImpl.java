package com.lps.ldtracker.serviceImpl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lps.ldtracker.repository.TrainingRepository;
import com.lps.ldtracker.service.ResourceDataService;
import com.lps.ldtracker.dto.ManageResourceDto;
import com.lps.ldtracker.dto.ResourceDataDto;
import com.lps.ldtracker.configuration.RealSessionAware;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.procedure.ProcedureCall;
import org.springframework.stereotype.Service;

import com.lps.ldtracker.configuration.RealSessionAware;

import lombok.RequiredArgsConstructor;

@Service
public class ResourceDataServiceImpl implements ResourceDataService, RealSessionAware {
	
	private static final String SP_GETRESOURCEDATALIST = "sp_getResourceDataList";
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ResourceDataDto> getRecordList() {

		List<ResourceDataDto> resList = new ArrayList<ResourceDataDto>();
		try {
			Session session = getRealSession(sessionFactory);
			ProcedureCall storedProcedureCall = session.createStoredProcedureCall(SP_GETRESOURCEDATALIST);
			List<Object[]> recordList = storedProcedureCall.getResultList();
			recordList.forEach(result -> {
				ResourceDataDto res = new ResourceDataDto();
				res.setMemberId((String) result[0]);
				res.setMemberName((String) result[1]);
				res.setEmployeeNum((String) result[2]);
				res.setRoleName((String) result[3]);
				res.setTeamName((String) result[4]);
				res.setCareerStep((String) result[5]);
				res.setIsActive((String) result[6]);				
				res.setProductName((String) result[7]);
				res.setCertName((String) result[8]);
				res.setCertLink((String) result[9]);
				res.setExpiryDate((Date) result[10]);
				res.setStartDate((Date) result[11]);
				res.setDueDate((Date) result[12]);
				res.setStatus((String) result[13]);		
//				res.setForCertification((String) result[6]);
//				res.setUpcomingCertication((String) result[7]);			
//				res.setOngoingTrainings((String) result[9]);
//				res.setOverdueCertifications((String) result[10]);				
				resList.add(res);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resList;
		
	}

}