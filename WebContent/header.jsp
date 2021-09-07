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
<link href="./style/login.css" rel="stylesheet">
</head>
<body>
	<div class="header">
		<div class="navTitle">
			<span id="menuIcon" onClick="$('.menu').animate({height: 'toggle'}, 400);">
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
	<!-- The Modal -->
<div id="log" class="modal">
<div class="close" onclick="$('#log').fadeToggle()"></div>

  <!-- Modal Content -->
    <div class="login-container animate">
	    <form class="login-form" action="<%= response.encodeURL("login")%>" id="form-login" method="post">
	    	<div class="row riga">
	    		<div class="titolo">Effettua il Login:</div>
			    <label id="name-label" for="uname"><b>Username</b></label>
			    <input type="text" placeholder="Enter Username" id="name" name="uname">
			
			    <label id="psw-label" for="psw"><b>Password</b></label>
			    <input type="password" placeholder="Enter Password" id="psw" name="psw">
			
		      	<button class="login-btn" type="submit">Login</button>
		      	<div class="login-btn">
			      	<a href="<%=response.encodeURL("./signup.jsp")%>">
			      		Registrati
			      	</a>
		      	</div>
	      	</div>
	  	</form>
    </div>
</div> 

	<% 
		}
		else if(role.equals("admin") || role.equals("user")){
	%>
	<div id="log" class="modal">
<div class="close" onclick="$('#log').fadeToggle()"></div>

	<div class="login-container animate">
	    <div class="container">
		    <div class="row riga-out justify-content-center">
			      <% 
					String ruolo = (String)session.getAttribute("role");
					if(ruolo.equals("admin")){
				  %> 
				<button class="login-btn"  type="submit" name="button" value="admin-area">Area amministratore</button>
				<% 
					}
				%>
					
				<button class="login-btn" type="submit" name="button" value="user-area">
					<a href="<%=response.encodeURL("./OrdiniUtente")%>">
						I Tuoi Ordini
					</a>
				</button>
				<button class="login-btn" type="submit" name="button" value="user-area">
					<a href="<%=response.encodeURL("./informazioniutente.jsp")%>">
						Le Tue Informazioni
					</a>
				</button>
				<button class="login-btn" type="submit" name="button" value="logout" id="logout">
					<a href="<%=response.encodeURL("./logout")%>">
						Logout	
					</a>
				</button>
			</div>
	    </div>
    </div>
</div>  
	<% 
		}
	%>
</body>
</html>