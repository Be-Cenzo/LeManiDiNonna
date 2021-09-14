<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
<link href="./style/accessdenied.css" rel="stylesheet">
<meta charset="UTF-8"></meta>
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

	<%@ include file="headlink.jsp" %>
<title>Access Denied</title>
</head>
<body>
<div class="wrapper">
	<%@ include file="header.jsp" %>
	
	<div class="contenuto">
		<img id="access-denied" src="./icon/shield.png" alt="denied">
		<div class="inner">
			<h1>Accesso negato</h1>
			<h4>Siamo spiacenti ma la pagina da lei richiesta non è accessibile, effettui il login e poi riprovi.</h4>
		</div>
	</div>
	
	<%@ include file="footer.jsp" %>
</div>
</body>
</html>