package com.avalon.workbench.services.concurrentPrograms;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.avalon.workbench.beans.concurrntPrograms.CPDetails;
import com.avalon.workbench.beans.concurrntPrograms.ConcurrentPrograms;
import com.avalon.workbench.beans.responsibilites.Responsibilites;
import com.avalon.workbench.repository.concurrentPrograms.ConcurrentProgramsRepository;
import com.avalon.workbench.repository.exception.WorkbenchDataAccessException;
import com.avalon.workbench.repository.user.UserRepositoryImpl;
import com.avalon.workbench.services.exception.WorkbenchServiceException;

@Service("ConcurrentProgramsServiceImpl")
public class ConcurrentProgramsServiceImpl implements ConcurrentProgramsService {
	protected static final Logger LOG_R = Logger
			.getLogger(ConcurrentProgramsServiceImpl.class);
	
	@Autowired
	@Qualifier("ConcurrentProgramsRepositoryImpl")
	private ConcurrentProgramsRepository concurrentProgramsRepository;

	public List<ConcurrentPrograms> getConcurrentPrograms(String uname,
			String respName) throws WorkbenchServiceException {
		// TODO Auto-generated method stub
		try {
			if (respName.contains("&")) {
				return concurrentProgramsRepository.getConcurrentPrograms(
						uname, respName.replace("&", "''&''"));
			} else {
				return concurrentProgramsRepository.getConcurrentPrograms(
						uname, respName);
			}
		} catch (WorkbenchDataAccessException e) {
			LOG_R.error("Exception occured ::" + e);
			throw new WorkbenchServiceException(e);
		}
	}

	public List<ConcurrentPrograms> getConcurrentProgramNames(String ccpName) {
		List<ConcurrentPrograms> list = null;
		try {
			list = concurrentProgramsRepository
					.getConcurrentProgramNames(ccpName);
		} catch (WorkbenchDataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<CPDetails> getCPDetails(String to_date, String from_date)
			throws WorkbenchDataAccessException {

		List<CPDetails> result = concurrentProgramsRepository.getCPDetails(
				to_date, from_date);

		for (CPDetails details : result) {
			//LOG_R.info("Date"+details.getRequest_date());
			// Converting Date
			String date = details.getRequest_date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
			Date date2 = null;
			try {
				date2 = formatter.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			String DATE_FORMAT = "dd/MMM/yy";
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
			// End of Changing Date Format
			details.setRequest_date(sdf.format(date2));// Setting Date Value
		}
		return result;

	}

	public List<ConcurrentPrograms> getSearchRelatedConc(String concName,
			String responsibilityName, String uname)
			throws WorkbenchDataAccessException {
		List<ConcurrentPrograms> getSearchRelatedConc = concurrentProgramsRepository
				.getSearchRelatedConc(concName, responsibilityName, uname);
		return getSearchRelatedConc;
	}

	// Functionality to be added
	public List<CPDetails> getSpecificCPDetails(String progName,
			String from_date, String to_date)
			throws WorkbenchDataAccessException {
		List<CPDetails> result = concurrentProgramsRepository
				.getSpecificCPDetails(progName, from_date, to_date);
		/*
		 * Iterator<CPDetails> it = result.iterator(); while (it.hasNext()) {
		 * CPDetails cpDetails = (CPDetails) it.next();
		 * LOG_R.info(cpDetails.getProcess_time() + " Date "+
		 * cpDetails.getRequest_date());
		 * 
		 * }
		 */// Testing if data is coming or not.

		return result;
	}

}