package com.avalon.workbench.services.responsibilites;

import java.util.List;

import org.springframework.stereotype.Service;

import com.avalon.workbench.beans.responsibilites.Responsibilites;
import com.avalon.workbench.repository.exception.WorkbenchDataAccessException;
import com.avalon.workbench.services.exception.WorkbenchServiceException;

@Service
public interface ResponsibilitesService {
	public List<Responsibilites> getResonsibilites(String uname) throws WorkbenchServiceException;
	public List<Responsibilites> getSearchRelatedResp(String responsibilityName,String userName) throws WorkbenchDataAccessException;
	
}
