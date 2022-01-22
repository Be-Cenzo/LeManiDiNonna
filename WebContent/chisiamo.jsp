<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="javax.sql.DataSource, java.util.ArrayList,catalogoManagement.*"%>
<!DOCTYPE html>
<html>
<head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
<%@ include file="headlink.jsp" %>
<link href="./style/chisiamo.css" rel="stylesheet">

<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta charset="UTF-8">
<title>Chi Siamo</title>
</head>
<body>
<div class="wrapper">
	<%@ include file="header.jsp" %>
	
	<div class="contenuto">
		<div class="image-icon">
			<div class="box left">
				<div id="carouselControls" class="carousel slide" data-bs-ride="carousel">
				  <div class="carousel-inner">
				    <div class="carousel-item active">
				      <img class="d-block w-100" src="./img/acquario.jpg" alt="acquario maglia">
				    </div>
				    <div class="carousel-item">
				      <img class="d-block w-100" src="./img/cloud-cap.jpg" alt="cappellino nuvola">
				    </div>
				    <div class="carousel-item">
				      <img class="d-block w-100" src="./img/peas-shopper.jpg" alt="peas shopper">
				    </div>
				  </div>
				  <button class="carousel-control-prev" type="button" data-bs-target="#carouselControls" data-bs-slide="prev">
				    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
				    <span class="visually-hidden">Previous</span>
				  </button>
				  <button class="carousel-control-next" type="button" data-bs-target="#carouselControls" data-bs-slide="next">
				    <span class="carousel-control-next-icon" aria-hidden="true"></span>
				    <span class="visually-hidden">Next</span>
				  </button>
				</div>
				<div class="text">
					I nostri prodotti sono completamente cuciti con amore e cura dalle mani di nonna
				</div>
				<img id="nonna" src="./icon/grandmother.png" alt="nonna">
			</div>
		</div>
			
		<div class="image-icon">
			<div class="box right">
				<img id="magazine" src="./icon/magazine.png" alt="magazine">
				<div class="text">
					Consulta il nostro catalogo di&nbsp;<a href="<%=response.encodeURL("./prodotti.jsp")%>"> Prodotti</a>
				</div>
				<div id="carouselControlsTwo" class="carousel slide" data-bs-ride="carousel">
				  <div class="carousel-inner">
				    <div class="carousel-item active">
				      <img class="d-block w-100" src="./img/renna-borsello.jpg" alt="renna">
				    </div>
				    <div class="carousel-item">
				      <img class="d-block w-100" src="./img/mcdonald.jpg" alt="mcdonald">
				    </div>
				    <div class="carousel-item">
				      <img class="d-block w-100" src="./img/dogs-grembiule.jpg" alt="dogs">
				    </div>
				  </div>
				  <button class="carousel-control-prev" type="button" data-bs-target="#carouselControlsTwo" data-bs-slide="prev">
				    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
				    <span class="visually-hidden">Previous</span>
				  </button>
				  <button class="carousel-control-next" type="button" data-bs-target="#carouselControlsTwo" data-bs-slide="next">
				    <span class="carousel-control-next-icon" aria-hidden="true"></span>
				    <span class="visually-hidden">Next</span>
				  </button>
				</div>
			</div>
		</div>
	
	</div>
	
	<%@ include file="footer.jsp" %>
</div>
</body>
</html>