<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
<link href="./style/accessdenied.css" rel="stylesheet">
<meta charset="ISO-8859-1"></meta>
<title>Access Denied</title>
</head>
<body>
	<div class="wrapper">
	<%@ include file="header.jsp" %>
	
	<div class="contenuto">
		<div class="row justify-content-center">
			<img id="access-denied" src="https://c.tenor.com/uUnfd6BfpEgAAAAC/you-shall-not-pass.gif" alt="not found">
			<div class="inner">
				<h1>Accesso negato</h1>
				<h4>Siamo spiacenti ma la pagina da lei richiesta non è accessibile, effettui il login e poi riprovi.</h4>
			</div>
		</div>
	</div>
	
	<%@ include file="footer.jsp" %>
</div>
</body>
</html>