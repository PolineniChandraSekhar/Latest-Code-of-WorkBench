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
			<a href="getResponsibilities?pageId=0">Responsibility||</a>${requestScope.respName}
			<a href="getConcurrentPrograms?pageId=0&respName=${requestScope.respName}">>>Concurrent
				Program||</a>${progName}
		</h3>

		<div id="content_main">

			<%-- // load our paging tag, pass pagedListHolder and the link --%>
			
		<!-- <div style=" width : 300px ; height : 100px;position: absolute ; top:50% ; left: 50%; "> -->
		<div>
		
		<h1 id="display2" style="color:RED;">Report  Not Generated</h1>
		</div>	
	</div>
	</div>
	<%@ include file="/WEB-INF/templates/footer.jsp"%>
</body>
</html>
