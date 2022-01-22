<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,catalogoManagement.*, javax.sql.DataSource"%>
<!DOCTYPE html>
<html>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
<link href="./style/signup.css" rel="stylesheet">
<%@ include file="headlink.jsp" %>

<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta charset="UTF-8">
<title>Registrazione</title>
</head>
<body>
<div class="wrapper">
	
	<%@ include file="header.jsp" %>
	
	<% 
		String role1 = (String)session.getAttribute("role");
		if(role1 != null){
			if(role1.equals("user") || role1.equals("admin")){
				response.sendRedirect(response.encodeURL("./accessdenied.jsp"));
			}
		}
		
		Integer errore = (Integer)request.getAttribute("errore-registrazione");
	%> 
	
	<div class="contenuto">
		<div class="signup-container">
			<form id="signup-form" class="signup-form" action="<%= response.encodeURL("./Signup")%>" method="post">
				<div class="row riga">
				    <div class="titolo">Registrazione</div>
					
					<%
						if(errore != null && errore == 1){ 
					%>
							<label class="errore">L'email inserita non è valida</label>
					<%
						}
					%>
					<%
						if(errore != null && errore == 2){ 
					%>
							<label class="errore">Il numero di telefono inserito non è valido</label>
					<%
						}
					%>
					
					<%
						if(errore != null && errore == 3){ 
					%>
							<label class="errore">La password inserita non è valida</label>
					<%
						}
					%>
					<%
						if(errore != null && errore == 4){ 
					%>
							<label class="errore">Il Nome non può essere vuoto</label>
					<%
						}
					%><%
						if(errore != null && errore == 5){ 
					%>
							<label class="errore">Il Cognome non può essere vuoto</label>
					<%
						}
					%><%
						if(errore != null && errore == 6){ 
					%>
							<label class="errore">La Data di Nascita non è corretta</label>
					<%
						}
					%><%
						if(errore != null && errore == 7){ 
					%>
							<label class="errore">La Provincia non può essere vuota</label>
					<%
						}
					%><%
						if(errore != null && errore == 8){ 
					%>
							<label class="errore">Il Comune non può essere vuoto</label>
					<%
						}
					%><%
						if(errore != null && errore == 9){ 
					%>
							<label class="errore">La via non può essere vuota</label>
					<%
						}
					%><%
						if(errore != null && errore == 10){ 
					%>
							<label class="errore">Il Civico non è valido</label>
					<%
						}
					%><%
						if(errore != null && errore == 4){ 
					%>
							<label class="errore">Il CAP non può essere vuoto</label>
					<%
						}
					%>
				    <label id="email-label" for="email"><b>Email</b></label>
				    <input id="email" type="text" placeholder="Inserisci Email" name="email">
				
				    <label id="pswd-label" for="psw"><b>Password</b></label>
				    <input id="pswd" type="password" placeholder="Inserisci Password" name="psw">
				
				    <label id="re-psw-label" for="psw-repeat"><b>Reinserisci Password</b></label>
				    <input id="re-psw" type="password" placeholder="Reinserisci Password" name="psw-repeat">
				    
				    <label id="nome-label" for="name"><b>Nome</b></label>
				    <input id="nome" type="text" placeholder="Inserisci Nome" name="name">
				
				    <label id="surname-label" for="surname"><b>Cognome</b></label>
				    <input id="surname" type="text" placeholder="Inserisci Cognome" name="surname">
				
				    <label id="birth-label" for="birth"><b>Data di nascita</b></label>
				    <input id="birth" type="date" name="birth" >
				    
				    <label id="ig-label" for="ig"><b>Nome Instagram</b></label>
				    <input id="ig" type="text" placeholder="Inserisci Nome Instagram" name="ig">
				
				    <label id="phone-label" for="phone"><b>Numero di Telefono</b></label>
				    <input id="phone" type="text" placeholder="Inserisci Numero Di Telefono" name="phone">
				    
				    <label id="provincia-label" for="provincia"><b>Provincia</b></label>
				    <input id="provincia" type="text" placeholder="Inserisci Provincia" name="provincia">
					
				    <label id="comune-label" for="comune"><b>Comune</b></label>
				    <input id="comune" type="text" placeholder="Inserisci Comune" name="comune">
				
				    <label id="via-label" for="via"><b>Via</b></label>
				    <input id="via" type="text" placeholder="Inserisci Via" name="via">
				
				    <label id="civico-label" for="civico"><b>Civico</b></label>
				    <input id="civico" type="number" name="civico" min="0">
				
				    <label id="cap-label" for="cap"><b>CAP</b></label>
				    <input id="cap" type="text" placeholder="Inserisci CAP" name="cap">
			
				    <button type="submit" class="signup-btn">Sign Up</button>
			    </div>
			</form>
		</div>
  	</div>
	
	<%@ include file="footer.jsp" %>
</div>

<script>
$(document).ready(function(){
	const signup = document.getElementById('signup-form');
	signup.addEventListener('submit', function(event){
		var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
		var passwordformat = /^[a-zA-Z0-9]{6,}$/;
		var numeroformat = /^((00|\+)39[\. ]??)??3\d{2}[\. ]??\d{6,7}$/;
		var email = $("#email").val();
		var password = $("#pswd").val();
		var repassword = $("#re-psw").val();
		var name = $("#nome").val();
		var surname = $("#surname").val();
		var birth = $("#birth").val();
		var phone = $("#phone").val();
		var provincia = $("#provincia").val();
		var comune = $("#comune").val();
		var via = $("#via").val();
		var civico = $("#civico").val();
		var cap = $("#cap").val();
		if (email == null || email == ""){
			$("#email").addClass("error");
		  	$("#email-label").html("<b>Email</b> - non può essere vuota");
		  	event.preventDefault();
		}
		else if(!email.match(mailformat)){
			$("#email").addClass("error");
		  	$("#email-label").html("<b>Email</b> - inserire una email valida");
		  	event.preventDefault();
		}
		else{
			$("#email").removeClass("error");
		  	$("#email-label").html("<b>Email</b>");
		}
		if (password == null || password == "" || !password.match(passwordformat)){
			$("#pswd").addClass("error");
		  	$("#pswd-label").html("<b>Password</b> - inserire una password di almeno 6 caratteri");
		  	event.preventDefault();
		}
		else if(password != null && password != "" && password.match(passwordformat)){
			$("#pswd").removeClass("error");
		  	$("#pswd-label").html("<b>Password</b>");
		}
		else if(repassword == null || repassword == ""){
			$("#re-psw").addClass("error");
		  	$("#re-psw-label").html("<b>Password</b> - non può essere vuoto");
		  	event.preventDefault();
		}
		else if(!(repassword == password)){
			$("#pswd").addClass("error");
		  	$("#pswd-label").html("<b>Password</b> - le password non coincidono");
		  	$("#re-psw").addClass("error");
		  	$("#re-psw-label").html("<b>Reinserisci Password</b> - le password non coincidono");
		  	event.preventDefault();
		}
		else{
			$("#pswd").removeClass("error");
		  	$("#pswd-label").html("<b>Password</b>");
		  	$("#re-psw").removeClass("error");
		  	$("#re-psw-label").html("<b>Password</b>");
		}
		if(name == null || name == ""){
			$("#nome").addClass("error");
		  	$("#nome-label").html("<b>Nome</b> - non può essere vuoto");
		  	event.preventDefault();
		}
		else{
			$("#nome").removeClass("error");
		  	$("#nome-label").html("<b>Nome</b>");
		}
		if(surname == null || surname == ""){
			$("#surname").addClass("error");
		  	$("#surname-label").html("<b>Cognome</b> - non può essere vuoto");
		  	event.preventDefault();
		}
		else{
			$("#surname").removeClass("error");
		  	$("#surname-label").html("<b>Cognome</b>");
		}
		if(birth == null || birth == ""){
			$("#birth").addClass("error");
		  	$("#birth-label").html("<b>Data di Nascita</b> - non può essere vuota");
		  	event.preventDefault();
		}
		else{
			$("#birth").removeClass("error");
		  	$("#birth-label").html("<b>Data di Nascita</b>");
		}
		if(!phone.match(numeroformat)){
			$("#phone").addClass("error");
		  	$("#phone-label").html("<b>Numero di Telefono</b> - inserire una numero valido");
		  	event.preventDefault();
		}
		else{
			$("#phone").removeClass("error");
		  	$("#phone-label").html("<b>Numero di Telefono</b>");
		}
		if (provincia == null || provincia == ""){
			$("#provincia").addClass("error");
		  	$("#provincia-label").html("<b>Provincia</b> - non può essere vuota");
		  	event.preventDefault();
		}
		else{
			$("#provincia").removeClass("error");
		  	$("#provincia-label").html("<b>Provincia</b>");
		}
		if (comune == null || comune == ""){
			$("#comune").addClass("error");
		  	$("#comune-label").html("<b>Comune</b> - non può essere vuoto");
		  	event.preventDefault();
		}
		else{
			$("#comune").removeClass("error");
		  	$("#comune-label").html("<b>Comune</b>");
		}
		if (via == null || via == ""){
			$("#via").addClass("error");
		  	$("#via-label").html("<b>Via</b> - non può essere vuota");
		  	event.preventDefault();
		}
		else{
			$("#via").removeClass("error");
		  	$("#via-label").html("<b>Via</b>");
		}
		if (civico == null || civico == ""){
			$("#civico").addClass("error");
		  	$("#civico-label").html("<b>Civico</b> - non può essere vuoto");
		  	event.preventDefault();
		}
		else{
			$("#civico").removeClass("error");
		  	$("#civico-label").html("<b>Via</b>");
		}
		if (cap == null || cap == ""){
			$("#cap").addClass("error");
		  	$("#cap-label").html("<b>CAP</b> - non può essere vuoto");
		  	event.preventDefault();
		}
		else{
			$("#cap").removeClass("error");
		  	$("#cap-label").html("<b>CAP</b>");
		}
	});
});
</script>
</body>
</html>