package com.avalon.workbench.repository.concurrentPrograms;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.avalon.workbench.beans.concurrntPrograms.CPDetails;
import com.avalon.workbench.beans.concurrntPrograms.ConcurrentPrograms;
import com.avalon.workbench.beans.responsibilites.Responsibilites;
import com.avalon.workbench.repository.exception.WorkbenchDataAccessException;

@Repository
public interface ConcurrentProgramsRepository {
	public List<ConcurrentPrograms> getConcurrentPrograms(String uname,String respNmae) throws WorkbenchDataAccessException;
	public List<CPDetails> getCPDetails(String to_date,String from_date) throws WorkbenchDataAccessException;
	public List<CPDetails> getSpecificCPDetails(String progName, String from_date, String to_date)throws WorkbenchDataAccessException;
	public List<ConcurrentPrograms> getSearchRelatedConc(String concName,String responsibilityName, String uname) throws WorkbenchDataAccessException;
	public List<ConcurrentPrograms> getConcurrentProgramNames(String ccpName) throws WorkbenchDataAccessException;
	
	
}
