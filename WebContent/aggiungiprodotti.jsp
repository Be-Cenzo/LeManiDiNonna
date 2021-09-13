<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="javax.sql.DataSource, java.util.ArrayList, model.*"%>
<!DOCTYPE html>
<html>
<head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
<%@ include file="headlink.jsp" %>
<link href="./style/aggiungiprodotti.css" rel="stylesheet">

<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta charset="UTF-8">
<title>Aggiungi prodotti</title>
</head>
<body>
<div class="wrapper">
	<%@ include file="header.jsp" %>
	<% 
		String role1 = (String)session.getAttribute("role");
		if(role1 == null || role1.equals("guest")){
			response.sendRedirect(response.encodeURL("./accessdenied.jsp"));
		}
	%> 
	<div class="contenuto">
		<div class="add-container">
			<form class="add-form" action="<%= response.encodeURL("UploadPhoto")%>" enctype="multipart/form-data" method="post">
				
				<div class="titolo">Aggiungi un Prodotto</div>
				<label for="talkPhoto"><b>Carica una foto: </b></label>
				<input class="file" type="file" name="talkPhoto" value="" maxlength="255">	
				
				
				<label id="tipo-label" for="tipo"><b>Tipo</b></label>
				<input id="tipo" type="text" placeholder="Inserisci Tipo" name="tipo">
		
				<label id="prezzo-label" for="prezzo"><b>Prezzo</b></label>
				<input id="prezzo" type="number" name="prezzo">
						
				<label id="colore-label" for="colore"><b>Colore</b></label>
				<input id="colore" type="text" placeholder="Inserisci Colore" name="colore">
						    
				<label id="desc-label" for="desc"><b>Descrizione</b></label>
				<input id="desc" type="text" placeholder="Inserisci Descrizione" name="desc">
						
				<label id="marca-label" for="marca"><b>Marca</b></label>
				<input id="marca" type="text" placeholder="Inserisci Marca" name="marca">
						
				<label id="modello-label" for="modello"><b>Modello</b></label>
				<input id="modello" type="text" placeholder="Inserisci Modello" name="modello" >
				
				<div class="radio-container">   
					<label for="taglia"><b>Taglia: </b></label>
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
						
				<label id="quant" for="quant"><b>Quantità</b></label>
				<input id="quant-input" type="number" name="quant">
						    
				<div class="deposito"  id="depositi">
					<div class="titolo">Seleziona un Deposito</div>
					
				</div>
				<input class="add-btn" type="submit" value="Upload">
				<input class="add-btn" type="reset">
			</form>
		</div>
	</div>
	
	
	<%@ include file="footer.jsp" %>
</div>

<script>
$(document).ready(function(){
	$.ajax({
		url: "GetDepositi",
		method: "GET"})
	.done(function (json){
		var checked = "checked";
		for(var i = 0; i<json.length; i++){
			if(i > 0)
				checked = "";
			$("#depositi").append("<div class='radio-element'><input type='radio' name='deposito' id='"+ json[i].ID +"' value='"+ json[i].luogo +"'" + checked + "><label for='" + json[i].ID +"'>" + json[i].luogo + "</label></div>");
		};
	});
});

</script>

</body>
</html>