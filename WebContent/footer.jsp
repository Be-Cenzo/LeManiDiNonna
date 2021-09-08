<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link href="./style/footer.css" rel="stylesheet">
</head>
<body>
	<footer>
		L'hanno fatto Becienz e Christian
	</footer>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-U1DAWAznBHeqEIlVSCgzq+c9gqGAJn5c/t99JyeKa9xxaYpSvHU5awsuZVVFIhvj" crossorigin="anonymous"></script>
<script>  

$(document).ready(function(){
 	const login = document.getElementById('form-login');
 	if(login != null){
	 	login.addEventListener('submit', function(event){
	 	var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
	 	var name=$("#name").val();  
	 	var password=$("#psw").val();
	 	if (name==null || name==""){  
	 	  	$("#name-label").html("<b>Username</b> - non può essere vuoto");
	 	  	event.preventDefault();
	 	}else if(!name.match(mailformat)){
	 	  	$("#name-label").html("<b>Username</b> - email non valida");
	 		event.preventDefault();
	 	}else if(password.length == 0){  
	 		$("#psw-label").html("<b>Password</b> - non può essere vuota");
	 		event.preventDefault();
	 	  }  
	 	});
 	}
 });

</script> 
</body>
</html>