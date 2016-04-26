package com.avalon.workbench.services.concurrentReport;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.avalon.workbench.beans.concurrntReport.Inputs;
import com.avalon.workbench.beans.concurrntReport.QueryStatus;
import com.avalon.workbench.repository.concurrentReport.ConcurrentReportRepository;
import com.avalon.workbench.repository.exception.WorkbenchDataAccessException;
import com.avalon.workbench.services.exception.WorkbenchServiceException;

@Service("ConcurrentReportServiceImpl")
public class ConcurrentReportServiceImpl implements ConcurrentReportService {
	protected static final Logger LOG_R = Logger
			.getLogger(ConcurrentReportServiceImpl.class);

	@Autowired
	@Qualifier(value = "concurrentReportRepositoryImpl")
	ConcurrentReportRepository concurrentReportRepository;

	@Autowired
	@Qualifier(value = "ConcurrentReportInputsServiceImpl")
	ConcurrentReportInputsService service;

	public String getConcurrentReport(String respName, String uname,
			String shortName, String concurrentName, ArrayList<String> params,String progName) throws WorkbenchServiceException,
			InterruptedException {
		try {
			String reqId;
			String fileName;
			String otype;
			//String exe = "";
			QueryStatus qs = null;
			Inputs inputs = service.getInputs(concurrentName);
			if (inputs != null) {
				reqId = concurrentReportRepository.generateConcurrentReport(
						respName, uname, shortName, concurrentName, inputs, params);
				//fileName = concurrentName + "_" + reqId + "_1.PDF";
				
				if( inputs.getDefault_output_type() == null) {
					 otype = "PDF";
					 LOG_R.info("Output type  set Default  "+otype);
				}
				else {
					otype = inputs.getDefault_output_type();
					LOG_R.info("Output type  else "+otype);
				}
				
				fileName = concurrentName + "_" + reqId + "_1."+otype;
				//Normal				
				do{
				 qs = concurrentReportRepository.getExecutionQuery(progName,reqId);				 
				 Thread.sleep(800);
				}while( !qs.getMeaning().equals("Completed") );
				
				if( qs.getStatus().equals("Normal") && qs.getMeaning().equals("Completed") )
					concurrentReportRepository.getConcurrentReport(fileName,"");
				else if( qs.getStatus().equals("Warning") ){
					LOG_R.error(" Request Completed With Warning");				
					}	
				else if(qs.getStatus().equals("Error") ){
					LOG_R.error("Request Completed with Error");					
				}
				
			} else {
				reqId = concurrentReportRepository.generateConcurrentReport(
						respName, uname, shortName, concurrentName, null, params);
				fileName = "o"+ reqId+".out";
				LOG_R.info("new fileName==" + fileName);
				//Thread.sleep(110000);
			
				concurrentReportRepository.getConcurrentReport(fileName,"");
			}
			LOG_R.info("file name sent to controller=="+fileName);
			concurrentReportRepository.saveCPHistory(concurrentName,reqId);
			return fileName;
		} catch (WorkbenchDataAccessException e) {
			LOG_R.error("Exception occured ::" + e);
			throw new WorkbenchServiceException(e);
		}

	}
	
	
	public String getConcurrentReportViewFile(String viewLink, String reqId) throws WorkbenchDataAccessException{
		return concurrentReportRepository.getConcurrentReport(viewLink, reqId);
	}
	
	

	/*public String getConcurrentReport(String respName, String uname,
			String shortName, String concurrentName, ArrayList<String> params,String progName) throws WorkbenchServiceException,
			InterruptedException {
		try {
			String reqId;
			String fileName;
			String exe=null;
			Date date = null;
			Inputs inputs = service.getInputs(concurrentName);
			if (inputs != null) {
				reqId = concurrentReportRepository.generateConcurrentReport(
						respName, uname, shortName, concurrentName, inputs, params);
				fileName = concurrentName + "_" + reqId + "_1.PDF";
				LOG_R.info(" inside if....new fileName==" + fileName);
				LOG_R.info("prog name:"+progName);
				while(exe==null){
				exe=concurrentReportRepository.getExecutionQuery(progName,reqId);
				LOG_R.info("Execution Time B4 conversion "+exe);
				//Time Conversion
				LOG_R.info("--------------Inside Whileloop-----------------");
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); 
				sdf.setTimeZone(TimeZone.getTimeZone("UTC")); 
				
				try {
					date = sdf.parse("1970-01-01 " +exe+".500");
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				LOG_R.info("--------------Outside Whileloop-----------------");
				LOG_R.info("in milliseconds: " + date.getTime());
				//Time Conversion END				
				Thread.sleep(date.getTime());
			
				//Thread.sleep(110000);
				concurrentReportRepository.getConcurrentReport(fileName);
			} else {
				reqId = concurrentReportRepository.generateConcurrentReport(
						respName, uname, shortName, concurrentName, null, params);
				fileName = "o"+ reqId+".out";
				LOG_R.info("new fileName==" + fileName);
				Thread.sleep(110000);
			
				concurrentReportRepository.getConcurrentReport(fileName);
			}
			LOG_R.info("file name sent to controller=="+fileName);
			return fileName;
		} catch (WorkbenchDataAccessException e) {
			LOG_R.error("Exception occured ::" + e);
			throw new WorkbenchServiceException(e);
		}

	}

*/
}
