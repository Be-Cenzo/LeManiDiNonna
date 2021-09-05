<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="./style/login.css" rel="stylesheet">
 
 <script type="text/javascript">  
function validateForm(){  
var name=$("#name").val();  
var password=$("#psw").val();
var valide;
if (name==null || name==""){  
  alert("Name can't be blank");  
  document.getElementById("form").addEventListener("submit", function(event){
	  event.preventDefault()
	});
  return false;  
}else if(password.length<6){  
  alert("Password must be at least 6 characters long.");
  document.getElementById("form").addEventListener("submit", function(event){
	  event.preventDefault()
	});
  return false;  
  }  
}  

</script>  

</head>
<body>

<!-- The Modal -->
<div id="log" class="modal">
<div class="close" onclick="$('#log').fadeToggle()"></div>

  <!-- Modal Content -->
    <div class="login-container animate">
	    <form class="login-form" action="<%= response.encodeURL("login")%>" id="form" method="post">
		    <label for="uname"><b>Username</b></label>
		    <input type="text" placeholder="Enter Username" id="name" name="uname">
		
		    <label for="psw"><b>Password</b></label>
		    <input type="password" placeholder="Enter Password" id="psw" name="psw">
		
	      	<button class="login-btn" type="submit" onclick="validateForm()">Login</button>
	  	</form>
	  	
	  	
    </div>
    
    
</div> 

</body>
</html>