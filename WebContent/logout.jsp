<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="./style/login.css" rel="stylesheet">
</head>
<body>

<!-- The Modal -->
<div id="log" class="modal">
<div class="close" onclick="$('#log').fadeToggle()"></div>

	<div class="login-container animate">
	    <div class="container">
	      <% 
			String ruolo = (String)session.getAttribute("role");
			if(ruolo.equals("admin")){
		  %> 
		<button type="submit" name="button" value="admin-area">Area amministratore</button>
		<% 
			}
		%>
			
		<button type="submit" name="button" value="user-area">Area Utente</button>
		<button type="submit" name="button" value="logout" id="logout">
			<a href="<%=response.encodeURL("./logout")%>">
				Logout	
			</a>
		</button>
	    </div>
    </div>
</div>  

</body>
</html>