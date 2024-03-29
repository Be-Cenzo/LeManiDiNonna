<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,catalogoManagement.*, javax.sql.DataSource, acquistoManagement.*, utenteManagement.*"%>
<!DOCTYPE html>
<html>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
<link href="./style/pagamento.css" rel="stylesheet">
<%@ include file="headlink.jsp" %>

<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta charset="UTF-8">
<title>Il tuo Ordine</title>
</head>
<body>
<div class="wrapper">
	<% 
		String role1 = (String)session.getAttribute("role");
		if(role1 == null || role1.equals("guest")){
			response.sendRedirect(response.encodeURL("./accessdenied.jsp"));
		}
	%> 
	
	<%@ include file="header.jsp" %>
	
	<div class="contenuto">
		<h3>Il tuo Ordine</h3>
		<div class="contenitore-flex">
			<div class="carrello">
				<div class="titolo">Riepilogo</div>
			<%
			Carrello cart = (Carrello)session.getAttribute("carrello");
				Float totale = 0f;
				if(cart!=null && !cart.isEmpty()){
					for(Prodotto prod : cart.getProdotti()){
						totale += prod.getQuantita()*prod.getPrezzo();
			%>
						
					<div class="row product-row">
						<div class="product-img">
							<img src="./getPicture?id=<%=prod.getCodice()%>" alt="esempio">
						</div>
						<div class="product-container">
							<div class="product-description">
								<a href="<%=response.encodeURL("ProdottoPage?id=" + prod.getCodice())%>">
										<%=prod.getDescrizione()%>
									</a>
							</div>
							<div class="product-details">
							<%
							if(prod.getTaglia() != null && !prod.getTaglia().equals("N")){
							%>
								<div class="product-size" id="size<%=prod.getCodice()%>">
									Taglia: <%=prod.getTaglia()%>
								</div>
							<%
							}
							%>
								<div class="product-quantity" id="quantity<%=prod.getCodice()%>">
									Quantità: <%=prod.getQuantita()%>
								</div>
								<div class="product-price" id="price<%=prod.getCodice()%>">
									<%=prod.getQuantita()*prod.getPrezzo()%>€
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
				<form id="paga-form" action="<%=response.encodeURL("./CreaOrdine")%>" method="post">
				<div class="indirizzi" id="indirizzi">
					<div class="titolo-informazioni">Seleziona un Indirizzo</div>
					<%
						Account user = (Account)session.getAttribute("user");
						if(user != null && !user.getIndirizzi().isEmpty()){
							for(Indirizzo ind : user.getIndirizzi()){
								%>
								
								<div class="row indirizzo-row justify-content-center">
									<input type="radio" name="idIndirizzo" value="<%=ind.getID() %>" <%if(user.getIndirizzi().indexOf(ind)==0){ %>checked<%} %>>
									<label for="indirizzo">Via <%=ind.getVia() %>, <%=ind.getCivico() %>, <%=ind.getComune() %>, <%=ind.getCAP() %>, <%=ind.getProvincia() %></label>
								</div>
								<%
							}
						}
					%>
					<div class="carrello-button" onclick="showAndHide('#indirizzi', '#corrieri')">Prosegui</div>
				</div>
				<div class="corrieri" id="corrieri">
					<div class="titolo-informazioni">Seleziona un Corriere</div>
					<%
					ArrayList<Spedizione> corrieri = (ArrayList<Spedizione>)session.getAttribute("corrieri");
									if(corrieri != null && !corrieri.isEmpty()){
										for(Spedizione corr : corrieri){
					%>
								<div class="row indirizzo-row justify-content-center">
									<input type="radio" name="corriere" value="<%=corr.getNome() %>" <%if(corrieri.indexOf(corr)==0){ %>checked<%} %>>
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
					<div class="titolo-informazioni">Inserisci i Dati di Pagamento</div>
					<div class="row indirizzo-row justify-content-center">
						<label>Nome Titolare</label>
						<input id="nome-carta" type="text" placeholder="Nome del titolare" name="nome-carta">
					</div>
					<div class="row indirizzo-row justify-content-center">
						<label>Numero di carta</label>
						<input id="numero-carta" type="text" placeholder="Numero di carta" name="numero-carta">
					</div>
					<div class="row indirizzo-row justify-content-center">
						<label>CVV</label>
						<input id="cvv" type="text" placeholder="CVV" name="cvv">
					</div>
					<div class="row justify-content-center">
						<div class="carrello-button" onclick="showAndHide('#paga', '#corrieri')">Indietro</div>
						<button class="carrello-button" id="paga-btn" type="submit">Paga</button>
					</div>
				</div>
				</form>
			</div>
		</div>
	</div>
	
	<%@ include file="footer.jsp" %>
</div>


<script>
function showAndHide(hide, show){
	$(hide).animate({height: "toggle"}, 400, "swing", $(show).animate({height: "toggle"}));
}

$(document).ready(function(){
	const paga = document.getElementById('paga-form');
	paga.addEventListener('submit', function(event){
		var pattern = /^[a-zA-Z]+( [a-zA-Z]+)*$/;
		var patternCard = /^\d{16}$/;
		var patternCVV = /^\d{3}$/
		if(!$("#nome-carta").val().match(pattern)){
			$("#nome-carta").addClass("error");
			event.preventDefault();
		}
		else{
			$("#nome-carta").removeClass("error");
		}
		if(!$("#numero-carta").val().match(patternCard)){
			$("#numero-carta").addClass("error");
			event.preventDefault();
		}else{
			$("#numero-carta").removeClass("error");
		}
		if(!$("#cvv").val().match(patternCVV)){
			$("#cvv").addClass("error");
			event.preventDefault();
		}else{
			$("#cvv").removeClass("error");
		}
		
	});
});

const validate = function(event){
		event.preventDefault();
}
</script>
</body>
</html>