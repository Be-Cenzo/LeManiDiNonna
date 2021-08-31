<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, model.*, javax.sql.DataSource"%>
    
   <%! String link = "GestisciCarrello"; %>
<!DOCTYPE html>
<html>
<head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
<link href="./style/carrello.css" rel="stylesheet">

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
		Carrello cart = (Carrello)session.getAttribute("carrello");
				if(cart!=null && !cart.isEmpty()){
				for(int prodID : cart.getProdotti().keySet()){
				Prodotto prod = cart.getProdotti().get(prodID);
		%>
			
		<div class="row product-row">
			<div class="product-img">
				<img src="./img/<%=prod.getImgurl() %>" alt="esempio">
			</div>
			<div class="product-container">
				<div class="product-description">
					<%=prod.getDescrizione()%>
					<a href="<%=response.encodeURL("./RimuoviDalCarrello?action=remove&id=" + prod.getCodice())%>">
						<span id="deleteIcon">
							<img src="./icon/trash.svg" alt="edit"/>
						</span>
					</a>
				</div>
				<div class="product-details">
					<div class="product-quantity" id="quantity<%=prod.getCodice()%>">
						<%=prod.getQuantità()%>
					</div>
					<div class="product-price" id="price<%=prod.getCodice()%>">
						<%=prod.getQuantità()*prod.getPrezzo() %>€
					</div>
				</div>
			</div>
		</div>
	<%	
		}%>
		<div class="row justify-content-center">
			<button class="svuota-carrello">
				<a href="<%=response.encodeURL("./RimuoviDalCarrello?action=all")%>">
					Svuota Carrello
				</a>
			</button>
		</div>
		<%
		}
		else{
			%>
			<div class="row justify-content-center">
				Non c'è niente qui.
			</div>
			<%
		}
	%>
	
	</div>
	
	<%@ include file="footer.jsp" %>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-U1DAWAznBHeqEIlVSCgzq+c9gqGAJn5c/t99JyeKa9xxaYpSvHU5awsuZVVFIhvj" crossorigin="anonymous"></script>
</body>
</html>