<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,catalogoManagement.*, javax.sql.DataSource, acquistoManagement.*"%>
<!DOCTYPE html>
<html>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
<link href="./style/ordini.css" rel="stylesheet">
<%@ include file="headlink.jsp" %>

<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta charset="UTF-8">
<title>I Tuoi Ordini</title>
</head>
<body>
<body>
<div class="wrapper">
	<% 
		String role1 = (String)session.getAttribute("role");
		if(role1 == null || role1.equals("guest")){
			response.sendRedirect(response.encodeURL("./accessdenied.jsp"));
			return;
		}
		
		ArrayList<Ordine> ordini = (ArrayList<Ordine>) session.getAttribute("ordini-utente");
		if(ordini == null){
			response.sendRedirect(response.encodeRedirectURL("./OrdiniUtente"));
			return;
		}
	%> 
	
	<%@ include file="header.jsp" %>
	<div class="contenuto">
		<div class="titolo">
			I Tuoi Ordini
		</div>
		<%
			for(Ordine ordine : ordini){
			%>	
				<div class="ordine" id="ordine-<%=ordine.getID()%>" onclick="$('#collapse-ordine-<%=ordine.getID()%>').animate({height: 'toggle'});">
					Ordine del : <%=ordine.getData() %>
					<div class="collapse-ordine" id="collapse-ordine-<%=ordine.getID()%>">
						<div class="dettagli-ordine">
							<%
								for(Prodotto prod : ordine.getProdotti()){
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
												</div>
												<div class="product-details">
												<%if(prod.getTaglia() != null && !prod.getTaglia().equals("N")){ %>
													<div class="product-size" id="size<%=prod.getCodice()%>">
														Taglia: <%=prod.getTaglia() %>
													</div>
												<%} %>
													<div class="product-quantity" id="quantity<%=prod.getCodice()%>">
														Quantità: <%=prod.getQuantita()%>
													</div>
													<div class="product-price" id="price<%=prod.getCodice()%>">
														<%=prod.getQuantita()*prod.getPrezzo()%>€
													</div>
												</div>
											</div>
										</div>
									<%
								}
							%>
							<div class="corriere-row">
								Spedito da: <%=ordine.getCorriere() %> <span class="price-span"><%=ordine.getCostoSped() %>€</span>
							</div>
							<div class="totale-row">
								Costo totale: <span class="price-span"><%=ordine.getPrezzo()+ordine.getCostoSped() %>€</span>
							</div>
						</div>
					</div>
				</div>
			<%	
			}
		if(ordini.size() <= 0){
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