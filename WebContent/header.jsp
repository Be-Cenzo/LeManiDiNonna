<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" type="image/png" href="./icon/favicon.png"/>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Handlee&display=swap" rel="stylesheet"> 
<link href="https://fonts.googleapis.com/css2?family=Princess+Sofia&display=swap" rel="stylesheet"> 
<link href="./style/header.css" rel="stylesheet">
<meta charset="UTF-8">
<base href="/LeManiDiNonna/">
</head>
<body>
	<div class="header">
		<div class="navTitle">
			<span id="menuIcon" onClick="$('.menu').animate({width: 'toggle'});">
				<img src="./icon/menu.svg" alt="menu"/>
			</span>
			<span id="invisibleIcon" style="opacity: 0">
				<img src="./icon/menu.svg" alt="menu"/>
			</span>
			<a href="<%=response.encodeURL("./") %>">
				Le Mani di Nonna
			</a>
			<span id="userIcon" onclick="document.getElementById('log').style.display='block'">
				<img src="./icon/user.svg" alt="user"/>
			</span>
			<a href="<%=response.encodeURL("./carrello.jsp") %>">
				<span id="cartIcon">
					<img src="./icon/carrello.svg" alt="carrello"/>
				</span>
			</a>
		</div>
	</div>
	<div class="menu">
		<a class="menu-element" href="<%=response.encodeURL("./prodotti.jsp") %>">Prodotti</a>
	</div>
	
	
	
	<% 
		String role = (String)session.getAttribute("role");
		if(role == null || role.equals("guest")){
	%> 
	<h1>PISELLONE</h1>
	<%@include file="login.jsp" %>
	<% 
		}
		else if(role.equals("admin") || role.equals("user")){
	%>
	<%@include file="logout.jsp" %>
	<% 
		}
	%>
	
	
	
</body>
</html>