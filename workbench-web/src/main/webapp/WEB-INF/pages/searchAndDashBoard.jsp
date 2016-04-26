<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tg" tagdir="/WEB-INF/tags"%>
<%-- <jsp:useBean id="pagedListHolder" scope="request" type="org.springframework.beans.support.PagedListHolder" />--%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>

<!DOCTYPE HTML>
<html>
<head>
<style type="text/css">
.inputField {
	-webkit-border-radius: 5px;
	-moz-border-radius: 5px;
	border-radius: 5px;
	border: 1px solid #8e8e8e;
	background-color: #f5f5f5;
	height: 16px;
	padding: 4px;
	padding-right: 28px;
	color: #4a4a4a;
	float: left;
}

.button {
	border: 0;
	padding: 0;
	margin: 0 0 0 -24px;
	width: 24px;
	height: 24px;
	float: left;
}

</style>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>WORKBENCH</title>
<link rel="stylesheet" type="text/css" href="resources/css/tcal.css" />
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
<script type="text/javascript" src="resources/js/tcal.js"></script>
<script type="text/javascript">
	function search1(progBasedSearch) {
		document.forms[0].action = progBasedSearch;
		//document.form.target.value="";
		document.form.submit();
		
	}
	function search2(progBasedSearch) {
		document.forms[0].action = progBasedSearch;
		document.forms[0].target="_blank";
		document.form.submit();
		
	}
	function getConcProgramName (progName) {
		
		var ccpName=document.getElementById(progName).value;
		window.open('listOfCPNames.html?progName='+ccpName,'mywindow','width=300,height=400,left=950,top=100,resizable=no,screenX=650,screenY=100');
		
	}
	function sendAndget(concName){
		
		document.getElementById("progName").value=concName;
		
	}
</script>
</head>
<body>

	<%@ include file="/WEB-INF/templates/header.jsp"%>
	<form action="" id="searchAndDBform" name="form" target="">

		<div id="page_container">
			<br /> <br /> <br /> <br />
			<div>
				<h1 style="">${msg}</h2>
				<hr>
				<br /> Enter Concurrent Program Name:
				<input type="search" name="progName" class="progName" 
				       onsearch="getConcProgramName('progName')" id=progName placeholder="Enter Name and Click Enter"/>
				     <br> To Date:<input
					type="text" name="to_date" class="tcal" value="" />&nbsp;&nbsp;
				From Date:<input type="text"  name="from_date" class="tcal" value="" /><br>
				
				<input type="button" value="Performance History" class="button"  onclick='search1("getConcurrentProgramDetailsList")'>
				<br>
		Select Graph Type
				 <select name="chartType">
					<option>select</option>
					<option value="Pie">Pie Chart</option>
					<!-- <option value="Bar">Bar Charts</option> -->
				</select>
				 <input type="hidden" name="pageId" value="0" />
				</br>
				<input type="button"  value="Click Here To Generate Graph" class="button" onclick='search2("generateGraph")'>
			</div>
		<br /> <br />
		<hr/>
		</div>
		
			<div id="page_container">
		<div id="breadcrumb_area">
			<div id="breadcrumb"></div>
		</div>
		<c:if test="${err_msg!=null}">
		<p align="center" style="color : Red ; font-size :150%;  ">${err_msg}</p>
		
		</c:if>
		<c:if test="${err_msg==null}">
		<div class="clearfix"></div>
		<h1>${programName} Program Performance History</h1>
		
		<form method="GET" >
		
		<%-- <div style="text-align: right; margin-right: 28px;">
					
				
			<div class="section_box">
			
				<div class="column_single">
					<table class="basic_grid" border="0" cellspacing="0"
						cellpadding="0">
						<tr>
						
							<th>Request Date</th>
						
							<th>Time Taken</th> 

							<th align="right" valign="top" class="right"></th>
						</tr>
						<c:forEach items="${parameters}" var="parameter"> 
						
						<c:forEach items="${pagedListHolder}"
								var="parameter">
							<tr>
								<td>${parameter.request_date}</td>
								<td>${parameter.process_time}</td>
								
							
						</c:forEach>
						
					</table>
					<input type="hidden" value="" name="req_id" id="req_id"/>
					<input type="hidden" value="" name="view_link" id="view_link"/>
<!-- 			<input type="hidden" value="" name="log_link" id="log_link"/> -->
					
				</div>
			</div>
			<div style="text-align: right; margin-right: 28px;">
			<div style="text-align: right; margin-right: 28px;">
					<tg:paging pagedListHolder="${pagedListHolder}"
						pagedLink="${pagedLink}" />
				</div>

			</div>
			<div class="clearfix"></div>
	</div>
 --%><!-- 	<input type="hidden" name="selectedRole"> -->
     <div style="text-align: right; margin-right: 28px;">
					<tg:paging pagedListHolder="${pagedListHolder}"
						pagedLink="${pagedLink}" />
				</div>

				<div class="section_box">


					<div class="column_single">
						<table class="basic_grid" border="0" cellspacing="0"
							cellpadding="0">
							<tr>
							<th>Request Date</th>
							<th>Time Taken</th> 
  						    <th align="right" valign="top" class="right"></th>
						    </tr>
							<c:forEach items="${pagedListHolder.pageList}" var="programs">
								<tr>
								<td>${programs.request_date}</td>
								<td>${programs.process_time}</td>
								</tr>
							</c:forEach>


						</table>
						
					</div>
				</div>
				<div style="text-align: right; margin-right: 28px;">
					<tg:paging pagedListHolder="${pagedListHolder}"
						pagedLink="${pagedLink}" />
				</div>
				<div class="clearfix"></div>
		</div>
     
     
     
        </c:if>
	<div class="clearfix"></div>
	</div>
	</form>
	</div>
	</div>
		
	</form>
	<%@ include file="/WEB-INF/templates/footer.jsp"%>
</body>
</html>
