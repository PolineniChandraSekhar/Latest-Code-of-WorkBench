package com.avalon.workbench.services.concurrentReport;

import java.util.List;

import org.springframework.stereotype.Service;

import com.avalon.workbench.beans.concurrntReport.Parameters;
import com.avalon.workbench.services.exception.WorkbenchServiceException;

@Service
public interface ConcurrentReportParamsService {
	public List<Parameters> getParams(String progName) throws WorkbenchServiceException;

	public int getParamValue(String respName);

	public List<String> getInDependentvalueSet(String flexVSName);

	public List<String> getDependantVS(String flexVS, String dependingOn);

	public List<String> getTableTypeVS(String f_vs_id);
}
