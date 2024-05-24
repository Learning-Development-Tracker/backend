package com.lps.ldtracker.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lps.ldtracker.configuration.RealSessionAware;
import com.lps.ldtracker.model.MemberCertDtl;
import com.lps.ldtracker.model.MemberInfo;
import com.lps.ldtracker.service.AdminService;
import com.lps.ldtracker.service.ApproverService;

import jakarta.persistence.ParameterMode;

@Service
public class ApproverServiceImpl implements ApproverService, RealSessionAware {
	
	private static final String SP_GETTEAMMEMBERLIST = "sp_getTeamMemberList";
	private static final String SP_GETMEMBERCERTIFICATION = "sp_getMemberCertification";
	private static final String SP_GETTRAININGSFORAPPROVAL = "sp_getTrainingsForApproval";
	private static final String SP_GETMEMBERTRAININGLIST = "sp_getMemberTrainingList";
	
	@Autowired
	SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<MemberInfo> getTeamMemberList(String strUserID){
		List<MemberInfo> usrList = new ArrayList<MemberInfo>();
		try {
			Session session = getRealSession(sessionFactory);
			ProcedureCall sp = session.createStoredProcedureCall(SP_GETTEAMMEMBERLIST);
			sp.registerStoredProcedureParameter("P_USERID", String.class, ParameterMode.IN);
			sp.setParameter("P_USERID", strUserID);
			List<Object[]> recordList = sp.getResultList();
			recordList.forEach(result -> {
				MemberInfo ul = new MemberInfo();
				ul.setMemberId((String) result[0]);
				ul.setLastName((String) result[1]);
				ul.setFirstName((String) result[2]);
				ul.setMiddleName((String) result[3]);
				ul.setFullName((String) result[4]);
				ul.setEmployeeNum((String) result[5]);
				ul.setRegionId((String) result[6]);
				ul.setEmailAddress((String) result[7]);
				ul.setEmploymentDt((String) result[8]);
				ul.setClCode((String) result[9]);
				ul.setTeamName((String) result[10]);
				ul.setEmpStatus((String) result[11]);
				ul.setRoleName((String) result[12]);
				ul.setPmId((String) result[13]);
				ul.setSupervisorId((String) result[14]);
				ul.setPmName((String) result[15]);
				ul.setSupervisorName((String) result[16]);
				usrList.add(ul);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return usrList;
	}
	
	@SuppressWarnings("unchecked")
	public List<MemberCertDtl> getMemberCertification(String strMemberID){
		List<MemberCertDtl> certDtl = new ArrayList<MemberCertDtl>();
		try {
			Session session = getRealSession(sessionFactory);
			ProcedureCall sp = session.createStoredProcedureCall(SP_GETMEMBERCERTIFICATION);
			sp.registerStoredProcedureParameter("P_MEMBERID", String.class, ParameterMode.IN);
			sp.setParameter("P_MEMBERID", strMemberID);
			List<Object[]> recordList = sp.getResultList();
			recordList.forEach(result -> {
				MemberCertDtl cd = new MemberCertDtl();
				cd.setMemberId((String) result[0]);
				cd.setCertID((String) result[1]);
				cd.setCertName((String) result[2]);
				cd.setDocID((String) result[3]);
				cd.setExpiryDate((String) result[4]);
				cd.setCompletionDate((String) result[5]);
				certDtl.add(cd);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return certDtl;
	}
	
	@SuppressWarnings("unchecked")
	public List<MemberCertDtl> getTrainingsForApproval(String strMemberID){
		List<MemberCertDtl> approvalsDtl = new ArrayList<MemberCertDtl>();
		try {
			Session session = getRealSession(sessionFactory);
			ProcedureCall sp = session.createStoredProcedureCall(SP_GETTRAININGSFORAPPROVAL);
			sp.registerStoredProcedureParameter("P_MEMBERID", String.class, ParameterMode.IN);
			sp.setParameter("P_MEMBERID", strMemberID);
			List<Object[]> recordList = sp.getResultList();
			recordList.forEach(result -> {
				MemberCertDtl cd = new MemberCertDtl();
				cd.setCertID((String) result[0]);
				cd.setCertName((String) result[1]);
				cd.setDuration((String) result[2]);
				cd.setFee((Integer) result[3]);
				cd.setCurrency((String) result[4]);
				cd.setCurrency((String) result[5]);
				cd.setExpiryDate((String) result[6]);
				cd.setIsRenewable((Boolean) result[7]);
				approvalsDtl.add(cd);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return approvalsDtl;
	}
	
	@SuppressWarnings("unchecked")
	public List<MemberCertDtl> getMemberTrainingList(String strMemberID){
		List<MemberCertDtl> approvalsDtl = new ArrayList<MemberCertDtl>();
		try {
			Session session = getRealSession(sessionFactory);
			ProcedureCall sp = session.createStoredProcedureCall(SP_GETMEMBERTRAININGLIST);
			sp.registerStoredProcedureParameter("P_MEMBERID", String.class, ParameterMode.IN);
			sp.setParameter("P_MEMBERID", strMemberID);
			List<Object[]> recordList = sp.getResultList();
			recordList.forEach(result -> {
				MemberCertDtl cd = new MemberCertDtl();
				cd.setProgressID((String) result[0]);
				cd.setTrainingID((String) result[1]);
				cd.setTrainingName((String) result[2]);
				cd.setStatusName((String) result[3]);
				cd.setMemberId((String) result[4]);
				cd.setApproverID((String) result[5]);
				cd.setStartDT((String) result[6]);
				cd.setDueDT((String) result[7]);
				cd.setExpiryDate((String) result[8]);
				cd.setCompletionDate((String) result[9]);
				cd.setStatusID((String) result[10]);
				cd.setTimeSpentInMinutes((Integer) result[11]);
				Integer ts = (Integer) result[11];
				if(ts != null) {
					cd.setTimeSpent(convertMinutes(ts));
				}
				approvalsDtl.add(cd);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return approvalsDtl;
	}
	
	public String convertMinutes(int minutes) {
        String retval= "";
    	int days = minutes / (24 * 60);
        int remainingMinutes = minutes % (24 * 60);
        int hours = remainingMinutes / 60;
        int remainingMinutesFinal = remainingMinutes % 60;
        if(days > 0) {
        	retval += days + " days, ";
        }
        retval += hours + " hours and " + remainingMinutesFinal + " minutes";
        return retval;
    }
}
