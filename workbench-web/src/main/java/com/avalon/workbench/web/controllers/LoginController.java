package com.avalon.workbench.web.controllers;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.avalon.workbench.beans.responsibilites.Responsibilites;
import com.avalon.workbench.repository.exception.WorkbenchDataAccessException;
//import com.avalon.workbench.repository.responsibilites.TestRRepository;
//import com.avalon.workbench.repository.user.TestRepository;
import com.avalon.workbench.services.exception.WorkbenchServiceException;
import com.avalon.workbench.services.responsibilites.ResponsibilitesService;
//import com.avalon.workbench.services.responsibilites.TestRService;
//import com.avalon.workbench.services.user.TestService;
import com.avalon.workbench.services.user.UserService;

@Controller
public class LoginController {

	protected static final Logger log = Logger.getLogger(LoginController.class);

	@Autowired
	@Qualifier("UserServiceImpl")
	UserService service;

	@Autowired
	@Qualifier("ResponsibilitesServiceImpl")
	ResponsibilitesService responsibilitesService;
	
		
	/*@Autowired
	@Qualifier("TestServiceImpl")
	TestService  tservice;
		
	@Autowired
	@Qualifier("TestRServiceimpl")
	TestRService  trservice;
*/
	
	@RequestMapping(value = "/checkUser", method = RequestMethod.POST)
	public String checkUser(String uname,String pass, Model model,HttpSession session) throws Exception {
		log.info("inside checkUser......");
		uname=uname.toUpperCase();//for case insensitive authentication ON 19-DEC-2015
		boolean flag = service.AuthenticateUser(uname, pass);
		if (flag){
			session.setAttribute("uname", uname);
			return "redirect:getResponsibilities?pageId=0";
		}
		else{
			model.addAttribute("status", "invalid Username");
			return "login";
		}
			
	}

	@RequestMapping(value = "/getResponsibilities", method = RequestMethod.GET)
	public String getResponsibilities(@RequestParam("pageId") int pageId,HttpSession session, Model model) throws WorkbenchServiceException {
		log.info("inside getResponsibilities......");
		String uname=(String) session.getAttribute("uname");
		if(uname==null){
			return "login";
		}
		List<Responsibilites> responsibilites = responsibilitesService
				.getResonsibilites(uname);
		
		Collections.sort(responsibilites);
	
		log.info("responsibilities"+responsibilites);
		
		PagedListHolder<Responsibilites> pagedListHolder = new PagedListHolder<Responsibilites>(
				responsibilites);
		
		int page = pageId > 0 ? pageId : 0;
		pagedListHolder.setPage(page);
		int pageSize = 10;
		pagedListHolder.setPageSize(pageSize);
		model.addAttribute("pagedListHolder", pagedListHolder);
		return "Responsibilites";
	}


	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request) throws Exception {
		if(request.getSession().getAttribute("uname")!=null){
			return "redirect:getResponsibilities?pageId=0";
		}
		return "login";
	}
	@RequestMapping(value = "/j_spring_security_logout", method = RequestMethod.GET)
	public String logout(HttpSession httpSession) throws Exception {
		httpSession.invalidate();
		return "login";
	}
	
	@RequestMapping(value = "/searchReponsibility", method = RequestMethod.GET)
	public String getsearchReponsibility(@RequestParam("responsibilityName") String  respName,HttpSession session,Model model,HttpServletResponse response) throws WorkbenchDataAccessException{
		String userName=(String)session.getAttribute("uname");
		log.info("Responsibility Name============="+respName+"usename============"+userName);
		Cookie searchRespCookie=new Cookie("responsibilityName",respName);
		response.addCookie(searchRespCookie);
		List<Responsibilites> respNames_search = responsibilitesService.getSearchRelatedResp(respName,userName); 
		PagedListHolder<Responsibilites> pagedListHolder = new PagedListHolder<Responsibilites>(respNames_search);
		int pageId=0;
		int page = pageId > 0 ? pageId : 0;
		pagedListHolder.setPage(page);
		int pageSize = 10;
		pagedListHolder.setPageSize(pageSize);
		int message_Status=pagedListHolder.getNrOfElements();
		model.addAttribute("message_Status",message_Status);
		model.addAttribute("pagedListHolder", pagedListHolder);
		model.addAttribute("respName", respName);
		return "search_Responsibilites";
	}
	@RequestMapping(value = "/searchReponsibility2", method = RequestMethod.GET)
	public String getsearchReponsibility2(@RequestParam("pageId") int pageId,HttpSession session,Model model,HttpServletRequest request) throws WorkbenchDataAccessException{
		String userName=(String)session.getAttribute("uname");
		Cookie [] cookies=request.getCookies();
		String respName=null;
		for(Cookie cookie : cookies){
			if(cookie.getName().equals("responsibilityName")){
				respName=cookie.getValue();
			}
			}
		log.info("Responsibility Name============="+respName+"usename============"+userName);
		
		List<Responsibilites> respNames_search = responsibilitesService.getSearchRelatedResp(respName,userName); 
		PagedListHolder<Responsibilites> pagedListHolder = new PagedListHolder<Responsibilites>(respNames_search);
		int page = pageId > 0 ? pageId : 0;
		pagedListHolder.setPage(page);
		int pageSize = 10;
		pagedListHolder.setPageSize(pageSize);
		int message_Status=pagedListHolder.getNrOfElements();
		model.addAttribute("message_Status",message_Status);
		model.addAttribute("pagedListHolder", pagedListHolder);
		model.addAttribute("respName", respName);
		return "search_Responsibilites";
	}
	
	
}	

		
		/*tservice.testMethod();
		log.info("After Calling Method in Controller");*/
		
		
	
		
		/*@RequestMapping(value = "/testingcall", method = RequestMethod.POST)
		public void  testingCall() throws Exception {
			log.info("testingCall method in Login Controller.....");
			tripository.testMethod1();
			log.info("After Calling Method in Repository");
		
		}*/
	

		
	
	
	
	
	
	
	
	
	
	

