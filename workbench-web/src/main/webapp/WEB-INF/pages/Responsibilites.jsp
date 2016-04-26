<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tg" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="pagedListHolder" scope="request" type="org.springframework.beans.support.PagedListHolder" />
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%
if(session.getAttribute("uname")==null){
response.sendRedirect("login");
}
%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>WORKBENCH</title>

<link href="resources/css/css_reset.css" rel="stylesheet"
	type="text/css">
<link href="resources/css/theme.css" rel="stylesheet" type="text/css">
<link href="resources/css/pagination.css" rel="stylesheet"
	type="text/css">
<link href="resources/css/main_style.css" rel="stylesheet"
	type="text/css">
<link rel="shortcut icon" href="resources/images/avalonlogo_new.png"
	type="image/x-icon" />
<script language="javascript" src="resources/js/jquery-1.6.2.min.js"></script>
<script language="javascript" src="resources/js/main.js"></script>
<script language="javascript" src="resources/js/common.js"></script>
<script type="text/javascript">
function searchCheck() {
	
	var searchContent=document.getElementById("searchField").value;
	if(searchContent==""||searchContent==null){
		
		return false;
	}
	
}
</script>
<style type="text/css">
#searchbox{overflow:auto;}

.searchbox {
     -webkit-border-radius: 5px;
     -moz-border-radius: 5px;
     border-radius: 5px;
     border:1px solid #8e8e8e;
     background-color:#f5f5f5;
     height:16px;
     padding:4px;
     padding-right:28px;
     color:#4a4a4a;
     float:left;
 }

.button{
    border:0;
    padding:0;
    margin:0 0 0 -24px;
    width:24px;
    height:24px;
    
    float:left;
}


 a.test{
    color:blue; }
</style> 
</head>
<body>

	<%@ include file="/WEB-INF/templates/header.jsp"%>

	<div id="page_container">
		<div id="breadcrumb_area">

		</div>
		<div class="clearfix"></div>
		<h1>Responsibilities</h1>
		<br/>
		<div id="content_main">
			<c:url value="/getResponsibilities" var="pagedLink">
				<c:param name="pageId" value="~" />
			</c:url>
			<%-- // load our paging tag, pass pagedListHolder and the link --%>
			<form method="GET" action="searchReponsibility" onsubmit="return searchCheck();">
				
				<div class="search" align="right">
				<table>
					<tr>
					<td><img alt="search Image" src="resources/images/search_logo.png"></td>
					<td><div id="searchbox" >
						<!--  <input type="text" placeholder="Enter Responsibility Name" name="responsibilityName" style="width: 200px; height: 55pxvalue;background:transparent url(http://iconizer.net/files/iPhone_Toolbar_icons/orig/magnifyingglass.png) no-repeat scroll left center;
						"/> -->
						<input type="text" class="searchbox" placeholder="Enter Responsibility Name" 
						     id="searchField" name="responsibilityName" style="width: 200px; height: 55pxvalue;" value="${respName}"/>
						
					</div>
					</td>
					<td >
					<div id="serchButton">
						 <input type="submit" class="button" value="Search" style="width: 50px; height: 60pxvalue;" > 
					</div></td></tr>
					</table>
				</div>
				<br/>
				
				<div style="text-align: right; margin-right: 28px;">
					<tg:paging pagedListHolder="${pagedListHolder}"	pagedLink="${pagedLink}" />
				</div>

				<div class="section_box">


					<div class="column_single">
						<table class="basic_grid" border="0" cellspacing="0"
							cellpadding="0">
							<tr>
								<!--  <th>User Name</th> -->
								<th>Responsibility Name</th>
								<!-- <th>Start Date</th>
								<th>End Date</th> -->
								<!--   <th>Respnsibilty Key</th>  -->
								<th>Application Name</th>
								<th align="right" valign="top" class="right"></th>
							</tr>
							
							<c:forEach items="${pagedListHolder.pageList}"
								var="responsibilites">
								
								<tr>
									<!--   <td>${responsibilites.user_name}</td> -->
									<td><a href="getConcurrentPrograms?pageId=0&respName=${responsibilites.responsibility_name}"  class="test">${responsibilites.responsibility_name}</a></td>
									<%-- <td>${responsibilites.start_date}</td>
									<td>${responsibilites.end_date}</td> --%>
									<!--  <td>${responsibilites.responsibility_key}</td>  -->
									<td>${responsibilites.application_name}</td>
									<!--   <td nowrap class="right"><a
										href="leadPersonEditPage?id=${leadPerson.leadPersonId}"><img
											src="resources/images/ico_edit.gif" title="Edit" width="18"
											height="20" class="icon"></a> <a
										href="deleteLeadPerson?id=${leadPerson.leadPersonId}"><img
											src="resources/images/ico_delete.gif" title="Delete"
											width="18" height="20" class="icon"></a></td>  -->
								</tr>
							</c:forEach>


						</table>
						  <c:if test="${message_Status==0}">
							
							<p align="center" style="color : Red ; font-size :150%;  ">There Are No Responsibilities To Display</p>
							
							</c:if>
					</div>
				</div>
				<div style="text-align: right; margin-right: 28px;">
					<tg:paging pagedListHolder="${pagedListHolder}"
						pagedLink="${pagedLink}" />
				</div>
				<div class="clearfix"></div>
		</div>
		<input type="hidden" name="selectedRole">
		<div class="button_row">
			<div class="buttion_bar_type2">

				<!-- <input type="button" value="Add New Lead Person" class="button"> -->

			</div>
			<div class="clearfix"></div>
		</div>
		</form>
	</div>
	</div>
	<%@ include file="/WEB-INF/templates/footer.jsp"%>
</body>
</html>
