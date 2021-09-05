<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
<link href="./style/index.css" rel="stylesheet">

<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta charset="UTF-8">
<title>Le Mani Di Nonna</title>
</head>
<body>
<div class="wrapper">
	<%@ include file="header.jsp" %> 

	<div class="contenuto">
		<div class="row">
			<div class="card" onclick="toggle();">
				<img src="./img/example.jpg" class="card-img-top" alt="esempio">
			</div>
			<div class="collapse" id="collapseExample">
				<div class="card-collapse-due card-body">
					Tutto 10 e' un regalo.
				</div>
			</div>
	
			<div class="card" onclick="toggle('#collapseExample2', 'fadeSpan2');">
				<img src="./img/example.jpg" class="card-img-top" alt="esempio">
				<span class="card-span" id="fadeSpan2">+</span>
				<div class="card-collapse card-body collapse-body" id="collapseExample2">
					<span class="card-span" style="color: black;">-</span>
					Tutto 10 e' un regalo.
				</div>
			</div>
	
			<div class="card" onclick="$('#collapseExample3').animate({width: 'toggle'});">
				<img src="./img/example.jpg" class="card-img-top" alt="esempio">
				<span class="card-span">+</span>
				<div class="collapse" id="collapseExample3">
					<div class="card-collapse-due card-body">
						Tutto 10 e' un regalo.
					</div>
				</div>
			</div>

		</div>
	</div>
	
	
	
	
	<%@ include file="footer.jsp" %> 
</div>
<script>
function toggle(id, span){
	$(id).fadeToggle();
	$(span).fadeToggle();
}

function toggleButton(span){
	if(span.text() === "+"){
		span.html("-");
		span.css("color", "black");
	}
	else{
		span.html("+");
		span.css("color", "white");
	}
}

</script>

</body>
</html>