package com.avalon.workbench.repository.concurrentReport;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.avalon.workbench.beans.concurrntReport.Parameters;
import com.avalon.workbench.repository.exception.WorkbenchDataAccessException;

@Repository
public interface ConcurrentReportParamsRepository {
	public List<Parameters> getParams(String progName) throws WorkbenchDataAccessException;

	public int getParamValue(String respName);

	public List<String> getInDependentvalueSet(String flexVSName);

	public List<String> getDependantVS(String flexVS, String dependingOn);

	public String getQuery(String f_vs_id);

	public List<String> getTableTypeVS(String query);
	
}
