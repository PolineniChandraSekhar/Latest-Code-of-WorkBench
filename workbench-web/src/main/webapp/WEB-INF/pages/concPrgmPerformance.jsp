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

<script language="javascript">

function outputView(kk,type){		
	var out=document.getElementById(kk+type).value;
	//alert('reqid '+kk+'  out :'+out);
	document.getElementById("req_id").value=kk;
	document.getElementById("view_link").value=out;
	alert(out);
}
</script>
</head>
<body>

	<%@ include file="/WEB-INF/templates/header.jsp"%>

	<div id="page_container">
		<div id="breadcrumb_area">
			<div id="breadcrumb"></div>
		</div>
		<c:if test="${msg!=null}">
		<p align="center" style="color : Red ; font-size :150%;  ">${msg}</p>
		
		</c:if>
		<c:if test="${msg==null}">
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
	<%@ include file="/WEB-INF/templates/footer.jsp"%>
</body>
</html>



