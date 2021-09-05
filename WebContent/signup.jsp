<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Registrazione</title>
</head>
<body>
	 <form action="<%= response.encodeURL("signup")%>" style="border:1px solid #ccc">
  <div class="container">
    <h1>Registrazione</h1>
    <p>Please fill in this form to create an account.</p>
    <hr>

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

    <label for="address"><b>Indirizzo</b></label>
    <input type="text" placeholder="Inserisci Indirizzo" name="birth" required>
    


    <p>By creating an account you agree to our <a href="#" style="color:dodgerblue">Terms & Privacy</a>.</p>

    <div class="clearfix">
      <button type="submit" class="signupbtn">Sign Up</button>
    </div>
  </div>
</form> 
</body>
</html>