package com.avalon.workbench.repository.concurrentReport;


import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.avalon.workbench.beans.concurrntReport.Inputs;
import com.avalon.workbench.beans.concurrntReport.QueryStatus;
import com.avalon.workbench.repository.exception.WorkbenchDataAccessException;

@Repository
public interface ConcurrentReportRepository {
	public String generateConcurrentReport(String respName, String uname, String shortName,String concurrentName, Inputs inputs, ArrayList<String> params) throws WorkbenchDataAccessException;
	public String getConcurrentReport(String fileName,String reqId) throws WorkbenchDataAccessException;
	public QueryStatus getExecutionQuery(String fileName,String reqId);
	public void saveCPHistory(String concurrentName, String reqId);
}
