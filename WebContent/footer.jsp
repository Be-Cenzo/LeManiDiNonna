
	<footer>
		<div>
			Le Mani di Nonna
		</div>
		<span>
			<a href="https://www.facebook.com/lemanidinonna">
				<img src="./icon/facebook.svg" alt="facebook">
			</a>
		</span>
		<span>
			<a href="https://www.instagram.com/lemanidinonna/">
				<img src="./icon/instagram.svg" alt="instagram">
			</a>
		</span>
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
	 	  	$("#name-label").html("<b>Username</b> - non pu� essere vuoto");
	 	  	event.preventDefault();
	 	}else if(!name.match(mailformat)){
	 	  	$("#name-label").html("<b>Username</b> - email non valida");
	 		event.preventDefault();
	 	}else if(password.length == 0){  
	 		$("#psw-label").html("<b>Password</b> - non pu� essere vuota");
	 		event.preventDefault();
	 	  }  
	 	});
 	}
 });

</script>