package com.lps.ldtracker.serviceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.sql.Timestamp;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.procedure.ProcedureCall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lps.ldtracker.configuration.RealSessionAware;
import com.lps.ldtracker.dto.ManageResourceDto;
import com.lps.ldtracker.model.ViewTrainingDetail;
import com.lps.ldtracker.repository.TrainingRepository;
import com.lps.ldtracker.service.ManageResourcesService;

@Service
public class ManageResourcesServiceImpl implements ManageResourcesService, RealSessionAware{

	private static final String SP_GETRESOURCELIST = "sp_getResourceList";
	private static final String SP_GETVIEWTRAININGDETAILS = "sp_getTrainingDetails";
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ManageResourceDto> getResourceList() {

		List<ManageResourceDto> resList = new ArrayList<ManageResourceDto>();
		try {
			Session session = getRealSession(sessionFactory);
			ProcedureCall storedProcedureCall = session.createStoredProcedureCall(SP_GETRESOURCELIST);
			List<Object[]> recordList = storedProcedureCall.getResultList();
			recordList.forEach(result -> {
				ManageResourceDto res = new ManageResourceDto();
				res.setMemberId((String) result[0]);
				res.setMembername((String) result[1]);
				res.setEmployeeNum((Integer) result[2]);
				res.setRoleName((String) result[3]);
				res.setTeamName((String) result[4]);
				res.setTrainings((String) result[5]);
				res.setCertifications((String) result[6]);
				resList.add(res);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resList;
		
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ViewTrainingDetail> getAllViewTrainingDetails() {
		
		List<ViewTrainingDetail> resList = new ArrayList<ViewTrainingDetail>();
		try {
			Session session = getRealSession(sessionFactory);
			ProcedureCall storedProcedureCall = session.createStoredProcedureCall(SP_GETVIEWTRAININGDETAILS);
			List<Object[]> recordList = storedProcedureCall.getResultList();
			recordList.forEach(result -> {
				ViewTrainingDetail res = new ViewTrainingDetail();
				res.setId((String) result[0]);
				res.setName((String) result[1]);
				res.setStatus((String) result[2]);
				res.setDuration((String) result[3]);
				res.setProgressNumber((BigDecimal) result[4]);
				res.setDate_started((Date) result[5]);
				res.setDate_completed((Timestamp) result[6]);
				res.setEstimated_hours((BigDecimal) result[7]);
				res.setDue_date((Date) result[8]);
				res.setTarget_date((Timestamp) result[9]);
				res.setLink((String) result[10]);
				res.setRemarks((String) result[11]);
				res.setDescription((String) result[12]);

				resList.add(res);
			});
		}catch (Exception e) {
			e.printStackTrace();
		}
		return resList;
	}

}
