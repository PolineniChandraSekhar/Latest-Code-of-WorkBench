<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tg" tagdir="/WEB-INF/tags"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>WORKBENCH</title>
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
<link href="resources/css/css_reset.css" rel="stylesheet"
	type="text/css">
<link href="resources/css/theme.css" rel="stylesheet" type="text/css">
<link href="resources/css/pagination.css" rel="stylesheet"
	type="text/css">
<link href="resources/css/main_style.css" rel="stylesheet"
	type="text/css">
<link rel="shortcut icon" href="resources/images/avalonlogo_new.png"
	type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="resources/css/tcal.css" />
<script language="javascript" src="resources/js/jquery-1.6.2.min.js"></script>
<script language="javascript" src="resources/js/main.js"></script>
<script language="javascript" src="resources/js/common.js"></script>
<script type="text/javascript" src="resources/js/tcal.js"></script>
<script language="javascript">

function outputView(kk,type){		
	var out=document.getElementById(kk+type).value;
	//alert('reqid '+kk+'  out :'+out);
	document.getElementById("req_id").value=kk;
	document.getElementById("view_link").value=out;
	alert(out);
}
function search1(progBasedSearch) {
	//alert("reached the method");
	document.forms[0].action = progBasedSearch;
	//document.form.target.value="";
	document.form.submit();
	
}
</script>
</head>
<body>

	<%@ include file="/WEB-INF/templates/header.jsp"%>

	<div id="page_container">
		<div id="breadcrumb_area">
			<div id="breadcrumb"></div>

		</div>
		<div class="clearfix"></div>
		<h1>List History</h1>
		<form method="GET" action="generateFile" name="form">
		<br>From Date:<input type="text" name="from_date" class="tcal" value="${from_date}" /><br/>
			To Date:<input type="text" name="to_date" class="tcal" value="${to_date}" /> <br> 
			<input type="button" value="Search History" class="button"
			                 onclick='search1("getConcurrentProgramList")'> <br>
		<c:url value="/getConcurrentProgramList?from_date=${from_date}&to_date=${to_date}" var="pagedLink">
			<c:param name="pageId" value="~" />
		</c:url>
		<c:if test="${param.pageId==0}">
		<input type="hidden" name="pageId" value="0"/>
		</c:if>
			<div style="text-align: right; margin-right: 28px;">
				<tg:paging pagedListHolder="${pagedListHolder}"
					pagedLink="${pagedLink}" />
			</div>

			<div class="section_box">

				<div class="column_single">
					<table class="basic_grid" border="0" cellspacing="0"
						cellpadding="0">
						<tr>
							<th>Concurrent Program Name</th>
							<th>Request ID</th>
							<th>Status</th>
							<th>Request Date</th>
							<th>Output File</th>
							<th>Log File</th>
							<th align="right" valign="top" class="right"></th>
						</tr>
						<%-- 						<c:forEach items="${parameters}" var="parameter">  --%>
						<tr><td>${msg}</td></tr>
						<c:forEach items="${pagedListHolder.pageList}" var="parameter">
							<tr>
								<td>${parameter.user_concurrent_program_name}</td>
								<td>${parameter.request_id}</td>
								<td>${parameter.status}</td>
								<td>${parameter.request_date}</td>
								<%-- <td>${parameter.logfile_name}</td>--%>
								<td width="120"><c:if
										test="${parameter.status eq 'Normal'}">
										<input type="submit" style="width: 50px; height: 60pxvalue;"
											value="Output" name="${parameter.request_id}"
											id="${parameter.request_id}"
											onClick="outputView(${parameter.request_id},'OP');" />
									</c:if> <c:if test="${parameter.status ne 'Normal'}">
										<input type="submit" style="width: 50px; height: 60pxvalue;"
											value="Output" name="${parameter.request_id}"
											id="${parameter.request_id}"
											onClick="outputView(${parameter.request_id},'OP');" disabled />
									</c:if></td>
								<td><c:if test="${parameter.status eq 'Normal'}">
										<input type="submit" style="width: 50px; height: 60pxvalue;"
											value="Logfile" name="${parameter.request_id}"
											id="${parameter.request_id}"
											onClick="outputView(${parameter.request_id},'LO');" />
									</c:if> <c:if test="${parameter.status ne 'Normal'}">
										<input type="submit" style="width: 50px; height: 60pxvalue;"
											value="Logfile" name="${parameter.request_id}"
											id="${parameter.request_id}"
											onClick="outputView(${parameter.request_id},'LO');"
											disabled="disabled" />
									</c:if></td>
							</tr>
							<%-- 							 <c:if test="${parameter.status==\"Normal\"}"> --%>
							<input type="hidden" value="${parameter.outfile_name}"
								id="${parameter.request_id}OP" />
							<input type="hidden" value="${parameter.logfile_name}"
								id="${parameter.request_id}LO" />
							<%-- 							</c:if>								 --%>

						</c:forEach>

					</table>
					<input type="hidden" value="" name="req_id" id="req_id" /> <input
						type="hidden" value="" name="view_link" id="view_link" />
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
	<!-- 	<input type="hidden" name="selectedRole"> -->

	<div class="clearfix"></div>
	</div>
	</form>
	</div>
	</div>
	<%@ include file="/WEB-INF/templates/footer.jsp"%>
</body>
</html>



