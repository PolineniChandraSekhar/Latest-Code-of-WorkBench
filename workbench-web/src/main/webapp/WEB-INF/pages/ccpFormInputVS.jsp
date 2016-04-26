<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tg" tagdir="/WEB-INF/tags"%>
<%-- <jsp:useBean id="pagedListHolder" scope="request" type="org.springframework.beans.support.PagedListHolder" />--%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>

<!DOCTYPE HTML>
<html>
<head>
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
<script type="text/javascript" src="resources/js/tcal2.js"></script>

<script type="text/javascript">
	function downloading() {
		i = 0;
		document.forms[0].style.display = "none";
		document.getElementById("image").style.display = "";
		getStatus();
		if (i == 0) {
			i == 1;
			document.forms[0].submit();
		}
	}
	function getStatus() {

		var status = getCookie("status");
		if (status == "success") {
			document.getElementById("image").style.display = "none";
			document.getElementById("display1").style.display = "";
			document.forms[0].style.display = "";
		} else if (status == "failure") {
			document.getElementById("display2").style.display = "";
			document.getElementById("image").style.display = "none";
			document.forms[0].style.display = "";
		} else {
			setTimeout(function() {
				getStatus()
			}, 1000);
		}
	}

	function getCookie(cname) {
		var name = cname + "=";
		var ca = document.cookie.split(';');
		for ( var i = 0; i < ca.length; i++) {
			var c = ca[i];
			while (c.charAt(0) == ' ')
				c = c.substring(1);
			if (c.indexOf(name) == 0)
				return c.substring(name.length, c.length);
		}

		//alert("No value");
		return "";
	}
</script>
</head>
<body>

	<%@ include file="/WEB-INF/templates/header.jsp"%>

	<div id="page_container">
		<div id="breadcrumb_area">
			<!-- 	<div id="breadcrumb">
				<ul>
					<li><a href="getResponsibilities?pageId=0">Home</a>&nbsp;&nbsp;</li>

				</ul>
			</div>
 -->
		</div>
		<div class="clearfix"></div>
		<%-- <h1><u>${respName}>${concurrentName}</u>>${progName}</h1> --%>
		<h3>
			<a href="getResponsibilities?pageId=0">Responsibility||</a>${respName}
			<a href="getConcurrentPrograms?pageId=0&respName=${respName}">>>Concurrent
				Program||</a>${progName}
		</h3>

		<div id="content_main">
			<div>
				<img alt="downloading" src="resources/images/download.gif"
					style="display: none;" id="image" align="middle">
				<h1 id="display1" style="display: none;">Successfully
					Downloaded</h1>
				<h1 id="display2" style="display: none; color: RED;">Dowload
					Failure Please Contact The Developer</h1>
			</div>
			<!-- // load our paging tag, pass pagedListHolder and the link -- onsubmit="downloading()"-->
			<form method="GET" action="getReport" onsubmit="downloading()">
				 <input type="hidden" value="${progName}" name="progName" /> <input
					type="hidden" value="${shortName}" name="shortName" /> <input
					type="hidden" value="${concurrentName}" name="concurrentName" /> <input
					type="hidden" value="${respName}" name="respName" />
				<div class="section_box">
					<div class="column_single">
						<table class="basic_grid" border="0" cellspacing="0"
							cellpadding="0">
							
							<c:forEach items="${parameters}" var="parameter">
								<tr>
									<td><c:if
											test="${parameter.prompt ne 'DebugFlag' && parameter.default_Value ne 'PER_BUSINESS_GROUP_ID'}">
										${parameter.prompt}
									</c:if></td>
									<td>
									<c:if
											test="${parameter.prompt ne 'DebugFlag' && parameter.default_Value ne 'PER_BUSINESS_GROUP_ID' 
											&& parameter.flexVSName ne 'FND_STANDARD_DATE' && parameter.flexVSName ne 'PER_DATES'
											&& parameter.flexVSName ne 'PER_DATES_STANDARD'
											&& parameter.value_set_type ne 'I'}">
											<input type="text" name="${parameter.prompt}">
											<c:if test="${parameter.required_flag =='Y'}">
												<font color="red">*</font>
											</c:if>
										</c:if>
									<c:if test="${parameter.value_set_type eq 'I'}">
											<%-- <input type="text" name="${parameter.prompt}"> --%>
											<select name="${parameter.prompt}">
											<option>--Select One--</option>
											<c:forEach items="${parameter.flex_Values}" var="fvalues">
												<option value="${fvalues}">${fvalues}</option>
											</c:forEach>
											</select>
											<c:if test="${parameter.required_flag =='Y'}">
												<font color="red">*</font>
											</c:if>
										</c:if>
										
										 <c:if
											test="${parameter.flexVSName eq 'FND_STANDARD_DATE' || parameter.flexVSName eq 'PER_DATES'
															  || parameter.flexVSName eq 'PER_DATES_STANDARD'}">
											<input type="text" name="${parameter.prompt}" class="tcal"
												value="" />
											<c:if test="${parameter.required_flag =='Y'}">
												<font color="red">*</font>
											</c:if>
										</c:if></td>
									<c:if test="${parameter.prompt eq 'DebugFlag'}">
										<input type="hidden" name="${parameter.prompt}"
											value="${parameter.default_Value}">
									</c:if>
									<c:if
										test="${parameter.default_Value eq  'PER_BUSINESS_GROUP_ID'}">
										<input type="hidden" name="${parameter.prompt}"
											value="${bgID}">
									</c:if>


								</tr>
							</c:forEach>


						</table>
						<div>
						Do you want to mail the Report ? 
						<input type="radio" name="confirm" value="yes" id="yBox">YES&nbsp;&nbsp;
						<input type="radio" name="confirm" value="no" id="nBox">NO
						<input type="text" placeholder="Enter mail Id" style="display:none;" id="mail" name="mailId">
						</div>
						<c:if test="${paramSize!=0}">
							<input type="submit" value="Submit" class="button">
							<a href="getConcurrentPrograms?pageId=0&respName=${respName}"><input
								type="button" value="Cancel" class="button" /></a>
						</c:if>
						<c:if test="${paramSize==0}">
							<h1 style="color: red; font-size: 15px; text-align: center;">
								There are no Parameters to display</h1>
						</c:if>
					</div>
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
		<!-- <div style=" width : 300px ; height : 100px;position: absolute ; top:50% ; left: 50%; "> -->

	</div>
	</div>
	<%@ include file="/WEB-INF/templates/footer.jsp"%>
</body>
</html>
