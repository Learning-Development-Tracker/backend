package com.lps.ldtracker.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.procedure.ProcedureCall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lps.ldtracker.configuration.RealSessionAware;
import com.lps.ldtracker.dto.ManageResourceDto;
import com.lps.ldtracker.service.ManageResourcesService;

@Service
public class ManageResourcesServiceImpl implements ManageResourcesService, RealSessionAware{

	private static final String SP_GETRESOURCELIST = "sp_getResourceList";
	
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

}
