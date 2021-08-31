<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
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
			<a href="<%=response.encodeURL("./") %>">
				Le Mani di Nonna
			</a>
			<span id="userIcon">
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

</body>
</html>