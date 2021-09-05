<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, model.*, javax.sql.DataSource"%>
<!DOCTYPE html>
<html>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
<link href="./style/signup.css" rel="stylesheet">

<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta charset="UTF-8">
<title>Registrazione</title>
</head>
<body>
<div class="wrapper">
	
	<%@ include file="header.jsp" %>
	
	<div class="contenuto">
		<div class="signup-container">
			<form class="signup-form" action="<%= response.encodeURL("./Signup")%>" method="post">
				<div class="row riga">
				    <div class="titolo">Registrazione</div>
				
				    <label for="email"><b>Email</b></label>
				    <input type="text" placeholder="Inserisci Email" name="email" required>
				
				    <label for="psw"><b>Password</b></label>
				    <input type="password" placeholder="Inserisci Password" name="psw" required>
				
				    <label for="psw-repeat"><b>Reinserisci Password</b></label>
				    <input type="password" placeholder="Reinserisci Password" name="psw-repeat" required>
				    
				    <label for="name"><b>Nome</b></label>
				    <input type="text" placeholder="Inserisci Nome" name="name" required>
				
				    <label for="surname"><b>Cognome</b></label>
				    <input type="text" placeholder="Inserisci Cognome" name="surname" required>
				
				    <label for="birth"><b>Inserisci la tua data di nascita</b></label>
				    <input type="date" name="birth" required>
				    
				    <label for="ig"><b>Nome Instagram</b></label>
				    <input type="text" placeholder="Inserisci Nome Instagram" name="ig" required>
				
				    <label for="phone"><b>Numero di telefono</b></label>
				    <input type="text" placeholder="Inserisci Numero Di Telefono" name="phone" required>
				    
				    <label for="provincia"><b>Provincia</b></label>
				    <input type="text" placeholder="Inserisci Provincia" name="provincia" required>
					
				    <label for="comune"><b>Comune</b></label>
				    <input type="text" placeholder="Inserisci Comune" name="comune" required>
				
				    <label for="via"><b>Via</b></label>
				    <input type="text" placeholder="Inserisci Via" name="via" required>
				
				    <label for="civico"><b>Civico</b></label>
				    <input type="number" name="civico" required>
				
				    <label for="cap"><b>CAP</b></label>
				    <input type="text" placeholder="Inserisci CAP" name="cap" required>
			
				    <button type="submit" class="signup-btn">Sign Up</button>
			    </div>
			</form>
		</div>
  	</div>
	
	<%@ include file="footer.jsp" %>
</div>
</body>
</html>