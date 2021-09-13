<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
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
		<a class="menu-element" href="<%=response.encodeURL("./chisiamo.jsp") %>">Chi Siamo</a>
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
		      	
			     <a href="<%=response.encodeURL("./signup.jsp")%>">
			      	<div class="login-btn">
			      		Registrati
		      		</div>
			     </a>
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
				<a href="<%=response.encodeURL("./aggiungiprodotti.jsp")%>">
				 	<button class="login-btn">
				 		Aggiungi un Prodotto
				 	</button>
				</a>
				<a href="<%=response.encodeURL("./aggiungiquantita.jsp")%>">
				 	<button class="login-btn">
				 		Aggiorna Quantità Prodotto
				 	</button>
				</a>
				<% 
					}
				%>
					
				
				<a href="<%=response.encodeURL("./OrdiniUtente")%>">
					<button class="login-btn">
						I Tuoi Ordini
					</button>
				</a>
				<a href="<%=response.encodeURL("./informazioniutente.jsp")%>">
					<button class="login-btn">
						Le Tue Informazioni
					</button>
				</a>
				<a href="<%=response.encodeURL("./logout")%>">
					<button class="login-btn">
						Logout	
					</button>
				</a>
			</div>
	    </div>
    </div>
</div>  
	<% 
		}
	%>
</body>
</html>