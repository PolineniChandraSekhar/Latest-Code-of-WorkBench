package com.avalon.workbench.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class WorkBenchFilter implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		Logger log = Logger.getLogger(WorkBenchFilter.class);
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		log.info("Request Url is:=====" + request.getRequestURI());
		HttpSession session = request.getSession();
		String totalUrl = request.getRequestURI();
		String url = request.getRequestURI().substring(
				request.getContextPath().length());

		if (url.equals("/")) {
			log.info("------------Redirecting To Login Page---------");
			response.sendRedirect("login");
		}

		if (url.equals("/login") || url.equals("/checkUser")
				|| totalUrl.contains("resources")) {
			log.info("No Security");

			response.setHeader("Cache-Control","no-cache, no-store, must-revalidate"); // HTTP 1.1.
			response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
			response.setDateHeader("Expires", 0); // Proxies.
			chain.doFilter(request, response);
			log.info("Post processing");
		}
		if (!((url.equals("/") || url.equals("/login") || url
				.equals("/checkUser")) || totalUrl.contains("resources"))) {
			log.info("Security");
			// else{
			if (session.getAttribute("uname") == null
					|| session.getAttribute("uname") == "") {
				log.info("the value of uname in session Attribute is========="
						+ session.getAttribute("uname"));
				response.sendRedirect("login");
			} else {
				// if(session.getAttribute("uname")!=null ||
				// session.getAttribute("uname")!=""){
				log.info("uname Attribute value is :========"
						+ session.getAttribute("uname"));
				
				 response.setHeader("Cache-Control","no-cache, no-store, must-revalidate"); // HTTP 1.1.
				 response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
				 response.setDateHeader("Expires", 0); // Proxies.
				 chain.doFilter(request, response);
       			 log.info("Post processing");
			}
		}

	}

	public void init(FilterConfig arg0) throws ServletException {

	}

}
