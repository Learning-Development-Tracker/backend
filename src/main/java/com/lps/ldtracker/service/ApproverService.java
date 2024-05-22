package com.lps.ldtracker.service;

import java.util.List;

import com.lps.ldtracker.model.MemberCertDtl;
import com.lps.ldtracker.model.MemberInfo;

public interface ApproverService{
	
	List<MemberInfo> getTeamMemberList(String strUserID);
	List<MemberCertDtl> getMemberCertification(String strMemberID);
	List<MemberCertDtl> getTrainingsForApproval(String strMemberID);
	List<MemberCertDtl> getMemberTrainingList(String strMemberID);
}
