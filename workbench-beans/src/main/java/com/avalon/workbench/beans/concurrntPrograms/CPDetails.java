package com.avalon.workbench.beans.concurrntPrograms;

public class CPDetails {
	
	public CPDetails(){}
	private String user_concurrent_program_name;
	private String request_id;
	private String status;
	private String request_date;
	private String logfile_name;
	private String outfile_name;
	private String process_time;
	
	
	public String getProcess_time() {
		return process_time;
	}
	public void setProcess_time(String process_time) {
		this.process_time = process_time;
	}
	public String getUser_concurrent_program_name() {
		return user_concurrent_program_name;
	}
	public void setUser_concurrent_program_name(String user_concurrent_program_name) {
		this.user_concurrent_program_name = user_concurrent_program_name;
	}
	public String getRequest_id() {
		return request_id;
	}
	public void setRequest_id(String request_id) {
		this.request_id = request_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRequest_date() {
		return request_date;
	}
	public void setRequest_date(String request_date) {
		this.request_date = request_date;
	}
	public String getLogfile_name() {
		return logfile_name;
	}
	public void setLogfile_name(String logfile_name) {
		this.logfile_name = logfile_name;
	}
	public String getOutfile_name() {
		return outfile_name;
	}
	public void setOutfile_name(String outfile_name) {
		this.outfile_name = outfile_name;
	}
	

	
	

}
