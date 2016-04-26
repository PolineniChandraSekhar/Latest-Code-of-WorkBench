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
function func(obj){
	//var ccpName=document.getElementById("ccpName").innerHTML;
	//alert(obj.firstChild.data);
	//alert(ccpName.user_concurrent_program_name);
	window.opener.sendAndget(obj.firstChild.data);
	window.close();

	
}

</script>
</head>
<body>

	<%@ include file="/WEB-INF/templates/header.jsp"%>

	<div id="page_container">
	<h1>${msg}</h1>
	<table>
	<c:forEach items="${list}" var="cpName">
	<tr>
	<td onclick="func(this)">${cpName.user_concurrent_program_name}</td>
	<!--  <a href="#"  onclick="func(${cpName.user_concurrent_program_name})" id="${cpName.user_concurrent_program_name}">${cpName.user_concurrent_program_name}</a><br>-->
	</tr>
	</c:forEach>
	</table>
	</div>
	<%@ include file="/WEB-INF/templates/footer.jsp"%>
</body>
</html>



