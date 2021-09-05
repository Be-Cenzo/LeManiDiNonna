<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, model.*, javax.sql.DataSource"%>
<!DOCTYPE html>
<html>
<head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
<link href="./style/login.css" rel="stylesheet">

<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta charset="UTF-8">
<title>Login</title>
</head>
<body>
<div class="wrapper">
	<%@ include file="header.jsp" %>
	
	<div class="contenuto">
		<div class="login-container-page">
		    <form class="login-form-page" action="login" method="post">
		    	<div class="row">
		    		<div class="titolo">Effettua il Login:</div>
				    <label for="uname"><b>Username</b></label>
				    <input type="text" placeholder="Enter Username" id="name" name="uname">
				
				    <label for="psw"><b>Password</b></label>
				    <input type="password" placeholder="Enter Password" id="psw" name="psw">
				
			      	<button class="login-btn" type="submit" onclick="validateform()">Login</button>
		      	</div>
		  	</form>
	    </div>
	</div>
	
	<%@ include file="footer.jsp" %>
</div>

<script type="text/javascript">  
function validateform(){  
var name=$("#name").val();  
var password=$("#psw").val();
  console.log("pisello");
if (name==null || name==""){  
  alert("Name can't be blank");  
  return false;  
}else if(password.length<6){  
  alert("Password must be at least 6 characters long.");  
  return false;  
  }  
}  

function ValidateEmail(inputText)
{
var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
if(inputText.value.match(mailformat))
{
document.form1.text1.focus();
return true;
}
else
{
alert("You have entered an invalid email address!");
document.form1.text1.focus();
return false;
}
}
</script>  
</body>
</html>