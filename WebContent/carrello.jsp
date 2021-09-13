<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, model.*, javax.sql.DataSource"%>
<!DOCTYPE html>
<html>
<head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
<link href="./style/carrello.css" rel="stylesheet">
<%@ include file="headlink.jsp" %>

<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta charset="UTF-8">
<title>Il tuo Carrello</title>
</head>
<body>

<div class="wrapper">
	<%@ include file="header.jsp" %>
	
	<div class="contenuto">
	
	<div class="titolo">Carrello</div>
	<%
		ArrayList<Integer> nonDisponibili = (ArrayList<Integer>)request.getAttribute("nonDisponibili");
		Carrello cart = (Carrello)session.getAttribute("carrello");
		Float totale = 0f;
		if(cart!=null && !cart.isEmpty()){
		for(int prodID : cart.getProdotti().keySet()){
			Prodotto prod = cart.getProdotti().get(prodID);
			totale += prod.getQuantità()*prod.getPrezzo();
		%>
			
		<div class="row product-row">
			<div class="product-img">
				<img src="./getPicture?id=<%=prod.getCodice()%>" alt="esempio">
			</div>
			<div class="product-container">
				<div class="product-description">
					<a href="<%=response.encodeURL("ProdottoPage?id=" + prod.getCodice())%>">
							<%= prod.getDescrizione() %>
						</a>
					<a href="<%=response.encodeURL("./RimuoviDalCarrello?action=remove&id=" + prod.getCodice())%>">
						<span id="deleteIcon">
							<img src="./icon/trash.svg" alt="edit"/>
						</span>
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
					<%if(nonDisponibili != null && nonDisponibili.contains(prod.getCodice())){ %>
					<div class="product-not-available" id="price<%=prod.getCodice()%>">
						Prodotto non disponibile
					</div>
					<%} %>
				</div>
			</div>
		</div>
	<%	
		}%>
		<div class="row justify-content-center">
				Totale: <%=totale %>€
		</div>
		<div class="row justify-content-center">
			<button class="carrello-button">
				<a href="<%=response.encodeURL("./RimuoviDalCarrello?action=all")%>">
					Svuota Carrello
				</a>
			</button>
			<%if(nonDisponibili == null){ %>
			<button class="carrello-button">
				<a href="<%=response.encodeURL("./Pagamento")%>">
					Prosegui al Pagamento
				</a>
			</button>
			<%}%>
		</div>
		<%
		}
		else{
			%>
			<div class="row justify-content-center">
				Non c'è niente qui, corri ad acquistare qualcosa!
			</div>
			<%
		}
	%>
	
	</div>
	
	<%@ include file="footer.jsp" %>
</div>


</body>
</html>