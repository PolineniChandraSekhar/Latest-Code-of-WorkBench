package com.avalon.workbench.web.controllers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.avalon.workbench.beans.concurrntPrograms.CPDetails;
import com.avalon.workbench.beans.concurrntPrograms.ConcurrentPrograms;
import com.avalon.workbench.beans.concurrntReport.Parameters;
import com.avalon.workbench.repository.exception.WorkbenchDataAccessException;
import com.avalon.workbench.services.concurrentPrograms.ConcurrentProgramsService;
import com.avalon.workbench.services.concurrentReport.ConcurrentReportParamsService;
import com.avalon.workbench.services.concurrentReport.ConcurrentReportService;
import com.avalon.workbench.services.exception.WorkbenchServiceException;
import com.avalon.workbench.services.graphs.GenerateGraph;
import com.avalon.workbench.services.mail.MailSend;

//

@Controller
public class ConcurrentProgramsController {
	protected static final Logger log = Logger
			.getLogger(ConcurrentProgramsController.class);

	@Autowired
	@Qualifier("ConcurrentProgramsServiceImpl")
	ConcurrentProgramsService concurrentProgramsService;

	@Autowired
	@Qualifier("ConcurrentReportServiceImpl")
	ConcurrentReportService concurrentReportService;

	@Autowired
	@Qualifier("ConcurrentReportParamsServiceImpl")
	ConcurrentReportParamsService concurrentReportParamsService;
	
	@Autowired
	MailSend mailSend;

	@Autowired
	// @Qualifier(
	GenerateGraph generateGraph;
	
	

	// String responsibilityName;

	@RequestMapping(value = "/getConcurrentPrograms", method = RequestMethod.GET)
	public String getConcurrentPrograms(@RequestParam("pageId") int pageId,
			HttpSession session, Model model, String respName)
			throws WorkbenchServiceException {
		// responsibilityName=respName;
		log.info("inside getConcurrentPrograms......");
		String uname = (String) session.getAttribute("uname");
		List<ConcurrentPrograms> programs = concurrentProgramsService
				.getConcurrentPrograms(uname, respName);
		PagedListHolder<ConcurrentPrograms> pagedListHolder = new PagedListHolder<ConcurrentPrograms>(
				programs);
		int page = pageId > 0 ? pageId : 0;
		pagedListHolder.setPage(page);
		int pageSize = 10;
		if (programs.size() == 0) {
			model.addAttribute("message_Status", 0);
		}
		pagedListHolder.setPageSize(pageSize);
		model.addAttribute("pagedListHolder", pagedListHolder);
		model.addAttribute("respName", respName);
		model.addAttribute("pagination", "getConcurrentPrograms");
		return "ConcurrentPrograms";

	}

	@RequestMapping(value = "/listOfCPNames", method = RequestMethod.GET)
	public String getlistOfCPNames(String progName, Model model) {
		log.info("Reached locpController:=========" + progName);
		List<ConcurrentPrograms> list = concurrentProgramsService
				.getConcurrentProgramNames(progName);
		if (list == null) {
			model.addAttribute("msg",
					"There are No ConcurrentPrograms With the Name " + progName);
		}
		model.addAttribute("list", list);
		return "listOfCPNames";

	}

	@RequestMapping(value = "/getReport", method = RequestMethod.GET)
	public void getFile(@RequestParam Map<String, String> allRequestParams,
			String respName, String progName, String shortName,
			String concurrentName, HttpServletResponse response,
			HttpServletRequest request, HttpSession session,String mailId) {
			int mail=0;
		try {
			List<Parameters> params = concurrentReportParamsService
					.getParams(progName);
			ArrayList<String> paramsList = new ArrayList<String>();
			// Modification By Mallik @ 20-03-15
			log.info("@getReport-------Length Of params" + params.size());
			log.info("@Concurrent Name -------" + concurrentName);
			String param1 = null;
			for (int i = 0; i < params.size(); i++) {
				log.info("-=-=-" + i + "-=-=-" + params.get(i));
				if ("Constant".equals(params.get(i).getValueType())) {

					param1 = params.get(i).getDefault_Value();

				} else {
					param1 = allRequestParams.get(params.get(i).getPrompt());
				}
				paramsList.add(param1);
			}

			// Modification End by mallik

			// String param1=allRequestParams.get(params.get(0).getPrompt());
			// ArrayList<String> paramsList=new ArrayList<String>();
			// paramsList.add(param1);
			// log.info("Param value "+param1);
			// log.info("parameter==="+paramsList.get(0)+"respName="+respName);
			String uname = (String) session.getAttribute("uname");

			String fileName = concurrentReportService.getConcurrentReport(
					respName, uname, shortName, concurrentName, paramsList,
					progName);

			log.info("filename=====" + fileName + "  Extension:  "
					+ fileName.substring(fileName.indexOf(".") + 1));

			// Test for excel Output on 16-Apr
			// fileName = "Book1.xlsx";

			if (!fileName.substring(fileName.indexOf(".") + 1).equals("PDF")) {
				log.info("-=-=-=-=-= Enterred");
				BufferedInputStream is = null;
				try {
					is = new BufferedInputStream(new FileInputStream("D://"
							+ fileName));
					response.setContentType("application/vnd.ms-excel");
					response.setHeader("Content-Disposition",
							"attachment; filename=" + fileName);
					Cookie c = new Cookie("status", "success");
					c.setMaxAge(15);
					response.addCookie(c);
					byte[] bfr = new byte[1024];
					int cnt = 0;
					while ((cnt = is.read(bfr, 0, bfr.length)) > 0)
						response.getOutputStream().write(bfr, 0, cnt);
					if(mailId!=null&&mailId!=""&&mail==0){
						mail=1;
						mailSend.sendMail(mailId,"D:/"+fileName,fileName );
					}
				} finally {
					
					try {
						if (is != null)
							is.close();
					} catch (IOException ex) {
					}
				}

			} else { // IF End for filename extension

				// get your file as InputStream
				InputStream is = new FileInputStream("D:/" + fileName);
				// copy it to response's OutputStream
				IOUtils.copy(is, response.getOutputStream());
				// response.setContentType("application/pdf");
				log.info("Called me mail is"+mailId);
				if(mailId!=null&&mailId!=""&&mail==0){
					mail=1;
					mailSend.sendMail(mailId,"D:/"+fileName,fileName);
				}
				response.flushBuffer();

			}
		} catch (WorkbenchServiceException e) {
			log.info("Error writing file to output stream. Filename was");
			throw new RuntimeException("IOError writing file to output stream");

		} catch (FileNotFoundException e) {
			log.info("File Not Found Exception");
			// throw new
			// RuntimeException("IOError writing file to output stream");
			request.setAttribute("respName", respName);
			request.setAttribute("progName", progName);
			RequestDispatcher requestDispatcher = request
					.getRequestDispatcher("WEB-INF/pages/downloadFailure.jsp");
			try {
				requestDispatcher.forward(request, response);
			} catch (ServletException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (IOException e) {

			log.info("IO Eception");
			throw new RuntimeException("IOError writing file to output stream");
		} catch (InterruptedException e) {

			log.info("Interrupted Exception");
			throw new RuntimeException("IOError writing file to output stream");
		}
	}

	@RequestMapping(value = "/getParameters", method = RequestMethod.GET)
	public String getParameters(String respName, String progName,
			String shortName, String concurrentName, Model model,
			HttpServletResponse response) throws WorkbenchServiceException {
		int bgID = 0;
		List<Parameters> params = concurrentReportParamsService
				.getParams(progName);
		params = checkValueSetType(params);
		for (Parameters param : params) {

			String default_value = param.getDefault_Value();
			if(default_value!=null){
			if (default_value.equals("PER_BUSINESS_GROUP_ID")) {
				
				bgID = concurrentReportParamsService.getParamValue(respName);
			}
			}
			if (param.getValue_set_type().equalsIgnoreCase("I")) {// For testing

				for (String values : param.getFlex_Values())
					log.info("LOV---" + values);

			}

		}

		model.addAttribute("bgID", bgID);
		model.addAttribute("progName", progName);
		model.addAttribute("parameters", params);
		model.addAttribute("progName", progName);
		model.addAttribute("shortName", shortName);
		model.addAttribute("concurrentName", concurrentName);
		model.addAttribute("respName", respName);
		model.addAttribute("paramSize", params.size());
		
		for (Parameters param : params) {
			if (param.getValue_set_type().equalsIgnoreCase("D")) {
				return "ccpFormInputVSD";
			}
		}
		
		for (Parameters param : params) {
			if (param.getValue_set_type().equalsIgnoreCase("I")) {
				return "ccpFormInputVS";
			}
		}
		
		return "ccpFormInput";
	}

	private List<Parameters> checkValueSetType(List<Parameters> params) {
		for (Parameters parameters : params) {

			log.info(parameters.getValue_set_type());
			if (parameters.getValue_set_type().equalsIgnoreCase("I")) {

				List<String> vs_values = concurrentReportParamsService
						.getInDependentvalueSet(parameters.getFlexVSName());
				log.info("Independent-=-=-=-=-=-=-=-=-=-==-=");
				if (vs_values != null) {
					log.info("Value at 0 index location=============="
							+ vs_values.get(0));
					parameters.setFlex_Values(vs_values);
				}
			} 
			else if (parameters.getValue_set_type().equalsIgnoreCase("D")) {
				
				 log.info("Dependent on another independent value");
			}
			/*else if(parameters.getValue_set_type().equalsIgnoreCase("F")){
				
				log.info("Table type value set");
				String f_vs_id=parameters.getFlex_value_set_id();
				List<String> table_vs=concurrentReportParamsService.getTableTypeVS(f_vs_id);
				if (table_vs != null) {
					log.info("Value at 0 index location=============="+ table_vs.get(0));
					for(String val : table_vs){//Testing{
						
						log.info("Values"+val);
					
					}
				parameters.setTable_VS(table_vs);
				}
			}*///blocking it temp
			else{
				log.info("Others===================");
			}

		}
		
		return params;

	}
	
	
	@RequestMapping(value ="/getDependantVS" , method = RequestMethod.GET)
	public @ResponseBody String getDependantVS(HttpServletRequest request ) throws WorkbenchServiceException{
		String returnVal="";
		String responsibility=request.getParameter("resp");
		String concurrentProgram=request.getParameter("conc");
		String dependant=request.getParameter("dependant");
		String dependingOn=request.getParameter("dependingVal");
		log.info("got data from ui==========Responsibility:"+responsibility+" CP:"+concurrentProgram+" DependantVS:"+dependant+" is dependant on:"+dependingOn);
		List<Parameters> params = concurrentReportParamsService.getParams(concurrentProgram);
		List<String> dependentValues = new ArrayList<String>();
		for(Parameters parameter : params){
			
			if(parameter.getPrompt().equals(dependant)){
				String flexVS=parameter.getFlexVSName();
				log.info("Flex_VS Name:"+flexVS);
				dependentValues =concurrentReportParamsService.getDependantVS(flexVS,dependingOn);
				//log.info("got the values");
			}
			
		}
		if(dependentValues.size()==0){
			
			return "empty";
			
		}
		for(int i=0;i<dependentValues.size();i++){
			returnVal=returnVal+"<option value='"+dependentValues.get(i)+"'>"+dependentValues.get(i)+"</option>";
		}
		return returnVal;
	}

	@RequestMapping(value = "/getConcurrentProgramList", method = RequestMethod.GET)
	public String getConcurrentProgramList(@RequestParam("pageId") int pageId,
			HttpSession session, Model model, String to_date, String from_date)
			throws WorkbenchServiceException, WorkbenchDataAccessException {
		/*
		 * List<Parameters> params = concurrentReportParamsService
		 * .getParams(progName);
		 */
		log.info("Look At ME");
		List<CPDetails> result = concurrentProgramsService.getCPDetails(
				to_date, from_date);
		if (result.size() == 0) {
			model.addAttribute("msg", "No Data Found");
		}
		PagedListHolder<CPDetails> pagedListHolder = new PagedListHolder<CPDetails>(
				result);
		int page = pageId > 0 ? pageId : 0;
		pagedListHolder.setPage(page);
		int pageSize = 10;
		pagedListHolder.setPageSize(pageSize);
		model.addAttribute("pagedListHolder", pagedListHolder);
		model.addAttribute("from_date", from_date);
		model.addAttribute("to_date", to_date);
		// model.addAttribute("parameters", result);
		// log.info("Welcome  " + pageId);
		return "cpdetails";

	}

	@RequestMapping(value = "/generateFile", method = RequestMethod.GET)
	public void generateFile(@RequestParam("req_id") String reqId,
			@RequestParam("view_link") String viewLink, Model model,
			HttpServletRequest request, HttpServletResponse response)
			throws WorkbenchDataAccessException, IOException {

		log.info("@/generateFile()....Request id ---" + reqId + "  View Link: "
				+ viewLink);
		String fileName = concurrentReportService.getConcurrentReportViewFile(
				viewLink, reqId);

		log.info("@/generateFile()...END1");
		// get your file as InputStream
		InputStream is = new FileInputStream("D:/" + fileName);
		log.info("@/generateFile()...END2");
		// copy it to response's OutputStream
		IOUtils.copy(is, response.getOutputStream());
		log.info("@/generateFile()...END3");
		// response.setContentType("application/pdf");
		response.flushBuffer();

		log.info("@/generateFile()...END");
	}

	@RequestMapping(value = "/searchConcurrent", method = RequestMethod.GET)
	public String getsearchConcurrent(
			@RequestParam("concurrentName") String concName,
			HttpSession session, Model model, HttpServletResponse response,
			@RequestParam("responsibilityName") String responsibilityName)
			throws WorkbenchDataAccessException {
		String uname = (String) session.getAttribute("uname");
		log.info("Concurrent Name=====%%========" + concName);
		Cookie searchConcurrentProgram = new Cookie("concurrentName", concName);
		// Cookie respCookie = new Cookie("respName", responsibilityName);
		// response.addCookie(respCookie);
		response.addCookie(searchConcurrentProgram);
		List<ConcurrentPrograms> sarchedResult = concurrentProgramsService
				.getSearchRelatedConc(concName, responsibilityName, uname);
		PagedListHolder<ConcurrentPrograms> pagedListHolder = new PagedListHolder<ConcurrentPrograms>(
				sarchedResult);
		int pageId = 0;
		int page = pageId > 0 ? pageId : 0;
		pagedListHolder.setPage(page);
		int pageSize = 10;
		int message_Status = pagedListHolder.getNrOfElements();
		pagedListHolder.setPageSize(pageSize);
		model.addAttribute("pagination", "searchConcurrent");
		model.addAttribute("pagedListHolder", pagedListHolder);
		model.addAttribute("respName", responsibilityName);
		model.addAttribute("message_Status", message_Status);
		model.addAttribute("concurrentName", concName);
		return "search_ConcurrentPrograms";

	}

	@RequestMapping(value = "/searchConcurrent2", method = RequestMethod.GET)
	public String getsearchConcurrent2(@RequestParam("pageId") int pageId,
			@RequestParam("respName") String responsibilityName,
			HttpSession session, Model model, HttpServletRequest request)
			throws WorkbenchDataAccessException {
		String userName = (String) session.getAttribute("uname");
		log.info("Chandra this is the query String"
				+ request.getParameter("respName"));
		// String responsibilityName = null;
		String concName = null;
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("concurrentName")) {
				concName = cookie.getValue();
			}
			/*
			 * if (cookie.getName().equals("respName")) {
			 * 
			 * responsibilityName=cookie.getValue();
			 * 
			 * }
			 */}

		log.info("Concurrent program Name to get Search list of next pages-------------:"
				+ concName + "-=-=-=-=-=-=-=-=" + responsibilityName);

		List<ConcurrentPrograms> sarchedResult = concurrentProgramsService
				.getSearchRelatedConc(concName, responsibilityName, userName);
		PagedListHolder<ConcurrentPrograms> pagedListHolder = new PagedListHolder<ConcurrentPrograms>(
				sarchedResult);

		int page = pageId > 0 ? pageId : 0;
		pagedListHolder.setPage(page);
		int pageSize = 10;
		int message_Status = pagedListHolder.getNrOfElements();
		pagedListHolder.setPageSize(pageSize);
		model.addAttribute("pagination", "searchConcurrent");
		model.addAttribute("pagedListHolder", pagedListHolder);
		model.addAttribute("respName", responsibilityName);
		model.addAttribute("message_Status", message_Status);
		model.addAttribute("concurrentName", concName);
		return "search_ConcurrentPrograms";

	}

	@RequestMapping(value = "/getSearchAndDashBoard")
	public String getSearchAndDashBoard(Model model) {

		model.addAttribute("msg",
				"Search ConcurrentProgram Report Details & Download Dashborads");
		model.addAttribute("err_msg", "");
		return "searchAndDashBoard";

	}

	@RequestMapping(value = "/getConcurrentProgramDetailsList", method = RequestMethod.GET)
	public String getConcurrentProgramDetailsList(Model model, int pageId,
			String progName, String to_date, String from_date)
			throws WorkbenchServiceException, WorkbenchDataAccessException {

		List<CPDetails> result = concurrentProgramsService
				.getSpecificCPDetails(progName, from_date, to_date);
		log.info(progName + " " + from_date + " " + to_date);
		log.info(result.size());
		if (result.size() != 0) {
			PagedListHolder<CPDetails> pagedListHolder = new PagedListHolder<CPDetails>(
					result);
			int page = pageId > 0 ? pageId : 0;
			pagedListHolder.setPage(page);
			int pageSize = 20;
			pagedListHolder.setPageSize(pageSize);
			model.addAttribute("pagedListHolder", pagedListHolder);
			model.addAttribute("programName", progName);
			/* return "concPrgmPerformance"; */
			return "searchAndDashBoard";
		}
		model.addAttribute("err_msg", "No Data Found");
		/* return "concPrgmPerformance"; */
		return "searchAndDashBoard";
	}

	@RequestMapping(value = "/generateGraph", method = RequestMethod.GET)
	public void generateGraph(HttpServletRequest request,
			HttpServletResponse response, String progName, String to_date,
			String from_date, String chartType)
			throws WorkbenchServiceException, WorkbenchDataAccessException {
		String pdfFileName = null;
		int BUFFER_SIZE = 4096;
		log.info(progName + " " + from_date + " " + to_date);
		if (chartType.equalsIgnoreCase("pie")) {
			pdfFileName = generateGraph.generatePieGraph(progName, from_date,
					to_date);
		} else {
			generateGraph.generateBarGraph(progName, from_date, to_date);
		}
		log.info("File name is " + pdfFileName);

		// Downloading file to Client System
		try {
			ServletContext context = request.getSession().getServletContext();
			String appPath = context.getRealPath("");// code can be avoided
			System.out.println("appPath = " + appPath);// code can be avoided

			// construct the complete absolute path of the file
			String fullPath = pdfFileName;
			File downloadFile = new File(fullPath);
			/*
			 * FileInputStream inputStream = new FileInputStream(downloadFile);
			 * 
			 * // get MIME type of the file String mimeType =
			 * context.getMimeType(fullPath); if (mimeType == null) { // set to
			 * binary type if MIME mapping not found mimeType =
			 * "application/octet-stream"; } log.info("MIME type: " + mimeType);
			 * 
			 * // set content attributes for the response
			 * response.setContentType(mimeType);
			 * response.setContentLength((int) downloadFile.length());
			 * 
			 * // set headers for the response String headerKey =
			 * "Content-Disposition"; String headerValue =
			 * String.format("attachment; filename=\"%s\"",
			 * downloadFile.getName());
			 * log.info("Header Value--------------------"+headerValue);
			 * response.setHeader(headerKey, headerValue);
			 * 
			 * // get output stream of the response OutputStream outStream =
			 * response.getOutputStream();
			 * 
			 * byte[] buffer = new byte[BUFFER_SIZE]; int bytesRead = -1;
			 * 
			 * // write bytes read from the input stream into the output stream
			 * while ((bytesRead = inputStream.read(buffer)) != -1) {
			 * outStream.write(buffer, 0, bytesRead); }
			 * 
			 * inputStream.close(); outStream.close();
			 */// get your file as InputStream
			InputStream is = new FileInputStream(pdfFileName);
			// copy it to response's OutputStream
			IOUtils.copy(is, response.getOutputStream());
			// response.setContentType("application/pdf");
			response.flushBuffer();
		} catch (Exception e) {

		}
		// End of downloading
	}

	@RequestMapping(value = "/downloading")
	public String downloadingPage() {

		return "downloading";
	}

}
