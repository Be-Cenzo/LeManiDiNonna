<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, model.*"%>
    <%
	  
  	ArrayList<Prodotto> prodotti = (ArrayList<Prodotto>) request.getAttribute("prodotti");
	
	if(prodotti == null) {
		response.sendRedirect(response.encodeRedirectURL("./ProdottiControl"));
		return;
	}
    
    %>
<!DOCTYPE html>
<html>
<head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
<link href="./style/prodotti.css" rel="stylesheet">
<%@ include file="headlink.jsp" %>

<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta charset="UTF-8">
<title>Prodotti</title>
</head>
<body>
<div class="wrapper">
	<%@ include file="header.jsp" %>
	
	<div class="contenuto">
		<div class="row justify-content-center">
			<form class="cerca-form" action="<%=response.encodeURL("CercaProdotto")%>" method="GET">
				<input id="cerca-input" type="text" name="cerca" placeholder="cerca">
				<button class="cerca-button" type="submit" id="cerca-btn" onClick="cercaProdotto()" >
					<img src="./icon/search.svg" alt="cerca">
				</button>
			</form>
		</div>
		<div class="row justify-content-center">
			<div class="filter-title">Filtri:</div> 
			<button class="filter-button" id="t-shirt" onClick="addFiltro('t-shirt')" >T-Shirt</button>
			<button class="filter-button" id="felpa" onClick="addFiltro('felpa')" >Felpa</button>
			<button class="filter-button" id="borsello" onClick="addFiltro('borsello')" >Borsello</button>
			<button class="filter-button" id="cappello" onClick="addFiltro('cappello')" >Cappello</button>
			<button class="filter-button" id="grembiule" onClick="addFiltro('grembiule')" >Grembiule</button>
			<button class="filter-button" id="shopper" onClick="addFiltro('shopper')" >Shopper</button>
			<button class="remove-filter" id="remove" onClick="addFiltro('remove')" >
				<img src="./icon/remove.svg" alt="rimuovi filtri">
			</button>
		</div>
		
		<div id="container-prodotti">
		<div class="row" id="prodotti">
	<%
		int i;
		for(i=0; i<prodotti.size(); i++){
		Prodotto prodotto = prodotti.get(i);
	%>
			<div class="card">
				<img src="./getPicture?id=<%=prodotto.getCodice()%>" class="card-img-top" onclick="toggle('#collapse<%=i %>', 'fadeSpan<%=i %>');" alt="<%= prodotto.getDescrizione() %>">
				<span class="card-span" onclick="toggle('#collapse<%=i %>', 'fadeSpan<%=i %>');" id="fadeSpan<%=i %>">+</span>
				<div class="card-collapse card-body collapse-body" id="collapse<%=i %>">
					<span class="card-span" onClick="toggle('#collapse<%=i %>', 'fadeSpan<%=i %>');" style="color: black;">-</span>
					<div class="card-description">
						<a href="<%=response.encodeURL("ProdottoPage?id=" + prodotto.getCodice())%>">
							<%= prodotto.getDescrizione() %>
						</a>
					</div>
					<div class="card-add-cart">
							<p>
								Prezzo: <%= prodotto.getPrezzo() %>€
							</p>
						<form action="<%=response.encodeURL("AggiungiAlCarrello")%>" method="GET">
						<%
							if(prodotto.getTaglia() == null){ %>
							<label for="taglia" style="width:100%">Taglia: </label>
							<input class="radio-button" type="radio" id="s" name="taglia" value="S" checked>
							<label for="s">S</label>
							<input class="radio-button" type="radio" id="m" name="taglia" value="M">
							<label for="m">M</label>
							<input class="radio-button" type="radio" id="l" name="taglia" value="L">
							<label for="l">L</label> 
						<%} %>
							<label for="quantità">Quantità: </label>
							<input class="numberForm" type="number" name="quantità" value="1" min="1">
							<input type="hidden" name="id" value=<%=prodotto.getCodice() %>>
							<input type="hidden" name="action" value="add">
							<button class="addCartIcon" type="submit">
								<img src="./icon/aggiungi-al-carrello.svg" alt="aggiungi"/>
							</button>
						</form>
						
					</div>
				</div>
			</div>
			<%
		}
	%>
		</div>
		</div>
	</div>
	
	
	
	
	<%@ include file="footer.jsp" %>
</div>

<script>

$(document).ready(function(){
	const cerca = document.getElementById('cerca-input');
	cerca.addEventListener('input', cercaProdotto);
});

function toggle(id, span){
	$(id).fadeToggle();
	$(span).fadeToggle();
}

function addFiltro(filtro){
	xhttp = new XMLHttpRequest();
	xhttp.open("GET", "FiltraProdotti?filtro=" + filtro);
	xhttp.send();
	if(filtro == "remove"){
		$(".filter-button").removeClass("selected");
	}
	else
		$("#" + filtro).toggleClass("selected");
	$("#container-prodotti").load("./prodotti.jsp #prodotti");
}

function cercaProdotto(){
	xhttp = new XMLHttpRequest();
	var cerca = $("#cerca-input").val();
	xhttp.open("GET", "CercaProdotto?cerca=" + cerca);
	xhttp.send();
	$("#container-prodotti").load("./prodotti.jsp #prodotti");
}
</script>
</body>
</html>