<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, model.*, javax.sql.DataSource"%>
<!DOCTYPE html>
<html>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
<link href="./style/pagamento.css" rel="stylesheet">

<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta charset="UTF-8">
<title>Il tuo Ordine</title>
</head>
<body>
<div class="wrapper">
	<%@ include file="header.jsp" %>
	
	<div class="contenuto">
		<h3>Il tuo Ordine</h1>
		<div class="contenitore-flex">
			<div class="carrello">
				<div class="titolo">Riepilogo</div>
			<%
			Carrello cart = (Carrello)session.getAttribute("carrello");
			Float totale = 0f;
			if(cart!=null && !cart.isEmpty()){
				for(int prodID : cart.getProdotti().keySet()){
					Prodotto prod = cart.getProdotti().get(prodID);
					totale += prod.getQuantità()*prod.getPrezzo();
					%>
						
					<div class="row product-row">
						<div class="product-img">
							<img src="./img/<%=prod.getImgurl() %>" alt="esempio">
						</div>
						<div class="product-container">
							<div class="product-description">
								<a href="<%=response.encodeURL("ProdottoPage?id=" + prod.getCodice())%>">
										<%= prod.getDescrizione() %>
									</a>
							</div>
							<div class="product-details">
							<%if(prod.getTaglia() != null && !prod.getTaglia().equals("N")){ %>
								<div class="product-size" id="size<%=prod.getCodice()%>">
									Taglia: <%=prod.getTaglia() %>
								</div>
							<%} %>
								<div class="product-quantity" id="quantity<%=prod.getCodice()%>">
									Quantità: <%=prod.getQuantità()%>
								</div>
								<div class="product-price" id="price<%=prod.getCodice()%>">
									<%=prod.getQuantità()*prod.getPrezzo() %>€
								</div>
							</div>
						</div>
					</div>
					<% }} %>
				<div class="row justify-content-center">
					Totale: <%=totale %>€
				</div>
			</div>
			<div class="informazioni">
				<form action="<%=response.encodeURL("./CreaOrdine")%>" method="post">
				<div class="indirizzi" id="indirizzi">
					<div class="titolo">Seleziona un Indirizzo</div>
					<%
						Account user = (Account)session.getAttribute("user");
						if(user != null && !user.getIndirizzi().isEmpty()){
							for(Indirizzo ind : user.getIndirizzi()){
								%>
								
								<div class="row indirizzo-row justify-content-center">
									<input type="radio" name="idIndirizzo" value="<%=ind.getID() %>" checked>
									<label for="indirizzo">Via <%=ind.getVia() %>, <%=ind.getCivico() %>, <%=ind.getComune() %>, <%=ind.getCAP() %>, <%=ind.getProvincia() %></label>
								</div>
								<%
							}
						}
					%>
					<div class="carrello-button" onclick="showAndHide('#indirizzi', '#corrieri')">Prosegui</div>
				</div>
				<div class="corrieri" id="corrieri">
					<div class="titolo">Seleziona un Corriere</div>
					<%
						ArrayList<Corriere> corrieri = (ArrayList<Corriere>)session.getAttribute("corrieri");
						if(corrieri != null && !corrieri.isEmpty()){
							for(Corriere corr : corrieri){
								%>
								<div class="row indirizzo-row justify-content-center">
									<input type="radio" name="corriere" value="<%=corr.getNome() %>" checked>
									<div style="display:grid">
										<label for="corriere"><%=corr.getNome() %></label>
										<label for="corriere">Prezzo: <%=corr.getPrezzo()%>€</label>
										<label for="corriere">Tempo: <%=corr.getTempo() %></label>
									</div>
								</div>
								<%
							}
						}
					%>
					<div class="row justify-content-center">
						<div class="carrello-button" onclick="showAndHide('#corrieri', '#indirizzi')">Indietro</div>
						<div class="carrello-button" onclick="showAndHide('#corrieri', '#paga')">Prosegui</div>
					</div>
				</div>
				<div class="paga" id="paga">
					<div class="titolo">Inserisci i Dati di Pagamento</div>
					<div class="row indirizzo-row justify-content-center">
						<label>Nome Titolare</label>
						<input type="text" name="indirizzo">
					</div>
					<div class="row indirizzo-row justify-content-center">
						<label>Numero di carta</label>
						<input type="text" name="indirizzo">
					</div>
					<div class="row indirizzo-row justify-content-center">
						<label>CVV</label>
						<input type="text" name="indirizzo">
					</div>
					<div class="row justify-content-center">
						<div class="carrello-button" onclick="showAndHide('#paga', '#corrieri')">Indietro</div>
						<button class="carrello-button" onclick="showAndHide('#paga', '#paga')" type="submit">Paga</button>
					</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<%@ include file="footer.jsp" %>
</div>


<script>
function showAndHide(hide, show){
	$(hide).animate({height: "toggle"}, 400, "swing", $(show).animate({height: "toggle"}));
}
</script>
</body>
</html>