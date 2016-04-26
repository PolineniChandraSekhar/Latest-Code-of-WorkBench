package com.avalon.workbench.beans.concurrntReport;

public class QueryStatus {
private String meaning;
public String getMeaning() {
	return meaning;
}
public void setMeaning(String meaning) {
	this.meaning = meaning;
}

private String status;

public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}

private String parent_request_id;
public String getParent_request_id() {
	return parent_request_id;
}
public void setParent_request_id(String parent_request_id) {
	this.parent_request_id = parent_request_id;
}

}
