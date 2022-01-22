<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,catalogoManagement.*"%>
    
<%
	Prodotto prodotto = (Prodotto) request.getAttribute("prodotto-page");
	if(prodotto == null){
		response.sendRedirect(response.encodeRedirectURL("./ProdottoPage"));
		return;
	}
%>
<!DOCTYPE html>
<html>
<head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
<link href="./style/prodotto.css" rel="stylesheet">
<%@ include file="headlink.jsp" %>

<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta charset="UTF-8">
<title><%=prodotto.getDescrizione() %></title>
</head>
<body>
<div class="wrapper">
	<%@ include file="header.jsp" %>
	
	<div class="contenuto">
		<div class="row justify-content-center">
			<div class="prodotto-container">
				<div class="prodotto-img">
					<div class="prodotto-img-container">
						<img src="./getPicture?id=<%=prodotto.getCodice()%>">
					</div>
				</div>
				<div class="prodotto-details">
					<div class="prodotto-description">
						<%=prodotto.getDescrizione() %>
					</div>
					<div class="card-add-cart">
							<label>
								Prezzo: <%= prodotto.getPrezzo() %>€
							</label>
						<form action="<%=response.encodeURL("AggiungiAlCarrello")%>" method="GET">
						<%
							if(prodotto.getTaglia() == null){ %>
							<div class="radio-container">
								<label for="taglia">Taglia:</label>
								<div class="radio">
									<div class="radio-element">
										<input class="radio-button" type="radio" id="s" name="taglia" value="S" checked>
										<label for="s">S</label>
									</div>
									<div class="radio-element">
										<input class="radio-button" type="radio" id="m" name="taglia" value="M">
										<label for="m">M</label>
									</div>
									<div class="radio-element"> 
										<input class="radio-button" type="radio" id="l" name="taglia" value="L">
										<label for="l">L</label>
									</div>
								</div>
							</div>
						<%} %>
							<div>
								<label for="quantità">Quantità: </label>
								<input class="numberForm" type="number" name="quantità" value="1" min="1">
							</div>
							<input type="hidden" name="id" value=<%=prodotto.getCodice() %>>
							<input type="hidden" name="action" value="add">
							<button id="addCartIcon" type="submit">
								Aggiungi al carrello <img src="./icon/aggiungi-al-carrello.svg" alt="aggiungi"/>
							</button>
						</form>
						
					</div>
				</div>
			</div>
		</div>
	</div>
	
	
	
	
	<%@ include file="footer.jsp" %>
</div>


</body>
</html>