package com.avalon.workbench.services.concurrentReport;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.avalon.workbench.beans.concurrntReport.Parameters;
import com.avalon.workbench.repository.concurrentReport.ConcurrentReportParamsRepository;
import com.avalon.workbench.repository.concurrentReport.ConcurrentReportRepository;
import com.avalon.workbench.repository.exception.WorkbenchDataAccessException;
import com.avalon.workbench.services.exception.WorkbenchServiceException;

@Service("ConcurrentReportParamsServiceImpl")
public class ConcurrentReportParamsServiceImpl implements
		ConcurrentReportParamsService {
	protected static final Logger LOG_R = Logger
			.getLogger(ConcurrentReportParamsServiceImpl.class);

	@Autowired
	@Qualifier(value = "concurrentReportParamsRepositoryImpl")
	ConcurrentReportParamsRepository concurrentReportParamsRepository;

	public List<Parameters> getParams(String progName) throws WorkbenchServiceException {
		// TODO Auto-generated method stub
		try {
			return concurrentReportParamsRepository.getParams(progName);
		} catch (WorkbenchDataAccessException e) {
			LOG_R.error("Exception occured ::" + e);
			throw new WorkbenchServiceException(e);
		}
	}

	public int getParamValue(String respName) {
		
		return concurrentReportParamsRepository.getParamValue(respName);
	}

	public List<String> getInDependentvalueSet(String flexVSName) {
		return concurrentReportParamsRepository.getInDependentvalueSet(flexVSName);
	}

	public List<String> getDependantVS(String flexVS, String dependingOn) {
		
		return concurrentReportParamsRepository.getDependantVS(flexVS,dependingOn);
	}

	public List<String> getTableTypeVS(String f_vs_id) {
		
		List<String> table_vs=new ArrayList<String>();
		
		String query=concurrentReportParamsRepository.getQuery(f_vs_id);
		if(query!=null){
			
			table_vs=concurrentReportParamsRepository.getTableTypeVS(query);
			return table_vs;
		}
		return table_vs;
	}



}
