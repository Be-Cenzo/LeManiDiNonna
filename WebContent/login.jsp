<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, model.*, javax.sql.DataSource"%>
    
    <% 
		String role1 = (String)session.getAttribute("role");
	    if(role1 != null){
			if(role1.equals("user") || role1.equals("user")){
				response.sendRedirect("./index.jsp");
			}
	    }
	%>
<!DOCTYPE html>
<html>
<head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">

<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta charset="UTF-8">
<title>Login</title>
</head>
<body>
<div class="wrapper">
	<%@ include file="header.jsp" %>
	<%
		Integer error = (Integer) request.getSession().getAttribute("loginError");
		String errorClass = "";
		if(error != null && error == 1){
			errorClass = "error";
		}
	%>
	
	<div class="contenuto">
		<div class="login-container-page">
		    <form id="form-page" class="login-form-page <%=errorClass %>" action="login" method="post">
		    	<div class="row riga">
		    		<div class="titolo">Effettua il Login:</div>
		    		<%
		    		if(error != null && error == 1){
		    		%>
		    			<div style="color:#bf1b1b">L'Email e/o la Password inserite sono errate</div>
		    		<%
		    		}
		    		%>
				    <label id="name-page-label" for="uname"><b>Username</b></label>
				    <input type="text" placeholder="Enter Username" id="name-page" name="uname">
				
				    <label id="psw-page-label" for="psw"><b>Password</b></label>
				    <input type="password" placeholder="Enter Password" id="psw-page" name="psw">
				
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
	
	<%@ include file="footer.jsp" %>
</div>

<script type="text/javascript">  

$(document).ready(function(){
	const login = document.getElementById('form-page');
	login.addEventListener('submit', function(event){
	var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
	var name=$("#name-page").val();  
	var password=$("#psw-page").val();
	if (name==null || name==""){  
	  	$("#name-page-label").html("<b>Username</b> - non può essere vuoto");
	  	event.preventDefault();
	}else if(!name.match(mailformat)){
	  	$("#name-page-label").html("<b>Username</b> - email non valida");
		event.preventDefault();
	}else if(password.length == 0){  
		$("#psw-page-label").html("<b>Password</b> - non può essere vuota");
		event.preventDefault();
	  }  
	});
});
</script>  
</body>
</html>