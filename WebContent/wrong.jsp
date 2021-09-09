<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
<link href="./style/wrong.css" rel="stylesheet">
<meta charset="ISO-8859-1"></meta>

	<%@ include file="headlink.jsp" %>
<title>Ops...</title>
</head>
<body>
<div class="wrapper">
	<%@ include file="header.jsp" %>
	
	<div class="contenuto">
		<img id="something-wrong" src="./icon/barrier.png" alt="wrong">
		<div class="inner">
			<h1>Qualcosa è andato storto...</h1>
			<h4>Siamo spiacenti, riprovi.</h4>
		</div>
	</div>
	
	<%@ include file="footer.jsp" %>
</div>
</body>
</html>