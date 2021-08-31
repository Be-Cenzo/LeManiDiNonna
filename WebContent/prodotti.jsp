<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, model.*, javax.sql.DataSource"%>
<!DOCTYPE html>
<html>
<head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
<link href="./style/index.css" rel="stylesheet">

<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta charset="UTF-8">
<title>Prodotti</title>
</head>
<body>
<div class="wrapper">
	<%@ include file="header.jsp" %>
	
	<div class="contenuto">
		<div class="row">
	<%
	DataSource ds = (DataSource) application.getAttribute("DataSource");
			ProdottoModelDS model = new ProdottoModelDS(ds);
			ArrayList<Prodotto> prodotti = model.doRetrieveAll("");
			int i;
			for(i=0; i<prodotti.size(); i++){
			Prodotto prodotto = prodotti.get(i);
	%>
			<div class="card">
				<img src="./img/aereoplano.jpg" class="card-img-top" onclick="toggle('#collapse<%=i %>', 'fadeSpan<%=i %>');" alt="esempio">
				<span class="card-span" onclick="toggle('#collapse<%=i %>', 'fadeSpan<%=i %>');" id="fadeSpan<%=i %>">+</span>
				<div class="card-collapse card-body collapse-body" id="collapse<%=i %>">
					<span class="card-span" onClick="toggle('#collapse<%=i %>', 'fadeSpan<%=i %>');" style="color: black;">-</span>
					<div class="card-description">
						<%= prodotto.getDescrizione() %>
					</div>
					<div class="card-add-cart">
						<form action="<%=response.encodeURL("AggiungiAlCarrello")%>" method="GET">
							<input class="numberForm" type="number" name="quantità" value="1" min="1">
							<input type="hidden" name="id" value=<%=prodotto.getCodice() %>>
							<input type="hidden" name="action" value="add">
							<button id="addCartIcon" type="submit">
								<img src="./icon/aggiungi-al-carrello.svg" alt="aggiungi"/>
							</button>
							<label>
								<%= prodotto.getPrezzo() %>€
							</label>
						</form>
						
					</div>
				</div>
			</div>
			<%
		}
	%>
		</div>
	</div>
	
	
	
	
	<%@ include file="footer.jsp" %>
</div>
<script>
function toggle(id, span){
	/*var collapse = document.getElementById("collapseExample");
	var sem = 0;
	for(classe in collapse.classList){
		if(classe === "show")
			sem = 1;
	}
	if(sem)
		collapse.classList.remove("show");
	else
		collapse.classList.add("show");*/
	$(id).fadeToggle();
	$(span).fadeToggle();
}

function aggiungiAlCarrello(id, quantità){
	var xhttp = new XMLHttpRequest();
	var url = encodeURI("GestisciCarrello?id="+id+"&action=add&quantità="+quantità);
	xhttp.open("GET", url, true);
	xhttp.send();
}
</script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-U1DAWAznBHeqEIlVSCgzq+c9gqGAJn5c/t99JyeKa9xxaYpSvHU5awsuZVVFIhvj" crossorigin="anonymous"></script>

</body>
</html>