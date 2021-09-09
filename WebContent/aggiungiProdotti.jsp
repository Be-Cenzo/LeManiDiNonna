<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="javax.sql.DataSource, java.util.ArrayList, model.*"%>
<!DOCTYPE html>
<html>
<head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">

<%@ include file="headlink.jsp" %>
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta charset="UTF-8">
<title>Aggiungi prodotti</title>
</head>
<body>
	<%@ include file="header.jsp" %>
	<% 
		String role1 = (String)session.getAttribute("role");
		if(role1 == null || role1.equals("guest")){
			response.sendRedirect(response.encodeURL("./accessdenied.jsp"));
		}
	%> 
	
	<form action="<%= response.encodeURL("UploadPhoto")%>" enctype="multipart/form-data" method="post">
		<br>
		<input class="file" type="file" name="talkPhoto" value="" maxlength="255">	
		<br>		
		<input type="submit" value="Upload"><input type="reset">
		
		
		<label id="tipo-label" for="tipo"><b>Tipo</b></label>
		<input id="tipo" type="text" placeholder="Inserisci Tipo" name="tipo">

		<label id="prezzo-label" for="prezzo"><b>Prezzo</b></label>
		<input id="prezzo" type="number" name="prezzo">
				
		<label id="colore-label" for="colore"><b>Colore</b></label>
		<input id="colore" type="text" placeholder="Reinserisci Colore" name="colore">
				    
		<label id="desc-label" for="desc"><b>Descrizione</b></label>
		<input id="desc" type="text" placeholder="Inserisci Descrizione" name="desc">
				
		<label id="marca-label" for="marca"><b>Marca</b></label>
		<input id="marca" type="text" placeholder="Inserisci Marca" name="marca">
				
		<label id="modello-label" for="modello"><b>Modello</b></label>
		<input id="modello" type="text" placeholder="Inserisci Modello" name="modello" >
				    
		<label for="taglia" style="width:100%">Taglia: </label>
		<input class="radio-button" type="radio" id="s" name="taglia" value="S" checked>
		<label for="s">S</label>
		<input class="radio-button" type="radio" id="m" name="taglia" value="M">
		<label for="m">M</label>
		<input class="radio-button" type="radio" id="l" name="taglia" value="L">
		<label for="l">L</label>
				
		<label id="quant" for="quant"><b>Quantità</b></label>
		<input id="quant" type="number"  name="quant">
				    
		<div class="deposito" id="deposito">
			<div class="titolo">Seleziona un Deposito</div>
			<%
			DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
			DepositoModelDS model = new DepositoModelDS(ds);
			ArrayList<Deposito> dep = (ArrayList<Deposito>)model.doRetrieveAll(null);
			if(dep != null && !dep.isEmpty()){
				for(Deposito d : dep){
					%>
					<div class="row indirizzo-row justify-content-center">
						<input type="radio" name="deposito" id="<%=d.getID() %> "value="<%=d.getID() %>" checked>
						<label for="<%=d.getID() %>"><%=d.getLuogo() %></label>
					</div>
					<%
					}
				}
			%>
	</form>
	
	
	
	<%@ include file="footer.jsp" %>
</body>
</html>