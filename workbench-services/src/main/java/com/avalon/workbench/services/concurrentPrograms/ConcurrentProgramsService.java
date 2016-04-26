package com.avalon.workbench.services.concurrentPrograms;

import java.util.List;

import org.springframework.stereotype.Service;

import com.avalon.workbench.beans.concurrntPrograms.CPDetails;
import com.avalon.workbench.beans.concurrntPrograms.ConcurrentPrograms;
import com.avalon.workbench.beans.responsibilites.Responsibilites;
import com.avalon.workbench.repository.exception.WorkbenchDataAccessException;
import com.avalon.workbench.services.exception.WorkbenchServiceException;

@Service
public interface ConcurrentProgramsService {
	public List<ConcurrentPrograms> getConcurrentPrograms(String uname,String resName) throws WorkbenchServiceException;
	public List<CPDetails> getCPDetails(String to_date,String from_date)throws WorkbenchDataAccessException;
	public List<CPDetails> getSpecificCPDetails(String progName, String from_date, String to_date)throws WorkbenchDataAccessException;
	public List<ConcurrentPrograms> getSearchRelatedConc(String concName,String responsibilityName, String userName) throws WorkbenchDataAccessException;
	public List<ConcurrentPrograms> getConcurrentProgramNames(String ccpName);
	
	
}
